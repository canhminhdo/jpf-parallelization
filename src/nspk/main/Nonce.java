package nspk.main;

/**
 * n(p1, p2, r)
 * 
 * A nonce is created by p1 for p2 with a fresh random number r
 * 
 * @author OgataLab
 *
 */
public class Nonce {
	
	private Principal p1;	// generator
	private Principal p2;	// for whom
	private Rand r;			// random
	
	public Nonce(Principal p1, Principal p2, Rand r) {
		this.p1 = p1;
		this.p2 = p2;
		this.r = r;
	}
	
	public Principal getGen() {
		return p1;
	}

	public Principal getForWhom() {
		return p2;
	}

	public Rand getRand() {
		return r;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		
		Nonce n = (Nonce)obj;
		
		return this.p1.equals(n.getGen()) &&
				this.p2.equals(n.getForWhom()) &&
				this.r.equals(n.getRand());
	}

	@Override
	public String toString() {
		return "n(" + this.p1.toString() + "," + this.p2.toString() + "," + this.r.toString() + ")";
	}
}
