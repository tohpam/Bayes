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
	public static final int simulationTime = 30; // Time of simulation
	public static final int mapWidth = 5; // Width of simulation map
	public static final int mapHeight = 5; // Height of simulation map
	public static final int acquaintanceCount = 5; // Number of acquaintance links
	
	

	
	public static void main(String[] args) {
		
		//////////////////////////////////////////////////////////
		///                   Initialization                   ///
		//////////////////////////////////////////////////////////
		
		// The map of the simulation
		World worldMap = new World (mapWidth,mapHeight);

		// The simulation person pool (e.g. population)
		List<Person> thePeople = new ArrayList<Person>(personCount); // ArrayList to hold Person objects
		
		// Populating the population
		for(int i = 0; i < personCount; i++){
			thePeople.add(new Person());
		}
		
		// Makes acquaintance links between the persons in the simulation
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
		
		
		// Initialization status
		System.out.println("Creating a " + mapWidth + "x" + mapHeight + " 2D simulation space,");
		System.out.println("with " + personCount + " persons and one stockpile in the center.");
		
		//////////////////////////////////////////////////////////
		///                     Simulation                     ///
		//////////////////////////////////////////////////////////
		
		System.out.println("\n------------------------------ Beginning of the simulation ----------------------------------\n");
		
		// DO PER TIME STEP
		for (int timeStep=0; timeStep<simulationTime; timeStep++) {
			
			// Randomly distribute the persons in the world for this round
			for (Person aPerson: thePeople){
				worldMap.placeAgent(aPerson);
			}
			
			// ^------ PRE-ROUND ------^ //
			
			
			// Each person plays its turn
			for(Person aPerson : thePeople){
				
				// Retrieving the person's neighbors
				List<Agent> neighbors = worldMap.findNearbyAgents(aPerson.xLocation, aPerson.yLocation);
				
				// If the person has neighbors, consider asking them for resources,
				// Starting with the agent with the highest affinity score (e.g. Stockpile > Acquaintances > Strangers )
				if (neighbors.size()>0){
					
					neighbors = aPerson.sortByAffinity(neighbors);
					
					boolean keepLooking = true;
					do {
						// Check next best agent until the person commits to ask (in opposition to giving up)
						// or there is no more neighbors
						
						Agent neighbor = neighbors.remove(0);
						Agent.ConsiderationValue val = neighbor.beConsidered(aPerson);
						
						if( val == Agent.ConsiderationValue.ASKER_APPROVED ){
							keepLooking = Agent.transferResources(neighbor, aPerson, 1) ? false : true;
							
						} else if( val == Agent.ConsiderationValue.ASKER_REJECTED ){
							keepLooking = false;
						}
						
					}while( keepLooking && neighbors.size()>0 );
					
				}
				
			}
			
			// v------ POST-ROUND ------v //
			
			// Removing the persons from the board
			// in anticipation of the next round
			for( Person aPerson: thePeople){
				worldMap.removeAgent(aPerson);
			}

		}
		
		// v-- End of Simulation --v
		System.out.println("\n--------------------------------- End of the simulation -------------------------------------\n");
		
		for( Person aPerson: thePeople ){
			System.out.println("\t" + aPerson.toString());
		}

		System.out.println("Simulation Over");
	
	}

}
