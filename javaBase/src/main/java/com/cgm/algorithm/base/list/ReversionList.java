package com.cgm.algorithm.base.list;

/**
 * 反转 链表
 */
public class ReversionList {

    public static Node rever(Node head) {
        if (head == null || head.next == null) return head;
        Node pre = null;
        Node next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;

    }
    public static Node reverRecursion(Node head) {
        if (head == null || head.next == null) return head;
        Node node = reverRecursion(head.next);
        head.next.next = head;
        head.next = null;
        return node;

    }
    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        rever(n1);

    }
}


class Node {
    int value;
    Node next;

    public Node(int value) {
        this.value = value;
    }
}
