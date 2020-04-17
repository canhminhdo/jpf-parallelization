package nspk.main;

import java.util.ArrayList;

public class PrinQueue<E> {
	
	ArrayList<E> queue;
	int n;
	
	public PrinQueue(int n) {
		this.n = n;
		this.queue = new ArrayList<E>();
	}
	
	public boolean contains(E e) {
		return queue.contains(e);
	}
	
	public synchronized void enqueue(E e) {
		if (!this.contains(e))
			queue.add(e);
	}
	
	public E getAt(int index) {
		return queue.get(index);
	}
	
	public boolean isReady() {
		return queue.size() == n;
	}
	
	public String toString() {
		return queue.toString();
	}
}
