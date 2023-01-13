package util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class LinkedList<T> extends Collection<T> {

    public LinkedList() {
        this.k = 0;
        this.first = null;
        this.last = null;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public int size() { // O(1)
        return k;
    }

    @Override
    public T get(int i) { // O(n), O(1)
        T r = null;
        if ((0 <= i) && (i < size())) {
            Link<T> current;
            if (i == size() - 1) {
                current = last;
            } else {
                current = first;
                int p = 0;
                while ((current != null) && (p < i)) {
                    current = current.next;
                    p++;
                }
            }
            assert (current != null);
            r = (T) current.info;
        } else {
            throw new IndexOutOfBoundsException();
        }
        return r;
    }

    @Override
    public Collection<T> set(int i, T v) { // O(n), O(1)
        if ((0 <= i) && (i < size())) {
            Link<T> current;
            if (i == size() - 1) {
                current = last;
            } else {
                current = first;
                int p = 0;
                while ((current != null) && (p < i)) {
                    current = current.next;
                    p++;
                }
            }
            assert (current != null);
            current.info = v;
        } else {
            throw new IndexOutOfBoundsException();
        }
        return this;
    }

    @Override
    public Collection<T> swap(int i, int j) {
        boolean f = (0 <= i) && (i < size())
                && (0 <= j) && (j < size());
        if (f) {

            T tmp = get(i);
            set(i, get(i + 1));
            set(i + 1, tmp);
            return this;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public Collection<T> add(T v) { // O(1)
        return add(-1, v);
    }

    @Override
    public Collection<T> addAll(Collection<T> c2) {
        Collection<T> tmp = new LinkedList<>();
        for (T v : c2) {
            tmp.add(-1, v);
        }
        while (!tmp.isEmpty()) {
            add(-1, tmp.remove());
        }
        return this;
    }

    @Override
    public Collection<T> add(int i, T v) { // O(n), O(1)
        if (v != null) {
            if (isEmpty()) {
                first = last = new Link(v);
            } else if ((i < 0) || (i == size())) {
                last.next = new Link(v);
                last = last.next;
            } else if (i == 0) {
                first = new Link(first, v);
            } else if ((0 < i) && (i < size())) {
                Link<T> current = first;
                int p = 0;
                while ((current != null) && (p < (i - 1))) {
                    current = current.next;
                    p++;
                }
                assert (current != null);
                current.next = new Link(current.next, v);
            } else {
                throw new IndexOutOfBoundsException();
            }
            k++;
        } else {
            throw new IllegalArgumentException();
        }
        return this;
    }

    @Override
    public T remove() {
        return remove(0);
    }

    @Override
    public T remove(int i) {
        T r = null;
        if (!isEmpty()) {
            if (i == 0) {
                r = first.info;
                first = first.next;
                if (isEmpty()) {
                    last = null;
                }
                k--;
            } else if ((0 <= i) && (i < size())) {
                Link<T> current = first;
                int p = 0;
                while ((current.next != null) && (p < (i - 1))) {
                    current = current.next;
                    p++;
                }
                assert (current != null);
                current.next = current.next.next;
                if (current.next == null) {
                    last = current;
                }
                k--;
            }
        }
        return r;
    }

    @Override
    public String toString() { // O(n)
        StringBuilder r = new StringBuilder("{");
        Iterator<T> i = this.iterator();
        while (i.hasNext()) {
            r.append(i.next());
            if (i.hasNext()) {
                r.append(", ");
            }
        }
        r.append("}");
        return r.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<>(first);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        super.forEach(action);
    }

    int getK() {
        return k;
    }

    Link<T> getFirst() {
        return first;
    }

    Link<T> getLast() {
        return last;
    }

    private int k;
    private Link<T> first;
    private Link<T> last;
}

class ListIterator<T> implements Iterator<T> {

    public ListIterator(Link<T> current) {
        this.current = current;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        T r = null;
        if (hasNext()) {
            r = current.info;
            current = current.next;
        } else {
            throw new NoSuchElementException();
        }
        return r;
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        Iterator.super.forEachRemaining(action);
    }

    private Link<T> current;
}

