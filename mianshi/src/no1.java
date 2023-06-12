public class no1 {
    static class Node{
        int val;
        Node next;
        public Node (int v){
            val = v;
        }
    }
    public void main(String[] args) {
        Node l1 = new Node(1);
        l1.next = new Node(2);
        l1.next.next = new Node(3);

        Node l2 = new Node(5);
        l2.next = l1.next;
        Node rs = process(l1, l2);
    }

    public static Node process(Node l1, Node l2){
        int len1 = 0;
        int len2 = 0;
        Node p = l1, q = l2;
        while(p != null){
            len1++;
            p = p.next;
        }
        while(q != null){
            len2++;
            q = q.next;
        }
        p = l1;
        q = l2;
        while(len1 > len2){
            p = p.next;
            len1--;
        }
        while(len2 > len1){
            q = q.next;
            len2--;
        }

        while(p != null){
            if(p == q)
                return p;
            else{
                p = p.next;
                q = q.next;
            }
        }
        return null;
    }
}