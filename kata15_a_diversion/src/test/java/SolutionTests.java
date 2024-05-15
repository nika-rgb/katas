import core.Solution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolutionTests {

    private static Stream<Arguments> baseCaseArguments() {
        return Stream.of(
                Arguments.of(1, 2L),
                Arguments.of(2, 3L)
        );
    }

    private static Stream<Arguments> differentScenarioArguments() {
        return Stream.of(
                Arguments.of(3, 5L),
                Arguments.of(5, 13L),
                Arguments.of(100, 5035488507601418376L)
        );
    }


    @ParameterizedTest
    @MethodSource("baseCaseArguments")
    void testSolution_base_case(Integer n, long expected) {
        Solution solution = new Solution();
        long actualResult = solution.doSolve(n);
        assertEquals(expected, actualResult);
    }

    @ParameterizedTest
    @MethodSource("differentScenarioArguments")
    void testSolution(Integer n, long expected) {
        Solution solution = new Solution();
        long actualResult = solution.doSolve(n);
        assertEquals(expected, actualResult);
    }

}
