package com.cgm.algorithm.base.common;

public class Tools {


    public static void swap(int[] arr, int i, int j) {
        if (arr == null || arr.length < 2) return;
        if (i < 0 || j < 0) return;
        if (i == j) try {
            throw new Exception("交换位置不能相同");
        } catch (Exception e) {
            e.printStackTrace();
        }
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    public static void swapByThree(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }


    public static int[] copyArr(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }


    public static boolean isEqual(int[] arr1, int[] arr2) {

        if (arr1 == null && arr2 == null) return true;
        if (arr1.length != arr2.length) return false;
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) return false;
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) return false;
        }
        return true;
    }


    //生成随机数组
    public static int[] generateRangeArray(int maxLength, int maxValue) {
        int[] arr = new int[(int) (Math.random() * (maxLength + 1))];
        for (int i = 0; i < arr.length; i++) {
            int a = (int) (Math.random() * (maxValue + 1));
            int b = (int) (Math.random() * (maxValue + 1));
            arr[i] = a - b;
        }
        return arr;
    }
}
