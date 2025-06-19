package com.lsh.arithmetic;

/**
 * https://leetcode.cn/problems/move-zeroes/?envType=study-plan-v2&envId=top-100-liked
 * 283. 移动零
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 *
 * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
 * 示例 1:
 *
 * 输入: nums = [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 * 示例 2:
 *
 * 输入: nums = [0]
 * 输出: [0]
 */
public class LeetCode_Hot04MoveZeros {

    /**
     * 双指针解法
     * 使用双指针，左指针指向当前已经处理好的序列的尾部，右指针指向待处理序列的头部。
     * 右指针不断向右移动，每次右指针指向非零数，则将左右指针对应的数交换，同时左指针右移。
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        int n = nums.length, left = 0 , right = 0;

        while (right < n){
            if (nums[right] != 0){
                //nums[right]元素不为0，交换 right 和light的位置
                swap(nums,left,right);
                //left指针向右移动一位，
                left++;
            }
            //right指针向右移动（如果nums[right]=0，则不交换，）
            right++;
        }

    }

    public void swap(int[] nums, int left, int right){
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
}
