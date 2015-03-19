package centalSystem;

public interface ICentralSystem {

	public void addClientAtQueue(int index);

	public void clientServedAtQueue(int index);

	public int getNextClientAtQueue(int index);

	public int getTotalClientAtQueue(int index);
	
	public void addCounter(IPostCounter counter);
	
	public int nextTurnAtCounter(int index);
	
	public int getCounterNumber();

}