package abp.main;

public class Dropper<P> extends Thread {
    private Channel<P> channel;
    private Cell<Boolean> finish;

    public Dropper(Channel<P> ch,Cell<Boolean> f) {
        this.channel = ch;
        this.finish = f;
    }

    public void run() {
        while (true) {
            try { Thread.sleep(100); }
            catch (InterruptedException e) { }
            if (finish.get()) break;
            P p = channel.get();
            /*
            if (p != null)
                System.out.println("dropped: " + p);
            */
        }
    }
}