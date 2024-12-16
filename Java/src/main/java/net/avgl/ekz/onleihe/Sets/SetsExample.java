package net.avgl.ekz.onleihe.Sets;

import java.util.HashSet;

/**
 * Sets are a collection of unique elements.
 */
public class SetsExample {


    public static void main(String[] args) {
        // addExample();
        // removeExample();
        // containsExample();
        // sizeExample();
        // clearExample();
        // isEmptyExample();
        // iteratorExample();
        // toArrayExample();
        // addAllExample();
        // removeAllExample();
        // retainAllExample();
        // containsAllExample();
        // equalsExample();
        // hashCodeExample();
    }

    /**
     * What types of sets exist
     *          // HashSet
     *         // LinkedHashSet
     *         // TreeSet
     */

    /**
     * HashSet
     */
    public void HashSetExample() {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("This");
        hashSet.add("is");
        hashSet.add("a");
        hashSet.add("HashSet");
        hashSet.add("example");

        System.out.println(hashSet);
    }

    /**
     * sort
     */
    public void sortExample() {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("This");
        hashSet.add("is");
        hashSet.add("a");
        hashSet.add("HashSet");
        hashSet.add("example");

        //Sort by default
        hashSet.stream().sorted().forEach(System.out::println);
    }

}
