package com.lsh.arithmetic;

import java.util.HashSet;

/**
 * 142. 环形链表 II
 * https://leetcode.cn/problems/linked-list-cycle-ii/description/?envType=study-plan-v2&envId=top-100-liked
 * 给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
 * 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。
 * 如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
 * 不允许修改 链表。
 *
 * 输入：head = [3,2,0,-4,2], pos = 1
 * 输出：返回索引为 1 的链表节点
 * 解释：链表中有一个环，其尾部连接到第二个节点。
 */
public class LeetCode_Hot142LinkedListCycleII {

    /**
     * 快慢指针解法：
     * 设两种指针，fast，slow，fast 每次走两步，slow 每次走一步。
     * fast指针走到链表末端，说明链表无环; 如果相遇说明有环。
     * 设链表一共有 a+b 节点，其中 a 节点在环外，b 节点在环内。
     * 两指针第一次相遇：
     * 此时fast指针走的是slow指针的两倍，即 fast = 2 * slow。因为fast指针每次走两步，slow指针每次走一步，所以当fast和slow相遇时，fast已经走了2倍的距离。
     * fast 比slow 多走了n个环的长度，即 fast = slow + n * b。（ 解析： 双指针都走过 a 步，然后在环内绕圈直到重合，重合时 fast 比 slow 多走 环的长度整数倍 ）。
     * 因此可以得到以下公式： slow = nb; fast = 2nb;  即 fast 和 slow 指针分别走了 2n，n 个环的周长。
     *
     * 如果让指针从链表头部一直向前走并统计步数k, 那么所有 走到链表入口节点时的步数 是：k=a+nb
     * 即先走 a 步到入口节点，之后每绕 1 圈环（ b 步）都会再次到入口节点。
     * 而目前 slow 指针走了 nb 步。因此，我们只要想办法让 slow 再走 a 步停下来，就可以到环的入口。
     *
     * 两指针第二次相遇：
     * 使用新指针从 head 节点出发，每次走一步，直到与 slow 指针相遇。此时相遇的节点就是入环节点。
     * 令 fast 指针回到链表头部，slow 指针不动。 此时 fast = 0; slow = nb;
     * slow 和 fast 指针同时向前走，每次走一步，直到相遇。
     * 当 fast指针走a步时。此时slow指针走 a+nb 步，此时两指针重合，刚好相遇在入环点。
     * 返回slow指针节点。
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head, slow = head;
        while(true){
            if (fast == null || fast.next == null) {
                return null; // 无环
            }
            fast = fast.next.next; // fast 每次走两步
            slow = slow.next; // slow 每次走一步
            if (fast == slow) { // 相遇
                break;
            }
        }
        // 相遇后，fast指针回到链表头部，slow指针不动
        fast = head;
        while (fast != slow) { // 当 fast 和 slow 相遇时，说明找到了入环点
            fast = fast.next; // fast 每次走一步
            slow = slow.next; // slow 每次走一步
        }
        return fast; // 返回入环点
    }

    /**
     * 哈希表解法：
     * 我们遍历链表中的每个节点，并将它记录下来；一旦遇到了此前遍历过的节点，就可以判定链表中存在环。
     * @param head
     * @return
     */
    public ListNode detectCycle2(ListNode head) {
        ListNode pos = head;
        HashSet<ListNode> set = new HashSet<>();
        while (pos != null){
            if (set.contains(pos)) {
                return pos; // 找到入环点
            }else {
                set.add(pos); // 记录当前节点
            }
            pos = pos.next; // 移动到下一个节点
        }
        return null; // 无环
    }
}
