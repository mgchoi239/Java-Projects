package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private Comparator<T> com;

    public MaxArrayDeque(Comparator<T> c) {
        this.com = c;
    }

    public T max() {
        if (this.isEmpty()) {
            return null;
        }
        int index = 0;
        T max = (T) this.get(0);
        while (index != this.size()-1) {
            if (com.compare((T) this.get(index),max) >0) {
                max = (T) this.get(index);
            }
            index += 1;
        }
        return max;
    }


    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        int index = 0;
        T max = (T) this.get(0);
        while (index != this.size()-1) {
            if (c.compare((T) this.get(index),max) >0) {
                max = (T) this.get(index);
            }
            index += 1;
        }
        return max;
    }
}
