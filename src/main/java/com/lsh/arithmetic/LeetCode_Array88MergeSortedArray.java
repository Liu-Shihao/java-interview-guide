package com.lsh.arithmetic;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/merge-sorted-array/description/?envType=study-plan-v2&envId=top-interview-150
 * 88. 合并两个有序数组
 */
public class LeetCode_Array88MergeSortedArray {

    /**
     * 解法1：双指针
     * 使用双指针，一个指向nums1，一个指向nums2，比较两个指针所指的元素，将较小的元素放入结果数组中。
     * 如果p1指针走完，将cur指向p2;如果p2指针走完，则cur指向p1
     * 如果p1指针的值小于p2指针的值，则将p1指向的值放入结果数组，并将p1指针后移一位；否则，将p2指针的值放入结果数组，并将p2指针后移一位。
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n){
        int p1 = 0;
        int p2 = 0;
        int[] sorted = new int[m+n];
        int cur;
        while(p1 < m || p2 < n){
            if (p1 == m){
                cur = nums2[p2++];
            } else if (p2 == n) {
                cur = nums1[p1++];
            }else if (nums1[p1] < nums2[p2]){
                cur = nums1[p1++];
            }else {
                cur = nums2[p2++];
            }
            //两个指针共移动的次数为当前的index（需要减1，因为上一步指针++）
            sorted[p1 + p2 -1] = cur;
        }
        for (int i = 0; i != m + n; i++){
            nums1[i] = sorted[i];
        }
    }

    /**
     * 解法2：排序
     * 时间复杂度为O((m+n)log(m+n))
     * 空间复杂度：O(log(m+n))
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge2(int[] nums1, int m, int[] nums2, int n){
        for(int i = 0; i < n; i++){
            nums1[m+i] = nums2[i];
        }
        Arrays.sort(nums1);
    }
}
