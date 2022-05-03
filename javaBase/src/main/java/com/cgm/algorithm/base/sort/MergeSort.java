package com.cgm.algorithm.base.sort;

import com.cgm.algorithm.base.common.Tools;

public class MergeSort {

    //O(nlogn)
    public static void main(String[] args) {

        int[] arr = {2, 4, 3, 3, 7, 6, 5, 9, 8};
        process(arr, 0, arr.length - 1);
        Tools.printArr(arr);

    }


    public static void process(int[] arr, int L, int R) {
        if (L == R) return;
        int mid = L + ((R - L) >> 1);
        process(arr, L, mid);
        process(arr, mid + 1, R);
        merge(arr, L, mid, R);
    }


    public static void merge(int[] arr, int L, int mid, int R) {
        int[] helpArr = new int[R - L + 1];
        int p1 = L;
        int p2 = mid + 1;
        int i = 0;
        while (p1 <= mid && p2 <= R) {
            helpArr[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= mid) {
            helpArr[i++] = arr[p1++];
        }
        while (p2 <= R) {
            helpArr[i++] = arr[p2++];
        }
        for (int j = 0; j < helpArr.length; j++) {
            arr[L + j] = helpArr[j];
        }
    }
}
