package com.lsh.sort;

public class InsertionSort {
    /**
     * 插入排序:
     *  从第一个元素开始，该元素可以认为已经被排序(从第二个元素开始，依次遍历数组。)
     *  对于每个元素，向前比较已排序区间的元素，找到合适的位置插入。
     *  插入时，已排序区间中比当前元素大的元素都向后移动一位。
     * @param arr
     */
    public void insertionSort(int[] arr){
        for (int i = 1; i < arr.length; i++) {
            //temp记录当前未排序的值
            int temp = arr[i];
            int j = i -1;//j表示已排序的最后一个元素的索引
            while (j >= 0 && arr[j] > temp){
                arr[j + 1] = arr[j];//
                j--;
            }
            //经过while循环，已经找到了当前元素(最开始的arr[i])的合适位置，将其插入到该位置（j+1）
            //j+1是因为j--了
            arr[j + 1] = temp;
        }
    }
}
