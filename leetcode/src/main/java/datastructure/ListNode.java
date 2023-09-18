package datastructure;

import java.util.HashSet;

/**
 * @Author Allenzsy
 * @Date 2022/12/1 3:33
 * @Description:
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode() {}
    public ListNode(int val) { this.val = val; }
    public ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    public static ListNode parseListNode(int[] nums) {
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        for(int n : nums) {
            cur.next = new ListNode(n);
            cur = cur.next;
        }
        return dummy.next;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        ListNode cur = this;
        final HashSet<ListNode> set = new HashSet<>();
        while (cur != null && !set.contains(cur)) {
            set.add(cur);
            builder.append(cur.val).append("->");
            cur = cur.next;
        }
        builder.append("null");
        return builder.toString();
    }

    public static void main(String[] args) {
        ListNode node = ListNode.parseListNode(new int[]{1,2,3,4,5,67,80});
        System.out.println(node);

    }
}
