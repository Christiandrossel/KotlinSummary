package net.avgl.ekz.onleihe;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class Temperature {
    public static void main(String args[]) {
        int numberOfTemp = 6; // the number of temperatures to analyse
//        int[] temperature = {1,-2,-8,4,5};
//        int[] temperature = {-5, -4, -2, 12, -40, 4, 2, 18, 11, 5,};
        int[] temperature = {42, 5, 12, 21, -5, 24};
        System.err.print("Temperatur: ");
        for (int i = 0; i < numberOfTemp; i++) {
//            temperature[i] = in.nextInt(); // a temperature expressed as an integer ranging from -273 to 5526
            System.err.print(temperature[i]+", ");
        }
        System.err.print("\n");
        int lowestTemperature = getTemperatureNearByNull02(temperature);
        // Write an answer using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(lowestTemperature);
    }

    static int getTemperatureNearByNull02(int[] temperature){
        int storeLowest=temperature[0];
        int storeIndex=0;
        int closeToNull=temperature[0];
        int closeToNullIndex=0;
        for (int i = 0; i < temperature.length; i++) {
            for (int j = temperature.length-1; j >= 0; j--) {
                storeLowest = getLowest(storeLowest, temperature[i], temperature[j]);
                storeIndex = getIndexOfLowest(storeLowest, temperature, storeIndex, j);
                System.err.println("found: " + storeLowest);
            }
            closeToNull = Math.abs(closeToNull) < Math.abs(storeLowest) ? closeToNull : storeLowest;
            closeToNullIndex = closeToNull == storeLowest ? storeIndex : closeToNullIndex;
            System.err.println("closeToNull: "+closeToNull+" index= "+closeToNullIndex);
        }
        System.err.println("closeToNull: "+closeToNull+" index= "+closeToNullIndex);
        return closeToNullIndex;
    }

    static int getLowest(int storeLowest, int tA, int tB) {
        if (Math.abs(storeLowest) >= Math.abs(tA)) {
            if (Math.abs(storeLowest) == Math.abs(tA)) {
                return storeLowest > 0 ? storeLowest : tA;
            }
            return tA;
        } else if(Math.abs(storeLowest) >= Math.abs(tB)) {
            if (Math.abs(storeLowest) == Math.abs(tB)) {
                return storeLowest > 0 ? storeLowest : tB;
            }
            return tB;
        } else {
            return storeLowest;
        }
    }

    static int getIndexOfLowest(int storeLowest, int[] temperature, int storeIndex, int j) {
        if (storeLowest == temperature[j]) {
            storeIndex = j;
        }
        return storeIndex;
    }

    static void checkNumberOfTemperature(int numberOfTemperature) {
        // if not set
        if (numberOfTemperature == 0) {
            System.out.println("No temperature found");
        }
    }
}
