package pl.dmcs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PowerTest {
    @Test
    public void additionTest1() {
        test(2, 2, 4);
        test(4, 1, 4);
        test(5, 3, 125);
    }

    private void test(int a, int b, int result) {
        assertEquals(result, Power.power(a, b));
    }
}