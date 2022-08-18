package algorithm.binarytree;

import datastructure.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static datastructure.TreeNode.parseTree;

public class offer32 {
    public static List<List<Integer>> levelOrder(TreeNode root) {
        Deque<TreeNode> deque1 = new LinkedList<>();
        Deque<TreeNode> deque2 = new LinkedList<>();
        List<List<Integer>> res = new LinkedList<>();
        boolean rToL = false;
        deque1.addLast(root);
        while(!deque1.isEmpty() || !deque2.isEmpty()) {
            List<Integer> layer1 = new LinkedList<>();
            List<Integer> layer2 = new LinkedList<>();
            while(!deque1.isEmpty()) {
                TreeNode node = null;
                node = deque1.removeFirst();
                layer1.add(node.val);
                if(node.left != null) {
                    deque2.addLast(node.left);
                }
                if(node.right != null) {
                    deque2.addLast(node.right);
                }
            }
            while(!deque2.isEmpty()) {
                TreeNode node = null;
                node = deque2.removeLast();
                layer2.add(node.val);
                if(node.right != null) {
                    deque1.addFirst(node.right);
                }
                if(node.left != null) {
                    deque1.addFirst(node.left);
                }
            }
            if (!layer1.isEmpty()) {
                res.add(layer1);
            }
            if(!layer2.isEmpty()) {
                res.add(layer2);
            }
        }
        return res;
    }

    public static List<List<Integer>> levelOrderOne(TreeNode root) {
        Deque<TreeNode> deque = new LinkedList<>();
        List<List<Integer>> res = new LinkedList<>();
        if (root == null) {
            return res;
        }
        boolean lToR = true;
        deque.addFirst(root);
        while(!deque.isEmpty()) {
            List<Integer> layer = new LinkedList<>();
            int count = deque.size();
            for (; count > 0; count--) {
                if (lToR) {
                    TreeNode node = deque.removeFirst();
                    layer.add(node.val);
                    if(node.left != null) deque.addLast(node.left);
                    if(node.right != null)  deque.addLast(node.right);
                } else {
                    TreeNode node = deque.removeLast();
                    layer.add(node.val);
                    if(node.right != null) deque.addFirst(node.right);
                    if(node.left != null)  deque.addFirst(node.left);
                }
            }
            lToR = !lToR;
            res.add(layer);

        }
        return res;
    }

    public static void main(String[] args) {
//        Deque<Integer> deque = new LinkedList<>();
//        deque.addFirst(1);
//        deque.addFirst(2);
//        deque.addFirst(3);
//        deque.addLast(7);
//        deque.addLast(8);
//        deque.addLast(9);



        TreeNode root = parseTree(new Integer[]{1,2,3,4,null,null,5});
        System.out.println(levelOrderOne(root));
    }
}
