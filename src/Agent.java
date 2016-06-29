public abstract class Agent {
	public int xLocation,yLocation;
	
	// function can be moved
	
	public static enum ConsiderationValue {
		ASKER_GIVESUP, ASKER_REJECTED, ASKER_APPROVED
	}
	
	public abstract ConsiderationValue beConsidered(Person p);
}
