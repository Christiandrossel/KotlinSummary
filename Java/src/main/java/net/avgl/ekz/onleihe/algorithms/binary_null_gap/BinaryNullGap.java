package net.avgl.ekz.onleihe.algorithms.binary_null_gap;


import java.util.ArrayList;
import java.util.List;

/**
 * Eine binäre Lücke innerhalb einer positiven Ganzzahl N ist jede maximale Folge von aufeinanderfolgenden Nullen,
 * die an beiden Enden von Einsen umgeben ist, in der binären Darstellung von N.
 *
 * Zum Beispiel hat die Zahl 9 die binäre Darstellung 1001 und enthält eine binäre Lücke der Länge 2.
 * Die Zahl 529 hat die binäre Darstellung 1000010001 und enthält zwei binäre Lücken: eine der Länge 4 und eine der Länge 3.
 * Die Zahl 20 hat die binäre Darstellung 10100 und enthält eine binäre Lücke der Länge 1.
 * Die Zahl 15 hat die binäre Darstellung 1111 und hat keine binären Lücken. Die Zahl 32 hat die binäre Darstellung 100000
 * und hat keine binären Lücken.
 *
 * Schreiben Sie eine Funktion: class Solution { public int solution(int N); } die,
 * gegeben eine positive Ganzzahl N, die Länge ihrer längsten binären Lücke zurückgibt.
 * Die Funktion sollte 0 zurückgeben, wenn N keine binäre Lücke enthält. Zum Beispiel, gegeben N = 1041,
 * sollte die Funktion 5 zurückgeben, weil N die binäre Darstellung 10000010001 hat und somit ihre längste binäre Lücke
 * der Länge 5 ist. Gegeben N = 32 sollte die Funktion 0 zurückgeben, weil N die binäre Darstellung '100000' hat
 * und somit keine binären Lücken.
 *
 * Schreiben Sie einen effizienten Algorithmus
 * für die folgenden Annahmen: N ist eine Ganzzahl im Bereich [1 .. 2.147.483.647].
 */

/**
 * Diese Lösung ist nicht effizient, da sie nicht die Anzahl der Schritte berücksichtigt, die für die Lösung des Problems
 * erforderlich sind. Die Lösung ist jedoch einfach und leicht zu verstehen.
 * Die Lösung besteht aus zwei Schritten:
 * 1. Überprüfen, ob die binäre Darstellung von N eine binäre Lücke enthält.
 * 2. Wenn die binäre Darstellung von N eine binäre Lücke enthält, finden Sie die längste binäre Lücke.
 * Die Lösung verwendet zwei Hilfsmethoden: isBinaryGap und getTheLongestGap.
 *
 */
public class BinaryNullGap {

    public static void main(String[] args) {
        BinaryNullGap binaryNullGap = new BinaryNullGap();
        BinaryNullGap.Solution solution = binaryNullGap.new Solution();

        System.out.println("The longest binary gap from 1041 is:");
        int result = solution.solution(1041);
        System.out.println(result); // 5
        System.out.println("the result is " + ((result == 5) ? "correct" : "incorrect, this must be 5"));

        System.out.println("The longest binary gap from 32 is:");
        result = solution.solution(32); // 0
        System.out.println(result);
        System.out.println("the result is " + ((result == 0) ? "correct" : "incorrect, this must be 0"));
    }

    public class Solution {
        public int solution(int N) {
            // convert the number to binary
            String binary = Integer.toBinaryString(N);
            System.out.println(binary);

            // find the longest binary gap
            // return the longest binary gap
            return findLongestBinaryGap(binary);
        }

        private int findLongestBinaryGap(String binary) {
            if (!isBinaryGap(binary)) {
                return 0;
            }

            String gap = getTheLongestGap(binary);
            return gap.length();
        }

        /**
         * check contains binary if have more than one binary gap
         * @param binary
         * @return
         */
        private boolean isBinaryGap(String binary) {
            String regex = "1(0+)1";
            return binary.matches(".*"+regex+".*");
        }

        private String getTheLongestGap(String binary) { // eaxample 10000010001 then must the result 00000
            List<String> gaps = getBinaryGaps(binary);
            String longestGap = "";
            for (String gap : gaps) {
                if (gap.length() > longestGap.length()) {
                    longestGap = gap;
                }
            }
            return longestGap;
        }

        private List<String> getBinaryGaps(String binary) {
//            List<String> gaps = new ArrayList<>();
//            String regex = "1(0+)1";
//            return gaps;

            String result = "";
            // A list of results
//            String[] results = binary.split("1");
            List<String> results = new ArrayList<>();
            // check the binary gap
            for (int i = 0; i < binary.length(); i++) {
                if (binary.charAt(i) == '1') { // if the binary is 1
                    for (int j = i + 1; j < binary.length(); j++) { // check the next binary
                        if (binary.charAt(j) == '0') { // if the next binary is 0
                            result += binary.charAt(j); // add the binary to result and build a gap
                        } else if (binary.charAt(j) == '1') { // if the next binary is 1 ends the gap
                            results.add(result); // add the result to the list
                            result = ""; // reset the result
                            i = j-1; // set the i to j
                            break; // break the loop
                        }
                    }
                }
            }
            return results;
        }
    }

}
