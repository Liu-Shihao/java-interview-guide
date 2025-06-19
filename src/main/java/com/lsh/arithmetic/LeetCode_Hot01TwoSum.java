package com.lsh.arithmetic;

import java.util.HashMap;

/**
 * https://leetcode.cn/problems/two-sum/?envType=study-plan-v2&envId=top-100-liked
 * 1. 两数之和
 * 难度：简单
 *
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素。
 * 你可以按任意顺序返回答案。
 * 示例 1：
 *
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 * 示例 2：
 *
 * 输入：nums = [3,2,4], target = 6
 * 输出：[1,2]
 * 示例 3：
 *
 * 输入：nums = [3,3], target = 6
 * 输出：[0,1]
 */
public class LeetCode_Hot01TwoSum {
    /**
     * 解法1： 枚举数组中的每一个数 x，寻找数组中是否存在 target-x
     * 复杂度分析
     * 时间复杂度：O(N2)，其中 N 是数组中的元素数量。最坏情况下数组中任意两个数都要被匹配一次。
     *
     * 空间复杂度：O(1)。
     * 
     * 这里使用的是 i++ ，但在这种for循环的上下文中，使用 i++ 或 ++i 实际上没有区别，因为：
     *
     * 1. 在for循环的第三部分（更新表达式）中，自增操作的返回值不会被使用
     * 2. 只有自增的副作用（使i的值增加1）是重要的
     * 3. 循环执行时，无论是 i++ 还是 ++i ，i的值都会增加1，且在下一次循环条件判断前完成
     * 所以在for循环的更新表达式中， i++ 和 ++i 的效果完全相同。只有在表达式的值被使用时（如赋值操作或条件判断），两者才会表现出差异。
     *
     * 从性能角度看，在现代编译器优化下，两者通常没有明显差异。选择哪一个主要是编码风格和可读性的问题。
     * 
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum1(int[] nums, int target) {
        for (int i = 0; i < nums.length-1; i++) {
            for (int j = i +1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    /**
     * 解法2：
     * 创建一个哈希表，对于每一个 x，我们首先查询哈希表中是否存在 target - x，然后将 x 插入到哈希表中，即可保证不会让 x 和自己匹配。
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum2(int[] nums, int target){
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            if (hashMap.containsKey(target-nums[i])){
                return new int[]{i,hashMap.get(target-nums[i])};
            }
            hashMap.put(nums[i], i);
        }
        return null;
    }


    public static void main(String[] args) {

    }

}
