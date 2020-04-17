package nspk.main;

/**
 * c1(p,n,q)
 * 
 * Message is encrypted by p's public key with information
 * n is nonce
 * q is the sender information (sender identifier)
 * 
 * @author OgataLab
 *
 */
public class Cipher1 implements Cipher {
	
	private Principal p;	// Public key of receiver
	private Nonce n;		// Nonce
	private Principal q;	// Generator
	
	public Cipher1(Principal p, Nonce n, Principal q) {
		this.p = p;
		this.n = n;
		this.q = q;
	}
	
	public Principal getGen() {
		return q;
	}
	
	public Nonce getNonce() {
		return n;
	}
	
	public Principal getEnc() {
		return p;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		
		Cipher1 c1 = (Cipher1)obj;
		
		return this.p.equals(c1.getEnc()) &&
				this.n.equals(c1.getNonce()) &&
				this.q.equals(c1.getGen());
	}

	@Override
	public String toString() {
		return "c1(" + this.p.toString() + "," + this.n.toString() + "," + this.q.toString() + ")";
	}
}
