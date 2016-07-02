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
	
	// TODO remove
	public static void printResourcesHead(List<Person> thePeople){
		
		
	}
	
	// TODO remove
	public static void printResourcesLine(List<Person> thePeople, int turnNumber){
		
	}
	
	public static void main(String[] args) {
		
		Simulation sim = new Simulation();
		
		sim.addChart(new Chart(sim, "Resource per person over time"){

			@Override
			public void printHead() {
				this.buffer.append("Turn");
				int i = 0;
				for(Person p : this.sim.thePeople) this.buffer.append("\tP"+ ++i);
				this.buffer.append("\n");
			}

			@Override
			public void printLine() {
				this.buffer.append(this.line++);
				for(Person p : this.sim.thePeople) this.buffer.append("\t"+ p.resourceLevel);
				this.buffer.append("\n");
			}
			
		});
		
		sim.execute();
	}
	
	//////////////////////////////////////////////////////
	//////////////////////////////////////////////////////
	
	public World worldMap;
	public List<Person> thePeople;
	
	protected List<Chart> charts;
	
	public Simulation(){
		// The map of the simulation
		worldMap = new World (mapWidth,mapHeight);

		// The simulation person pool (e.g. population)
		thePeople = new ArrayList<Person>(personCount); // ArrayList to hold Person objects
		
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
		
		// Charts
		charts = new ArrayList<Chart>();
		
	}
	
	public void addChart(Chart c){
		this.charts.add(c);
	}
	
	public void execute(){
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
			
			// > Printing out current resource states
			for( Chart c : this.charts ) c.printLine();
			
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

		System.out.println("\n---------------------------------------- Charts ---------------------------------------------\n");

		for( Chart c : this.charts ) System.out.println(c.toString());
		
	}


}
