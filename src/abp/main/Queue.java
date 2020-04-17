package abp.main;

public interface Queue<E> {
    E top();
    NeQueue<E> enqueue(E e);
    Queue<E> dequeue();
    Queue<E> duptop();
}