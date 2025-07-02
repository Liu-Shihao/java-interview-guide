package com.lsh.arithmetic;


import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 94. 二叉树的中序遍历
 * https://leetcode.cn/problems/binary-tree-inorder-traversal/description/?envType=study-plan-v2&envId=top-100-liked
 * 给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
 * 
 * 二叉树的中序遍历：即按照 左子树 根节点 右子树的顺序遍历
    1
   / \
  2   3
 / \
4   5
中序遍历为：4，2，5，1，3
 */

public class LeetCode_94BinaryTreeInorderTraversal {
    /**
     * 递归解法
     * 按照访问左子树——根节点——右子树的方式遍历这棵树，而在访问左子树或者右子树的时候我们按照同样的方式遍历，直到遍历完整棵树。
     * 因此整个遍历过程天然具有递归的性质，我们可以直接用递归函数来模拟这一过程。
     * 时间复杂度：O(n)，其中 n 为二叉树节点的个数。二叉树的遍历中每个节点会被访问一次且只会被访问一次。
     * 空间复杂度：O(n)。空间复杂度取决于递归的栈深度，而栈深度在二叉树为一条链的情况下会达到 O(n) 的级别。
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        inorder(root, res);
        return res;
    }
    public void inorder(TreeNode root, List<Integer> res){
        if (root == null){
            //递归终止条件：碰到空节点
            return;
        }
        //遍历左子树
        inorder(root.left, res);
        res.add(root.val);
        inorder(root.right, res);
    }


    /**
     * 迭代解法
     * 思路：
     * 1. 从根节点开始，先将根节点入栈，然后将左子树入栈，直到左子树为空
     * 2. 从栈中弹出一个节点，将节点的值加入结果列表
     * 3. 将当前节点的右子树入栈，重复步骤1，2，3，直到栈为空
     *
     * 时间复杂度：O(n)，其中 n 为二叉树节点的个数。二叉树的遍历中每个节点会被访问一次且只会被访问一次。
     * 空间复杂度：O(n)。空间复杂度取决于栈深度，而栈深度在二叉树为一条链的情况下会达到 O(n) 的级别。
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            res.add(root.val);
            root = root.right;
            //即使当前节点的 right 为null，只要栈里还有节点，就会继续弹栈、访问节点，然后再判断这些节点的右子树。
        }
        return res;
    }
}
