//MAIN CLASS

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Simulation {
	// Simulation Parameters
	public static final int personCount = 10; // Size of population
	public static final int stockpileCount = 1; // Number of stockpiles
	public static final int simulationTime = 1; // Time of simulation
	public static final int mapWidth = 5; // Width of simulation map
	public static final int mapHeight = 5; // Height of simulation map
	public static final int acquaintanceCount = 5; // Number of acquaintance links

	
	public static void main(String[] args) {
		// INSTANTIATORS
		
		// Creates the map
		World worldMap = new World (mapWidth,mapHeight); // Creates the map of the simulation

		// Creates the simulation population and places them on the map
		List<Person> thePeople = new ArrayList<Person>(personCount); // ArrayList to hold Person objects
		for(int i = 0; i < personCount; i++){
			Person aPerson = new Person();
			thePeople.add(aPerson);
			worldMap.placeAgent(aPerson);
		}
		
		// Makes acquaintance links between people in the simulation
		List<Person> willBeAcquainted = new ArrayList<Person>(thePeople);
		
		for (int i=0; i<acquaintanceCount; i++){
			Collections.shuffle(willBeAcquainted);
			Person a = willBeAcquainted.get(0);
			Person b = willBeAcquainted.get(1);
			Person.makeAcquainted(a, b);
		}
		
		// Places the stockpiles on the map
		for(int i = 0; i < stockpileCount; i++){
			worldMap.placeAgent(new Stockpile());
		}
		
//		for (Person aPerson : thePeople){
//			System.out.println("x, y " + aPerson.xLocation + ", " + aPerson.yLocation);
//		}
		
		// System.out.println("Length of the person list is " + thePeople.size());
		
		// SYSTEM STATUS
		System.out.println("Creating a " + mapWidth + "x" + mapHeight + " 2D simulation space,");
		System.out.println("with " + personCount + " persons and one stockpile in the center.");
		
		// DO PER TIME STEP
		for (int timeStep=0; timeStep<simulationTime; timeStep++) {
			// thePile.replenish(); // Stockpile adds 10 new resources
			
			// DO PER PERSON
			for(Person aPerson : thePeople){
				
				List<Agent> neighbors = worldMap.findNearbyAgents(aPerson.xLocation, aPerson.yLocation);
				
				neighbors = aPerson.sortByAffinity(neighbors);
				
//				// To display/test the sorting
//				for(Agent a : neighbors){
//					System.out.print(a.getClass().getName());
//					System.out.println("("+a.interactionAffinity(aPerson)+")");
//				}
				
				if (neighbors.size()>0){
					
					Agent.ConsiderationValue val;
					do {
						val = neighbors.remove(0).beConsidered(aPerson);
					}while(val == Agent.ConsiderationValue.ASKER_GIVESUP && neighbors.size()>0);
					
				}
				
				
				System.out.println("Length of the neighbor list is " + neighbors.size() +" "+aPerson);
				
			}
			
			// Shuffling the persons on the board
			
			// > First, we remove them
			for( Person aPerson: thePeople){
				worldMap.removeAgent(aPerson);
			}
				
			// > Then we place them again
			for (Person aPerson: thePeople){
				worldMap.placeAgent(aPerson);
			}

		}
		
		System.out.println("----------------------------------------------------------------");
		for( Person aPerson: thePeople ){
			System.out.println(aPerson.toString());
		}
		
		System.out.println("Over");
	
	}

}
