package datastructure;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class TreeNode {

    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public static TreeNode parseTree(Integer[] treeArray) {
        if(treeArray == null || treeArray.length == 0 || treeArray[0] == null) {
            return null;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(treeArray[0]);
        queue.add(root);
        int index = 0;

        while (!queue.isEmpty()) {
            int left = 2*index + 1;
            int right = 2*index + 2;
            TreeNode node = queue.remove();
            if (node == null) {
                index++;
                continue;
            }
            if (left < treeArray.length && treeArray[left] != null) {
                node.left = new TreeNode(treeArray[left]);
            }
            if (right < treeArray.length && treeArray[right] != null) {
                node.right = new TreeNode(treeArray[right]);
            }
            queue.add(node.left);
            queue.add(node.right);
            index++;
        }

        return root;
    }

    public static void main(String[] args) {
        preOrder(parseTree(new Integer[]{3,9,20,null,null,15,7,null,null,null,null,2,3,4,5}), 0);
    }

    public static void preOrder(TreeNode root, int deep) {
        if(root == null) {
            return;
        }
        deep++;
        preOrder(root.left, deep);
        preOrder(root.right, deep);

        System.out.println(deep);
    }

}
