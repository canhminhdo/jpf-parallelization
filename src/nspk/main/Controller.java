package nspk.main;

import java.io.Serializable;
import java.util.ArrayList;

import gov.nasa.jpf.vm.Verify;

public class Controller<E> implements Serializable {

	private ArrayList<E> list;
	
	public Controller() {
		this.list = new ArrayList<E>();
	}
	
	public Controller(ArrayList<E> list) {
		this.list = list;
	}
	
	public Controller(ArrayList<E> list, ArrayList<E> except_list) {
		ArrayList<E> left_list = new ArrayList<E>();
		for (E e : list) {
			if (!except_list.contains(e)) {
				left_list.add(e);
			}
		}
		this.list = left_list;
	}
	
	public void remove(E e) {
		if (list.contains(e))
			list.remove(e);
	}
	
	public void remove(ArrayList<E> removeList) {
		for (E e : removeList) {
			this.remove(e);
		}
	}
	
	public void add(E e) {
		if (!list.contains(e))
			list.add(e);
	}
	
	public E getNext(ArrayList<E> except_list) {
		
		ArrayList<E> left_list = new ArrayList<E>();
		for (E e : this.list) {
			if (!except_list.contains(e)) {
				left_list.add(e);
			}
		}
		switch (left_list.size()) {
			case 0:
				return null;
			default:
				int index = Verify.getInt(0, left_list.size() - 1);
				return left_list.get(index);
		}
	}
	
	public ArrayList<E> getList() {
		return this.list;
	}
	
	public E find(Object o) {
		for (int i = 0; i < this.list.size(); i ++) {
			if (this.list.get(i).toString().equals(o.toString())) {
				return this.list.get(i);
			}
		}
		return null;
	}
	
	public E getOne() {
		switch (list.size()) {
			case 0:
				return null;
			default:
				int index = Verify.getInt(0, list.size() - 1);
				return list.get(index);
		}
	}
	
	@Override
	public String toString() {
		if (list.size() == 0) {
			return "emp";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		sb.append(list.get(0));
		for (int i = 1; i < list.size(); i ++) {
			sb.append(" ");
			sb.append(list.get(i));
		}
		sb.append(")");
		return sb.toString();
	}
}
