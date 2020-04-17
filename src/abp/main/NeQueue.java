package abp.main;

public class NeQueue<E> implements Queue<E> {
    private E head;
    private Queue<E> tail;

    public NeQueue(E e,Queue<E> q) {
        head = e;
        tail = q;
    }

    public E top() { return head; }

    public NeQueue<E> enqueue(E e) {
        NeQueue<E> q;
        if (tail instanceof EmptyQueue) {
            Queue<E> eq = new EmptyQueue<E>();
            q = new NeQueue<E>(e,eq);
        } else {
            q = tail.enqueue(e);
        }
        return new NeQueue<E>(head,q);
    }

    public Queue<E> dequeue() { return tail; }

    public NeQueue<E> duptop() {
        NeQueue<E> q = new NeQueue<E>(head,tail);
        return new NeQueue<E>(head,q);
    }
}