package algorithm.binarytree;

import datastructure.TreeNode;

import static datastructure.TreeNode.parseTree;

/**
 * @Author Allenzsy
 * @Date 2022/11/4 23:32
 * @Description:
 */
public class Solution222 {
    /**
     用上完全二叉树的性质,就是一直遍历左节点,和右节点,如果高度一样,则直接计算,如果不一样还老实去递归
     位运算优先级最低!!!!!!!!!!!!!~!!!!!!!!!!!
     */
    public int countNodes(TreeNode root) {
        if(root == null) return 0;

        TreeNode node = root;
        int l_hight = 0;
        while(node != null) {
            node = node.left;
            l_hight++;
        }
        node = root;
        int r_hight = 0;
        while(node != null) {
            node = node.right;
            r_hight++;
        }
        if(l_hight == r_hight) {
            return (1 << (l_hight)) - 1;
        } else {
            return countNodes(root.left) + countNodes(root.right) + 1;
        }

    }

    public static void main(String[] args) {
        TreeNode root = parseTree(new Integer[]{1,2,3,4,5,6});
        int res = new Solution222().countNodes(root);
        System.out.println(res);
    }
}
