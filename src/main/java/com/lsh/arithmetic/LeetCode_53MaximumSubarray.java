package com.lsh.arithmetic;

/**
 * 53. 最大子数组和
 * https://leetcode.cn/problems/maximum-subarray/description/?envType=study-plan-v2&envId=top-100-liked
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 
 * 子数组是数组中的一个连续部分。
 * 示例 1：
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 * 
 * 示例 2：
 * 输入：nums = [1]
 * 输出：1
 * 
 * 示例 3：
 * 输入：nums = [5,4,-1,7,8]
 * 输出：23
 * 
 * 提示：
 * 1 <= nums.length <= 105
 * -104 <= nums[i] <= 104
 * 
 * 进阶：如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的 分治法 求解。
 */

public class LeetCode_53MaximumSubarray {
   
    /**
     * 动态规划解法：
     * 定义两个变量：
     * pre：表示以当前元素结尾的最大子数组和，
     * 更新以当前元素结尾的最大子数组和，如果之前的子数组和是负数，那么加上当前元素值，只会让当前元素值变小，所以直接取当前元素值为当前最大值
     * maxAns：表示全局最大子数组和
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }
    
}
