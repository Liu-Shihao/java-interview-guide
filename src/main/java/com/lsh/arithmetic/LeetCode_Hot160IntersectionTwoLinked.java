package com.lsh.arithmetic;

import java.util.HashSet;

/**
 * https://leetcode.cn/problems/intersection-of-two-linked-lists/description/?envType=study-plan-v2&envId=top-100-liked
 * 160. 相交链表
 * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
 */
public class LeetCode_Hot160IntersectionTwoLinked {

    class ListNode{
        int val;
        ListNode next;
        ListNode(int x){
            val = x;
            next = null;
        }
    }

    /**
     * 双指针解法：
     * 核心思想：
     * 1. 两个指针 nodeA 和 nodeB 分别指向链表 headA 和 headB 的头节点
     * 2. 同时遍历链表，当 nodeA 到达链表 headA 的末尾时，将其指向链表 headB 的头节点；
     *    当 nodeB 到达链表 headB 的末尾时，将其指向链表 headA 的头节点。
     * 3. 这样做的目的是为了让两个指针在遍历完各自链表的长度差后，能够同时到达相交节点。
     * 4. 如果两个链表相交，那么 nodeA 和 nodeB 一定会在相交节点相遇；
     *    如果不相交，那么 nodeA 和 nodeB 最终都会指向 null，循环结束。
     * 
     * 时间复杂度：O(m+n)，其中 m 和 n 分别是链表 headA 和 headB 的长度。
     * 空间复杂度：O(1)。
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB){
        if (headA == null || headB == null) {
            return null;
        }
        ListNode nodeA = headA;
        ListNode nodeB = headB;
        while (nodeA != nodeB){
            //指针A遍历结束后，开始遍历链表B
            nodeA = nodeA == null ? headB : nodeA.next;
            //指针B遍历结束后，开始遍历A
            nodeB = nodeB == null ? headA : nodeB.next;
        }
        //如果两个链表有交点，则一定会在共同遍历完两个链表的长度差后相遇
        //A走完A的长度，再走B的长度到达B链表中的交点
        //B走完B的长度，再走A的长度到达A链表中的交点
        //此时指针a和b走的长度是一样，
        return nodeA;
    }


    /**
     * 哈希表
     * 首先将链表A的所有节点加入哈希表中
     * 然后遍历链表B，判断节点是否在哈希表中
     *
     * 注意要contains判断ListNode类型，而不是value值。会有重复的value节点。
     * 时间复杂度：O(m+n)，其中 m 和 n 分别是链表 headA 和 headB 的长度。
     * 空间复杂度：O(m)，其中 m 是链表 headA 的长度。
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode1(ListNode headA, ListNode headB){
        if (headA == null || headB == null) {
            return null;
        }
        ListNode nodeA = headA;
        HashSet<ListNode> set = new HashSet<>();
        while (nodeA != null) {
            set.add(nodeA);
            nodeA = nodeA.next;
        }
        ListNode nodeB = headB;
        while (nodeB != null) {
            if (set.contains(nodeB)) {
                return nodeB;
            }
            nodeB = nodeB.next;
        }
        return null;
    }
    
}
