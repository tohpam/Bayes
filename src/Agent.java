/**
 * Agents are actors who can interact with Persons.
 * 
 * @author Pam Toh
 * 
 * @see Person @see Stockpile
 */
public abstract class Agent {
	
	/**
	 * Coordinates of the agent in the world.
	 */
	// TODO: Do we really need this ? It would be great to dissociate the agent
	// from the internal representation of the world.
	public int xLocation,yLocation;
		
	/**
	 * Possible end states for a person/agent interaction. 
	 * 
	 * @author Pam Toh
	 * @see Agent#beConsidered(Person)
	 */
	public static enum ConsiderationValue {
		ASKER_GIVESUP, ASKER_REJECTED, ASKER_APPROVED
	}
	
	/**
	 * Simulates person p considering to ask the  agent for resources.
	 * If the person commits to asking and is approved, the transaction
	 * is taken care of.
	 * 
	 * @param p The person considering to ask for resources.
	 * 
	 * @return the state in which ended the interaction.
	 * 		ASKER_GIVESUP if the person didn't commit to ask the agent.
	 * 		ASKER_REJECTED if the agent declined the trade.
	 * 		ASKER_APPROVED if the agent accepted to give some resources.
	 * 
	 * @see Agent.ConsiderationValue
	 */
	public abstract ConsiderationValue beConsidered(Person p);
	
	/**
	 * Computes the tendency (as a score) of Person p to interact with current Agent.
	 * 
	 * @param p Person whose affinity with the Agent is to be computed.
	 * @return Affinity score. Higher score means more affinity.
	 */
	public abstract int interactionAffinity(Person p);
}
