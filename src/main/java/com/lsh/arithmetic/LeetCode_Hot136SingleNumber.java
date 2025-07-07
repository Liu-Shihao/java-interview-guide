package com.lsh.arithmetic;

/**
 * 136. 只出现一次的数字
 * https://leetcode.cn/problems/single-number/description/?envType=study-plan-v2&envId=top-100-liked
 * 给你一个 非空 整数数组 nums ，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * 
 * 你必须设计并实现线性时间复杂度的算法来解决此问题，且该算法只使用常量额外空间。
 * 
 * 使用哈希表记录每个数字出现的次数，但是时间和空间复杂度都是O(n)，不符合题意
 * 
 * 异或（XOR）：是一种位运算，在 Java 中用符号 ^ 表示。即：相同为0，不同为1
 * 如果两个位相同，结果为0；
 * 如果两个位不同，结果为1；
 * 异或运算满足交换律和结合律：a ^ b = b ^ a  ；  (a ^ b) ^ c = a ^ (b ^ c)
 * 任何数和0异或都是任何数本身：a ^ 0 = a
 * 任何数和自己异或都是0：a ^ a = 0
 * 异或的常见用法：
 * 1. 不使用临时变量，交换两个变量的值。
 * 2. 判断两个数是否符号相反。
 * 3. 找出数组中唯一不重复的数字
 */
public class LeetCode_Hot136SingleNumber {

    /**
     * 异或解法：
     * 把数组中的第一位，与数组中的其他位都异或，最后得到的结果就是只出现一次的数字
     * 原理：
     * 两个相同的数异或的结果为0。
     * 一个数与0异或的结果为这个数本身。
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        for(int i = 1; i< nums.length; i++){
            nums[0] ^= nums[i];
        }
        return nums[0]; 
    }
}
