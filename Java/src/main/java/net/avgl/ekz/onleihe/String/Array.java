package net.avgl.ekz.onleihe.String;

import java.util.Arrays;
import java.util.Comparator;

public class Array {
    public static void main(String[] args) {
        arraysCompare();

        arraysSort();
    }


    private static void arraysSort() {
        String[] words = {"Welcome", "to", "the", "jungle"};
        String[] words2 = {"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"};
        String[] words3 = {"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "cat"};

        System.out.println(Arrays.toString(words));
        // sort the array
        Arrays.sort(words);
        System.out.println(Arrays.toString(words)+"\n");

        System.out.println(Arrays.toString(words2));
        Arrays.sort(words2, Comparator.comparingInt(String::length));
        System.out.println(Arrays.toString(words2));

        System.out.println(Arrays.toString(words3));
        Arrays.sort(words3, Comparator.comparingInt(String::length));
        System.out.println(Arrays.toString(words3));
    }

    private static void arraysCompare() {
        String[] words = {"Welcome", "to", "the", "jungle"};
        String[] words2 = {"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"};
        String[] words3 = {"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "cat"};
        String[] words4 = {"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"};

        System.out.println(Arrays.toString(words));
        // compare the array
        // the result is 0 if the arrays are equal
        // a value less than 0 if the first array is lexicographically less than the second
        int result = Arrays.compare(words, words2);
        System.out.println(Arrays.toString(words)+"\n");
        System.out.println((result == 0) ? "The arrays are equal" : "The arrays are not equal");

        System.out.println(Arrays.toString(words2));
        result = Arrays.compare(words2, words3);
        System.out.println(result == 0 ? "The arrays are equal" : "The arrays are not equal");

        System.out.println(Arrays.toString(words3));
        result = Arrays.compare(words3, words2);
        System.out.println(result == 0 ? "The arrays are equal" : "The arrays are not equal");

        System.out.println(Arrays.toString(words4));
        result = Arrays.compare(words4, words2);
        System.out.println(result == 0 ? "The arrays are equal" : "The arrays are not equal");
    }
}
