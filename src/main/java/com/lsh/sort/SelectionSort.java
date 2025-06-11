package com.lsh.sort;

public class SelectionSort {
    
    /**
     * 选择排序：
     *  首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置。
     *  再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
     *  重复第二步，直到所有元素均排序完毕。
     * @param arr
     */
    public void selectionSort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]){
                    minIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }
}
