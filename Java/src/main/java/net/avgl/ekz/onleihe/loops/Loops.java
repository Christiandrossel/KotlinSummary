package net.avgl.ekz.onleihe.loops;

public class Loops {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");


    }


    public static void forLoop() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
            if (i == 3) {
                break; // Exit the loop
            }
            if (i == 2) {
                continue; // Skip the rest of the loop
            }
            if (i == 4) {
                return; // Exit the program
            }
            System.out.println("This will not be printed");
        }
        System.out.println("This is the end");
    }

    public static void whileLoop() {
        int i = 1;
        while (i <= 5) {
            System.out.println("i = " + i);
            i++;
            for (int j = 1; j <= 3; j++) {
                System.out.println("j = " + j);
                if (j == 2) {
                    break; // Exit the inner loop
                }
            }
        }
    }
}
