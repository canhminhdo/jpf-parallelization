package tas.main;

public interface Lock {
	public void requestCS(MyThread thread);

	public void releaseCS(MyThread thread);
}
