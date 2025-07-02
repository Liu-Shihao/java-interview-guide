package com.lsh.arithmetic;

/**
 * 101. 对称二叉树
 * https://leetcode.cn/problems/symmetric-tree/description/?envType=study-plan-v2&envId=top-100-liked
 * 给定一个二叉树，检查它是否是镜像对称的。
 */
public class LeetCode_Hot101SymmetricTree {
    /**
     * 递归
     * 如果一个树的左子树与右子树镜像对称，那么这个树是对称的。
     * 因此，该问题可以转化为：两个树在什么情况下互为镜���？
     * 如果同时满足下面的条件，两个树互为镜像：
     * 1.它们的两个根结点具有相同的值。
     * 2.每个树的右子树都与另一个树的左子树镜像对称。
     * 时间复杂度：O(n)，其中 n 为二叉树的节点个数。
     * 空间复杂度：O(n)，其中 n 为二叉树的节点个数。
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        return check(root, root);
    }

    /**
     * 通过「同步移动」两个指针的方法来遍历这棵树，
     * p 指针和 q 指针一开始都指向这棵树的根，
     * 随后 p 右移时，q 左移，p 左移时，q 右移。
     * 每次检查当前 p 和 q 节点的值是否相等，
     * 如果相等再判断左右子树是否对称。
     * 
     * @param p
     * @param q
     * @return
     */
    public boolean check(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        //判断当前节点值是否相等
        //判断左子树是否对称
        //判断右子树是否对称
        return p.val == q.val && check(p.left, q.right) && check(p.right, q.left);
    }

}
