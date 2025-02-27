package pl.dmcs.factorizer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class PrimeFactorsCalculatorTest {
    @Test
    void testPrimeFactorsZero() {
        test(List.of(), 0);
    }

    @Test
    void testPrimeFactorsOne() {
        test(List.of(), 1);
    }

    @Test
    void testPrimeFactorsTwo() {
        test(List.of(2), 2);
    }

    @Test
    void testPrimeFactorsThree() {
        test(List.of(3), 3);
    }

    @Test
    void testPrimeFactorsFour() {
        test(List.of(2, 2));
    }

    @Test
    void testPrimeFactorsMultipleOccurencesMoreThanTwo() {
        test(List.of(5, 5, 7, 7, 7));
    }

    @Test
    void testPrimeFactorsLargeNumber() {
        test(List.of(2, 2, 5, 7, 11, 13, 37));
    }

    private void test(List<Integer> expectedResult, int number) {
        assertEquals(expectedResult, PrimeFactorsCalculator.calcPrimeFactors(number));
    }

    private void test(List<Integer> primeFactorsList) {
        int number = 1;
        for (Integer integer : primeFactorsList) {
            number *= integer;
        }

        test(primeFactorsList, number);
    }
}
