package com.lsh.arithmetic;

import com.sun.source.tree.Tree;

/**
 * 543. 二叉树的直径
 * https://leetcode.cn/problems/diameter-of-binary-tree/description/?envType=study-plan-v2&envId=top-100-liked
 *
 * 给你一棵二叉树的根节点，返回该树的 直径 。
 * 二叉树的 直径 是指树中任意两个节点之间最长路径的 长度 。这条路径可能经过也可能不经过根节点 root 。
 * 两节点之间路径的 长度 由它们之间边数表示。
 */
public class LeetCode_Hot543DiameterOfBinaryTree {

    // 用于存储最大直径
    private int maxDiameter;

    /**
     * DFS 深度优先搜索
     * @param root
     * @return
     */
    public int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0; // 初始化最大直径为0
        depth(root); // 计算深度并更新最大直径
        return maxDiameter; // 返回最大直径
    }

    /**
     * DFS  深度优先搜索
     * 当前节点的深度 =  左右子树的最大深度 + 1
     * @param root
     * @return
     */
    public int depth(TreeNode root){
        if (root == null) {
            return 0;
        }
        int leftDepth = depth(root.left);
        int rightDepth = depth(root.right);
        if (leftDepth + rightDepth > maxDiameter) {
            //对于每个节点，左右子树深度之和即为以该节点为路径中心的直径。
            maxDiameter = leftDepth + rightDepth; // 更新最大直径
        }
        return Math.max(leftDepth, rightDepth) + 1;
    }




}
