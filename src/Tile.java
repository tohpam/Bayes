// TILE

// One tile is one 1x1 piece on the simulation grid map
//
//import java.util.ArrayList;
//
//public class Tile {
//    public final int xPosition,yPosition; // Position of the tile
//    boolean occupied = false; // Whether tile is occupied or not
//    ArrayList<Person> occupant = new ArrayList<>(); // ArrayList to hold occupant of tile
//    
//    // Creates an instance of a Tile
//    public Tile(int x, int y) {
//        xPosition = x;
//        yPosition = y;
//    }
//    
//    // Tile becomes occupied by a Person
//    public void addPerson(Person p){
//        this.occupant.add(p);
//        this.occupied = true;
//    }
//    
//    // Tile is vacated
//    public void removePerson(Person p){
//        this.occupant.remove(p);
//        this.occupied = false;
//    }
//    
//    // Shows whether the tile is occupied or not
//    public boolean checkTile(){
//    	return this.occupied;
//    }
//    
//    // Returns the Person that is occupying the Tile
//    // ???
//    
//}
