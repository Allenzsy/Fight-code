package algorithm.binarytree;

import datastructure.TreeNode;

/*
给你二叉树的根结点 root ，请你将它展开为一个单链表：
展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
展开后的单链表应该与二叉树 先序遍历 顺序相同。
链接：https://leetcode-cn.com/problems/flatten-binary-tree-to-linked-list
*/

public class Solution114 {
    /**
     * 题解：
     * 使用后序遍历，因为站在某个节点上考虑，如果左子树和右子树都已经被展开成链表了，那就可以直接将左子树串进右子树
     * 这样就将这节点处理完了；因此，代码上就先递归地处理左子树，再递归地处理右子树
     */
    public void flatten(TreeNode root) {
        if(root == null) {
            return;
        }
        flatten(root.left);
        flatten(root.right);

        TreeNode left = root.left;
        TreeNode right = root.right;

        root.left = null;
        root.right = left;

        TreeNode p = root;
        while(p.right != null) {
            p = p.right;
        }
        p.right = right;

    }
}
