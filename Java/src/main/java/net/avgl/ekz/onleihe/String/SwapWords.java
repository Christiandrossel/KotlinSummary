package net.avgl.ekz.onleihe.String;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class SwapWords {
    public static void main(String[] args) {
//        SwapWords swapWords = new SwapWords();
//        String sentence = swapWords.spinWords("Welcome");
//        System.out.println(sentence);

//        String sentence = "Welcome to the jungle";

//        sentence = spinWordsArray(sentence);
//        System.out.println(sentence);

        arraysSort();
    }


    public String spinWords(String sentence) {
        System.out.println(sentence);
        //step one: split the text in words
        String[] words = sentence.split(" ");

        // check if more the 1 words
        for(int i = 0; i < words.length; i++){
            words[i] = swapWords(words[i], 5);
        }

        String newSentence = String.join(" ", words);
        System.out.println(newSentence);
        return newSentence;
    }

    public static String swapWords(String word, int lengthOfChar){
        char[] characters = word.trim().toCharArray();
        int left = 0;
        int right = characters.length-1;
        char temp;

        if(characters.length >= lengthOfChar) {
            while(left < right) {
                //example hello
                temp = characters [right];
                characters[right] = characters[left];
                characters[left] = temp;
                left++;
                right--;
            }
        }
        return new String(characters);
    }

    public String spinWordsStringBuilder(String sentence) {
        String[] words = sentence.split(" ");
        for (int i=0; i<words.length; i++) {
            if (words[i].length() >= 5) {
                words[i] = new StringBuilder(words[i]).reverse().toString();
            }
        }
        return String.join(" ",words);
    }

    private static String spinWordsArray(String sentence) {
        String words = Arrays.stream(sentence.split(" "))
                .map(i -> i.length() > 4 ? new StringBuilder(i).reverse().toString() : i)
                .collect(Collectors.joining(" "));
        return words;
    }

    private static void arraysSort() {
        String[] words = {"Welcome", "to", "the", "jungle"};
        String[] words2 = {"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"};

        System.out.println(Arrays.toString(words));
        // sort the array
        Arrays.sort(words);
        System.out.println(Arrays.toString(words)+"\n");

        System.out.println(Arrays.toString(words2));
        Arrays.sort(words2, Comparator.comparingInt(String::length));
        System.out.println(Arrays.toString(words2));
    }

    private static void arraysCompare() {
        String[] words = {"Welcome", "to", "the", "jungle"};
        String[] words2 = {"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"};

        System.out.println(Arrays.toString(words));
        // compare the array
        // the result is 0 if the arrays are equal
        // a value less than 0 if the first array is lexicographically less than the second
        int result = Arrays.compare(words, words2);
        System.out.println(Arrays.toString(words)+"\n");
        System.out.println((result == 0) ? "The arrays are equal" : "The arrays are not equal");

        System.out.println(Arrays.toString(words2));
        Arrays.sort(words2, Comparator.comparingInt(String::length));
        System.out.println(Arrays.toString(words2));
    }
}
