//Gruppnummer: 8
//Benjamin Avery
//Ludvig Svee
//Sebastian Gieser

package game;

public abstract class Player {
	protected XOEnum me;

	public Player(XOEnum me) {
		this.me = me;
	}

	// makes a move on the board and return true if it did so
	abstract public boolean makeMove(Program p, Board board);

	// resets after every game
	abstract public void reset(XOEnum winner);
}
