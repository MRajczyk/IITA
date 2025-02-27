package pl.dmcs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AggregatedProjectTest {
    @Test
    public void aggregatedAdditionTest() {
        testAggregatedAddition(2, 2, 4);
        testAggregatedAddition(5, 12, 17);
    }

    @Test
    public void aggregatedMultiplicationTest() {
        testAggregatedMultiplication(1, 5, 5);
        testAggregatedMultiplication(12, 12, 144);
    }

    @Test
    public void aggregatedPowerTest() {
        testAggregatedPower(5, 2, 25);
        testAggregatedPower(6, 3, 216);
    }

    private void testAggregatedAddition(int a, int b, int result) {
        assertEquals(result, AggregatedProject.aggregatedAddition(a, b));
    }

    private void testAggregatedMultiplication(int a, int b, int result) {
        assertEquals(result, AggregatedProject.aggregatedMultiplication(a, b));
    }

    private void testAggregatedPower(int a, int b, int result) {
        assertEquals(result, AggregatedProject.aggregatedPower(a, b));
    }
}