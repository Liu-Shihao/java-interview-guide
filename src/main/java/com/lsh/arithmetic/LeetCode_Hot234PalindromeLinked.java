package com.lsh.arithmetic;

import java.util.ArrayList;

import com.lsh.arithmetic.ListNode;

/**
 * https://leetcode.cn/problems/palindrome-linked-list/description/?envType=study-plan-v2&envId=top-100-liked
 * 
 * 234. 回文链表
 * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
 * 示例 1：
 * 输入：head = [1,2,2,1]
 * 输出：true
 * 示例 2：
 * 输入：head = [1,2]
 * 输出：false
 * 示例 3：
 * 输入：head = [1,2,3,2,1]
 * 输出：true
 */

public class LeetCode_Hot234PalindromeLinked {
    
    /**
     * 首先将链表复制到数组中，在通过双指针判断是否为回文
     * 时间复杂度：O(n)，其中 n 指的是链表的元素个数。
     * 空间复杂度：O(n)，其中 n 指的是链表的元素个数，我们使用了一个数组列表存放链表的元素值。
     * @param head
     * @return
     */
    public boolean isPalindrome1(ListNode head) {
        //遍历链表到数组中
        ArrayList<Integer> vals = new ArrayList<>();
        ListNode cur = head;
        while(cur != null){
            vals.add(cur.val);
            cur = cur.next;
        }

        //使用双指针判断是否回文
        int front = 0;
        int back = vals.size() - 1;
        while (front < back) {
            if (vals.get(front) != vals.get(back)){
                return false;
            }
            front++;
            back--;
        }
        return true;

    }
}

