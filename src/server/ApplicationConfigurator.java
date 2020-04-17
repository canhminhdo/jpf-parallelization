package server;

import jpf.ExChoicePoint;
import server.factory.ServerFactory;

/**
 * Bootstrap environment from here. You need configure your case study in this
 * file
 * 
 * @author OgataLab
 *
 */
public class ApplicationConfigurator {

	private static ApplicationConfigurator _instance = null;
	private Application app = null;
	private ExChoicePoint trace = null;

	/**
	 * Configure your case study in this function
	 * 
	 * @return
	 */
	public static ApplicationConfigurator getInstance() {
		if (_instance == null) {	
			_instance = new ApplicationConfigurator();
		}
		return _instance;
	}

	public ApplicationConfigurator() {
		this.app = new Application(new ServerFactory());
	}

	public Application getApplication() {
		return this.app;
	}
	
	public void setTrace(ExChoicePoint trace) {
		this.trace = trace;
	}
	
	public ExChoicePoint getTrace() {
		return this.trace;
	}
	
	public boolean isUsingTrace() {
		return trace != null;
	}
	
	public void reset() {
		trace = null;
	}
}
