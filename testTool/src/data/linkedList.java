package data;


import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @description: 自定义一个链表数据类
 * @program: usefulTools
 * @author: hyq
 * @create: 2020-05-09 12:13
 **/
public class linkedList<T> {

    /**
     * Node 类
     */
    private class Node {
        private T data;
        private Node next;

        public Node() {
        }

        Node(T data) {
            this.data = data;
        }
    }

    // 头节点指针
    private Node head;
    // 尾节点指针
    private Node last;
    // 链表实际长度
    private int size = 8;

    public linkedList(int size) {
        this.size = size;
    }

    /**
     * 获取 链表节点
     *
     * @param index 节点索引位置
     * @return
     */
    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("超出链表节点范围");
        }
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = head.next;
        }
        return temp;
    }

    /**
     * 插入 链表节点
     *
     * @param data  插入的元素
     * @param index 插入节点索引位置
     */
    private void insert(T data, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("超出链表节点范围");
        }
        // 新建一个节点，此时 insertNode.next 为空
        Node insertNode = new Node(data);

        if (size == 0) {
            // 空链表
            head = insertNode;
            last = insertNode;

        } else if (index == 0) {
            // 插入头部，顺序不能乱
            insertNode.next = head;
            head = insertNode;
        } else if (index == size) {
            // 插入尾部，顺序不能乱
            last.next = insertNode;
            last = insertNode;
        } else {
            // 插入中部，顺序不能乱
            // 将插入节点的指向，与上一个节点的指向，同步
            insertNode.next = getNode(index - 1).next;
            // 再把上一个节点的指向，转为 插入的节点
            getNode(index - 1).next = insertNode;
        }
        size++;
    }

    /**
     * 删除 链表节点
     *
     * @param index 删除节点索引位置
     */
    private Node removeNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("超出链表节点范围");
        }
        Node removeNode = null;
        if (index == 0) {
            // 删除头节点
            removeNode = head;
            head = head.next;
        } else if (index == size - 1) {
            // 删除尾节点
            Node beforeNode = getNode(index - 1);
            removeNode = beforeNode.next;
            beforeNode.next = null;
            last = beforeNode;
        } else {
            // 删除中间节点
            // 获取前一个节点
            Node beforeNode = getNode(index - 1);
            // 获取下一个节点，这样子找不需要循环链表
            Node nextNode = beforeNode.next.next;
            removeNode = beforeNode.next;
            beforeNode.next = nextNode;
        }
        size--;
        return removeNode;
    }


    /**
     * 输出链表
     */
    public void output(){
        Node temp = this.head;
        while (temp!=null){
            System.out.println("data:"+temp.data);
            temp = temp.next;
        }
    }


    public static void main(String[] args) {
        linkedList<Integer> myLinkList = new linkedList<>(5);
        System.out.println(myLinkList.size);
        myLinkList.insert(3,0);
        myLinkList.insert(5,1);
        myLinkList.insert(6,2);
        myLinkList.insert(2,3);
        myLinkList.insert(9,4);
        myLinkList.output();
        System.out.println("====删除啦====");
        myLinkList.removeNode(1);
        myLinkList.output();

        LinkedList<Integer> integers = new LinkedList<>();

    }
}
