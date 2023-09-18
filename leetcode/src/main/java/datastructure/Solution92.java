package datastructure;

import org.junit.Test;

/**
 * @Author Allenzsy
 * @Date 2023/2/22 22:53
 * @Description:
 */
public class Solution92 {

    public ListNode reverseBetween(ListNode head, int left, int right) {

        // 特殊情况
        if (left == right || head.next == null) {
            return head;
        }

        // 双指针指针反转链表再串起来
        ListNode p1 = null, p2 = head;

        // 先用 p2 定位到要开始反转链表的头节点
        for (int i = 1; i < left; i++) {
            p1 = p2;
            p2 = p2.next;
        }
        // 代表开始反转链表的前一个节点和起始节点都缓存起来
        ListNode reverseBeginPrev = p1;
        ListNode reverseBegin = p2;

        for (int i = 0; i <= right-left; i++) {
            ListNode temp = p2.next;
            p2.next = p1;
            p1 = p2;
            p2 = temp;
        }
        // 此时 p1 指向反转链表的结尾, p2 指向反转链表结尾的后一个节点
        reverseBegin.next = p2;
        if (reverseBeginPrev != null) {
            reverseBeginPrev.next = p1;
        } else {
            head = p1;
        }

        return head;
/*        if (head.next == null) return head;
        if (head.next != null && head.next.next == null) {
            if (right - left <= 0) {
                return head;
            } else {
                ListNode p1 = head.next;
                p1.next = head;
                head.next = null;
                return p1;
            }
        }
        ListNode pStart = head;
        for (int i = 1; i < left-1; i++) {
            pStart = pStart.next;
        }
        ListNode p1 = pStart, p2 = pStart.next, p3 = pStart.next.next;
        ListNode revEnd = p2;
        for (int i = 0; i <= right-left && p3!=null; i++) {
            p2.next = p1;
            p1 = p2;
            p2 = p3;
            p3 = p3.next;
        }
        // 此时 p2 应该指向了反转链表后的下一个节点
        pStart.next = p1;
        revEnd.next = p2;

        return head;*/

    }

    @Test
    public void test_case1() {
        ListNode head = ListNode.parseListNode(new int[]{1,2,3,4,5});
        new Solution92().reverseBetween(head, 2, 4);

        head = ListNode.parseListNode(new int[]{1,2,3});
        new Solution92().reverseBetween(head, 3, 3);

        head = ListNode.parseListNode(new int[]{3,5});
        new Solution92().reverseBetween(head, 1, 2);
    }
}
