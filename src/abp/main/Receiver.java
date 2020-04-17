package abp.main;

import java.util.Collection;

public class Receiver<P> extends Thread {
    private Channel<Pair<P,Boolean>> channel1;
    private Channel<Boolean> channel2;
    private Collection<P> packetsReceived;
    private Cell<Boolean> finish;
    private Boolean flag2 = true;

    public Receiver(Channel<Pair<P,Boolean>> ch1,
                    Channel<Boolean> ch2,
                    Collection<P> c,
                    Cell<Boolean> f) {
        this.channel1 = ch1;
        this.channel2 = ch2;
        this.packetsReceived = c;
        this.finish = f;
    }

    public void run() {
        while (true) {
            Boolean b = channel2.put(flag2);
            /*
            if (b != null)
                System.out.println("RecSending " + flag2);
            */
            Pair<P,Boolean> pr = channel1.get();
            if (pr != null) {
                /*
                System.out.println("RecReceived " + pr);
                */
                if (pr.second() == flag2) {
                    packetsReceived.add(pr.first());
                    flag2 = !flag2;
                }
            }
            if (finish.get()) break;
        }
        /*
        System.out.println("Receiver has received all packets!");
        */
    }
}