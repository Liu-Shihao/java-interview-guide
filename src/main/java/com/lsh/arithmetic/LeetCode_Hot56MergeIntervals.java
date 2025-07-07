package com.lsh.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 56. 合并区间
 * https://leetcode.cn/problems/merge-intervals/description/?envType=study-plan-v2&envId=top-100-liked
 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
 * 请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
 */
public class LeetCode_Hot56MergeIntervals {

    /**
     * 
     * 思路：
     * 首先将数组 intervals 按照左端点排序，然后遍历数组，
     * 如果当前区间的左端点在结果数组的最后一个区间的右端点之后，
     * 则说明当前区间与结果数组的最后一个区间不重合，直接将当前区间加入结果数组。
     * 如果当前区间的左端点在结果数组的最后一个区间的右端点之前，
     * 则说明当前区间与结果数组的最后一个区间重合，将当前区间合并到结果数组的最后一个区间。
     * 遍历结束后，返回结果数组。
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][2];
        }
        // 先对数组进行排序,按照左边界排序
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        ArrayList<int[]> merged = new ArrayList<>();
        for(int[] arr: intervals){
            int L = arr[0], R = arr[1];
            //如果merged集合为空，或者merged集合中的最后一个元素的右边界小于当前区间的左边界（即区间不相交）
            //就新增到merged集合
            if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < L) {
                merged.add(new int[]{L, R});
            }else{
                //否则，说明当前区间与merged集合中的最后一个元素有交集，就更新merged集合中的最后一个元素的右边界
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size()-1)[1], R);
            }
        }
        //将merged集合转换为数组
        return merged.toArray(new int[merged.size()][]);
    }
}
