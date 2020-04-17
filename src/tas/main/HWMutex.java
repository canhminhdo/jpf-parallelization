package tas.main;

import java.io.Serializable;

public class HWMutex implements Lock, Serializable {
	private TestAndSet lockFlag;
	
	public HWMutex() {}
	
	public HWMutex(TestAndSet lockFlag) {
		this.lockFlag = lockFlag;
	}
	
	public TestAndSet getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(TestAndSet lockFlag) {
		this.lockFlag = lockFlag;
	}

	@Override
	public void requestCS(MyThread thread) {	// entry protocol
		while(lockFlag.testAndSet(1, thread) == 1);
	}

	@Override
	public void releaseCS(MyThread thread) {	// exit protocol
		lockFlag.testAndSet(0, thread);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		HWMutex other = (HWMutex) obj;
		return this.lockFlag.equals(other.lockFlag);
	}

	@Override
	public String toString() {
		return lockFlag.toString();
	}

}
