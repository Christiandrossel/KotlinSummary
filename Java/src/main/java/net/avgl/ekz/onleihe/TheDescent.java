package net.avgl.ekz.onleihe;

import java.util.Arrays;
import java.util.Scanner;

/**
 * The while loop represents the game.
 * Each iteration represents a turn of the game
 * where you are given inputs (the heights of the mountains)
 * and where you have to print an output (the index of the mountain to fire on)
 * The inputs you are given are automatically updated according to your last actions.
 **/
public class TheDescent {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int detectionHigh = 9; // is the high from the ship
        int[] mountainHigh = {0,6,7,5,0,8,1,0};
        int round = 10;

        System.out.println("Mountain High start: " + Arrays.toString(mountainHigh));

        // game loop
        while (round > 0) {
            System.out.println("Detection High: " + detectionHigh);
//            for (int i = 0; i < 8; i++) {
//                int mountainH = in.nextInt(); // represents the height of one mountain.
//                mountainHigh[i] = mountainH;
//            }
            int indexFromMountain = getHighestMountain(mountainHigh);
            System.out.println("Index from highest Mountain: " + indexFromMountain);
            System.out.println("Shooting on Mountain: " + indexFromMountain + " with high: " + mountainHigh[indexFromMountain]);
            mountainHigh[indexFromMountain] = 0;
            System.out.println("New Mountain High: " + Arrays.toString(mountainHigh));
            detectionHigh--;
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            //System.out.println("4"); // The index of the mountain to fire on.
            round --;
        }
    }


    static int getHighestMountain(int[] mountainHigh) {
        int[] sortedMountainHigh = mountainHigh.clone();
        Arrays.sort(sortedMountainHigh);
        int highestMountain = sortedMountainHigh[sortedMountainHigh.length - 1];

        for (int i = 0; i < mountainHigh.length; i++) {
            if (mountainHigh[i] == highestMountain) {
                return i;
            }
        }
        return 0;
    }

    static void simpleMountainHighCalc() {
        //for (int i = 0; i < 8; i++) {
        //     int mountainH = in.nextInt(); // represents the height of one mountain.
        //   mountainHigh[i] = mountainH;
        // if (mountainH >= 0){
        //   if(mountainH >= detectionHigh) {
        //     System.out.println(""+i); // The index of the mountain to fire on.
        //} else {
        //  detectionHigh--;
        //}
        //}
        //}
    }
}



