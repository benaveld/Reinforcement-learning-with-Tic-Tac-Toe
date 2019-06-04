//Gruppnummer: 8
//Benjamin Avery
//Ludvig Svee
//Sebastian Gieser

package game;

import game.ai.LearningPlayer;
import processing.core.PApplet;

public class Program extends PApplet {

	public static final int screenWidth = 640;
	public static final int screenHeight = 640;

	private final int WIN_TIMEOUT = 1000;

	private Board board;
	private boolean firstPlayer = true;
	private XOEnum winner = XOEnum.empty;

	private Player player1;
	private Player player2;

	private int winSavedTime = 0;

	@Override
	public void settings() {
		super.settings();
		setSize(screenWidth, screenHeight);
	}

	@Override
	public void setup() {
		super.setup();
		board = new Board(this, 0, 0, screenWidth, screenHeight);

		player1 = new HumanPlayer(XOEnum.X);
		player2 = new LearningPlayer(XOEnum.O);
	}

	@Override
	public void draw() {
		if (winner == XOEnum.empty) {
			update();
		}

		background(200);
		board.draw(this);

		if (winner != XOEnum.empty) {
			textSize(100);
			if (winner == XOEnum.draw) {
				text(winner.name(), width / 3f, height / 2f);
			} else {
				text(winner.name() + " wins", width / 3f, height / 2f);
			}
			fill(0);

			if (millis() - winSavedTime >= WIN_TIMEOUT) {
				reset();
			}
		}
	}

	public void update() {
		boolean moved;
		if (firstPlayer) {
			moved = player1.makeMove(this, board);
		} else {
			moved = player2.makeMove(this, board);
		}

		if (moved) {
			firstPlayer = !firstPlayer;
			winner = board.getWinner();
			if (winner == XOEnum.empty && board.isDraw()) {
				winner = XOEnum.draw;
			}

			if (winner != XOEnum.empty) {
				firstPlayer = true; //remove later
				
				player1.reset(winner);
				player2.reset(winner);
				winSavedTime = millis();
			}
		}
	}

	public void reset() {
		board.clear();
		winner = XOEnum.empty;
	}

	public static void main(String[] args) {
		PApplet.main("game.Program");
	}

}
