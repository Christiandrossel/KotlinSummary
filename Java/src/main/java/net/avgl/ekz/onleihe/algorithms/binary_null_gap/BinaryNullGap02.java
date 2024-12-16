package net.avgl.ekz.onleihe.algorithms.binary_null_gap;

/**
 * Diese Lösung verwendet eine einfache Schleife, um das längste Null-Gap in der Binärdarstellung einer Zahl zu finden.
 */
public class BinaryNullGap02 {

    public static void main(String[] args) {
        BinaryNullGap02 solution = new BinaryNullGap02();
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
        String binary = Integer.toBinaryString(N); // Binärdarstellung
        int maxGap = 0; // Maximales Gap
        int currentGap = 0; // Aktuelles Gap
        boolean counting = false; // Ob das Zählen gestartet wurde

        for (char c : binary.toCharArray()) {
            if (c == '1') {
                if (counting) { // Wenn eine 1 gefunden wird und wir gezählt haben
                    maxGap = Math.max(maxGap, currentGap); // Prüfen, ob es das längste Gap ist
                }
                counting = true; // Start der Zählung
                currentGap = 0; // Gap zurücksetzen
            } else if (counting) {
                currentGap++; // Innerhalb eines Gaps, daher zählen wir die Nullen
            }
        }
        return maxGap;
    }
}
