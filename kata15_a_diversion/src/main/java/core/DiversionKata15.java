package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class DiversionKata15 {
    private static final Logger LOG = LoggerFactory.getLogger(DiversionKata15.class.getSimpleName());
    private final Solution solution;

    public DiversionKata15() {
        solution = new Solution();
    }

    public void solve() {
        int n = readUserInput();
        long result = solution.doSolve(n);
        LOG.info("Found {} of combinations that satisfy the needs", result);
    }

    private int readUserInput() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        LOG.info("Please input number n the length of binary number");
        try {
            String inputLine = bufferedReader.readLine();
            Integer value = Integer.parseInt(inputLine.strip());
            if (value <= 0)
                throw new IllegalArgumentException("Not valid number for this kata");

            return value;
        } catch (IOException | IllegalArgumentException e) {
            LOG.warn("number n is not in valid format, please try again");
            System.exit(0);
        }
        return 0;
    }

}
