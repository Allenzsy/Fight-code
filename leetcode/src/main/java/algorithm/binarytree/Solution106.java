package algorithm.binarytree;

import datastructure.TreeNode;

/**
 * @Author Allenzsy
 * @Date 2022/11/9 1:02
 * @Description:
 */
public class Solution106 {

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int n = inorder.length;
        return dfs(inorder, 0, n-1, postorder, 0, n-1);
    }

    TreeNode dfs(int[] inorder,   int inL, int inR,
                 int[] postorder, int poL, int poR) {


        if(inL > inR) return null; // 只有一个节点返回

        int rootVal = postorder[poR];

        int rootIdxIn = inL;
        while(rootIdxIn <= inR ) {
            if(rootVal == inorder[rootIdxIn]) {
                break;
            }
            rootIdxIn++;
        }
        int rightNums = inR - rootIdxIn;

        TreeNode root = new TreeNode(rootVal);
        root.left =  dfs(inorder,   inL, rootIdxIn-1,
                postorder, poL, poR-rightNums-1);

        root.right = dfs(inorder,   rootIdxIn+1, inR,
                postorder, poR-rightNums, poR-1);
        return root;
    }

    public static void main(String[] args) {
        int[] inorder = {9,3,15,20,7};
        int[] postorder = {9,15,7,20,3};
        new Solution106().buildTree(inorder, postorder);
    }

}
