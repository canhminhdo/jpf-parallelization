package abp.main;

public class Pair<E1,E2> {
    private E1 elt1;
    private E2 elt2;

    public Pair(E1 e1,E2 e2) {
        this.elt1 = e1;
        this.elt2 = e2;
    }

    public E1 first() { return elt1; }
    public E2 second() { return elt2; }

    public String toString() {
        return "<" + elt1 + "," + elt2 + ">";
    }
}