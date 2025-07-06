package com.lsh.arithmetic;

/**
 * 70. 爬楼梯  动态规划
 * https://leetcode.cn/problems/climbing-stairs/description/?envType=study-plan-v2&envId=top-100-liked
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 */
public class LeetCode_Hot70ClimbingStairs {
    /**
     * 因为每次只能爬1阶或2阶，所以爬到第x级台阶的情况有两种：
     * 1.从第x-1级台阶爬1级到第x级
     * 2.从第x-2级台阶爬2级到第x级
     * 
     * 爬到第x级台阶的方案数 = 爬到第x-1级台阶的方案数 + 爬到第x-2级台阶的方案数
     * f(x)= f(x-1) + f(x-2)
     * 
     * 递归解法： 超时
     * 时间复杂度：O(2^n)
     * 空间复杂度：O(n)
     * 计算climbStairs(5)会重复计算climbStairs(3)和climbStairs(2)多次。
     * 可以使用记忆化递归优化，避免重复计算。
     */
    public int climbStairs(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        return climbStairs(n - 1) + climbStairs(n - 2);
    }
    // climbStairs(5) 
    // = climbStairs(4) + climbStairs(3)  
    // = [climbStairs(3) + climbStairs(2)] + [climbStairs(2) + climbStairs(1)]  
    // = [[climbStairs(2) + climbStairs(1)] + climbStairs(2)] + [climbStairs(2) + climbStairs(1)]  
    // = [[2 + 1] + 2] + [2 + 1]  
    // = 3 + 2 + 2 + 1  
    // = 8
    // 可以看到：
    // climbStairs(3) 被计算了 ​​2 次​​（在 climbStairs(5) 和 climbStairs(4) 里各算一次）。
    // climbStairs(2) 被计算了 ​​3 次​​（在 climbStairs(4)、climbStairs(3) 和 climbStairs(5) 里各算一次）。
    // climbStairs(1) 被计算了 ​​2 次​​（在 climbStairs(3) 和 climbStairs(2) 里各算一次）。
    // ​​问题​​：随着 n 增大，重复计算会呈指数级增长（O(2^n)），导致超时。


    /**
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param n
     * @return
     */
    public int climbStairs2(int n) {
        //存储中间结果，避免重复计算
        int[] memo = new int[n + 1];
        return climbStairsMemory(n, memo);
    }
    /**
     * 递归解法优化： 记忆化递归
     * @param n
     * @return
     */
    public int climbStairsMemory(int n, int[] memo) {
        if (memo[n] > 0) {
            return memo[n];
        }
        if (n == 1) {
            memo[n] = 1;
        }else if (n == 2) {
            memo[n] = 2;
        }else{
            memo[n] = climbStairsMemory(n - 1, memo) + climbStairsMemory(n - 2, memo);
        }
        return memo[n];
    }


    /**
     * 解法三：使用动态规划
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param n
     * @return
     */
    public int climbStairs3(int n){
        if (n==1) {
            return 1;
        }
        //注意dp数组的长度是n+1，第n阶台阶才不会数组越界
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }


    /**
     * 解法四：斐波那契数列
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * @param n
     * @return
     */
    public int climbStairs4(int n){
        if (n == 1) {
            return 1;
        }
        int first = 1;
        int second = 2;
        //避免了重复计算，通过保存中间结果（first和second）逐步推导，空间复杂度仅为O(1)。
        for (int i = 3; i <= n; i++) {
            int third = first + second;
            first = second;
            second = third;
        }
        return second;
    }
}
