// STOCKPILE

// One stockpile object will be instanced in the simulation.
// The stockpile will stay in one location (0,0).
// The stockpile will gain one resource per time unit.
// The stockpile will always give resources when asked.

public class Stockpile extends Agent {

	/**
	 * A stockpile always accepts to give resources.
	 * @return {@link ConsiderationValue#ASKER_APPROVED}
	 */
	@Override
	public ConsiderationValue beConsidered(Person p) {
		return ConsiderationValue.ASKER_APPROVED;
	}

	// Returns false because Agent cannot be moved if it is a Stockpile
//	@Override
//	public boolean canBeMoved(Agent a) {
//		return false;
//	}

	@Override
	public int interactionAffinity(Person p) {
		return 15;
	}

	/**
	 * Having an infinite amount of resource, a stockpile can withstand any
	 * alteration to its resource level. Always return true.
	 * @return true
	 */
	@Override
	public boolean alterResourceLevel(int amount) {
		return true;
	}
}
