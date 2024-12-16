package net.avgl.ekz.onleihe.String;


public class StringExamples {

    public static void main(String[] args) {
//        equealsExample();
//
        compareToExample();
    }

    /**
     * compare()
     */
    public static void equealsExample() {
        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = "World";
        String str4 = new String("Hello");

        System.out.println(str1.equals(str2)); // true
        System.out.println(str1.equals(str3)); // false
        System.out.println(str1.equals(str4)); // true
    }

    public static void compareToExample() {
        String str1 = "Hello World";
        String str2 = "Hello World";
        String str3 = "This is a sentence";
        String str4 = new String("Hello");

        System.out.println(str1.compareTo(str2)); // 0
        System.out.println(str1.compareTo(str3)); // -1 because str1 is less than str3
        System.out.println(str1.compareTo(str4)); // 6 because str1 is greater than str4
    }
}
