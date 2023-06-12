package secRound;
/**
 * Q：编程题1
 * 在微服务的架构下，公司内部会有非常多的独立服务。
 * 服务之间可以相互调用，往往大型应用调用链条很长，如果出现循环依赖将出现非常恶劣的影响。
 * 对于一个具体应用，已知各个服务的调用关系（即依赖关系），请判断是否存在循环调用。
 * 输入：
 * 一组服务依赖关系list，('A', 'B') 表示 A 会调用 B 服务
 * service_relations = [('A', 'B'), ('A', 'C'), ('B', 'D'), ('D', 'A')]
 * 由于存在 A - B - D - A 故存在循环依赖，返回True；反之如果不存在，返回False
 * Follow up：
 * 1. 如果有多个环，请都检测出来
 * 2. 返回每个环中的服务名
 *
 * Q：编程题2
 * 运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制 。
 * 实现 LRUCache 类：
 * LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存
 * int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
 * void put(int key, int value) 如果关键字已经存在，则变更其数据值；
 * 如果关键字不存在，则插入该组「关键字-值」。
 * 当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
 * 进阶：你是否可以在 O(1) 时间复杂度内完成这两种操作？需要注意线程安全问题
 *
 *
 * Q：编程题3
 * 在微服务的架构下，公司内部会有非常多的独立服务。
 * 服务之间可以相互调用，往往大型应用调用链条很多也很长，我们需要找出耗时最大的链条进行优化。（假设服务同时调用其依赖的下游服务）
 * 例如：
 * A服务依赖B服务，平均调用延迟100ms，记为(A, B, 100)
 * 其他依赖和延迟如下：
 * (A, C, 200)
 * (A, F, 100)
 * (B, D, 100)
 * (D, E, 50)
 * (C, G, 300)
 * 那么服务A有三条调用链：A-B-D-E，A-C-G，A-F，平均延迟250，500，100
 * 延迟最大的调用链是A-C-G，延迟为500ms
 * 输入：
 * [(A, B, 100), (A, C, 200), (A, F, 100), (B, D, 100), (D, E, 50), (C, G, 300)]
 * 输出：
 * 500
 * PS：输入可以硬编码，无需实现字符串解析
 *
 *
 * Q：编程题4
 * 给两个单链表，如何判断两个单链表是否相交？若相交，则找出第一个相交的节点 时间和空间复杂度尽量低
 */

import java.util.HashMap;

public class no2 {

    static class Node{
        int key;
        int val;
        Node pre;
        Node next;
        public Node(int k, int v){
            key = k;
            val = v;
        }
        public Node(){}
    }
    public static int maxsize;
    public static int size = 0;
    static HashMap<Integer, Node> map = new HashMap<>() ;
    static Node head, tail;
    public static void main(String[] args) {
        LRUCache(2);
        put(1, 1);
        System.out.println(get(1));
        put(2, 2);
        put(3, 3);
        System.out.println(get(2));
        System.out.println(get(1));

    }

    public static void LRUCache(int capacity){
        maxsize = capacity;
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.pre = head;

    }

    public static int get(int key){
        if(map.containsKey(key)){
            Node n = map.get(key);
            delete(n);
            insertLast(n);
            return n.val;
        }else
            return -1;
    }

    public static void put(int key, int value){
        if(map.containsKey(key)){
            Node tp = map.get(key);
            tp.val = value;
            delete(tp);
            insertLast(tp);
            return;
        }
        Node n = new Node(key, value);
        if(size < maxsize){
            map.put(key, n);
            insertLast(n);
            size++;
        }else{
            map.remove(head.next.val);
            delete(head.next);
            insertLast(n);
        }
    }

    public static void insertLast(Node n){
        Node p = tail.pre;
        p.next = n;
        n.pre = tail.pre;
        n.next = tail;
        tail.pre = n;
    }

    public static void delete(Node n){
        Node p = n.pre;
        p.next = n.next;
        n.next.pre = p;
        n.next = null;
        n.pre = null;
    }
}
