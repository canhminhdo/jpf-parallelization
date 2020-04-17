package mq;

import gov.nasa.jpf.JPF;
import jpf.TraceMessage;
import server.Application;
import server.ApplicationConfigurator;

/**
 * This file show how to start JPF program from internal java program
 * 
 * @author OgataLab
 *
 */
public class RunJPF extends Thread {

	/**
	 * RunJPF constructor to generate configList for each case study. Change another
	 * case study, replace the current one to another
	 * 
	 * @param trace
	 */
	public RunJPF(TraceMessage traceMsg) {
		Application app = ApplicationConfigurator.getInstance().getApplication();
		app.setTraceMsg(traceMsg);
	}

	/**
	 * Running JPF program with configuration that is built from a Observer
	 * Component object
	 */
	public void run() {
		try {
			// add JPF configuration on the fly
			JPF jpf = new JPF();
			jpf.run();
			System.out.println("FINISHED RUNNING JOB");
		} catch (Exception e) {
			System.out.println("JPF Exception Start");
			e.printStackTrace();
			System.out.println("JPF Exception End");
		}
	}
}
