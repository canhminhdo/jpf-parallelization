package nspk.main;

public class TestNSPK {

	public static void main(String[] args) {
		Network<Message<Cipher>> nw = new Network<Message<Cipher>>(Constants.nw);
		MultiSet<Rand> rand = new MultiSet<Rand>(Constants.rand);
		MultiSet<Nonce> nonces = new MultiSet<Nonce>(Constants.nonces);

		Rand r1 = new Rand(Constants.r1);
		Rand r2 = new Rand(Constants.r2);
		rand.add(r1);
		rand.add(r2);

		MultiSet<Principal> prins = new MultiSet<Principal>(Constants.prins);
		Principal p = new Principal(Constants.p, nw, rand, nonces, prins, true);
		Principal q = new Principal(Constants.q, nw, rand, nonces, prins, true);
		Intruder intrdr = new Intruder(Constants.intrdr, nw, rand, nonces, prins, false);
		prins.add(p);
		prins.add(q);
		prins.add(intrdr);

		// starting
		NSPK nspk = new NSPK();
		nspk.begin(p, q, intrdr);
	}
}