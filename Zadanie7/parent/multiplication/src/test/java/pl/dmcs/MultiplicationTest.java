package pl.dmcs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiplicationTest {
    @Test
    public void additionTest1() {
        test(2, 2, 4);
        test(4, 1, 4);
        test(5, 5, 25);
    }

    private void test(int a, int b, int result) {
        assertEquals(result, Multiplication.multiply(a, b));
    }
}