package com.cgm.algorithm.base.dichotomize;

/**
 * 二分内容
 */
public class DichotomizeSubject {

    public static void main(String[] args) {
        int[] arr = {1, 2, 4, 4, 5, 5, 5, 6, 8, 9};

        System.out.println(dichotomizeSearch(arr, 3));
    }

    public static boolean dichotomizeSearch(int[] arr, int num) {

        if (arr == null || arr.length == 0) {
            return false;
        }

        int L = 0;
        int R = arr.length - 1;
        while (L < R) { // 至少两个数  一个数退出
            int mid = L + ((R - L) >> 1);
            if (arr[mid] == num) {
                return true;
            }
            if (arr[mid] > num) {
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return arr[L] == num; //一个数验证
    }


    //有序arr上找出 满足大于等于num的最左边的位置
    // 1 3  3 4 4  7 9 11 12
    public static int lastLeftIndex(int[] arr, int num) {

        if (arr == null || arr.length == 0) {
            return -1;
        }
        int L = 0;
        int R = arr.length - 1;
        int mid = 0;
        int ans = -1;
        while (L <= R) {
            mid = L + ((R - L) >> 1);
            if (arr[mid] >= num) {
                ans = mid;
                R = mid - 1;
                //L = mid + 1;
            } else {
                L = mid + 1;
            }
        }

        return ans;
    }


    //局部最小值问题
    public static int partMin(int[] arr) {

        if (arr == null || arr.length == 0) return -1;

        if (arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }

        if (arr[arr.length - 2] < arr[arr.length - 1]) {
            return arr.length - 1;
        }


        int L = 1;
        int R = arr.length - 2;
        int mid = 0;
        while (L < R) {
            mid = L + ((R - L) >> 1);
            if (arr[mid - 1] > arr[mid]) {
                R = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) {
                L = mid + 1;
            }else{
                return mid;
            }
        }
        return L;
    }
}
