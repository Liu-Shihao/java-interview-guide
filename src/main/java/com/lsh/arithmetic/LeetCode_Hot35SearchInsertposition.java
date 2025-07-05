package com.lsh.arithmetic;

/**
 * 35. 搜索插入位置
 * https://leetcode.cn/problems/search-insert-position/description/?envType=study-plan-v2&envId=top-100-liked
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 * 请必须使用时间复杂度为 O(log n) 的算法。
 * 
 * 示例 1:
 * 输入: nums = [1,3,5,6], target = 5
 * 输出: 2
 * 
 */
public class LeetCode_Hot35SearchInsertposition {

    /**
     * 二分查找算法（Binary Search）是一种在有序数组中查找某个目标值的高效算法。
     * 其基本思想是：每次都将查找范围缩小一半，直到找到目标值或查找范围为空为止。
     * 设定查找区间的左右边界（通常为 left 和 right）
     * 计算中间位置 mid = (left + right) / 2
     * 比较中间元素 nums[mid] 与目标值 target：
     *    - 如果相等，返回 mid。
     *    - 如果 target 小于 nums[mid]，则在左半区间继续查找（right = mid - 1）。
     *    - 如果 target 大于 nums[mid]，则在右半区间继续查找（left = mid + 1）。
     * 重复上述过程，直到 left > right，查找结束。
     * 时间复杂度：O(logn)
     * 空间复杂度：O(1)
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if(target == nums[mid]){
                return mid;
            }else if(target > nums[mid]){
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return left;
    }
}
