package com.darius.sort;

/**
 * 快速排序
 * <p>
 * Create by im_dsd 2020/9/19 5:44 下午
 */
class QuickSort {
    public static int[] quickSort(int[] array, int start, int end) {
        if (array == null || start < 0 || end >= array.length || start >= end) {
            return array;
        }
        int index = partition(array, start, end);
        quickSort(array, start, index - 1);
        quickSort(array, index + 1, end);
        return array;
    }

    public static int partition(int[] array, int start, int end) {
        int pivot = array[end];
        int i = (start - 1);
        for (int j = start; j < end; j++) {
            if (array[j] < pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, ++i, end);
        return i;
    }

    public static void swap(int[] array, int i, int j) {
        int swapTemp = array[i];
        array[i] = array[j];
        array[j] = swapTemp;
    }
}
