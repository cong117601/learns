package com.cgm.algorithm.base.arr;

/**
 * 选择、冒泡、插入 O(n2)、二分查找
 */
public class sort {

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void selectionSort(int[] arr) {

        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            int minValueIndex = i;
            for (int j = i + 1; j < N; j++) {
                minValueIndex = arr[j] < arr[minValueIndex] ? j : minValueIndex;
            }
            //swap
            swap(arr, i, minValueIndex);
        }
    }

    public static void bubbleSort(int[] arr) {

        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 1; j <= i; j++) {
                if (arr[j - 1] > arr[j]) {
                    swap(arr, j - 1, j);
                }
            }
        }
    }


    public static void insertSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 0 - 0
        // 0 - 1
        // 0 - N-1
        int N = arr.length;
        for (int end = 1; end < N; end++) {
            int newNumIndex = end;
            while (newNumIndex - 1 >= 0 && arr[newNumIndex - 1] > arr[newNumIndex]) {
                swap(arr, newNumIndex - 1, newNumIndex);
                newNumIndex--;
            }
//            for (int preIndex = end - 1, preIndex >=0 && arr[preIndex] > arr[preIndex + 1];
//            preIndex--){
//                swap(arr, preIndex, preIndex+1);
//            }
        }
    }

    /**
     * 二分查找 对于有序数组
     *
     * @param arr
     * @param num
     * @return
     */
    public static int binarySearch(int[] arr, int num) {

        if (arr == null || arr.length == 0) {
            return -1;
        }
        int L = 0;
        int R = arr.length;

        int ans = -1;
        while (L <= R) {
            int mid = (L + R) / 2;
            if (arr[mid] == num) {
                ans = mid;
                return ans;
            } else if (arr[mid] > num) {
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return ans;
    }

    /**
     * 有序数组中，查找>=num的最左位置
     *
     * @param arr
     * @param num
     * @return
     */
    public static int moreLeftNoMoreNumOfIndex(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int ans = -1;
        int L = 0;
        int R = arr.length - 1;
        int mid = (L + R) / 2;
        while (L <= R) {
            if (arr[mid] >= num) {
                ans = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return ans;

    }

    public static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = {2, 4, 1, 9, 6, 7, 4, 3, 8};
        printArr(arr);
        bubbleSort(arr);
        printArr(arr);
    }
}
