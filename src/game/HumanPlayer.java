//Gruppnummer: 8
//Benjamin Avery
//Ludvig Svee
//Sebastian Gieser

package game;

public class HumanPlayer extends Player {

	public HumanPlayer(XOEnum me) {
		super(me);
	}

	@Override
	public boolean makeMove(Program p, Board board) {
		if (!p.mousePressed) {
			return false;
		}
		p.mousePressed = false;
		// System.out.format("Mouse pressed at %d %d.%n", p.mouseX, p.mouseY);

		int mx = p.mouseX - board.getX();
		int my = p.mouseY - board.getY();
		if (mx < 0 || my < 0 || mx > board.getWidth() || my > board.getHeight()) {
			return false;
		}

		int sx = Math.floorDiv(mx, board.getWidth() / Board.NR_SPACE_WIDTH);
		int sy = Math.floorDiv(my, board.getHeight() / Board.NR_SPACE_WIDTH);

		if (board.get(sx, sy) == XOEnum.empty) {
			board.set(sx, sy, me);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void reset(XOEnum winner) {
	}
}
