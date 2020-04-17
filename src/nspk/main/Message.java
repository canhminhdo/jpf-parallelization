package nspk.main;

public class Message<E extends Cipher> {
	private Principal creator;	// who create
	private Principal sender;	// seeming sender
	private Principal receiver;	// to whom
	private E cipher;	// cipher will be included
	private String name;	// name of message such as m1, m2, m3
	
	public Message(String name) {
		this.name = name;
	}
	
	public Message(String name, Principal creator, Principal sender, Principal receiver, E cipher) {
		this.name = name;
		this.creator = creator;
		this.sender = sender;
		this.receiver = receiver;
		this.cipher = cipher;
	}
	
	public Principal getCreator() {
		return creator;
	}

	public Principal getSender() {
		return sender;
	}

	public Principal getReceiver() {
		return receiver;
	}

	public E getCipher() {
		return cipher;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		
		Message<E> message = (Message<E>)obj;
		
		return this.name.equals(message.getName()) &&
			this.creator.equals(message.getCreator()) &&
			this.sender.equals(message.getSender()) &&
			this.receiver.equals(message.getReceiver()) &&
			this.cipher.equals(message.getCipher());
	}
	
	public String toString() {
		return name + "(" + creator + "," + sender + "," + receiver + "," + cipher + ")";
	}
}
