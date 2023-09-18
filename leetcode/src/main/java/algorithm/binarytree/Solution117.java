package algorithm.binarytree;

import datastructure.Node;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author Allenzsy
 * @Date 2022/11/5 0:12
 * @Description:
 */
public class Solution117 {
    public Node connect(Node root) {

        if(root == null) return root;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int num = queue.size();
            Node node = null;
            for (int i = 0; i < num; i++) {
                node = queue.poll();
                if(node != null) {
                    node.next = (i == num-1) ? null : queue.peek();
                    if(node.left != null) queue.offer(node.left);
                    if(node.right != null) queue.offer(node.right);
                }
            }
        }

        return root;
    }


    /*void dfs(Node n1, Node n2) {
        if(n1 == null && n2 == null) return;

        if(n1 != null) {
            n1.next = n2;
        }

        // 链接同一父节点的左右节点
        if(n1 != null) dfs(n1.left, n1.right);
        if(n2 != null) dfs(n2.left, n2.right);
        // 链接不同父节点的右节点和左节点
        if(n1 != null && n2 != null) dfs(n1.right, n2.left);

    }*/

    public static void main(String[] args) {
        Node root = Node.parseTree(new Integer[]{1,2,3,4,5,null,7});
        Node res = new Solution117().connect(root);
        System.out.println(res);
    }
}
