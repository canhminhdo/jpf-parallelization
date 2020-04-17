package cloudsync.main;

import java.io.Serializable;

import gov.nasa.jpf.vm.Verify;

public class PC extends Thread implements Serializable {
	
	public static enum LabelP {idlep, gotval, updated}
	
	protected String pid;
	
	protected LabelP statusp;
	
	protected int valp;
	
	protected int tmp;
	
	protected Cloud cloud;
	
	public PC() {}
	
	public PC(String pid, LabelP statusp, int valp, int tmp, Cloud cloud) {
		this.pid = pid;
		this.statusp = statusp;
		this.valp = valp;
		this.tmp = tmp;
		this.cloud = cloud;
	}
	
	public Cloud getCloud() {
		return cloud;
	}

	public void setCloud(Cloud cloud) {
		this.cloud = cloud;
	}

	public String getPid() {
		return this.pid;
	}

	public LabelP getStatusp() {
		return statusp;
	}

	public int getValp() {
		return valp;
	}

	public int getTmp() {
		return tmp;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setStatusp(LabelP statusp) {
		this.statusp = statusp;
	}

	public void setValp(int valp) {
		this.valp = valp;
	}

	public void setTmp(int tmp) {
		this.tmp = tmp;
	}
	
	public boolean isIdlep() {
		return statusp == LabelP.idlep;
	}
	
	public boolean isGotval() {
		return statusp == LabelP.gotval;
	}
	
	public boolean isUpdated() {
		return statusp == LabelP.updated;
	}
	
	@Override
	public void run() {
		while(true) {
			boolean result = getval() || updated() || gotoidle();
//			System.out.println("Result = " + result + " -> " + this.toString());
//			System.out.println(cloud.toString());
		}
	}
	
	public boolean getval() {
		synchronized (cloud) {
			if (isIdlep() && cloud.isIdelc()) {
//				Verify.beginAtomic();
//				this.modval();
//				Verify.endAtomic();
				
				Verify.beginAtomic();
				setTmp(cloud.getValc());
				cloud.setStatusc(Cloud.LabelC.busy);
				setStatusp(LabelP.gotval);
				Verify.endAtomic();
				return true;
			}
			return false;
		}
	}
	
	public void modval() {
		// Refresh valp by plus 1, avoiding tedious values
		setValp(getValp() + 1);
	}
	
	public boolean updated() {
		synchronized (cloud) {
			if (isGotval() && cloud.isBusy()) {
				Verify.beginAtomic();
				setStatusp(LabelP.updated);
				if (getTmp() > getValp()) {
					setValp(getTmp());
				} else {
					setTmp(getValp());
					cloud.setValc(getValp());
				}
				Verify.endAtomic();
				return true;
			}
			return false;
		}
	}
	
	public boolean gotoidle() {
		synchronized (cloud) {
			if (isUpdated() && cloud.isBusy()) {
				Verify.beginAtomic();
				setStatusp(LabelP.idlep);
				setTmp(0);
				cloud.setStatusc(Cloud.LabelC.idlec);
				Verify.endAtomic();
				return true;
			}
			return false;
		}
	}

	@Override
	public String toString() {
		return "(pc[" + pid + "]: < " + statusp + "," + valp + "," + tmp + " >)";
	}
	
}
