package nspk.main;

/**
 * m3(p, n)
 * 
 * Message is encrypted by p's public key with information
 * n is nonce
 * @author OgataLab
 *
 */
public class Cipher3 implements Cipher {
	
	private Principal p;
	private Nonce n;
	
	public Cipher3(Principal p, Nonce n) {
		this.p = p;
		this.n = n;
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
		
		Cipher3 c3 = (Cipher3)obj;
		
		return this.p.equals(c3.getEnc()) && this.n.equals(c3.getNonce());
	}
	
	@Override
	public String toString() {
		return "c3(" + this.p.toString() + "," + this.n.toString() + ")";
	}
}
