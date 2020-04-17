package abp.main;

public class EmptyQueue<E> implements Queue<E> {
    public EmptyQueue() { }
    public E top() { return null; }
    public NeQueue<E> enqueue(E e) {
        Queue<E> eq = new EmptyQueue<E>();
        return new NeQueue<E>(e,eq);
    }
    public Queue<E> dequeue() { return this; }
    public Queue<E> duptop() { return this; }
}