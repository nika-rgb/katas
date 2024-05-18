import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookPricingTest {
    private static final int SINGLE_BOOK_COST = 8;

    private BookPricing bookPricing;

    private static Stream<Arguments> getDiscountedBasketsWithSimpleCombinations() {
        return Stream.of(
                Arguments.of(new int[]{1, 2}, 15.2),      // simple combinations
                Arguments.of(new int[]{2, 3, 4}, 21.6),
                Arguments.of(new int[]{1, 2, 3, 4}, 25.6),
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 30.0)
        );
    }

    private static Stream<Arguments> getDiscountedBasketsWithPairOfCombinations() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 1, 2}, 30.4), // simple combinations with repeated books
                Arguments.of(new int[]{1, 2, 3, 1, 2, 3}, 43.2),
                Arguments.of(new int[]{1, 2, 3, 4, 1, 2, 3, 4}, 51.2),
                Arguments.of(new int[]{1, 2, 3, 4, 5, 1, 2, 3, 4, 5}, 60.0)
        );
    }

    private static Stream<Arguments> getDiscountedBooksPlusOneOutOfGroup() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 2}, 23.2),
                Arguments.of(new int[]{2, 3, 4, 3}, 29.6)
        );
    }

    private static Stream<Arguments> getMultipleDiscounts() {
        return Stream.of(
                Arguments.of(new int[]{1, 1, 2, 2, 3}, 36.8),
                Arguments.of(new int[]{1, 2, 4, 1, 2, 4, 4}, 51.2),
                Arguments.of(new int[]{1, 2, 2, 3, 4, 5}, 38.0)
        );
    }

    private static Stream<Arguments> getEdgeCaseInput() {
        return Stream.of(
                Arguments.of(new int[]{1, 1, 2, 2, 3, 3, 4, 5}, 51.2),
                Arguments.of(new int[]{1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5}, 141.2)
        );
    }

    @BeforeEach
    void setUp() {
        bookPricing = new BookPricing(SINGLE_BOOK_COST);
    }

    @Test
    void testEmptyBucket() {
        int[] basket = new int[]{};

        Double cost = bookPricing.getMinBasketPrice(basket);

        assertEquals(0, cost.doubleValue());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testSingleBookPrice(int book) {
        int[] basket = new int[]{book};

        Double cost = bookPricing.getMinBasketPrice(basket);

        assertEquals(SINGLE_BOOK_COST, cost.doubleValue());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testSingleTypeofBookInBasket(int book) {
        int[] basket = new int[]{book, book};

        Double cost = bookPricing.getMinBasketPrice(basket);

        assertEquals(2 * SINGLE_BOOK_COST, cost.doubleValue());

    }

    @ParameterizedTest
    @MethodSource("getDiscountedBasketsWithSimpleCombinations")
    void testDiscountedBasket(int[] basket, Double expectedCost) {

        Double cost = bookPricing.getMinBasketPrice(basket);

        assertEquals(expectedCost, cost.doubleValue());
    }

    @ParameterizedTest
    @MethodSource("getDiscountedBasketsWithPairOfCombinations")
    void testDiscountedBasketWithRepeatingCombinations(int[] basket, Double expectedCost) {

        Double cost = bookPricing.getMinBasketPrice(basket);

        assertEquals(expectedCost, cost.doubleValue());
    }

    @ParameterizedTest
    @MethodSource("getDiscountedBooksPlusOneOutOfGroup")
    void testSingleBookOutOfDiscount(int[] basket, Double expectedCost) {

        Double cost = bookPricing.getMinBasketPrice(basket);

        assertEquals(expectedCost, cost.doubleValue());
    }

    @ParameterizedTest
    @MethodSource("getMultipleDiscounts")
    void testMultipleDiscounts(int[] basket, Double expectedCost) {

        Double cost = bookPricing.getMinBasketPrice(basket);

        assertEquals(expectedCost, cost.doubleValue());
    }

    @ParameterizedTest
    @MethodSource("getEdgeCaseInput")
    void testEdgeCase_basketWithTwoFourDiscountedBooksBetterThanOneContainingFiveAndSecondThree(int[] basket, Double expectedCost) {

        Double cost = bookPricing.getMinBasketPrice(basket);

        assertEquals(expectedCost, cost.doubleValue());
    }


}
