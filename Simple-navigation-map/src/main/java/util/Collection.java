package util;

public abstract class Collection<T> implements Iterable<T> {

    public boolean isEmpty() {
        return size() == 0;
    }

    public abstract int size();

    public abstract T get(int i);

    public abstract Collection<T> set(int i, T v);

    public abstract Collection<T> swap(int i, int j);

    public abstract Collection<T> add(T v);

    public abstract Collection<T> add(int i, T v);

    public Collection<T> addAll(T[] c2) {
        for (T v : c2) {
            add(-1, v);
        }
        return this;
    }

    public abstract Collection<T> addAll(Collection<T> c2);

    public abstract T remove();

    public abstract T remove(int i);

    public Collection<T> clear() {
        while (!isEmpty()) {
            remove();
        }
        return this;
    }

}
