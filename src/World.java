// WORLD (MAP)

// Contains the 2D arrays that keep track of agent locations in a virtual map
// Has a user-defined width and height

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class World {
    public Agent[][] agentMap; // Two-dimensional array containing Agents
    public final int width; // x-axis from 0 to width
    public final int height; // y-axis from 0 to height

    // Creates an instance of a map with a user-defined size
    public World (int width, int height) {
        this.width = width;
        this.height = height;
        this.agentMap = new Agent[width][height]; // 2D Array gets its predetermined size
    }

    // Places an Agent (usually Person) in a position in the 2D array
    public void placeAgent(Agent a){
        int x,y; // x for horizontal, y for vertical
        
        Random rand = new Random();
                
    	do{ // Try a random x and y
        	x = rand.nextInt(width);
        	y = rand.nextInt(height);
        }while(agentMap[x][y] != null); // If the space in (x,y) is taken, try again
    	
    	agentMap[x][y] = a; // Puts Agent in the empty (x,y) slot
    	
    	// Updates Agent's coordinates
    	a.xLocation = x;
    	a.yLocation = y;
    	
    }
    
    public List<Agent> findNearbyAgents(int x, int y){
    	List<Agent> neighbors = new LinkedList<Agent>();
    	
    	for(int i = x-1; i <= x+1; i++){
    		for(int j = y-1; j <= y+1; j++){
    			if (isInBound(i,j)){
    				Agent a = agentMap[i][j];
    				if(a != null)neighbors.add(a);
    			}
    		} // how to exclude the agent who is looking for his neighbors?
    	}
    	return neighbors;
    }
    
    public boolean isInBound(int x, int y){
    	return x<width && y<height && x>=0 && y>=0;
    }
    
    public void removeAgent(Agent a){
    	int x,y; // x for horizontal, y for vertical
        
        // Get current coordinates of x and y
    	x = a.xLocation;
    	y = a.yLocation;
    	
        agentMap[x][y] = null; // Set that slot of the map array as null
    }
}

    // UNUSED STUFF
    
    // public static Tile[][] coordinates; // Two-dimensional array
    // public ArrayList<Tile> theTiles= new ArrayList<>(); // ArrayList to hold tiles in the map
    // Creates an instance of a map with a user-defined size
    // Calls the Tile constructor and adds tiles to the map
    
// Calls the Tile constructor and adds tiles to the map
//    public World (int width, int height) {
//      coordinates = new Tile[width][height];
//      for(int i = 0; i<width; i++){
//          for(int j = 0; j<height; j++){
//              coordinates[i][j] = new Tile(i,j);
//              theTiles.add(coordinates[i][j]);
//          }
//      }
//    }

// coordinates[x][y].addPerson(p);

    // A Person is moved from the Tile
//	public static void movePerson (Person p){
//		int x = p.getX();
//		int y = p.getY();
//		
//		coordinates[x][y].removePerson(p);
//	}
