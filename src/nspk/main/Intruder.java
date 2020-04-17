package nspk.main;

import java.util.ArrayList;
import java.util.Arrays;

import gov.nasa.jpf.vm.Verify;

public class Intruder extends Principal {
	
	public Intruder(String id) {
		super(id);
	}
	
	public Intruder(String id, Network<Message<Cipher>> nw, MultiSet<Rand> rand, MultiSet<Nonce> nonces,
			MultiSet<Principal> prins, boolean is_principal) {
		super(id, nw, rand, nonces, prins, is_principal);
	}
	
	@Override
	public void run() {
		Verify.beginAtomic();
		this.initialize();
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
	
	public void fake() {
		this.fake11();
		this.fake12();
		this.fake21();
		this.fake22();
		this.fake31();
		this.fake32();
	}
	
	public void fake11() {
		if (!this.nonces.isEmpty()) {
			Verify.beginAtomic();
			Controller<Nonce> nonceController = new Controller<Nonce>(this.nonces.getAll());
			Nonce n = nonceController.getOne();	// automatically select one Nonce
			
			Principal p = this.prinController.getOne();	// sender
			Principal q = this.prinController.getNext(new ArrayList<Principal>(Arrays.asList(p)));	// receiver
			
			Cipher1 c1 = new Cipher1(q, n, p);
			Message<Cipher> m1_fake = new Message<Cipher>(Constants.m1, this, p, q, c1);
			if (nw.add(m1_fake)) {
//				System.out.println(this + " - fake11 - " + nw);
			}
			Verify.endAtomic();
		}
	}
	
	public void fake12() {
		Verify.beginAtomic();
		ArrayList<Message<Cipher>> list = nw.getMessageByTypeAndExceptMe(Constants.m1, this);
		Verify.endAtomic();
		if (list.size() > 0) {
			Verify.beginAtomic();
			Controller<Message<Cipher>> messageController = new Controller<Message<Cipher>>(list);
			Message<Cipher> m1 = messageController.getOne();	// automatically select one m1 message
			Cipher c1 = m1.getCipher();
			
			Principal p = this.prinController.getOne();	// sender
			Principal q = this.prinController.getNext(new ArrayList<Principal>(Arrays.asList(p)));	// receiver
			
			Message<Cipher> m1_fake = new Message<Cipher>(Constants.m1, this, p, q, c1);
			if (nw.add(m1_fake)) {
//				System.out.println(this + " - fake12 - " + nw);
			}
			Verify.endAtomic();
		}
	}
	
	public void fake21() {
		if (this.nonces.size() > 1) {
			Verify.beginAtomic();
			Controller<Nonce> nonceController = new Controller<Nonce>(this.nonces.getAll());
			Nonce n1 = nonceController.getOne();
			Nonce n2 = nonceController.getNext(new ArrayList<Nonce>(Arrays.asList(n1)));
			
			Principal p = this.prinController.getOne();	// receiver
			Principal q = this.prinController.getNext(new ArrayList<Principal>(Arrays.asList(p)));	// sender
			
			Cipher2 c2 = new Cipher2(p, n1, n2, q);
			Message<Cipher> m2_fake = new Message<Cipher>(Constants.m2, this, q, p, c2);
			if (nw.add(m2_fake)) {
//				System.out.println(this + " - fake21 - " + nw);
			}
			Verify.endAtomic();
		}
	}
	
	public void fake22() {
		Verify.beginAtomic();
		ArrayList<Message<Cipher>> list = nw.getMessageByTypeAndExceptMe(Constants.m2, this);
		Verify.endAtomic();
		if (list.size() > 0) {
			Verify.beginAtomic();
			Controller<Message<Cipher>> messageController = new Controller<Message<Cipher>>(list);
			Message<Cipher> m2 = messageController.getOne();	// automatically select one m2 message
			Cipher c2 = m2.getCipher();
			
			Principal p = this.prinController.getOne();	// receiver
			Principal q = this.prinController.getNext(new ArrayList<Principal>(Arrays.asList(p)));	// sender
			
			Message<Cipher> m2_fake = new Message<Cipher>(Constants.m2, this, q, p, c2);
			if (nw.add(m2_fake)) {
//				System.out.println(this + " - fake22 - " + nw);
			}
			Verify.endAtomic();
		}
	}
	
	public void fake31() {
		if (!this.nonces.isEmpty()) {
			Verify.beginAtomic();
			Controller<Nonce> nonceController = new Controller<Nonce>(this.nonces.getAll());
			Nonce n = nonceController.getOne();	// automatically select one Nonce
			
			Principal p = this.prinController.getOne();	// sender
			Principal q = this.prinController.getNext(new ArrayList<Principal>(Arrays.asList(p)));	// receiver
			Cipher3 c3 = new Cipher3(q, n);
			
			Message<Cipher> m3_fake = new Message<Cipher>(Constants.m3, this, p, q, c3);
			if (nw.add(m3_fake)) {
//				System.out.println(this + " - fake31 - " + nw);
			}
			Verify.endAtomic();
		}
	}
	
	public void fake32() {
		Verify.beginAtomic();
		ArrayList<Message<Cipher>> list = nw.getMessageByTypeAndExceptMe(Constants.m3, this);
		Verify.endAtomic();
		if (list.size() > 0) {
			Verify.beginAtomic();
			Controller<Message<Cipher>> messageController = new Controller<Message<Cipher>>(list);
			Message<Cipher> m3 = messageController.getOne();	// automatically select one m3 message
			Cipher c3 = m3.getCipher();
			
			Principal p = this.prinController.getOne();	// sender
			Principal q = this.prinController.getNext(new ArrayList<Principal>(Arrays.asList(p)));	// receiver
			
			Message<Cipher> m3_fake = new Message<Cipher>(Constants.m3, this, p, q, c3);
			if (nw.add(m3_fake)) {
//				System.out.println(this + " - fake32 - " + nw);
			}
			Verify.endAtomic();
		}
	}
	
	@Override
	public void do_intruder(Nonce n) {
		Verify.beginAtomic();
		if (this.nonces.add(n)) {
//			System.out.println(this.nonces);
		}
		Verify.endAtomic();
	}

	@Override
	public String toString() {
		return id;
	}
}
