package net.avgl.ekz.onleihe;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Iterate {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        mapLoop();
        System.out.println("Sortierte Map:");
        sortMap();
        System.out.println("Sortierte Map nach Werten:");
        sortMapByValues();
    }


    private static void mapLoop() {
        Map<Integer, String> days = Map.of(
            1, "Monday",
            2, "Tuesday",
            3, "Wednesday",
            4, "Thursday",
            5, "Friday",
            6, "Saturday",
            7, "Sunday"
        );

        for (Map.Entry<Integer, String> entry : days.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        days.forEach((key, value) -> System.out.println(key + " = " + value));

        days.forEach((key, value) -> {
            if (key % 2 == 0) {
                System.out.println(key + " = " + value);
            }
        });
    }

    /**
     * Sortiert eine Map nach den Schlüsseln Map<Key, Value>
     */
    private static void sortMap() {
        Map<Integer, String> days = Map.of(
                2, "Tuesday",
                5, "Friday",
                1, "Monday",
                7, "Sunday",
                3, "Wednesday",
                4, "Thursday",
                6, "Saturday"
        );

        // TreeMap sortiert automatisch nach Schlüsseln
        Map<Integer, String> sortedByKeys = new TreeMap<>(days);

        System.out.println("Sortiert nach Schlüsseln:");
        sortedByKeys.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    /**
     * Sortiert eine Map nach den Werten Map<Key, Value>
     */
    private static void sortMapByValues() {
        Map<Integer, String> days = Map.of(
                2, "Tuesday",
                5, "Friday",
                1, "Monday",
                7, "Sunday",
                3, "Wednesday",
                4, "Thursday",
                6, "Saturday"
        );

        // TreeMap sortiert automatisch nach Schlüsseln
        Map<Integer, String> sortedByValues = new TreeMap<>((o1, o2) -> {
            String v1 = days.get(o1);
            String v2 = days.get(o2);
            return v1.compareTo(v2);
        });

        sortedByValues.putAll(days);

        System.out.println("Sortiert nach Werten:");
        sortedByValues.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
