package abp.main;

public class Channel<P> {
    private Queue<P> queue;
    private final int bound;
    private int nop;

    public Channel(int bound) {
        this.queue = new EmptyQueue<P>();
        this.bound = bound;
        this.nop = 0;
    }

    public synchronized P put(P p) {
        if (bound <= nop) return null;
        queue = queue.enqueue(p);
        nop++;
        return p;
    }

    public synchronized P get() {
        if (nop <= 0) return null;
        P p = queue.top();
        queue = queue.dequeue();
        nop--;
        return p;
    }

    public synchronized P duptop() {
        if (bound <= nop || nop <= 0) return null;
        queue = queue.duptop();
        nop++;
        return queue.top();
    }
}