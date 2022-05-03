package com.cgm.algorithm.base.arr;

/**
 * 归并排序
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {3, 4, 51, 12, 2, 1, 9, 7, 6, 4};
        sort.printArr(arr);
        mergeSort2(arr);
        sort.printArr(arr);
    }

    /**
     * 递归
     *
     * @param arr
     */
    static void mergeSort1(int[] arr) {
        if (arr == null | arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    //arr[L,R] 范围上 的数保证有序
    static void process(int[] arr, int L, int R) {
        if (L == R) {
            return;
        }
        //防止溢出最大整数 2^31-1
        int mid = L + ((R - L) >> 1);
        //左边有序
        process(arr, L, mid);
        process(arr, mid + 1, R);
        merge(arr, L, mid, R);
    }

    static void merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = M + 1;
        while (p1 <= M && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        //剩下没走完的
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }

        for (int j = 0; j < help.length; j++) {
            arr[L + j] = help[j];
        }

    }


    static void mergeSort2(int[] arr) {
        if (arr == null | arr.length < 2) {
            return;
        }
        int step = 1;
        int N = arr.length;
        while (step < N) {
            //第一个左组开始
            int L = 0;
            //每个左组,通过步长找左组的位置
            while (L < N) {
                int M = 0;
                //N-1-L+1
                if (N - L >= step) {
                    M = L + step - 1;
                } else {
                    M = N - 1;
                }

                if (M == N - 1) {
                    break;
                }
                //L...M M+1
                //确定右组
                int R = 0;
                if (N - 1 - (M + 1) + 1 >= step) {
                    R = M + step;
                } else {
                    R = N - 1;
                }
                //L..M M+1..R
                merge(arr, L, M, R);
                if (R == N - 1) {
                    break;
                } else {
                    L = R + 1;
                }

            }

            //防止溢出最大整数 2^31-1
            if (step > (N / 2)) {
                break;
            }

            step *= 2;


        }
    }

}


