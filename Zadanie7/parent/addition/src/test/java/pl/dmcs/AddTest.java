package pl.dmcs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddTest {
    @Test
    void additionTest1() {
        test(2, 2, 4);
        test(4, 1, 5);
    }

    private void test(int a, int b, int result) {
        assertEquals(result, Add.add(a, b));
    }
}