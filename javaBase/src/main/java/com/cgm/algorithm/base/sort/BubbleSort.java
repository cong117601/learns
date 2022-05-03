package com.cgm.algorithm.base.sort;

import com.cgm.algorithm.base.common.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BubbleSort {

    public static void main(String[] args) {

        int testTime = 100;
        int maxLength = 10;
        int maxValue = 10;
        boolean flag = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = Tools.generateRangeArray(maxLength, maxValue);
            Tools.printArr(arr1);
            int[] arr2 = Tools.copyArr(arr1);
            Arrays.sort(arr2);
            bubbleSort(arr1);
            if (!Tools.isEqual(arr1, arr2)) {
                flag = false;
                break;
            }
        }

        System.out.println(flag ? "没问题" : "有错误");

    }


    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        //0-n
        //0-n-1
        boolean flag = false;
        for (int i = arr.length - 1; i >= 0; i--) {
            flag = true;
            for (int j = 1; j <= i; j++) {
                if (arr[j - 1] > arr[j]) {
                    Tools.swap(arr, j - 1, j);
                    flag = false;
                }
            }
            if (flag) break;
        }
    }




}
