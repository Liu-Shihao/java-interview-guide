package com.lsh.arithmetic;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/12 10:04 下午
 * @desc ：LeetCode21 合并两个有序链表
 * https://leetcode.cn/problems/merge-two-sorted-lists/description/?envType=study-plan-v2&envId=top-100-liked
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * 输入：l1 = [1,2,4], l2 = [1,3,4]
 * 输出：[1,1,2,3,4,4]
 */
public class LeetCode_Hot21MergeTwoSortedList {

    class ListNode{
        int val;
        ListNode next;
        ListNode(){};
        ListNode(int val){
            this.val = val;
        }
        ListNode(int val, ListNode next){
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 递归解法：
     * 递归解法思路：
     * 我们可以递归地定义在两个链表上的 merge 操作（忽略边界情况，比如空链表等）：
     * （1）如果 l1 或者 l2 一开始就是空链表 ，那么没有任何操作需要合并，所以我们只需要返回非空链表。
     * （2）否则，我们要判断 l1 和 l2 哪一个链表的头节点的值更小，然后递归地决定下一个添加到结果里的节点。如果两个链表有一个为空，递归结束。
     * 时间复杂度：O(n+m)，其中 n 和 m 分别为两个链表的长度。因为每次调用递归都会去掉 l1 或者 l2 的头节点（直到至少有一个链表为空），函数 mergeTwoList 至多只会递归调用每个节点一次。
     * 空间复杂度：O(n+m)，其中 n 和 m 分别为两个链表的长度。递归调用 mergeTwoLists 函数时需要消耗栈空间，栈空间的大小取决于递归调用的深度。
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoSortedList(ListNode list1, ListNode list2){
        //如果 l1 或者 l2 一开始就是空链表 ，那么没有任何操作需要合并，所以我们只需要返回非空链表。
        if (list1 == null) {
            return list2;
        }else if (list2 == null){
            return list1;
        }else if(list1.val < list2.val){
            //否则，我们要判断 l1 和 l2 哪一个链表的头节点的值更小，然后递归地决定下一个添加到结果里的节点。如果两个链表有一个为空，递归结束。
            list1.next = mergeTwoSortedList(list1.next, list2);
            return list1;
        }else {
            list2.next = mergeTwoSortedList(list1, list2.next);
            return list2;
        }
    }

    /**
     * 迭代解法：
     * @param list1
     * @param list2
     * @return
     */
    public  ListNode mergeTwoListNode2(ListNode list1, ListNode list2){

        ListNode preHead = new ListNode(-1);
        //定义prev指针，维护next节点
        ListNode prev = preHead;

        while(list1 != null && list2 != null){
            if (list1.val <= list2.val) {
                // 如果 l1 当前节点的值小于等于 l2 ，我们就把 l1 当前的节点接在 prev 节点的后面同时将 l1 指针往后移一位。
                prev.next = list1;
                list1 = list1.next;
            }else {
                //否则，我们对 l2 做同样的操作。不管我们将哪一个元素接在了后面，我们都需要把 prev 向后移一位
                prev.next = list2;
                list2 = list2.next;
            }
            prev = prev.next;
        }
        //合并后 l1 和 l2 最多只有一个还未被合并完，我们直接将链表末尾指向未合并完的链表即可
        prev.next = list1 == null ? list2 : list1;
        return preHead.next;
    }

}