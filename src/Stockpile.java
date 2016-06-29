// STOCKPILE

// One stockpile object will be instanced in the simulation.
// The stockpile will stay in one location (0,0).
// The stockpile will gain one resource per time unit.
// The stockpile will always give resources when asked.
// TODO: edit comments to reflect actual behavior !

public class Stockpile extends Agent {

	@Override
	public ConsiderationValue beConsidered(Person p) {
		p.receiveResource();
		return ConsiderationValue.ASKER_APPROVED;
	}
}
