package pl.dmcs;

public class AggregatedProject {
  public static int aggregatedAddition(int a, int b) {
    return Add.add(a, b);
  }

  public static int aggregatedMultiplication(int a, int b) {
    return Multiplication.multiply(a, b);
  }

  public static int aggregatedPower(int a, int b) {
    return Power.power(a, b);
  }
}
