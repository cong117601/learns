package com.cgm.algorithm.base.sort;

import com.cgm.algorithm.base.common.Tools;

import java.util.Arrays;

public class SelectSort {


    public static void main(String[] args) {
        int testTime = 3;
        int maxLength = 50;
        int maxValue = 100;
        boolean flag = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = Tools.generateRangeArray(maxLength, maxValue);
            Tools.printArr(arr1);
            int[] arr2 = Tools.copyArr(arr1);
            Arrays.sort(arr2);
            selectSort(arr1);
            Tools.printArr(arr1);
            if (!Tools.isEqual(arr1, arr2)) {
                flag = false;
                break;
            }
        }

        System.out.println(flag ? "没问题" : "有错误");

    }


    public static void selectSort(int[] arr) {

        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;
            }

            Tools.swapByThree(arr, minIndex, i);
        }
    }
}
