package nspk.main;

import java.io.Serializable;

public class Pair<E1,E2> implements Serializable {
    private E1 elt1;
    private E2 elt2;

    public Pair(E1 e1,E2 e2) {
        this.elt1 = e1;
        this.elt2 = e2;
    }

    public E1 first() { return elt1; }
    public E2 second() { return elt2; }

    public String toString() {
        return "< d(" + elt1 + ")," + elt2 + " >";
    }
    
    public String toCommand() {
    	return elt1 + "-" + elt2;
    }

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		Pair<E1,E2> other = (Pair<E1,E2>) obj;
		return first().equals(other.first()) && second().equals(other.second());
	}
    
    
}