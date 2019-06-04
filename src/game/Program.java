//Gruppnummer: 8
//Benjamin Avery
//Ludvig Svee
//Sebastian Gieser

package game;

import processing.core.PApplet;

public class Program extends PApplet {

	public static final int screenWidth = 640;
	public static final int screenHeight = 640;

	private Board bord;
	private boolean firstPlayer = true;

	private Player player1;
	private Player player2;

	@Override
	public void settings() {
		super.settings();
		setSize(screenWidth, screenHeight);
	}

	@Override
	public void setup() {
		super.setup();
		bord = new Board(this, 0, 0, screenWidth, screenHeight);

		player1 = new HumanPlayer();
		player2 = new HumanPlayer();
	}

	@Override
	public void draw() {
		update();

		background(200);
		bord.draw(this);
	}

	public void update() {
		boolean moved;
		if (firstPlayer) {
			moved = player1.makeMove(this, bord, XOEnum.X);
		} else {
			moved = player2.makeMove(this, bord, XOEnum.O);
		}
		if (moved) {
			firstPlayer = !firstPlayer;
		}
	}

	public static void main(String[] args) {
		PApplet.main("game.Program");
	}

}
