package nspk.main;

import java.io.Serializable;
import java.util.ArrayList;

public class MultiSet<E> implements Serializable {
	// Order is irrelevant
	protected ArrayList<E> multiset;
	protected String oc;
	
	public MultiSet(String oc) {
		this.multiset = new ArrayList<E>();
		this.oc = oc;
	}
	
	public boolean contains(E e) {
		return multiset.contains(e);
	}
	
	public synchronized boolean add(E e) {
		if (this.contains(e))
			return false;
		
		multiset.add(e);
		return true;
	}
	
	public boolean _add(E e) {
		if (this.contains(e))
			return false;
		
		multiset.add(e);
		return true;
	}
	
	public boolean remove(E e) {
		return multiset.remove(e);
	}
	
	public E removeTop() {
		if (!multiset.isEmpty()) {
			return multiset.remove(0);
		}
		return null;
	}
	
	public E getTop() {
		return multiset.get(0);
	}
	
	public E getLast() {
		if (isEmpty())
			return null;
		return multiset.get(multiset.size() - 1);
	}
	
	public int size() {
		return multiset.size();
	}
	
	public boolean isEmpty() {
		return multiset.size() == 0;
	}
	
	public ArrayList<E> getAll() {
		return multiset;
	}
	
	public String toString() {
		if (multiset.size() == 0) {
			return oc + ": emp";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(oc + ": (");
		sb.append(multiset.get(0));
		for (int i = 1; i < multiset.size(); i ++) {
			sb.append(" ");
			sb.append(multiset.get(i));
		}
		sb.append(")");
		return sb.toString();
	}
}
