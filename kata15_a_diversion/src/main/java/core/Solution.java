package core;

public class Solution {

    // Returns the number of sequences that doesn't have adjacent 1's given that binary number length will be equal to n
    public long doSolve(int n) {
        // n: 1 -> 2
        // n: 2 => 3 [00, 01, 10]
        // n: 3 => 5 [100, 010, 001, 101, 000]
        // n: 4 => 8 [0000, 1000, 0100, 0010, 0001, 1010, 1001, 0101]
        // Solution: Observing above results it's easy to see that it's good old fibonachi sequence
        // To solve this kata efficiently I will use dynamic programming bottom up approach

        long [] resultArray = new long [n];
        resultArray[0] = 2;

        if (n > 1)
            resultArray[1] = 3;

        for (int index = 2; index < n; index++) {
            resultArray[index] = resultArray[index - 1] + resultArray[index - 2];
        }

        return resultArray[n - 1];
    }

}
