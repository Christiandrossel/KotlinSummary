package net.avgl.ekz.onleihe.pointer;

/**
 * In Java is a reference to an object, not a pointer to a memory location.
 * The reference is a memory address, but you can't access it directly.
 * You can't change the memory address of the reference, but you can change the object it points to.
 * The reference is a pointer to the object, not the object itself.
 *
 */
public class Pointer {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        Pointer pointer = new Pointer();
        pointer.stringReference();
    }

    private void stringReference() {
        String str = "Tino";
        String str2 = str;
        printName(str);
        System.out.println(str); // Tino

        str = "World";
        System.out.println(str); // World
        System.out.println(str2); // Tino
    }

    private void ObjectReference() {
        Object obj = new Object();
        Object obj2 = obj;
        obj = new Object();
        System.out.println(obj2); // Object@15db9742
    }

    private void ArrayReference() {
        int[] arr = new int[3];
        int[] arr2 = arr;
        arr = new int[5];
        System.out.println(arr2); // [I@6d06d69c
    }

    private void ArrayReference2() {
        int[] arr = new int[3];
        int[] arr2 = arr;
        arr[0] = 1;
        System.out.println(arr2[0]); // 1
    }

    private void printName(String name) {
        name = name + " World";
        System.out.println(name);
    }
}
