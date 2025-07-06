package com.lsh.arithmetic;

import java.util.ArrayList;
import java.util.List;
/**
 * 118. 杨辉三角
 * https://leetcode.cn/problems/pascals-triangle/description/?envType=study-plan-v2&envId=top-100-liked
 * 给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。
 * 
 * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
 *      1
 *     1 1
 *    1 2 1
 *   1 3 3 1
 *  1 4 6 4 1
 * 
 * 示例 1:
 * 输入: numRows = 5
 * 输出: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
 * 
 * 示例 2:
 * 输入: numRows = 1
 * 输出: [[1]]
 * 
 * 提示:
 * 1 <= numRows <= 30
 */
public class LeetCode_Hot118PascalsTriangle {
    /**
     * 动态规划
     * 每个数字等于上一行的左右两个数字之和
     * 即第 n 行的第 i 个数等于第 n−1 行的第 i−1 个数和第 i 个数之和。
     * @param numRows
     * @return
     */
    public List<List<Integer>> generate(int numRows) {
        ArrayList<List<Integer>> triangle = new ArrayList<>();
        triangle.add(List.of(1));
        triangle.add(List.of(1,1));
        for (int i = 2; i < numRows; i++) {
            List<Integer> pre = triangle.get(i - 1);
            List<Integer> cur = new ArrayList<>();
            cur.add(1);
            for (int j = 1; j < i; j++) {
                cur.add(pre.get(j - 1) + pre.get(j));
            }
            cur.add(1);
            triangle.add(cur);
        }
        return triangle;  
    }
}
