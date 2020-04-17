package tas.main;

import java.io.Serializable;

import gov.nasa.jpf.vm.Verify;

public class TestAndSet implements Serializable {
	Integer myValue = 0;

	public TestAndSet() {
	}

	public TestAndSet(Integer myValue) {
		this.myValue = myValue;
	}

	public synchronized int testAndSet(int newValue, MyThread thread) {
//		Verify.beginAtomic();
		int oldValue = myValue;
		myValue = newValue;

		if (thread.getLoc().equals("rs") && oldValue == 0) {
			thread.setLoc("cs");
		}
		if (thread.getLoc().equals("cs") && newValue == 0) {
			thread.setLoc("rs");
		}
//		Verify.endAtomic();
		return oldValue;
	}

	public int getValue() {
		return this.myValue;
	}

	public void setMyValue(Integer myValue) {
		this.myValue = myValue;
	}

	public Boolean isLock() {
		return this.myValue == 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		TestAndSet other = (TestAndSet) obj;
		return this.myValue.equals(other.getValue());
	}

	@Override
	public String toString() {
		return Boolean.toString(isLock());
	}
}
