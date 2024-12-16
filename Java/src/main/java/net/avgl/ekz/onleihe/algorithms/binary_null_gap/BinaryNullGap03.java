package net.avgl.ekz.onleihe.algorithms.binary_null_gap;

/**
 * Diese Lösung ist effizienter, da sie die Anzahl der Schritte reduziert, die zum Finden der Lücken erforderlich sind.
 * Die Lösung verwendet eine einfache Schleife, um das längste Null-Gap in der Binärdarstellung einer Zahl zu finden.
 * Die Lösung besteht aus zwei Schritten:
 * 1. Überprüfen, ob die binäre Darstellung von N eine binäre Lücke enthält.
 * 2. Wenn die binäre Darstellung von N eine binäre Lücke enthält, finden Sie die längste binäre Lücke.
 */
public class BinaryNullGap03 {

    public static void main(String[] args) {
        BinaryNullGap03 solution = new BinaryNullGap03();

        System.out.println("The longest binary gap from 1041 is:");
        int result = solution.solution(1041);
        System.out.println(result); // 5
        System.out.println("the result is " + ((result == 5) ? "correct" : "incorrect, this must be 5"));

        System.out.println("The longest binary gap from 32 is:");
        result = solution.solution(32); // 0
        System.out.println(result);
        System.out.println("the result is " + ((result == 0) ? "correct" : "incorrect, this must be 0"));
    }

    public int solution(int N) {
        int maxGap = 0;
        int currentGap = 0;
        boolean counting = false;

        while (N > 0) {
            if ((N & 1) == 1) { // Prüfe das niedrigste Bit
                if (counting) {
                    maxGap = Math.max(maxGap, currentGap);
                }
                counting = true;
                currentGap = 0;
            } else if (counting) {
                currentGap++;
            }
            N >>= 1; // Verschiebe die Bits nach rechts
        }
        return maxGap;
    }
}
