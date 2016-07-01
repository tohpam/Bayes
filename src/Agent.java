//AGENT

//Parent class of Person and Stockpile classes
// Test comment

public abstract class Agent {
	public int xLocation,yLocation; // Coordinates
	
	// Returns true if Person and false if Stockpile
	public abstract boolean canBeMoved(Agent a);
	
	// Enumeration of possible interaction results
	public static enum ConsiderationValue {
		ASKER_GIVESUP, ASKER_REJECTED, ASKER_APPROVED
	}
	
	// Person p will consider/interact with the Agent and return a result
	public abstract ConsiderationValue beConsidered(Person p);
	
	/**
	 * Computes the tendency score of Person p to interact with current Agent.
	 * 
	 * @param p Person whose affinity with the Agent is to be computed.
	 * @return Affinity score. Higher score means more affinity.
	 */
	public abstract int interactionAffinity(Person p);
}
