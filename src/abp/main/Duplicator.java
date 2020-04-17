package abp.main;

public class Duplicator<P> extends Thread {
    private Channel<P> channel;
    private Cell<Boolean> finish;

    public Duplicator(Channel<P> ch,Cell<Boolean> f) {
        this.channel = ch;
        this.finish = f;
    }

    public void run() {
        while (true) {
            try { Thread.sleep(100); }
            catch (InterruptedException e) { }
            if (finish.get()) break;
            P p = channel.duptop();
            /*
            if (p != null)
                System.out.println("duplicated: " + p);
            */
        }
    }
}