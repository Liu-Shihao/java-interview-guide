package com.lsh.arithmetic;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 169. 多数元素
 * https://leetcode.cn/problems/majority-element/description/?envType=study-plan-v2&envId=top-100-liked
 * 给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 */
public class LeetCode_Hot169MajorityElement {
    /**
     * 暴力解法：遍历数组中每个元素，使用哈希表计算次数。
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        HashMap<Integer,Integer> count = new HashMap<>();
        for(int n : nums){
            count.put(n, count.getOrDefault(n, 0) + 1);
        }
        for(int n:count.keySet()){
            if(count.get(n) > nums.length / 2){
                return n;
            }
        }
        return 0;
    }

    /**
     * 排序解法：将数组排序，下标为n/2的元素一定是众数。
     * 时间复杂度：O(nlogn)
     * 空间复杂度：O(logn)
     * @param nums
     * @return
     */
    public int majorityElement2(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }
}
