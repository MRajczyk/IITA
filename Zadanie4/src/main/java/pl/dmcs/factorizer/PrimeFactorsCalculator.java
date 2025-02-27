package pl.dmcs.factorizer;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class PrimeFactorsCalculator {
    public static List<Integer> calcPrimeFactors(int n) {
        ArrayList<Integer> result = new ArrayList<>();

        if(n <= 1) {
            return result;
        }

        for(int i = 2; i <= sqrt(n); i++) {
            while(n % i == 0) {
                result.add(i);
                n /= i;
            }
        }
        if(n > 1) {
            result.add(n);
        }

        return result;
    }
}
