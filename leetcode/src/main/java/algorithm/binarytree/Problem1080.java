package algorithm.binarytree;

import datastructure.TreeNode;

/**
 * @Author Allenzsy
 * @Date 2022/8/15 1:58
 * @Description:
 */
public class Problem1080 {

    public TreeNode sufficientSubset(TreeNode root, int limit) {
        boolean removed = dfs(root, 0, limit);
        return removed ? null : root;
    }

    // 返回值决定是否需要删除, 内部比较自己当前的总和与limit
    public boolean dfs(TreeNode node, int sum, int limit) {
        if(node.left == null && node.right == null) {
            // 到达叶子节点, 判断当前和与limit的大小
            return sum + node.val < limit;
        }
        int curSum = sum + node.val;
        boolean left = node.left == null ? true : dfs(node.left, curSum, limit);
        boolean right = node.right == null ? true : dfs(node.right, curSum, limit);

        if(left) { node.left = null; }
        if(right) { node.right = null; }

        return left && right;
    }

    // 只删除叶子节点的, 理解错误
    // 返回值决定是否需要删除, 内部比较自己当前的总和与limit
    public boolean dfsWrong(TreeNode node, int sum, int limit) {
        if(node == null) {
            return true;
        }
        int curSum = sum + node.val;
        boolean left = dfsWrong(node.left, curSum, limit);
        boolean right = dfsWrong(node.right, curSum, limit);

        if(left) { node.left = null; }
        if(right) { node.right = null; }

        return left && right && (curSum < limit);
    }

}
