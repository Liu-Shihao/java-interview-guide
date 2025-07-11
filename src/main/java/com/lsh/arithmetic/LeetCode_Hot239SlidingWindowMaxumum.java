package com.lsh.arithmetic;

import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * 239. 滑动窗口最大值
 * https://leetcode.cn/problems/sliding-window-maximum/description/?envType=study-plan-v2&envId=top-100-liked
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * 返回 滑动窗口中的最大值 。
 * 示例 1：
 *
 * 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
 * 输出：[3,3,5,5,6,7]
 * 解释：
 * 滑动窗口的位置                最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 * 示例 2：
 *
 * 输入：nums = [1], k = 1
 * 输出：[1]
 */
public class LeetCode_Hot239SlidingWindowMaxumum {

    /**
     * 优先队列解法：利用优先级队列，获取当前滑动窗口内的最大值。
     * 初始时，我们将数组 nums 的前 k 个元素放入优先队列中。
     * 每当我们向右移动窗口时，我们就可以把一个新的元素放入优先队列中，此时堆顶的元素就是堆中所有元素的最大值。
     * 然而这个最大值可能并不在滑动窗口中，在这种情况下，这个值在数组 nums 中的位置出现在滑动窗口左边界的左侧。
     * 因此，当我们后续继续向右移动窗口时，这个值就永远不可能出现在滑动窗口中了，我们可以将其永久地从优先队列中移除。
     *
     * 时间复杂度：O(n log k)，其中 n 是数组 nums 的长度。我们需要遍历整个数组，每次插入和删除操作的时间复杂度为 O(log k)。
     * 空间复杂度：O(n)，我们需要使用一个优先队列来存储滑动窗口内的元素。
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow1(int[] nums, int k) {
        int n = nums.length;
        //创建大顶堆
        PriorityQueue<int[]> pq = new PriorityQueue<>((pair1, pair2) -> pair1[0] != pair2[0] ? pair2[0] - pair1[0] : pair2[1] - pair1[1]);
        //首先将前 k 个元素加入优先队列，优先队列中存储数组：值和index）
        for (int i = 0; i < k; i++) {
            pq.offer(new int[]{nums[i], i});
        }
        //结果数组
        int[] res = new int[n - k + 1];
        //当前滑动窗口的最大值， peek方法查看堆顶元素，但是不会移除元素。
        res[0] = pq.peek()[0];
        //从第 k 个元素开始，向右滑动窗口
        for (int i = k; i < n; i++) {
            //将当前元素加入优先队列
            pq.offer(new int[]{nums[i], i});
            //移除不在滑动窗口内的元素, 查看堆顶元素的index位置，如果小于等于 i - k，则说明该元素已经不在滑动窗口内，需要将其移除。
            //注意：这里只判断了堆顶元素是否过期，不需要考虑其他元素是否过期，非最大的残留过期元素不影响结果。
            //即使堆中有其他过期元素，只要它们不是堆顶，就不会干扰当前最大值的选取
            while (pq.peek()[1] <= i - k) {
                pq.poll();
            }
            //更新结果数组，当前滑动窗口的最大值就是优先队列的堆顶元素。
            res[i - k + 1] = pq.peek()[0];
        }
        return res;
    }

    /**
     * 单调队列解法：当滑动窗口向右移动时，我们需要把一个新的元素放入队列中。为了保持队列的性质，我们会不断地将新的元素与队尾的元素相比较，如果前者大于等于后者，那么队尾的元素就可以被永久地移除，我们将其弹出队列。我们需要不断地进行此项操作，直到队列为空或者新的元素小于队尾的元素。
     *
     * 维护一个始终按递减顺序排列的队列（队头到队尾），保证队头元素始终是当前窗口的最大值。这样每次窗口滑动时，只需：
     * 移除过期元素：检查队头元素是否已不在窗口范围内（通过存储索引实现）。
     * 维护单调性：新元素入队前，从队尾移除所有比它小的元素，确保队列的递减性
     *
     *
     * 每个元素仅入队和出队一次，时间和空间复杂度均为 O(n)和 O(k)
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow2(int[] nums, int k) {
        int n = nums.length;
        //创建一个双端队列，注意是存储元素的索引，可以精确判断队头元素是否过期（即索引是否小于当前窗口左边界）
        Deque<Integer> deque = new LinkedList<>();
        // 初始化前 k 个元素的单调队列
        for (int i = 0; i < k; ++i) {
            //如果队列不为空，并且当前元素大于等于队列尾部元素，则移除队列尾部元素
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                //队列中的元素按值递减排列，意味着任何新元素若比队尾元素大，则队尾元素在未来窗口中不可能成为最大值（因为新元素更大且存活时间更长），可直接移除
                deque.pollLast();
            }
            //将当前元素的索引加入队列尾部
            deque.offerLast(i);
        }

        int[] ans = new int[n - k + 1];
        ans[0] = nums[deque.peekFirst()];
        for (int i = k; i < n; ++i) {
            //将当前元素加入队列,并维护单调性，当前元素与队尾元素比较
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                //如果当前元素大于等于队尾元素，则移除队尾元素
                deque.pollLast();
            }
            //将当前元素的索引加入队列尾部
            deque.offerLast(i);
            //对头元素永远是当前窗口的最大值，只需要判断是否过期，i此时是滑动窗口的右边界
            while (deque.peekFirst() <= i - k) {
                //如果队头元素的索引小于等于 i - k，说明它已经不在当前窗口内，需要将其移除
                deque.pollFirst();
            }
            ans[i - k + 1] = nums[deque.peekFirst()];
        }
        return ans;

    }
}
