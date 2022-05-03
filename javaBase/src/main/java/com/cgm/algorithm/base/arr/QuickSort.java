package com.cgm.algorithm.base.arr;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 4, 5, 6, 7, 8, 10, 4};
        int[] partition = partition(arr, 0, arr.length-1);
        sort.printArr(partition);
        Qs(arr);
        sort.printArr(arr);
    }


    static void Qs(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);

    }


    static void process(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        int[] equalE = partition(arr, L, R);
        System.out.println(equalE[0]+"--"+equalE[1]);
        process(arr, L, equalE[0] - 1);
        process(arr, equalE[1] + 1, R);
    }

    //使得大于最后一个数的放在右边，小于等于在左边
    static void splitNum(int[] arr) {
        int lessEqualR = -1;
        int index = 0;
        int mostR = arr.length - 1;
        while (index < arr.length) {
            //当前数<=右边，当前数和区域下一个做交换 区域+1。当前数+1
            //当前数>右边，当前数+1
            if (arr[index] <= arr[mostR]) {
//               sort.swap(arr,lessEqualR+1,index);
//               lessEqualR++;
//               index++;
                sort.swap(arr, ++lessEqualR, index++);

            } else {
                index++;
            }
        }
    }


    //使数组中相等的在中间，左边都比他小，右边都比他大
    static void splitNum2(int[] arr) {
        //左区域
        int LBorder = -1;
        //右区域
        int RBorder = arr.length - 1;
        int index = 0;
        int mostR = arr.length - 1;
        while (index < RBorder) {
            //当前数<=右边，当前数和左区域下一个做交换 区域+1。当前数+1
            //当前数>右边，当前数和右区域前一个交换  当前数+1
            if (arr[index] < arr[mostR]) {
                sort.swap(arr, LBorder + 1, index);
                LBorder++;
                index++;

            } else if (arr[index] > arr[mostR]) {
                sort.swap(arr, RBorder-1, index);
                RBorder--;
            } else {
                index++;
            }
        }
        sort.swap(arr, RBorder, arr.length - 1);
    }

    /**
     * arr[L..R] 范围上，小于arr[R]的在左边
     * 等于在中间
     * 大于在右边
     * 返回等于区域的左右位置
     *
     * @param arr
     * @param L
     * @param R
     */
    static int[] partition(int[] arr, int L, int R) {
        //小于R 的左边界
        int lessR = L - 1;
        //大于R的右边界
        int moreL = R;
        int index = L;
        while (index < moreL) {
            if (arr[index] < arr[R]) {
                sort.swap(arr, lessR + 1, index);
                lessR++;
                index++;

            } else if (arr[index] > arr[R]) {
                sort.swap(arr, moreL - 1, index);
                moreL--;
            } else {
                index++;
            }
        }
        //最后一个要和大于区的第一个交换
        sort.swap(arr, moreL, R);
        return new int[]{lessR + 1, moreL};
    }
}
