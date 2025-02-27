package pl.dmcs.stringcalculator;

import java.util.ArrayList;
import java.util.Comparator;

public class StringCalculator {

    public int add (String s) {
        String delimiters = "[,\n]";
        String[] numbersArray;
        ArrayList<Integer> negativeNumbers = new ArrayList<Integer>();

        if(s.isEmpty()) {
            return 0;
        }
        if (s.startsWith("//[")) {
            numbersArray = getMultipleDelimiters(s, delimiters);
        }
        else if (s.startsWith("//")) {
            numbersArray = getSingleDelimiter(s);
        }
        else {
            numbersArray = s.split(delimiters);
        }

        int sum = 0;

        for(String num: numbersArray) {
            int p_num = Integer.parseInt(num);
            if(p_num > 1000) {
                continue;
            }
            else if(p_num < 0) {
                negativeNumbers.add(p_num);
            }
            else {
                sum += Integer.parseInt(num);
            }
        }

        if (!negativeNumbers.isEmpty()) {
            throw new IllegalArgumentException("Negatives not allowed: " + negativeNumbers);
        }

        return sum;
    }

    private String[] getSingleDelimiter(String s) {
        int delimiterIndex = s.indexOf('\n');
        String delimiters = s.substring(2, delimiterIndex);
        s = s.substring(delimiterIndex + 1);
        return s.split(delimiters);
    }

    private String[] getMultipleDelimiters(String s, String originalDelimtiers) {
        ArrayList<String> listMultipleDelimiters = new ArrayList<>();

        int delimiterIndex = s.indexOf('\n');
        String tempDelimitersString = s.substring(2, delimiterIndex);

        int startIdx = 0;
        int endIdx;
        for(int i = 0 ; i < tempDelimitersString.length(); i++) {
            if(tempDelimitersString.charAt(i) == '[') {
                startIdx = i;
            }
            if(tempDelimitersString.charAt(i) == ']') {
                endIdx = i;
                String newDelimiter = tempDelimitersString.substring(startIdx + 1, endIdx);
                listMultipleDelimiters.add(newDelimiter);
            }
        }

        s = s.substring(delimiterIndex + 1);
        listMultipleDelimiters.sort((s1, s2) -> Integer.compare(s2.length(), s1.length()));
        for(String del: listMultipleDelimiters) {
            s = s.replace(del, ",");
        }
        return s.split(originalDelimtiers);
    }
}
