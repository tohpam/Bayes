// PERSON

// The person object is generated with an initial resource level and location.
// This class will make the decision to seek resources via Bayes decision net.
// It will move at the end of its "turn".

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Person extends Agent {
	public static final int maxInitialResource = 10;
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

	public void addToAcquaintances(Person p){
		this.acquaintances.add(p);
	}

	public static void makeAcquainted(Person a, Person b){
		a.addToAcquaintances(b);
		b.addToAcquaintances(a);
	}
	
//	// Returns a random number for acquaintance
//	public int randAcquaintance(){
//		Random rand = new Random();
//		int randomNum = rand.nextInt(101);
//		return randomNum;
//	}
//	
	
	public boolean isAcquainted(Person p){
		return this.acquaintances.contains(p);
	}
	
	// Increases resources by one unit after successful inquiry
	public void receiveResource(){
		this.resourceLevel ++;
	}
	
	public static void transferResource(Person giver, Person receiver){
		if(giver.resourceLevel>0){
			receiver.resourceLevel++;
		}
	}
	
	// Gets the outcome of asking for resources
	// By generating a random number and compares to probability of success
	public static boolean getSuccess(float result){
		return new Random().nextFloat() < result;
	}

	// returns a boolean saying that 
	public ConsiderationValue beConsidered(Person p) {
		boolean acquainted = p.isAcquainted(this);
		
		// INTEGRATION WITH BAYES DECISION NET
		DecisionMaker.Decision result = DecisionMaker.makeDecision(p.resourceLevel, acquainted);
		if (result.tryAsk){ // If utility of asking is greater than ignoring
			boolean successful = Person.getSuccess(result.probTransfer);
			if (successful=true){
				Person.transferResource(this, p);
				return ConsiderationValue.ASKER_APPROVED;
			}
			return ConsiderationValue.ASKER_REJECTED;
		}
		return ConsiderationValue.ASKER_GIVESUP;
	}

//	public String toString(){
//	return "Person{ xLocation: "+ this.xLocation + "yLocation: " + this.yLocation + "}";
//}
	
}
