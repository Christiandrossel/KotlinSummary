package net.avgl.ekz.onleihe.pointer;

class OuterClass {
    int x = 10;

    class InnerClass {
        int y = 5;
    }
    static class StaticInnerClass {
        int z = 15;
    }
}

public class Classes {
    public static void main(String[] args) {
        OuterClass myOuter = new OuterClass();

        OuterClass.InnerClass myInner = myOuter.new InnerClass();
        System.out.println(myInner.y + myOuter.x);

        OuterClass.StaticInnerClass myStaticInner = new OuterClass.StaticInnerClass();
        System.out.println(myStaticInner.z);
    }
}
