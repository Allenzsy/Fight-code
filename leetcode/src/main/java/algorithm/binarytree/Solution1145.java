package algorithm.binarytree;

import datastructure.TreeNode;

import static datastructure.TreeNode.parseTree;

public class Solution1145 {
    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        /**
         玩家二一定胜利的情况为以下三种，思路就是能占领大于一半的节点：
         1. x的左子树节点数大于n/2，则选择左子树
         2. x的右子树节点数大于n/2，则选择右子树
         3. x及其子树节点数小于n/2，则选择父节点
         */
        int half = n/2;
        TreeNode xRoot = findPlayerOneNode(root, x);
        int xLeft = countNode(root.left);
        if(xLeft > half) {
            return true;
        }
        int xRight = countNode(root.right);
        if(xRight > half) {
            return true;
        }
        if(xLeft + xRight < half) {
            return true;
        }
        return false;
    }
    private TreeNode findPlayerOneNode(TreeNode root, int x) {
        if(root == null) {
            return null;
        }
        if(root.val == x) {
            return root;
        }
        TreeNode left = findPlayerOneNode(root.left, x);
        TreeNode right = findPlayerOneNode(root.right, x);
        return left != null ? left : right;
    }
    private int countNode(TreeNode root) {
        if(root == null) return 0;
        return countNode(root.left) + countNode(root.right) + 1;
    }

    public static void main(String[] args) {
        TreeNode root = parseTree(new Integer[]{1,2,3});
        boolean res = new Solution1145().btreeGameWinningMove(root, 3, 3);
        System.out.println(res);
    }


}
