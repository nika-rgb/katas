import java.util.*;
import java.util.stream.Stream;

public class BookPricing {

    private final int singleBookPrice;
    private final Map <Integer, Double> bookDiscountMap;

    public BookPricing(int singleBookPrice) {
        this.singleBookPrice = singleBookPrice;
        this.bookDiscountMap = constructDiscountMap();
    }

    private Map<Integer, Double> constructDiscountMap() {
        // We can also have a separate class which will actually have method to apply discount to the price
        // Since logic is simple enough, I will use map currently
        Map <Integer, Double> discountMap = new HashMap<>();
        discountMap.put(1, 1.0); // Ideally we would have those books in an enum
        discountMap.put(2, 0.95);
        discountMap.put(3, 0.90);
        discountMap.put(4, 0.80);
        discountMap.put(5, 0.75);
        return discountMap;
    }


    /**
     * GetMinBasketPrice method will get the basket consisting of Harry Potter books and will try to
     * Calculate the minimum price that you can pay assuming that each book costs 8 Dollars and there a couple of rules
     * to get the discount:
     * buy 2 different books - 5% discount
     * buy 3 different books - 10% discount
     * buy 4 different books - 20% discount
     * buy 5 different books - 25% discount
     * @param basket List of books in the basket
     * @return Double corresponding to the price of basket
     */
    public Double getMinBasketPrice(int [] basket) {

        List<Set<Integer>> groups = new ArrayList<>();

        Arrays.stream(basket)
                .forEach(book -> insertIntoSuitableGroup(book, groups));

        return calculateCostOfAllGroups(groups);
    }

    // Sum costs of all generated groups
    private Double calculateCostOfAllGroups(List<Set<Integer>> groups) {
        return groups.
                stream()
                .map(group -> calculateCostForGroup(group.size()))
                .reduce(0.0, Double::sum);
    }

    private void insertIntoSuitableGroup(int book, List<Set<Integer>> groups) {
        List <Integer> suitableGroups = getGroupsMissingBook(book, groups);

        // If all groups already contain the book then it makes sense to create a new group which might give us a separate discount
        if (suitableGroups.isEmpty()) {
            groups.add(createGroupWithBook(book));
            return;
        }

        Integer bestGroup = findBestGroup(suitableGroups, groups);

        groups.get(bestGroup).add(book);
    }

    // Find the group for current book, decision is made based on the cost analysis whichever group is most affected will be chosen
    private Integer findBestGroup(List <Integer> suitableGroups, List<Set<Integer>> groups) {
        return suitableGroups.stream()
                .map(group -> new AbstractMap.SimpleEntry<>(group, groups.get(group).size()))
                .min(new GroupComparator())
                .orElse(new AbstractMap.SimpleEntry<>(-1, -1)) // In case of no suitable groups just return dummy entry, it will never happen though
                .getKey();
    }

    private Set<Integer> createGroupWithBook(int book) {
        Set <Integer> group = new HashSet<>();
        group.add(book);
        return group;
    }

    private List<Integer> getGroupsMissingBook(int book, List<Set<Integer>> groups) {
        return Stream.iterate(0, i -> i + 1)
                .limit(groups.size())
                .filter(i -> !groups.get(i).contains(book))
                .toList();
    }


    private Double calculateCostForGroup(Integer groupSize) {
        return applyDiscount((double) groupSize * singleBookPrice, groupSize);
    }

    private Double applyDiscount(Double price, Integer groupSize) {
        return price * bookDiscountMap.get(groupSize);
    }

    private class  GroupComparator implements Comparator <Map.Entry<Integer, Integer>> {

        // Analyze the effect on groups in case we add a new book, what will be the effect and choose the group which is
        // most affected based on cost
        @Override
        public int compare(Map.Entry<Integer, Integer> group1, Map.Entry<Integer, Integer> group2) {
            Double group1CurrentCost = calculateCostForGroup(group1.getValue());
            Double group1NewCost = calculateCostForGroup(group1.getValue() + 1);

            Double group2CurrentCost = calculateCostForGroup(group2.getValue());
            Double group2NewCost = calculateCostForGroup(group2.getValue() + 1);

            return Double.compare(group1NewCost - group1CurrentCost, group2NewCost - group2CurrentCost);
            // Compare differences to understand which group is most affected
        }


    }

}
