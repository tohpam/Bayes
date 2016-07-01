// PERSON

// The person object is generated with an initial resource level and location.
// This class will make the decision to seek resources via Bayes decision net.
// It will move at the end of its "turn".

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Person extends Agent {
	public static final int maxInitialResource = 10; // Maximum initial resources a Person can have
	public int resourceLevel; // Current resource level of the Person
	public List<Person> acquaintances; // People that the Person knows


	// Creates an instance of a Person
	public Person() {
		this.resourceLevel = randResource();
		this.acquaintances = new ArrayList<Person>();
	}
	
	// Returns a random number for initial resource
	private int randResource(){
		return new Random().nextInt(maxInitialResource+1);
	}

	// Adds another Person to list of acquaintances
	public void addToAcquaintances(Person p){
		this.acquaintances.add(p);
	}

	// Adds Person a and b to each other's list of acquaintances
	public static void makeAcquainted(Person a, Person b){
		a.addToAcquaintances(b);
		b.addToAcquaintances(a);
	}
	
	// Says whether this Person is acquainted with another Person
	public boolean isAcquainted(Person p){
		return this.acquaintances.contains(p);
	}
	
	// Increases resources by one unit after successful inquiry
	public void receiveResource(){
		this.resourceLevel ++;
	}
	
	// Giver Person transfers resource to Receiver Person if available
	public static void transferResource(Person giver, Person receiver){
		if(giver.resourceLevel>0){
			receiver.resourceLevel++;
		}
	}
	
	// Gets the outcome of asking for resources
	public static boolean getSuccess(float result){
		return new Random().nextFloat() < result;
	}

	// Process of attempting/doing a resource transfer
	// Also returns the decision that was made
	@Override
	public ConsiderationValue beConsidered(Person p) {
		boolean acquainted = p.isAcquainted(this); // Check whether pair is acquainted
		
		// INTEGRATION WITH BAYES DECISION NET
		DecisionMaker.Decision result = DecisionMaker.makeDecision(p.resourceLevel, acquainted);
		if (result.tryAsk){ // If utility of asking is greater for Person p
			boolean successful = Person.getSuccess(result.probSuccess);
			if (successful=true){ // If Person p's request was approved
				Person.transferResource(this, p);
				return ConsiderationValue.ASKER_APPROVED;
			}
			return ConsiderationValue.ASKER_REJECTED;
		}
		return ConsiderationValue.ASKER_GIVESUP;
	}

	// Returns true because Agent can be moved if it is a Person
	@Override
	public boolean canBeMoved(Agent a) {
		return true;
	}

	@Override
	public int interactionAffinity(Person p) {
		return this.isAcquainted(p) ? 10 : 5;
	}
	
	/**
	 * Returns a new List<Agent> sorted by interaction affinity with the person.
	 * @param agents List of agents to be sorted.
	 * @return New list of sorted agents.
	 * @see Agent#interactionAffinity(Person)
	 */
	public List<Agent> sortByAffinity(List<Agent> agents){
		return agents.stream()
					 .sorted((a1, a2) -> a2.interactionAffinity(this) - a1.interactionAffinity(this) )
					 .collect(Collectors.toList());
	}

//	public String toString(){
//	return "Person{ xLocation: "+ this.xLocation + "yLocation: " + this.yLocation + "}";
//}
	
}
