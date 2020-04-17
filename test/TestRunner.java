import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import config.AppConfigTest;
import database.ConnectionFactoryTest;
import jpf.ChoiceSelectorTest;

public class TestRunner {
	
	public static void main(String[] args) {
		JUnitCore junit = new JUnitCore();
		Result result = junit.run(
			AppConfigTest.class,
			ConnectionFactoryTest.class,
			ChoiceSelectorTest.class
		);
		resultReport(result);
		
		
	}
	
	public static void resultReport(Result result) {
	    System.out.println("Finished running testing");
	    System.out.println("------------------------");
	    System.out.println("Failures: " + result.getFailureCount());
	    System.out.println("Ignored: " + result.getIgnoreCount());
	    System.out.println("Time: " + result.getRunTime() + "ms");
	    System.out.println("------------------------");
	    
	    if (result.getFailureCount() > 0) {
	    	System.out.println("Failures analytic");
	    	System.out.println("------------------------");
	    	for (Failure failure : result.getFailures()) {
				System.out.println(failure.toString());
			}
	    }
	}
}
