package jpf;

import java.io.Serializable;

import config.AppConfig;

public class TraceMessage implements Serializable {
	
	String appName;
	ExChoicePoint ex;
	int length;
	
	public TraceMessage(ExChoicePoint ex) {
		this.ex = ex;
		this.length = ex == null ? 0 : ex.getLength();
		this.appName = AppConfig.getInstance().getAppName();
	}

	public String getAppName() {
		return appName;
	}

	public ExChoicePoint getEx() {
		return ex;
	}

	public int getLength() {
		return length;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setEx(ExChoicePoint ex) {
		this.ex = ex;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return appName + " - " + length;
	}
	
}
