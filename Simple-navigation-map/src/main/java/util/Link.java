package util;

class Link<T> {

    public Link(Link<T> next, T info) {
        this.next = next;
        this.info = info;
    }

    public Link(T info) {
        this(null, info);
    }

    Link<T> next;
    T info;
}
