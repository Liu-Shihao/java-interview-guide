package com.lsh.arithmetic;

import java.util.*;

/**
 * https://leetcode.cn/problems/3sum/?envType=study-plan-v2&envId=top-100-liked
 * 15. 三数之和
 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 *
 * 示例 1：
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * 解释：
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
 * 不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
 * 注意，输出的顺序和三元组的顺序并不重要。
 * 示例 2：
 *
 * 输入：nums = [0,1,1]
 * 输出：[]
 * 解释：唯一可能的三元组和不为 0 。
 * 示例 3：
 *
 * 输入：nums = [0,0,0]
 * 输出：[[0,0,0]]
 * 解释：唯一可能的三元组和为 0 。
 */
public class LeetCode_Hot15ThreeSum {
    /**
     * 双指针解法
     * 首先对数组进行排序，避免出现[a,b,c]、[b,c,a]..情况
     * 首先for循环遍历每一个元素
     * 使用second和third指针
     *      second指针从first元素最右边开始递增
     *      third指针从数组最右侧开始递减
     *
     *  在使用for循环开始移动second指针
     *
     *  判断 如果 second指针值加上third指针值 大于 target（-nums[first]） 值, 则third--
     *  如果两指针相遇 second==third ，则结束结束本次循环
     *  如果second指针值加上third指针值 等于 target，则添加到ans集合中
     *
     *
     *  注意需要边界条件判断
     *  first 指针大于0的时候（即不是第一位元素），需要判断当前first的值是否等于前一位的值，如果相等则跳过
     *  second 指针同上判断：second指针不是首位元素时，需要判断值是否等于前一位，如果相等则跳过
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        ArrayList<List<Integer>> ans = new ArrayList<>();

        for(int first = 0; first < n; first++){
            // first不是第一位，并且不等于上一位的值
            if (first > 0 && nums[first] == nums[first-1]){
                continue;
            }
            // third指针从最右边开始
            int third = n - 1;
            int target = -nums[first];

            //second指针从first右边开始移动，小于数组边界
            for (int second = first + 1; second < n; second++){
                //如果second指针的值和上一次second指针的值相同，则跳过，会导致重复结果
                if (second > first + 1 && nums[second] == nums[second-1]){
                    continue;
                }
                // second指针在third指针左侧
                // 并且second指针的值加上third指针的值大于target，则third指针左移（因为数组此时已经有序）
                //并且判断条件只能是大于，不能!=
                while(second < third && nums[second] + nums[third] > target){
                    third--;
                }
                //如果两个指针相遇，则结束本次循环
                if (second == third){
                    break;
                }
                //如果second指针的值加上third指针的值等于target，则添加到结果集
                if (nums[second] + nums[third] == target){
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    ans.add(list);
                }
            }
        }
        return ans;
    }

}
