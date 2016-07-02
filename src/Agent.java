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
	
	/**
	 * Increase or decrease the resource level of the agent. It's an all-or-nothing operation and
	 * will return false if the operation cannot be performed.
	 * @param amount Amount of resource to be added or withdrawn.
	 * @return Whether or not the operation could be performed.
	 */
	public abstract boolean alterResourceLevel(int amount);
	
	/**
	 * Transfer resources from Agent provider to Agent beneficiary. This is an all-or-nothing
	 * operation and will return false if cannot be performed.
	 * @param provider Agent from whom resources are taken.
	 * @param beneficiary Agent to whom resources are given.
	 * @param amount Quantity of resource transfered. Should be positive.
	 * @return Whether or not the transfer could be performed.
	 */
	public static boolean transferResources(Agent provider, Agent beneficiary, int amount){
		if( provider.alterResourceLevel(-amount) ){
			beneficiary.alterResourceLevel(+amount);
			return true;
		}
		return false;
	}
}
