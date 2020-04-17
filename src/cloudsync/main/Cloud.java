package cloudsync.main;

import java.io.Serializable;

public class Cloud implements Serializable {
	
	public static enum LabelC {idlec, busy}
	
	protected LabelC statusc;
	
	protected int valc;
	
	public Cloud() {}
	
	public Cloud(LabelC statusc, int valc) {
		this.statusc = statusc;
		this.valc = valc;
	}
	
	public LabelC getStatusc() {
		return statusc;
	}

	public int getValc() {
		return valc;
	}

	public void setStatusc(LabelC statusc) {
		this.statusc = statusc;
	}

	public void setValc(int valc) {
		this.valc = valc;
	}
	
	public boolean isIdelc() {
		return statusc == LabelC.idlec;
	}
	
	public boolean isBusy() {
		return statusc == LabelC.busy;
	}

	@Override
	public String toString() {
		return "(cloud: < " + statusc + "," + valc + " >)";
	}
	
}
