package com.lsh.arithmetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * https://leetcode.cn/problems/subarray-sum-equals-k/description/?envType=study-plan-v2&envId=top-100-liked
 * 560. 和为 K 的子数组
 * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。
 * 子数组是数组中元素的连续非空序列。
 * 示例 1：
 * 输入：nums = [1,1,1], k = 2
 * 输出：2
 * 示例 2：
 * 输入：nums = [1,2,3], k = 3
 * 输出：2
 */
public class LeetCode_Hot560SubArraySumEqualsK {


    /**
     * 使用前缀和 + 哈希表 解法
     *
     * 前缀和： 前i个元素的和
     * prefixSum[i] = nums[0] + nums[1] + ... + nums[i-1]
     * 核心思想：子数组的和可以表示为两个前缀和的差
     * 子数组 nums[i..j] 的和可以表示为：sum(i,j)=prefixSum[j+1]−prefixSum[i]
     * 因此，如果我们能快速找到之前是否出现过 prefixSum[j] = currentSum - k，就能确定存在一个子数组的和为 k。
     * 哈希表优化:为了进一步优化到 O(n)，可以用哈希表记录前缀和的出现次数
     *
     * 时间复杂度：O(n)，只需遍历数组一次。
     * 空间复杂度：O(n)，哈希表存储前缀和。
     * @param nums
     * @param k
     * @return
     */
    public int subArraySum(int[] nums, int k){
        int count = 0, sum = 0;
        //使用哈希表记录前缀和的出现次数。
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1); //初始化：前缀和为0， 出现1次
        for(int num : nums){
            sum += num;
            //：计算当前前缀和 sum，并检查 sum - k 是否在哈希表中
            if (map.containsKey(sum - k)){
                //如果存在，说明存在子数组的和为 k。
                count += map.get(sum - k);
            }
            //更新哈希表中当前 sum 的出现次数。
            map.put(sum, map.getOrDefault(sum, 0) + 1 );
        }
        return count;
    }


    /**
     * 枚举法：
     *
     * 时间复杂度为O(n^2)
     * 空间复杂度 O(1)
     * @param nums
     * @param k
     * @return
     */
    public int subArraySum1(int[] nums, int k){
        int count = 0;
        //外层循环遍历所有可能的子数组其实位置，定义start从0 开始，表示每个子数组的开始位置
        for(int start = 0; start < nums.length; start++){
            int sum = 0;
            //内层循环从start开始向前（左）遍历，并记录当前所所有子元素的和
            for(int end = start; end >= 0; --end){
                sum += nums[end];
                if (sum == k){
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 暴力枚举获得数组的所有子数组: 出内存限制
     * 但是时间复杂度为O(n^3)
     * @return
     */
    public static int enumerateSubarrays(int[] arr, int k){
        ArrayList<int[]> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j =  i; j < arr.length; j++) {
                int[] subarray = new int[j - i + 1];
                for (int l = i; l <= j; l++) {
                    subarray[l - i] = arr[l];
                }
                list.add(subarray);
            }
        }
        int ans = 0;
        for(int[] subArr : list){
            int sum = 0;
            for (int i = 0; i < subArr.length; i++) {
                sum += subArr[i];
            }
            if(sum == k){
                ans++;
            }
        }
        return ans;
    }
}
