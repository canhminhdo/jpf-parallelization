package nspk.main;

import java.util.ArrayList;

public class Network<E extends Message<Cipher>> extends MultiSet<E> {

	public Network(String oc) {
		super(oc);
	}
	
	public ArrayList<Message<Cipher>> responseMatching(Principal p) {
		ArrayList<Message<Cipher>> list = new ArrayList<Message<Cipher>>();
		for (int i = 0; i < multiset.size(); i ++) {
			Message<Cipher> message = multiset.get(i);
			if (message.getName().equals(Constants.m1) && message.getReceiver().equals(p)) {
				Cipher1 c1 = (Cipher1) message.getCipher();
				if (c1.getEnc().equals(p) && c1.getGen().equals(message.getSender())) {
					list.add(message);
				}
			}
		}
		return list;
	}
	
	public ArrayList<Pair<Message<Cipher>, Message<Cipher>>> confirmationMatching(Principal p) {
		ArrayList<Pair<Message<Cipher>, Message<Cipher>>> pairs = new ArrayList<Pair<Message<Cipher>, Message<Cipher>>>();
		for (int i = 0; i < multiset.size(); i ++) {
			Message<Cipher> m1 = multiset.get(i);
			if (m1.getName().equals(Constants.m1) && 
				m1.getCreator().equals(p) &&
				m1.getSender().equals(p) &&
				!m1.getReceiver().equals(p)
			) {
				Cipher1 c1 = (Cipher1) m1.getCipher();
				if (c1.getEnc().equals(m1.getReceiver()) && c1.getGen().equals(p)) {
					for (int j = 0; j < multiset.size(); j ++) {
						if (j != i) {
							Message<Cipher> m2 = multiset.get(j);
							if (m2.getName().equals(Constants.m2) &&
								m2.getSender().equals(m1.getReceiver()) &&
								m2.getReceiver().equals(m1.getSender())) {
								
								Cipher2 c2 = (Cipher2) m2.getCipher();
								if (c2.getEnc().equals(m2.getReceiver()) &&
										c2.getGen().equals(m2.getSender()) &&
										c2.getNonce1().equals(c1.getNonce())
								) {
									Pair<Message<Cipher>, Message<Cipher>> pair = new Pair<Message<Cipher>, Message<Cipher>>(m1, m2);
									pairs.add(pair);
								}
							}
						}
					}
				}
			}
		}
		return pairs;
	}
	
	public ArrayList<Message<Cipher>> getMessageByTypeAndExceptMe(String type, Principal p) {
		ArrayList<Message<Cipher>> list = new ArrayList<Message<Cipher>>();
		for (int i = 0; i < multiset.size(); i ++) {
			Message<Cipher> m = multiset.get(i);
			if (m.getName().equals(type) && !m.getCreator().equals(p)) {
				list.add(m);
			}
		}
		return list;
	}
}
