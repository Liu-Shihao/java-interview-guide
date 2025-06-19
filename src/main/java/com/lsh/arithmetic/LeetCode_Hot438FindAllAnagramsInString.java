package com.lsh.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.cn/problems/find-all-anagrams-in-a-string/description/?envType=study-plan-v2&envId=top-100-liked
 * 438. 找到字符串中所有字母异位词
 *
 * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 * 输入: s = "cbaebabacd", p = "abc"
 * 输出: [0,6]
 * 解释:
 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
 */
public class LeetCode_Hot438FindAllAnagramsInString {
    /**
     * 根据题目要求，我们需要在字符串 s 寻找字符串 p 的异位词。
     * 因为字符串 p 的异位词的长度一定与字符串 p 的长度相同，
     * 所以我们可以在字符串 s 中构造一个长度为与字符串 p 的长度相同的滑动窗口，
     * 并在滑动中维护窗口中每种字母的数量；
     * 当窗口中每种字母的数量与字符串 p 中每种字母的数量相同时，则说明当前窗口为字符串 p 的异位词。
     *
     * @param s
     * @param p
     * @return
     */
    public List<Integer> findAnagrams1(String s, String p) {
        int sLen = s.length(), pLen = p.length();
        if (sLen < pLen) return new ArrayList<>();

        List<Integer> ans = new ArrayList<>();
        int[] sCount = new int[26];// 统计 s 的前 pLen 个字母的出现次数（即第一个窗口）。
        int[] pCount = new int[26];// 统计 p 中每个字母的出现次数。

        //获取s第一个窗口和p中出每个字母出现的次数
        for(int i = 0; i<pLen;i++){
            ++sCount[s.charAt(i) - 'a'];
            ++pCount[p.charAt(i) - 'a'];
        }
        //比较 sCount 和 pCount，如果相等，说明第一个窗口是 p 的字母异位词，将起始索引 0 加入结果列表。
        if (Arrays.equals(sCount, pCount)){
            ans.add(0);//即从s第一个元素开始就是p的字母异位词，把索引0加入到ans中
        }
        // 滑动窗口：每次移动一位，更新sCount并检查
        for(int i = 0; i < sLen-pLen; i++){
            // i 索引从0 开始，但是循环内首先把最左侧字符的计数减一
            --sCount[s.charAt(i) - 'a'];//移除窗口左侧的字符,将窗口左侧的字符计数-1
            ++sCount[s.charAt(i+pLen) - 'a'];//添加窗口右侧的新字符, 添加窗口右侧字符的数量
            // 检查当前窗口是否是字母异位词
            if (Arrays.equals(sCount, pCount)){
                ans.add(i+1);//注意此时索引是i+1,因为之前已经移除了索引为i的字符。
            }
        }
        return ans;

    }

    /**
     * 优化
     * @param s
     * @param p
     * @return
     */
    public List<Integer> findAnagrams(String s, String p) {
        int sLen = s.length(), pLen = p.length();
        if (sLen < pLen) return new ArrayList<>();
        ArrayList<Integer> ans = new ArrayList<>();
        //count 是一个长度为 26 的数组，用于统计字母频率（'a' 到 'z'）。
        int[] count = new int[26];
        for(int i = 0; i < pLen; i++){
            ++count[s.charAt(i) - 'a'];// 统计 s 的前 pLen 个字母
            --count[p.charAt(i) - 'a'];// 统计 p 的所有字母

        }
        //count[i] > 0：s 的该字母比 p 多。
        //count[i] < 0：s 的该字母比 p 少。
        //count[i] == 0：s 和 p 的该字母数量相同。

        int differ = 0;//differ 表示 count 数组中不为 0 的字母数量。
        for (int i = 0; i < 26; i++) {
            if (count[i] != 0){
                ++differ;
            }
        }
        if (differ == 0) {
            ans.add(0);//如果 differ == 0，说明 s 的前 pLen 个字母和 p 的字母频率完全相同，即它们是字母异位词。
        }
        for (int i = 0; i < sLen - pLen; i++) {
            //移除左边界字符 s[i]
            if (count[s.charAt(i) - 'a'] == 1){
                //如果该字母原始频率是1，那移除之后变为0，维护differ，需要-1
                --differ;// 移除后该字母频率变为 0，差异减少
            } else if (count[s.charAt(i) - 'a'] == 0) {
                //如果该字母原来频率为0，那么移除后则变为-1，需要增加differ
                ++differ;// 移除后该字母频率变为 -1，差异增加
            }
            --count[s.charAt(i) - 'a'];

            // 添加右边界字符 s[i + pLen]
            if (count[s.charAt(i + pLen) - 'a'] == -1){
                --differ;// 添加后该字母频率变为 0，差异减少
            } else if (count[s.charAt(i + pLen) - 'a'] == 0) {
                ++differ;// 添加后该字母频率变为 1，差异增加
            }
            ++count[s.charAt(i + pLen) - 'a'];
            if (differ == 0){
                ans.add(i+1);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("abc".charAt(0));//a  charAt(0) 获取该索引位置的字符
        System.out.println("abc".charAt(0) - 'a');//0
    }
}
