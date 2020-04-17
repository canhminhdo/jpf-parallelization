package cloudsync.main;

import java.util.ArrayList;

public class TestCloudSync {

	public static void main(String[] args) {

		Cloud cloud = new Cloud(Cloud.LabelC.idlec, 2);
		PC p1 = new PC(Constants.p1, PC.LabelP.idlep, 1, 0, cloud);
		PC p2 = new PC(Constants.p2, PC.LabelP.idlep, 2, 0, cloud);
		PC p3 = new PC(Constants.p3, PC.LabelP.idlep, 3, 0, cloud);
		ArrayList<PC> pcList = new ArrayList<PC>();

		pcList.add(p1);
		pcList.add(p2);
		pcList.add(p3);

		CloudSync cloudsync = new CloudSync();
		cloudsync.begin(pcList);
	}
}
