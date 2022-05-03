package com.cgm.algorithm.base.list;

public class DeleteGivenValue {

    static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }


    public static Node removeValue(Node head, int num) {
        //确定从哪位置开始，如果单链表前几个的值是num则应该重新选择头节点
        while (head != null) {
            if (head.value != num) {
                break;
            }
            head = head.next;
        }
        // 1.head==null
        // 2.head!=null
        Node pre = head;
        Node cur = head;
        while (cur != null) {
            if (cur.value == num) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }

        return head;
    }
}
