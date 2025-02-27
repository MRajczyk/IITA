package helloworld;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldTest {
    @Test
    public void helloWorldTest() {
        assertEquals(1 + 2, 3);
    }
}