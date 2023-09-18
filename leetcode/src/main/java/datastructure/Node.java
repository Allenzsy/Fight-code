package datastructure;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author Allenzsy
 * @Date 2022/11/5 0:11
 * @Description:
 */
public class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }

    public static Node parseTree(Integer[] treeArray) {
        if(treeArray == null || treeArray.length == 0 || treeArray[0] == null) {
            return null;
        }
        Queue<Node> queue = new LinkedList<>();
        Node root = new Node(treeArray[0]);
        queue.add(root);
        int index = 0;

        while (!queue.isEmpty()) {
            int left = 2*index + 1;
            int right = 2*index + 2;
            Node node = queue.remove();
            if (node == null) {
                index++;
                continue;
            }
            if (left < treeArray.length && treeArray[left] != null) {
                node.left = new Node(treeArray[left]);
            }
            if (right < treeArray.length && treeArray[right] != null) {
                node.right = new Node(treeArray[right]);
            }
            queue.add(node.left);
            queue.add(node.right);
            index++;
        }

        return root;
    }
}
