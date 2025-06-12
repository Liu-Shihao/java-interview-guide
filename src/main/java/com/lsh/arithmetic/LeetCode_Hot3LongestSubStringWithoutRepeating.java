package com.lsh.arithmetic;

import java.util.HashSet;

/**
 * https://leetcode.cn/problems/longest-substring-without-repeating-characters/description/?envType=study-plan-v2&envId=top-100-liked
 * 3. 无重复字符的最长子串
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长 子串 的长度。
 * 示例 1:
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 */
public class LeetCode_Hot3LongestSubStringWithoutRepeating {

    /**
     * 滑动窗口解法： 我们使用滑动窗口技巧，维护一个窗口 [i, rk]，这个窗口内的字符串是 不含重复字符 的子串
     * 时间复杂度：O(n)，
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || "".equals(s)) return 0;
        //occ 是一个HashSet，保存当前窗口中出现的字符（用来判断是否有重复）。
        HashSet<Character> occ = new HashSet<>();
        int rk = -1, ans = 0;//rk 是右指针，初始值为 -1，表示还没开始滑动。
        // 左指针 i 从字符串左边向右滑动。
        for(int i = 0; i < s.length(); i++){
            if (i != 0){
                //当左指针 i 向右滑动一格，意味着之前的字符 s.charAt(i-1) 不再出现在窗口里，所以要从集合中移除。
                occ.remove(s.charAt(i-1));
            }
            //右指针 rk 不断向右移动（扩大窗口），直到遇到重复字符。
            while(rk + 1 < s.length() && !occ.contains(s.charAt(rk+1))){
                //只要下一个字符不在集合中，就加入集合，并更新右指针。
                occ.add(s.charAt(rk+1));
                ++rk;
            }
            ans = Math.max(ans,rk-i+1);
        }
        return ans;
    }
}
