package net.avgl.ekz.onleihe.algorithms;

import java.util.HashSet;

public class FindLowestPositiveNumber {

    public static void main(String[] args) {
        int[] arr = {1, 3, 6, 4, 1, 2};
        System.out.println(findLowestPositiveNumber(arr));
    }

    public static int findLowestPositiveNumber(int[] arr) {
        int n = arr.length;
        int[] temp = new int[n];
        for (int i = 0; i < n; i++) {
            if (arr[i] > 0 && arr[i] <= n) {
                temp[arr[i] - 1] = arr[i];
            }
        }
        for (int i = 0; i < n; i++) {
            if (temp[i] != i + 1) {
                return i + 1;
            }
        }
        return n + 1;
    }


    /**
     * This is a demo task.
     *
     * Write a function:
     *
     * class Solution { public int solution(int[] A); }
     *
     * that, given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.
     *
     * For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.
     *
     * Given A = [1, 2, 3], the function should return 4.
     *
     * Given A = [−1, −3], the function should return 1.
     *
     * Write an efficient algorithm for the following assumptions:
     *
     * N is an integer within the range [1..100,000];
     * each element of array A is an integer within the range [−1,000,000..1,000,000].
     */
    class Solution {
        public int solution(int[] A) {
            // Step 1: Use a HashSet to store all positive integers from the array
            HashSet<Integer> set = new HashSet<>();
            for (int num : A) {
                if (num > 0) {
                    set.add(num);
                }
            }

            // Step 2: Check the smallest missing positive integer
            int smallestMissing = 1; // Start from 1
            while (set.contains(smallestMissing)) {
                smallestMissing++;
            }

            return smallestMissing;
        }
    }

}
