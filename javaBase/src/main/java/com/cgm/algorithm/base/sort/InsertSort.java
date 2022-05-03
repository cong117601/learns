package com.cgm.algorithm.base.sort;

import com.cgm.algorithm.base.common.Tools;

import java.util.Arrays;

/**
 * 插入排序
 */
public class InsertSort {


    public static void main(String[] args) {


        int testTime = 3;
        int maxLength = 10;
        int maxValue = 10;
        boolean flag = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = Tools.generateRangeArray(maxLength, maxValue);
            Tools.printArr(arr1);
            int[] arr2 = Tools.copyArr(arr1);
            Arrays.sort(arr2);
            insertSort(arr1);
            Tools.printArr(arr1);
            if (!Tools.isEqual(arr1, arr2)) {
                flag = false;
                break;
            }
        }

        System.out.println(flag ? "没问题" : "有错误");

    }


    public static void insertSort(int[] arr) {

        if (arr == null || arr.length < 2) {
            return;
        }


        //0 ~ 0
        //0 ~ 1
        //0 ~ 2

        for (int i = 1; i < arr.length; i++) {
            int end = i;
            while (end - 1 >= 0 && arr[end - 1] > arr[end]) {
                Tools.swap(arr, end - 1, end);
                end--;
            }
        }
    }
}
