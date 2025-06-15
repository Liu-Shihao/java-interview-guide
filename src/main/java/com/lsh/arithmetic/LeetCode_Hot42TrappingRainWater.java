package com.lsh.arithmetic;

/**
 * https://leetcode.cn/problems/trapping-rain-water/description/?envType=study-plan-v2&envId=top-100-liked
 * 42. 接雨水  Hard
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出：6
 * 解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
 */
public class LeetCode_Hot42TrappingRainWater {

    //TODO 动态规划解法



    /**
     * 双指针解法的核心思想是：对于每一个位置，能接到的雨水量取决于其左边和右边的最大高度的较小值减去当前位置的高度。
     * 设定两个指针，分别从数组的左右两端向中间移动。
     * 用 leftMax 和 rightMax 分别记录当前左侧和右侧的最大高度。
     * 每次比较左右指针对应的高度，较小的一侧决定当前能接的雨水量。
     * 如果左侧高度较小，则计算 leftMax - height[left] ，累加到结果，并左指针右移；否则计算 rightMax - height[right] ，累加到结果，并右指针左移。
     * 直到左右指针相遇，即遍历完整个数组。
     * 空间复杂度为 O(1)，时间复杂度为 O(n)。
     * @param height
     * @return
     */
    public int trap1(int[] height) {
        int ans = 0;
        //用两个指针 left 和 right，分别指向数组最左和最右。
        int left = 0, right = height.length - 1;
        //用 leftMax 记录左边扫描到的最大高度，用 rightMax 记录右边扫描到的最大高度。
        int leftMax = 0, rightMax = 0;
        
        while(left < right){
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            //每次比较 height[left] 和 height[right]：
            if (height[left] < height[right]){
                //如果 height[left] < height[right]，说明左边较矮，决定先处理左边，因为接雨水量取决于较低的一边。
                //此时，leftMax 肯定比 rightMax 小或等于（因为 height[left]<height[right]），所以能接雨水量是 leftMax - height[left]。
                ans += leftMax - height[left];
                left++;
            }else {
                //说明右边的柱子比较矮，那么对于柱子 i，它能接的雨水量就是 rightMax - height[i]。
                ans += rightMax - height[right];
                right--;
            }
        }
        return ans;
    }
    
}
