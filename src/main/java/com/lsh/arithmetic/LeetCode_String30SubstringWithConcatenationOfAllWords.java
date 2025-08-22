package com.lsh.arithmetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.cn/problems/substring-with-concatenation-of-all-words/?envType=problem-list-v2&envId=string
 */
public class LeetCode_String30SubstringWithConcatenationOfAllWords {


     public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        int m = words.length;//单词个数
        int n = words[0].length();//单词长度
        int ls = s.length();//字符串的长度
        for(int i = 0; i < n; i++){
            if(i + m*n > ls){
                break;//从i位置开始，如果连放m个长度为n的单词，放不下，就结束循环，没有比较继续了
            }
            //初始化差值哈希表 differ: 当前窗口中各单词数量 与 words 中应有数量 的差值(如果differ为空，说明当前窗口中各单词数量 与 words 中应有数量 相同)
            Map<String, Integer> differ = new HashMap<>();
            for(int j = 0; j < m; j++){
                //从 i 开始，连续取出 m 个长度为 n 的子串（模拟单词），加入 differ，表示这些单词在当前窗口中出现了。
                String word = s.substring(i + j * n ,i + (j + 1) * n);//获取单词
                differ.put(word, differ.getOrDefault(word, 0) + 1);
                
            }
            //减去 words 中应有的次数
            for(String word : words){
                differ.put(word, differ.getOrDefault(word, 0) - 1);
                if (differ.get(word) == 0) {
                    differ.remove(word);
                }
            }
            //滑动窗口，逐步右移，start是窗口的起始位置，窗口固定长度为m*n，每次右移n
            for(int start = i; start < ls - m * n + 1; start += n){
                //第一次不更新differ，因为differ已经初始化了，只是减去了words中应有的次数
                if(start != i){
                    // 添加最右边进入窗口的单词
                    String word = s.substring(start + (m - 1) * n, start + m * n);
                    differ.put(word, differ.getOrDefault(word, 0)+1);
                    if (differ.get(word) == 0) {
                        differ.remove(word);
                    }

                    // 移除最左边退出窗口的单词
                    word = s.substring(start - n, start);
                    differ.put(word, differ.getOrDefault(word, 0) - 1);
                    if (differ.get(word) == 0) {
                        differ.remove(word);
                    }
                }
                
                if (differ.isEmpty()) {
                    res.add(start);
                }  
            }
        }
        return res;
    }
}
