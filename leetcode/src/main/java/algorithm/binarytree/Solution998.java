package algorithm.binarytree;

import datastructure.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static datastructure.TreeNode.parseTree;

/**
 * @Author Allenzsy
 * @Date 2022/11/22 0:32
 * @Description:
 */
public class Solution998 {


    List<String> path = new ArrayList<>();

    public String smallestFromLeaf(TreeNode root) {
        dfs(root, new StringBuilder());
        Collections.sort(path);
        return path.get(0);
    }

    void dfs(TreeNode node, StringBuilder s) {
        if(node == null) return;
        char val = (char) ('a'+node.val);
        s.append(val);
        if(node.left == null && node.right == null) {
            s.reverse();
            path.add(s.toString());
            s.reverse();
            return;
        }

        dfs(node.left, s);
        dfs(node.right, s);
        s.deleteCharAt(s.length() - 1);
    }

    public static void main(String[] args) {
        TreeNode root = parseTree(new Integer[]{25,1,3,1,3,0,2});
        StringBuilder builder = new StringBuilder();
        new Solution998().dfs(root, builder);
    }
}
