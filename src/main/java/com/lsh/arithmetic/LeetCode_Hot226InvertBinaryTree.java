package com.lsh.arithmetic;

/**
 * 226. 翻转二叉树
 * https://leetcode.cn/problems/invert-binary-tree/description/?envType=study-plan-v2&envId=top-100-liked
 * 给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
 * 
 * 这是一道很经典的二叉树问题。
 * 我们从根节点开始，递归地对树进行遍历
 * 并从叶子节点先开始翻转。如果当前遍历到的节点 root 的左右两棵子树都已经翻转，
 * 那么我们只需要交换两棵子树的位置，即可完成以 root 为根节点的整棵子树的翻转。
 */
public class LeetCode_Hot226InvertBinaryTree {

    /**
     * 我们从根节点开始，递归地对树进行遍历
     * 并从叶子节点先开始翻转。如果当前遍历到的节点 root 的左右两棵子树都已经翻转，
     * 那么我们只需要交换两棵子树的位置，即可完成以 root 为根节点的整棵子树的翻转。
     * 时间复杂度：O(n)，其中 n 为二叉树节点的个数。每个节点在递归中只被遍历一次。
     * 空间复杂度：O(n)，其中 n 为二叉树的高度。递归函数需要栈空间，而栈空间取决于递归的深度，因此空间复杂度等价于二叉树的高度。
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null){
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        //交换左右子树位置
        root.left = right;
        root.right = left;
        //返回根节点
        return root;
    }

}
