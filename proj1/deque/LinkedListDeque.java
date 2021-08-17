package deque;

public class LinkedListDeque<T> implements Deque<T>{
    private int size;
    private LinkedListNode sentinal;
    private LinkedListNode recurNode;

    public LinkedListDeque(){
        this.sentinal=new LinkedListNode(29, null, null);
        size=0;
        sentinal.next=sentinal;
        recurNode=sentinal.next;
        sentinal.prev=sentinal;
    }

    private class LinkedListNode<T>{
        private T value;
        private LinkedListNode next;
        private LinkedListNode prev;
        public LinkedListNode(T item, LinkedListNode next, LinkedListNode prev) {
            this.value = item;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void addFirst(T item) {
        LinkedListNode a = new LinkedListNode(item, sentinal.next, sentinal);
        sentinal.next.prev=a;
        sentinal.next=a;
        size++;
        recurNode=sentinal.next;
    }

    @Override
    public void addLast(T item) {

        LinkedListNode a =new LinkedListNode(item,sentinal,sentinal.prev);
        sentinal.prev.next=a;
        sentinal.prev=a;
        size++;
        recurNode=sentinal.next;
    }

//    public boolean isEmpty() {
//            return (size==0);
//        }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void printDeque(){
        if (this.isEmpty()) {
            System.out.println();
            return;
        }
        LinkedListNode a = sentinal.next;
        while (a.next!=sentinal){
            System.out.print(a.value+" ");
            a=a.next;
        }
        System.out.print(a.value);
        System.out.println();
    }
    @Override
    public T removeFirst(){
        if (this.isEmpty()) {
            return null;
        }
        Object value;
        value = sentinal.next.value;
        sentinal.next.next.prev=sentinal;
        sentinal.next=sentinal.next.next;
        size--;
        recurNode=sentinal.next;
        return (T) value;
    }
    @Override
    public T removeLast(){
        if (this.isEmpty()) {
            return null;
        }
        Object value;
        value=sentinal.prev.value;
        sentinal.prev.prev.next=sentinal;
        sentinal.prev=sentinal.prev.prev;
        size--;
        recurNode=sentinal.next;
        return (T) value;
    }
    @Override
    public T get(int index){
        LinkedListNode b;
        b=this.sentinal.next;
        if (index>this.size())
            return null;
        for (int i=0; i<index; i++){
            b=b.next;
        }
        return (T) b.value;
    }
    private T recurHelper(int index){
        if (index>=this.size())
            return null;
        if (index == 0) {
            return (T) recurNode.value;
        } else {
            recurNode = recurNode.next;
            return recurHelper(index -1);
        }
    }
    
    public T getRecursive(int index){
        recurNode=sentinal.next;
        return recurHelper(index);

    }
    @Override
    public boolean equals(Deque o) {
        if (o instanceof Deque) {
            if (((Deque) o).size()!=this.size())
                return false;
            for (int i = 0; i < ((Deque) o).size(); i++) {
                T ll=(T)((Deque) o).get(i);
                T ar=this.get(i);
                if (!ar.equals(ll)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
