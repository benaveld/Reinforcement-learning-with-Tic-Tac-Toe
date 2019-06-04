package game;

public class HumanPlayer implements Player {

	@Override
	public boolean makeMove(Program p, Board board, XOEnum state) {
		if(!p.mousePressed) {
			return false;
		}
		p.mousePressed = false;
		//System.out.format("Mouse pressed at %d %d.%n", p.mouseX, p.mouseY);
		
		int mx = p.mouseX - board.getX();
		int my = p.mouseY - board.getY();
		if(mx < 0 || my < 0 || mx > board.getWidth() || my > board.getHeight()) {
			return false;
		}
		
		int sx = Math.floorDiv(mx, board.getWidth()/Board.NR_SPACE_WIDTH);
		int sy = Math.floorDiv(my, board.getHeight()/Board.NR_SPACE_WIDTH);
		
		board.set(sx, sy, state);
		
		return true;
	}

}
