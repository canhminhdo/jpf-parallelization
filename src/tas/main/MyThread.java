package tas.main;

import java.io.Serializable;

public class MyThread extends Thread implements Serializable {
	private int myId;
	private Lock lock;
	private String loc;
	
	public MyThread() {}
	
	public MyThread(int id, Lock lock, String loc) {
		this.myId = id;
		this.lock = lock;
		this.loc = loc;
	}
	
	public void rs() {
		System.out.println(myId + " is not in CS");
	}
	
	public void cs() {
		System.out.println(myId + " is in CS *****");
	}
	
	public int getMyId() {
		return myId;
	}

	public void setMyId(int myId) {
		this.myId = myId;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}
	
	public Lock getLock() {
		return lock;
	}

	public void setLock(Lock lock) {
		this.lock = lock;
	}

	public void run() {
        while (true) {
            lock.requestCS(this);
            lock.releaseCS(this);
        }
    }

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		MyThread other = (MyThread) obj;
		return loc.equals(other.loc);
	}

	@Override
	public String toString() {
		return loc;
	}
}
