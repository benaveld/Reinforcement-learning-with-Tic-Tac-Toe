package game;

public abstract interface Player {
	public boolean makeMove(Program p, Board board, XOEnum state);
}
