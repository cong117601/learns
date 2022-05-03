package com.cgm.algorithm.base;

import java.util.HashMap;

public class PrintBit {

    public static void print(int num) {

        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");

        }
        System.out.println();
    }

    public static void main(String[] args) {
        print(3);
        print(-3);

        int a = 5;
        int b = (~a + 1);
    }


    public int[] twoSum(int[] numbers, int target) {
        int[] ans = new int[2];
        if(numbers==null || numbers.length <2  ) return null;
        int L = 0;
        int R = numbers.length-1;
        while(numbers[L] + numbers[R] != target){
            int i = (numbers[L] + numbers[R] > target) ? R-- : L++;
        }
        ans[0] = L;
        ans[1] = R;
        return ans;
    }
}
