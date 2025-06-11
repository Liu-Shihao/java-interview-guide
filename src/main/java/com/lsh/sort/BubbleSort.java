package com.lsh.sort;

public class BubbleSort {

    /**
     *冒泡排序:
     *  比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     *  对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
     *  针对所有的元素重复以上的步骤，除了最后一个。
     *  持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     *
     * @param arr
     * @return
     */
    public void bubbleSort(int[] arr){
        //外层循环控制排序的轮数。每次外层循环结束后，数组中最大的元素会被“冒泡”到数组的末尾。
        for (int i = 0; i < arr.length - 1; i++) {
            //内层循环控制每一轮排序的次数。每次内层循环结束后，数组中最大的元素会被“冒泡”到数组的末尾。
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]){
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    
}
