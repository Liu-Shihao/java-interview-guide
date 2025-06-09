package com.lsh.arithmetic;

/**
 * https://leetcode.cn/problems/container-with-most-water/?envType=study-plan-v2&envId=top-100-liked
 * 11. 盛最多水的容器
 * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 输入：[1,8,6,2,5,4,8,3,7]
 * 输出：49
 * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 */
public class LeetCode_Hot11_ContainerWithMostWater {

    /**
     * 双指针解法：
     * 1. 用两个指针分别指向数组的开头和结尾。
     * 2. 计算当前两指针所围成的面积，并更新最大值。
     * 3. 移动较小的指针，因为移动较大的指针只会减小面积，而移动较小的指针可能会增加面积。
     * 4. 重复步骤 2 和 3，直到两个指针相遇。
     * 5. 返回最大值。
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * @param height
     */
    public int maxArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int left = 0, right = height.length - 1;
        int maxArea = 0;
        while (left < right) {
            int high = Math.min(height[left], height[right]);
            int length = right - left;
            int currentArea = high * length;
            maxArea = Math.max(maxArea, currentArea);
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }


    /**
     * 暴力解法：会超出时间限制
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     * 1. 遍历数组，对于每个元素，计算它与其他元素组成的容器的面积。
     * 2. 更新最大值。
     * 3. 返回最大值。
     * @param height
     * @return
     */
    public int maxArea2(int[] height) {
        if (height == null || height.length == 0){
            return 0;
        }
        int maxArea = 0;

        for(int left = 0; left < height.length; left++){
            for (int right = left+1; right < height.length; right++) {
                int high = Math.min(height[left], height[right]);
                int length = right - left;
                int currentMaxArea = high * length;
                maxArea = Math.max(maxArea, currentMaxArea);
            }
        }
        return maxArea;
    }
}
