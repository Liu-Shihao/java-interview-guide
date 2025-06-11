package com.lsh.sort;

public class QuickSort {


    /**
     * 快速排序
     * 
     * @param arr
     * @param left
     * @param right
     */
    public static void quickSort(int[] array) {
        if (array == null || array.length < 2) return;
        quickSort(array, 0, array.length - 1);
    }


    public static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int index = partition(arr, left, right);
            quickSort(arr, left, index - 1);
            quickSort(arr, index + 1, right);
        }
    }
    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[right];//选择最后一个元素作为基准（也可以选第一个或随机）
        int i = left - 1;// i 维护小于等于 pivot 的区域末尾索引；
        for (int j = left; j < right; j++) {
            // 遍历数组，如果当前元素小于基准，则将其放到小于等于 pivot 的区域
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, right);//最后将 pivot 放到中间（i+1 位置）。
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
