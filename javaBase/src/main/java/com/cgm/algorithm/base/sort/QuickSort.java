package com.cgm.algorithm.base.sort;

import com.cgm.algorithm.base.arr.sort;
import com.cgm.algorithm.base.common.Tools;

import java.util.HashMap;

public class QuickSort {


    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 4, 5, 6, 7, 8, 10, 4};
        process(arr, 0, arr.length - 1);
        Tools.printArr(arr);
    }


    public static void process(int[] arr, int L, int R) {

        if (L >= R) {
            return;
        }
        //随机快排
        Tools.swap(arr, L + ((int) Math.random() * (R - L + 1)), R);
        int[] equalsIndex = getEqualsIndex(arr, L, R);
        System.out.println(equalsIndex[0] + " " + equalsIndex[1]);
        process(arr, L, equalsIndex[0] - 1);
        process(arr, equalsIndex[1] + 1, R);


    }

    // 等于的放中间，小于放左边，大于放右边，返回等于区域的左右边界
    public static int[] getEqualsIndex(int[] arr, int L, int R) {
        if (L > R) {
            return new int[]{-1, -1};
        }
        if (L == R) {
            return new int[]{L, R};
        }
        //=左边界
        int less = L - 1;
        //=右边界
        int more = R;
        //当前位置
        int index = L;

        while (index < more) {
            if (arr[index] == arr[R]) {
                index++;
            } else if (arr[index] < arr[R]) {
                Tools.swapByThree(arr, less + 1, index);
                less++;
                index++;
            } else {
                Tools.swapByThree(arr, index, more - 1);
                more--;
            }
        }
        Tools.swapByThree(arr, more, R);

        return new int[]{less + 1, more};

    }



}
