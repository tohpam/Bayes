// MAIN METHOD

// import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class World {
    // public static Tile[][] coordinates; // Two-dimensional array
    public Agent[][] agentMap; // Two-dimensional array
    // public ArrayList<Tile> theTiles= new ArrayList<>(); // ArrayList to hold tiles in the map
    public final int width;
    public final int height;

    // Creates an instance of a map with a user-defined size
    // Calls the Tile constructor and adds tiles to the map
    public World (int width, int height) {
//        coordinates = new Tile[width][height];
//        for(int i = 0; i<width; i++){
//            for(int j = 0; j<height; j++){
//                coordinates[i][j] = new Tile(i,j);
//                theTiles.add(coordinates[i][j]);
//            }
//        }
        this.width = width;
        this.height = height;
        this.agentMap = new Agent[width][height];
    }

    // A Person is placed on a Tile
    public void placeAgent(Agent a){
        int x,y;
        
        Random rand = new Random();
                
    	do{
        	x = rand.nextInt(width);
        	y = rand.nextInt(height);
        }while(agentMap[x][y] != null);
    	
    	// System.out.println("x, y " + x + ", " + y);
    	
    	agentMap[x][y] = a;
    	
    	a.xLocation = x;
    	a.yLocation = y;
    	
    	//System.out.println("x, y " + a.xLocation + ", " + a.yLocation);
    	
        // coordinates[x][y].addPerson(p);
    } // how to clear array?
    
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
    
    public void removeAgent (Agent a){
    	// fancy comment
    }
    
    // A Person is moved from the Tile
//	public static void movePerson (Person p){
//		int x = p.getX();
//		int y = p.getY();
//		
//		coordinates[x][y].removePerson(p);
//	}

}
