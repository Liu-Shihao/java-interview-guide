package com.lsh.arithmetic;

import java.util.*;

/**
 *
 * https://leetcode.cn/problems/group-anagrams/description/?envType=study-plan-v2&envId=top-100-liked
 * 49. 字母异位词分组
 * 难度：中等
 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 *
 * 字母异位词 是由重新排列源单词的所有字母得到的一个新单词。(组成单词的字母元素相同，位置不同)
 * 示例 1:
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * 示例 2:
 *
 * 输入: strs = [""]
 * 输出: [[""]]
 * 示例 3:
 *
 * 输入: strs = ["a"]
 * 输出: [["a"]]
 */
public class LeetCode_Hot02_GroupAnagrams {

    /**
     *方法一：排序
     * 由于互为字母异位词的两个字符串包含的字母相同，因此对两个字符串分别进行排序之后得到的字符串一定是相同的，故可以将排序之后的字符串作为哈希表的键。
     * @param strs
     * @return
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }
        
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (String word: strs){
            char[] charArray = word.toCharArray();
            Arrays.sort(charArray);
            String key = new String(charArray);
//            if (!hashMap.containsKey(key)){
//                // 使用可变的ArrayList而不是不可变的List.of()
//                List<String> list = new ArrayList<>();
//                list.add(word);
//                hashMap.put(key, list);
//            } else {
//                hashMap.get(key).add(word);
//            }

            //代码优化
            List<String> list = hashMap.getOrDefault(key, new ArrayList<String>());
            list.add(word);
            hashMap.put(key, list);

        }
        return new ArrayList<List<String>>(hashMap.values());
    }

    public static void main(String[] args) {
        String[] strs = new String[]{"eat","tea","tan","ate","nat","bat"};
        System.out.println(groupAnagrams(strs));
    }


}
