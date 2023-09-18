package algorithm.binarytree;

import datastructure.TreeNode;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Allenzsy
 * @Date 2023/9/13 0:38
 * @Description: 662. 二叉树最大宽度
 * https://leetcode.cn/problems/maximum-width-of-binary-tree/
 */
public class Solution662 {

    Map<Integer, Integer> map = new HashMap<>();
    int ans;

    public int widthOfBinaryTree(TreeNode root) {
        dfs(root, 1, 0);
        return ans;
    }

    void dfs(TreeNode root, int u, int depth) {
        if (root == null) {
            return;
        }
        if (!map.containsKey(depth)) {
            map.put(depth, u);
        }
        ans = Math.max(ans, u - map.get(depth) + 1);
        // 重标号
        u = u - map.get(depth) + 1;
        dfs(root.left, u << 1, depth + 1);
        dfs(root.right, u << 1 | 1, depth + 1);
    }

    @Test
    public void test_1() {
        final TreeNode root = TreeNode.parseTree(new Integer[]{1, 3, 2, 5, 3, null, 9});
        final int width = new Solution662().widthOfBinaryTree(root);
        System.out.println(width);
    }

}
