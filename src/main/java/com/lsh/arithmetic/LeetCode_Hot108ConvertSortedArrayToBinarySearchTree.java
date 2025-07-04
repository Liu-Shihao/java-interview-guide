package com.lsh.arithmetic;

/**
 * 108. 将有序数组转换为二叉搜索树
 * https://leetcode.cn/problems/convert-sorted-array-to-binary-search-tree/description/?envType=study-plan-v2&envId=top-100-liked
 * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 平衡 二叉搜索树。
 *
 * 二叉搜索树（Binary Search Tree, BST）是一种特殊的二叉树，具有以下性质：
 * 1.对于每个节点，其左子树中所有节点的值都小于该节点的值。其右子树中所有节点的值都大于该节点的值。
 * 2.左子树和右子树本身也必须是二叉搜索树。
 *
 * 中序遍历有序：对二叉搜索树进行中序遍历，得到的节点值是按升序排列的。
 * 以下是一棵二叉搜索树的示例：
 *        8
 *       / \
 *      3   10
 *     / \    \
 *    1   6    14
 *       / \   /
 *      4   7 13
 *
 * 节点 8 的左子树中所有节点值（3, 1, 6, 4, 7）小于 8，右子树中所有节点值（10, 14, 13）大于 8。
 * 对这棵树进行中序遍历，结果为：1, 3, 4, 6, 7, 8, 10, 13, 14（升序）
 */
public class LeetCode_Hot108ConvertSortedArrayToBinarySearchTree {

    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null; // 如果数组为空，返回null
        }
        return buildBST(nums, 0, nums.length - 1); // 调用辅助方法构建平衡二叉搜索树
    }

    /**
     * 递归方法构建平衡二叉搜索树
     * @param nums
     * @param left
     * @param right
     * @return
     */
    public TreeNode buildBST(int[] nums, int left, int right) {
        if (left > right) {
            return null; // 如果左索引大于右索引，返回null
        }

        int mid = left + (right - left) / 2; // 计算中间索引
        TreeNode node = new TreeNode(nums[mid]); // 创建当前节点

        // 递归构建左子树和右子树
        node.left = buildBST(nums, left, mid - 1);
        node.right = buildBST(nums, mid + 1, right);

        return node; // 返回当前节点
    }



}
