package com.lsh.sort;

public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {10, 3, 5, 7, 9, 2, 4, 6, 8, 1};
        mergeSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
    /**
     * 归并排序：
     *  归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。
     *  将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。    
     * @param arr
     */
    public static void mergeSort(int[] arr){
        int[] temp = new int[arr.length]; // 在排序前，先建好一个长度等于原数组长度的临时数组，避免递归中频繁开辟空间
        mergeSort(arr, 0, arr.length - 1, temp); 
    }

    public static void mergeSort(int[] arr, int left, int right, int[] temp){
        if (left < right){
            // int mid = (left + right) / 2; 
            int mid = left + (right - left) / 2;//防止整数溢出的中点计算方式，避免 left + right 时发生整数溢出
            mergeSort(arr, left, mid, temp); // 左半边排序
            mergeSort(arr, mid + 1, right, temp);// 右半边排序
            merge(arr, left, mid, right, temp);// 合并两个有序子数组
        }

    }


     // 合并两个有序子数组
     private static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left;    // 左半部分起始索引
        int j = mid + 1;  // 右半部分起始索引
        int k = 0;        // temp 数组的索引

        // 比较左右两部分的元素，按升序放入 temp
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        // 处理剩余元素（左半部分可能还有剩余）
        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        // 处理剩余元素（右半部分可能还有剩余）
        while (j <= right) {
            temp[k++] = arr[j++];
        }

        // 将 temp 中的数据复制回原数组
        System.arraycopy(temp, 0, arr, left, k);
    }

}
