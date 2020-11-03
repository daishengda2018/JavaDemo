package com.darius.sort;

import org.junit.Assert;


/**
 * Create by im_dsd 2020/9/19 6:38 下午
 */
public class QuickSortTest {

    @org.junit.Test
    public void sort() {
        int[] actual = { 9, 5, 1, 0, 6, 2, 3, 4, 7, 8 };
        int[] expected = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        QuickSort.quickSort(actual, 0, actual.length - 1);
        Assert.assertArrayEquals(expected, actual);

        int[] array1 = new int[0];
        QuickSort.quickSort(array1, 0, actual.length - 1);
        Assert.assertArrayEquals(array1, array1);
    }
}