package com.lsh.sort;

public class HeapSort {
    public static void heapSort(int[] arr) {
        int len = arr.length;
        // 构建大顶堆
        for (int i = len / 2 - 1; i >= 0; i--) {
            heapify(arr, len, i);
        }
        // 从堆顶开始，依次将最大元素放到数组末尾
        for (int i = len - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0); // 重新调整堆   
        }
    }
    private static void heapify(int[] arr, int len, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < len && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < len && arr[right] > arr[largest]) {
            largest = right;
        }
        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            heapify(arr, len, largest); // 递归调整子树 
        }
    }
    
}
