package com.lsh.arithmetic;

import java.util.*;

/**
 * 128. 最长连续序列
 * 中等
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 *
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 * 示例 1：
 *
 * 输入：nums = [100,4,200,1,3,2]
 * 输出：4
 * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
 * 示例 2：
 *
 * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
 * 输出：9
 * 示例 3：
 *
 * 输入：nums = [1,0,1,2]
 * 输出：3
 */
public class LeetCode_Hot03_LongestConsecutiveSequence {
    /**
     * 我们考虑枚举数组中的每个数 x，考虑以其为起点，不断尝试匹配 x+1,x+2,⋯ 是否存在，假设最长匹配到了 x+y，那么以 x 为起点的最长连续序列即为 x,x+1,x+2,⋯,x+y，其长度为 y+1，我们不断枚举并更新答案即可。
     * 对于匹配的过程，暴力的方法是 O(n) 遍历数组去看是否存在这个数，但其实更高效的方法是用一个哈希表存储数组中的数，这样查看一个数是否存在即能优化至 O(1) 的时间复杂度。
     * 仅仅是这样我们的算法时间复杂度最坏情况下还是会达到 O(n
     * 2
     *  )（即外层需要枚举 O(n) 个数，内层需要暴力匹配 O(n) 次），无法满足题目的要求。但仔细分析这个过程，我们会发现其中执行了很多不必要的枚举，如果已知有一个 x,x+1,x+2,⋯,x+y 的连续序列，而我们却重新从 x+1，x+2 或者是 x+y 处开始尝试匹配，那么得到的结果肯定不会优于枚举 x 为起点的答案，因此我们在外层循环的时候碰到这种情况跳过即可。
     * 那么怎么判断是否跳过呢？由于我们要枚举的数 x 一定是在数组中不存在前驱数 x−1 的，不然按照上面的分析我们会从 x−1 开始尝试匹配，因此我们每次在哈希表中检查是否存在 x−1 即能判断是否需要跳过了。
     *
     * 简单来说就是每个数都判断一次这个数是不是连续序列的开头那个数。
     * 怎么判断呢，就是用哈希表查找这个数前面一个数是否存在，即num-1在序列中是否存在。存在那这个数肯定不是开头，直接跳过。
     * 因此只需要对每个开头的数进行循环，直到这个序列不再连续，因此复杂度是O(n)。
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        // 处理边界情况
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 使用HashSet存储所有数字，便于O(1)时间查找
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        // 最长连续序列长度
        int longestStreak = 0;

        // 遍历所有数字
        for (int num : numSet) {
            // 只有当num-1不存在时，才开始计算连续序列
            // 这样可以确保我们只从连续序列的起点开始计算
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;
                
                // 不断查找下一个连续的数字
                while (numSet.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }
                
                // 更新最长连续序列的长度
                longestStreak = Math.max(longestStreak, currentStreak);
            }
            // 如果num-1存在，说明num不是连续序列的起点，跳过
        }
        
        return longestStreak;

    }
}
