package nspk.main;

import java.util.ArrayList;
import java.util.Arrays;

import gov.nasa.jpf.vm.Verify;

public class Principal extends Thread {	
	protected String id;
	protected Network<Message<Cipher>> nw;
	protected MultiSet<Rand> rand;
	protected MultiSet<Nonce> nonces;
	protected MultiSet<Principal> prins;
	protected Controller<RewriteRule> rwController;
	protected Controller<Principal> prinController;
	
	public Principal(String id) {
		this.id = id;
		this.rwController = new Controller<RewriteRule>();
	}
	
	public Principal(String id, Network<Message<Cipher>> nw, MultiSet<Rand> rand, MultiSet<Nonce> nonces,
			MultiSet<Principal> prins, boolean is_principal) {
		this.id = id;
		this.nw = nw;
		this.rand = rand;
		this.nonces = nonces;
		this.prins = prins;
		if (is_principal) {
			this.rwController = new Controller<RewriteRule>(
				new ArrayList<RewriteRule>(
					Arrays.asList(new Challenge(), new Response(), new Confirmation())
				)
			);
		} else {
			this.rwController = new Controller<RewriteRule>(
					new ArrayList<RewriteRule>(
						Arrays.asList(new Response(), new Confirmation(), new Fake())
					)
				);
		}
	}

	public void initialize() {
		this.prinController = new Controller<Principal>(this.prins.getAll(), new ArrayList<Principal>(Arrays.asList(this)));
	}

	@Override
	public void run() {
		Verify.beginAtomic();
		initialize();
		Verify.endAtomic();
		while(true) {
			// selection method to run and then execute
			Verify.beginAtomic();
			RewriteRule rr = this.rwController.getOne();
//			System.out.println(this.id);
			Verify.endAtomic();
			rr.execute(this);
		}
	}
	
	public void challenge() {
		synchronized (rand) {
			Verify.beginAtomic();
			boolean is_empty = rand.isEmpty();
			Verify.endAtomic();
			if (!is_empty) {
				Verify.beginAtomic();
				Principal p = this;	// will be sender
				Principal q = this.prinController.getOne();	// Automatically select a receiver by PrinController
				
				Rand r = rand.removeTop();
				Nonce n = new Nonce(p, q, r);
				Cipher1 c1 = new Cipher1(q, n, p);
				Message<Cipher> m1 = new Message<Cipher>(Constants.m1, p, p, q, c1);
				if (nw.add(m1)) {
//					System.out.println(this + " - " + nw);
				}
				q.do_intruder(n);
				// Sender once `challenge`, cannot do challenge anymore as well as response
				this.rwController.remove(new Challenge());
				this.rwController.remove(new Response());

				// No body should use challenge
				ArrayList<Principal> prins = this.getPrins().getAll();
				for (int i = 0; i < prins.size(); i ++) {
					prins.get(i).getRwController().remove(new Challenge());
				}
				Verify.endAtomic();
			}
//			else {
//				Verify.beginAtomic();
//				this.rwController.remove(new Challenge());
//				this.rwController.remove(new Response());
//				Verify.endAtomic();
////				System.out.println(this + " - challengen - " + this.rwController.getList());
//			}
		}
	}
	
	public void response() {
		synchronized (rand) {
			Verify.beginAtomic();
			boolean is_empty = rand.isEmpty();
			Verify.endAtomic();
			if (!is_empty) {
				Verify.beginAtomic();
				ArrayList<Message<Cipher>> m1_matching = nw.responseMatching(this);
				int m1_size = m1_matching.size();
				Verify.endAtomic();
				if (m1_size > 0) {
					Verify.beginAtomic();
					Controller<Message<Cipher>> m1Controller = new Controller<Message<Cipher>>(m1_matching);
					Message<Cipher> m1 = m1Controller.getOne();
					// Sending back to sender
					Principal q = m1.getReceiver();	// will be sender now
					Principal p = m1.getSender();	// will be receiver now
					Rand r = rand.removeTop();
					Cipher1 c1 = (Cipher1) m1.getCipher();
					Nonce n1 = c1.getNonce();
					Nonce n2 = new Nonce(q, p, r);
					Cipher2 c2 = new Cipher2(p, n1, n2, q);
					Message<Cipher> m2 = new Message<Cipher>(Constants.m2, q, q, p, c2);
					if (nw.add(m2)) {
//						System.out.println(this + " - " + nw);
					}
					p.do_intruder(n1);
					p.do_intruder(n2);
					
					this.rwController.remove(new Challenge());
					this.rwController.remove(new Response());
					
					// No body should use Challenge, Response
					ArrayList<Principal> prins = this.getPrins().getAll();
					for (int i = 0; i < prins.size(); i ++) {
						prins.get(i).getRwController().remove(new Challenge());
						prins.get(i).getRwController().remove(new Response());
					}

					Verify.endAtomic();
				}
			}
//			else {
//				Verify.beginAtomic();
//				this.rwController.remove(new Challenge());
//				this.rwController.remove(new Response());
//				Verify.endAtomic();
//			}
		}
	}
	
	public void confirmation() {
		Verify.beginAtomic();
		ArrayList<Pair<Message<Cipher>, Message<Cipher>>> pairs = nw.confirmationMatching(this);
		int pairs_size = pairs.size();
		Verify.endAtomic();
		if (pairs_size > 0) {
			Verify.beginAtomic();
			Controller<Pair<Message<Cipher>, Message<Cipher>>> pairsController = new Controller<Pair<Message<Cipher>,Message<Cipher>>>(pairs);
			Pair<Message<Cipher>, Message<Cipher>> pair = pairsController.getOne();

			Message<Cipher> m1 = pair.first();
			Message<Cipher> m2 = pair.second();
			Cipher2 c2 = (Cipher2) m2.getCipher();
			Cipher3 c3 = new Cipher3(m1.getReceiver(), c2.getNonce2());
			Message<Cipher> m3 = new Message<Cipher>(Constants.m3, m1.getCreator(), m1.getSender(), m1.getReceiver(), c3);
			if (nw.add(m3)) {
//				System.out.println(this + " - " + nw);
			}
			m1.getReceiver().do_intruder(c3.getNonce());
			Verify.endAtomic();
		}
	}
	
	public void do_intruder(Nonce n) {}
	
	public void fake() {}
	
	public MultiSet<Principal> getPrins() {
		return this.prins;
	}
	
	public Controller<RewriteRule> getRwController() {
		return this.rwController;
	}
	
	public Network<Message<Cipher>> getNw() {
		return nw;
	}

	public MultiSet<Rand> getRand() {
		return rand;
	}

	public MultiSet<Nonce> getNonces() {
		return nonces;
	}

	public Controller<Principal> getPrinController() {
		return prinController;
	}

	public void setNw(Network<Message<Cipher>> nw) {
		this.nw = nw;
	}

	public void setRand(MultiSet<Rand> rand) {
		this.rand = rand;
	}

	public void setNonces(MultiSet<Nonce> nonces) {
		this.nonces = nonces;
	}

	public void setPrins(MultiSet<Principal> prins) {
		this.prins = prins;
	}

	public void setRwController(Controller<RewriteRule> rwController) {
		this.rwController = rwController;
	}

	public void setPrinController(Controller<Principal> prinController) {
		this.prinController = prinController;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		
		Principal p = (Principal)obj;
		
		return this.id.equals(p.id);
	}

	@Override
	public String toString() {
		return id;
	}
}
