package cloudsync.main;

import java.util.ArrayList;

public class CloudSync {
	
	public CloudSync() {}
	
	public void begin(ArrayList<PC> pcList) {
		try {
			System.out.println("==========START==========");
			for (PC pc : pcList) {
				pc.start();
			}
			
			for (PC pc : pcList) {
				pc.join();
			}
			System.out.println("==========END==========");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
