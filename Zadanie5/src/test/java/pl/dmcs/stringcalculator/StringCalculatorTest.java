package pl.dmcs.stringcalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringCalculatorTest {

    StringCalculator stringCalculator;

    @BeforeEach
    void instantiateCalculator() {
        this.stringCalculator = new StringCalculator();
    }

    @Test
    void testEmptyString() {
        test("", 0);
    }

    @Test
    void testOneNumber() {
        test("3", 3);
        test("5", 5);
        test("6", 6);
    }

    @Test
    void testTwoNumbers() {
        test("3,1", 4);
        test("5,5", 10);
        test("32,33", 65);
    }

    @Test
    void testThreeNumbers() {
        test("3,1,4", 8);
        test("5,5,5", 15);
        assertEquals(9, stringCalculator.add("1,3,5"));
    }

    @Test
    void testNewLines() {
        test("1\n2,3", 6);
        test("4\n5,6", 15);
    }

    @Test
    void testDifferentDelimiters() {
        test("//;\n1;2", 3);
        test("//;\n3;2;1", 6);
    }

    @Test
    void testNegativeNumber() {
        testException("-1", "Negatives not allowed: [-1]");
        testException("-1,-2", "Negatives not allowed: [-1, -2]");
    }

    @Test
    void testAbove1000() {
        test("1001,2", 2);
        test("1001,2,1000,3,1002", 1005);
    }

    @Test
    void testLongerDelimiters() {
        test("//[***]\n1***2***3", 6);
        test("//[%%]\n2%%3%%3", 8);
    }

    @Test
    void testMultipleDelimiters() {
        test("//[*][%]\n1*2%3", 6);
        test("//[#][|]\n2#2|10", 14);
    }

    @Test
    void testMultipleLongDelimiters() {
        test("//[***][%][%%][*]\n1***3%3%%7*6", 20);
        test("//[****][%][&&][$]\n32****3%3&&7$6", 51);
    }

    private void test(String input, int expectedResult) {
        assertEquals(expectedResult, stringCalculator.add(input));
    }

    private void testException(String input, String expectedExceptionMessage) {
        Exception excpt = assertThrows(IllegalArgumentException.class, () -> {stringCalculator.add(input);});
        assertEquals(excpt.getMessage(), expectedExceptionMessage);
    }
}
