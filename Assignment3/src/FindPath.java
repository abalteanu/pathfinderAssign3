import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class findPath that finds the path through the maze using algorithm.
 * @author Ana Balteanu
 *
 */
public class FindPath {
	
	/**
	 * Private instance variable pyramidMap
	 */
	private Map pyramidMap;
	
	/**
	 * Constructor that takes in the file name input containing descriptions of the pyramid chambers
	 * @param fileName
	 */
	public FindPath(String fileName){ 
		// tries to open file, catches thrown exceptions from map constructor
		try {
			this.pyramidMap = new Map(fileName);
		} catch (InvalidMapCharacterException e) {
			e.getMessage();
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
	}
	
	/**
	 * Method that finds a path from entrance to treasure chamber(s)
	 * @return the stack of chambers representing the path
	 */
	public DLStack<Chamber> path() {
		
		DLStack<Chamber> chambers = new DLStack<Chamber>();
		
		// identifying start chamber and number of treasures
		Chamber start = pyramidMap.getEntrance();
		int N = pyramidMap.getNumTreasures();
		
		// pushing start chamber onto stack as first move
		chambers.push(start);
		start.markPushed(); // marking start as pushed as it was pushed onto stack
		
		int found = 0; // for number of chambers that have been found
		
		while(!chambers.isEmpty()) {
			
			Chamber curr = chambers.peek(); // finding current chamber
			Chamber best;	// for the best next move
			
			if(curr.isTreasure() && found == N) break;	// if all treasure has been found, break out of loop
			else {
				best = this.bestChamber(curr);	//finding the best next best move using bestChamber function
				
				if(best == null) {
					// if the best chamber is just null, pop the last chamber from the stack
					// mark this popped chamber as popped to know that it has been popped.
					best = chambers.pop(); // since best holds null, it is useless so using best variable to store popped chamber
					best.markPopped();
				} else {
					// if best chamber is not null, push that chamber onto the stack as your next moe
					chambers.push(best);
					best.markPushed();	// mark that chamber as pushed. 
				}
			}
			
		}
		
		return chambers;
	}
	
	/**
	 * Returns value of pyramidMap
	 * @return pyramidMap, containing all chambers in map
	 */
	public Map getMap() {
		return pyramidMap;
	}
	
	/**
	 * Function checking if a chamber is dim. Checks if chamber is not null, sealed, lighted and if one of the neighboring
	 * chambers is lighted.
	 * @param currentChamber is the chamber to be checked if dim or not
	 * @return true or false depending on if the function determines the chamber to be dim or not (based on above criteria)
	 */
	public boolean isDim(Chamber currentChamber) {
		boolean dim = false;
		
		if(currentChamber != null) {
			// checking if the current chamber is not null
			
			if(!currentChamber.isSealed() && !currentChamber.isLighted()) {
				// checking if the current chamber is not lit or sealed

				for(int i = 0; i < 6; i++) {
					// checking if each of the neighboring chambers is lit. As soon as one is lit, dim = true
					if(currentChamber.getNeighbour(i)!= null) {
						if(currentChamber.getNeighbour(i).isLighted()) dim = true;
					}
				}
			}

		}
		
		return dim;
	}
	
	/**
	 * Function determining the best next move (which of the six chambers is best).
	 * Specifications are as follows:
	 * 1. First preference given to UNMARKED TREASURE chamber with the smallest index
	 * 2. Next preference given to UNMARKED LIGHTED chamber with the smallest index
	 * 3. Next preference given to UNMARKED DIM chamber with the smallest index
	 * 4. If NO unmarked treasure, OR lighted OR dim chambers, return null
	 * @param currentChamber, which is the chamber to move from. 
	 * @return chamber that is next best move, or null if no next best move.
	 */
	public Chamber bestChamber(Chamber currentChamber) {
		// preferred is six means none of them are unchecked (must return null)
		int preferred = 6;
		
		for(int i = 5; i > 0; i--) {
			// iterating backwards from 5 to 0 to ensure smallest index is favoured
			System.out.println(i);
			//if(currentChamber.isLighted()) System.out.println("Lit");
			if(currentChamber.getNeighbour(i) == null) continue; // if neighbouring chamber is null, continue to next chamber
			else if(!currentChamber.getNeighbour(i).isMarked()) {
				// checking if neighbouring chamber is marked
				if(currentChamber.getNeighbour(i).isTreasure()) preferred = i; // first checking if this unmarked chamber is a treasure chamber
				else if(currentChamber.getNeighbour(i).isLighted()) preferred = i; // next checking if this unmarked chamber is lit
				else if(this.isDim(currentChamber.getNeighbour(i))) preferred = i; // next checking if this unmarked chamber is dim
			}
			// if neighbouring chamber is marked, then return null.
			else preferred = 6;
		}
		
		if(preferred == 6) return null;
		else return currentChamber.getNeighbour(preferred);
	}
}
