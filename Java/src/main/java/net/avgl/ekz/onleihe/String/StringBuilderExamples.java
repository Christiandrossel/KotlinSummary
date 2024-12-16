package net.avgl.ekz.onleihe.String;


public class StringBuilderExamples {
    public static void main(String[] args) {
        appendExample();
    }

    public static void appendExample() {
        var welcome = "Welcome";
        var to = "to";
        var the = "the";
        var jungle = "jungle";
        var stringBuilder = new StringBuilder();
        stringBuilder.append(welcome);
        stringBuilder.append(" ");
        stringBuilder.append(to);
        stringBuilder.append(" ");
        stringBuilder.append(the);
        stringBuilder.append(" ");
        stringBuilder.append(jungle);

        System.out.println(stringBuilder.toString());
    }


}
