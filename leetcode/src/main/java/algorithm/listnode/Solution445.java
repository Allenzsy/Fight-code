package algorithm.listnode;

import datastructure.ListNode;

/**
 * @Author Allenzsy
 * @Date 2022/12/1 3:41
 * @Description:
 */
public class Solution445 {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode p1 = l1, p2 = l2;
        int len1 = 0, len2 = 0;
        while(p1 != null || p2 != null) {
            if(p1 != null) {
                p1 = p1.next;
                len1++;
            }
            if(p2 != null) {
                p2 = p2.next;
                len2++;
            }
        }
        ListNode dummy = new ListNode(-1), cur = dummy;
        int n = (int) Math.abs(len1 - len2);
        while(n-- > 0) {
            cur.next = new ListNode(0);
            cur = cur.next;
        }
        cur.next = len1 <= len2 ? l1 : l2;
        ListNode ans = getSum(dummy.next, len1 <= len2 ? l2 : l1);
        if(ans.val >= 10) {
            ListNode temp = new ListNode(ans.val / 10, ans);
            ans.val = ans.val % 10;
            ans = temp;
        }
        return ans;
    }

    ListNode getSum(ListNode l1, ListNode l2) {
        if(l1 == null && l2 == null) {
            return null;
        }
        ListNode sumNode = getSum(l1.next, l2.next);
        int p = 0;
        if(sumNode != null) {
            p = sumNode.val / 10;
            sumNode.val = sumNode.val % 10;
        }
        return new ListNode(l1.val + l2.val + p, sumNode);
    }

    public static void main(String[] args) {
        ListNode l1 = ListNode.parseListNode(new int[]{7,2,4,3});
        ListNode l2 = ListNode.parseListNode(new int[]{5,6,4});
        new Solution445().addTwoNumbers(l1, l2);
    }

}
