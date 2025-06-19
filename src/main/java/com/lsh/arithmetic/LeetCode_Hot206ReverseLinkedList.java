package com.lsh.arithmetic;

/**
 * https://leetcode.cn/problems/reverse-linked-list/description/
 * 206. 反转链表
 * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
 * 输入：head = [1,2,3,4,5]
 * 输出：[5,4,3,2,1]
 */
public class LeetCode_Hot206ReverseLinkedList {

     public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * 
     * 解法一：迭代
     * 在遍历链表时，将当前节点的 next 指针改为指向前一个节点。
     * 由于节点没有引用其前一个节点，因此必须事先存储其前一个节点。
     * 在更改引用之前，还需要存储后一个节点。最后返回新的头引用。
     * 时间复杂度：O(n) 需要遍历一次链表
     * 空间复杂度：O(1)
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        // 必须事先存储其前一个节点
        ListNode pre = null;
        ListNode current = head;

        while (current != null){
            //存储当前节点的next节点，并把当前节点的next节点指向pre
            ListNode next = current.next;
            current.next = pre;
            //把当前节点赋值给pre
            pre = current;
            //把当前节点的next节点赋值给current
            current = next;
        }
        return pre;

    }

    /**
     * 解法二：递归
     * 递归过程示例：假设链表为 1 → 2 → 3 → null：
     * 第一次调用：head = 1，head.next = 2
     *        递归调用 reverseList2(2)
     * 第二次调用：head = 2，head.next = 3
     *        递归调用 reverseList2(3)
     * 第三次调用：head = 3，head.next = null
     *        递归终止，返回 newHead = 3
     * 回到第二次调用：：head = 2，head.next = 3
     *        反转链表，head.next.next = head，即 3.next = 2
     *        断开head与后续节点的连接，head.next = null，即 2.next = null
     *        返回 newHead = 3
     * 回到第一次调用：head = 1，head.next = 2
     *        反转链表，head.next.next = head，即 2.next = 1
     *        断开head与后续节点的连接，head.next = null，即 1.next = null
     *        返回 newHead = 3
     * 最终返回 head = 3
     * 时间复杂度：O(n) 需要遍历一次链表
     * 空间复杂度：O(n) 递归调用的栈空间
     * 
     * @param head
     * @return
     */
    public ListNode reverseList2(ListNode head) {
        //递归终止条件：
        //当head为空或者head.next为空时，直接返回head。
        //说明当前链表没有节点或者只有一个节点，此时不需要反转，直接返回head。
        if (head == null || head.next == null){
            return head;
        }
        ListNode newHead = reverseList2(head.next);
        head.next.next = head;//把当前节点接到反转后链表的末尾
        head.next = null;//断开head与后续节点的连接，防止形成环
        return newHead;
    }
    
}