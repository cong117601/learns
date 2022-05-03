package com.cgm.algorithm.base.sort;

import com.cgm.algorithm.base.common.Tools;

/**
 * 先让数组都变为大根堆 建立堆的过程：
 * 1）从上到下的方法，O（nlogN）
 * 2) 从下到上 O（N）
 * <p>
 * 把堆的最大值和堆末尾的值交换，然后减少堆大小，再去调整堆，一直重复。 0(nlogn)
 * 堆大小减为0之后，排序完成
 */
public class HeapSort {


    public static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            Tools.swapByThree(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }


    public static void heapify(int[] arr, int index, int heapSize) {

        int left = 2 * index + 1;
        while (left < heapSize) { //如果有左孩子
            //确定剩余堆中最大的位置
            int largest = (left + 1 < heapSize) && (arr[left + 1] > arr[left]) ? left + 1 : left;
            int maxValueIndex = arr[largest] > arr[index] ? largest : index;
            if (maxValueIndex == index) {
                break;
            }
            Tools.swapByThree(arr, maxValueIndex, index);
            index = maxValueIndex;
            left = index * 2 + 1;
        }
    }


    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        //初始化成大根堆
//        for (int i = 0; i < arr.length; i++) {
//            heapInsert(arr, i);
//        }

        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        int heapSize = arr.length;
        Tools.swapByThree(arr, 0, --heapSize); //大根堆第0位置和最后一个交换，并且把数组大小减一

        while (heapSize > 0) {
            //调整堆
            heapify(arr, 0, heapSize);
            Tools.swapByThree(arr, 0, --heapSize);
        }


    }


    public static void main(String[] args) {
        int[] arr = {2, 1, 5, 4, 3};
        heapSort(arr);
        Tools.printArr(arr);
    }
}
