package com.cgm.algorithm.base.merge;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

/**
 * 小和问题
 * <p>
 * 数组中每个位置上 前面比它小的数据和
 * 6,5,4,3, 7,  9
 * <p>
 * 0 0 0 0 18  25
 */
public class Code_SmallKernel {


    public static void main(String[] args) {
        Integer[] integers = {};
        System.out.println(integers.length);

    }


    public static int process(int[] arr, int L, int R) {
        if (arr == null || arr.length < 2) return -1;
        if (L == R) return 0;
        int mid = L + ((R - L) >> 1);
        return process(arr, L, mid) + process(arr, mid + 1, R) + merge(arr, L, mid, R);
    }


    public static int merge(int[] arr, int L, int mid, int R) {

        int[] helpArr = new int[R - L + 1];
        int p1 = L;
        int p2 = mid + 1;
        int i = 0;
        int res = 0;
        while (p1 <= L && p2 <= R) {
            res += arr[p1] < arr[p2] ? (R - p2 + 1) * arr[p1] : 0;
            helpArr[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= L) {
            helpArr[i++] = arr[p1++];
        }
        while (p2 <= R) {
            helpArr[i++] = arr[p2++];
        }
        for (int num : helpArr) {
            arr[L++] = num;
        }
        return res;
    }
}
