package tas.main;

import gov.nasa.jpf.vm.Verify;

public class TestTAS {

	public static int N = 3;
	public static String[] loc = new String[N];
	public static Integer lock = 0;

	public static void main(String[] args) throws InterruptedException {
		// Read arguments
		readArguments(args);

		// Initial for lock
		TestAndSet lockFlag = new TestAndSet();
		lockFlag.setMyValue(lock);
		Lock lock = new HWMutex(lockFlag);

		// Initial for threads
		MyThread t[] = new MyThread[N];
		for (int i = 0; i < N; i++) {
			t[i] = new MyThread(i, lock, loc[i]);
		}

		// Start threads
		for (int i = 0; i < N; i++) {
			t[i].start();
		}
		
//		assert 1 == 2 : "not equal";
		// Join threads
//		for (int i = 0; i < N; i++) {
//			t[i].join();
//		}
		
	}

	static void readArguments(String[] args) {

		if (args.length > 0) {
			lock = Integer.parseInt(args[0]);
			System.arraycopy(args, 1, loc, 0, N);
		} else {
			for (int i = 0; i < N; i++) {
				loc[i] = "rs";
			}
		}
	}
}
