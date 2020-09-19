package com.darius.sort;

/**
 * 快速排序
 *
 * Create by im_dsd 2020/9/19 5:44 下午
 */
class QuickSort {
    public static int[] Sort(int[] array, int start, int end) {
        if (array == null || array.length == 0 || start < 0 || end >= array.length) {
            return new int[0];
        }

        return array;
    }

    public static int partition(int[] array, int start, int end) {
        int pivot = array[end];
        int i = (start - 1);
        for (int j = start; j < end; j ++) {
            if (array[j] < pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, end);
        return  i + 1;
    }

    public static void swap(int[] array, int i, int j) {
        int swapTemp = array[i];
        array[i] = array[j];
        array[j] = swapTemp;
    }
}
