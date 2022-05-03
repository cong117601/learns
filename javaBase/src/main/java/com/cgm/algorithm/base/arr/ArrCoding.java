package com.cgm.algorithm.base.arr;

import com.cgm.algorithm.base.common.Tools;

public class ArrCoding {


    //计算数组从L到R的数字和
    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 9, 7, 10, 11, -5};

        System.out.println(rangAdd(arr, 1, 3));
    }


    public static int rangAdd(int[] arr, int start, int end) {
        if (arr == null || start == -1 || end == -1) {
            return -1;
        }
        int[] helpArr = new int[arr.length];
        helpArr[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            helpArr[i] = helpArr[i - 1] + arr[i];
        }
        Tools.printArr(helpArr);
        return start == 0 ? (helpArr[end]) : (helpArr[end] - helpArr[start - 1]);
    }


}
