package deque;
import java.util.Arrays;

public class ArrayDeque<T> implements Deque<T> {

    private T[] item;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        item = (T[]) new Object[8];
        size = 0;
        nextFirst = 7;
        nextLast = 0;
    }

    private T[] expandHelper(T[] item) {
        T[] newArray = (T []) new Object[size*2];
        for (int i=0; i<size;i++){
            newArray[i]=get(i);
        }
        nextLast=size;
        nextFirst= newArray.length-1;
        return newArray;

    }

    private T[] removeHelper(T[] item) {

        T[] newArray = (T []) new Object[size*4];
        for (int i=0; i<size;i++){
            newArray[i]=get(i);
        }
        nextLast=size;
        nextFirst= newArray.length-1;
        return newArray;
    }

    @Override
    public void addFirst(T objA){
        if (size >= item.length) {
            item=expandHelper(item);
        }
        this.item[nextFirst] = (T) objA;
        size++;
        if (nextFirst ==0) {
            nextFirst = item.length-1;
        } else {
            nextFirst --;
        }
    }

    @Override
    public void addLast(T objA){
        if (size >= item.length) {
            item=expandHelper(item);
        }
        this.item[nextLast] = (T) objA;
        size++;
        if (nextLast == item.length-1) {
            nextLast = 0;
        } else {
            nextLast ++;
        }
    }

    @Override
    public T removeFirst(){
        if (isEmpty())
            return null;
        T save=item[(nextFirst+1)% item.length];
        if (size*4<=item.length)
            item=removeHelper(item);
        size--;
        nextFirst =(nextFirst+1)% item.length;
        return save;
    }

    @Override
    public T removeLast(){
        T save;
        if (isEmpty())
            return null;
        if (nextLast ==0) {
            save = item[item.length - 1];
        } else {
            save = item[nextLast - 1];
        }
        if (size*4<=item.length)
            item=removeHelper(item);
        size--;
        if (nextLast ==0)
            nextLast = item.length-1;
        else
            nextLast --;
        return save;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void printDeque(){
        int i=0;
        if (size==0)
            return;
        for (; i<size-1; i++){
            System.out.print(get(i)+" ");
        }
        System.out.print(get(i));
        System.out.println();

    }

    @Override
    /* in case of bug check if its index or memory index */
    public T get(int index){
        if (index > size) {
            return null;
        }
        return item[(index+nextFirst+1)%item.length];
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Deque) {
            if (((Deque) o).size()!=this.size())
                return false;
            for (int i = 0; i < ((Deque) o).size(); i++) {
                T ll=(T)((Deque) o).get(i);
                T ar=this.get(i);
                if (!ll.equals(ar)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
