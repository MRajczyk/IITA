package pl.dmcs.calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
  @Test
  public void testAddition() {
      final int result = Calculator.add(1, 2);
      assertEquals(3, result);
  }

  @Test
  public void testAdditionWithNegativeInteger() {
      final int result = Calculator.add(1, -2);
      assertEquals(-1, result);
  }

  @Test
  public void testAdditionTwoNegativeIntegers() {
      final int result = Calculator.add(-1, -2);
      assertEquals(-3, result);
  }
  
  @Test
  public void testAdditionZero() {
      final int result = Calculator.add(13, 0);
      assertEquals(13, result);
  }
}