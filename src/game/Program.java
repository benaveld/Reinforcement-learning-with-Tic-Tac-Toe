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

	public Program() {

	}

	@Override
	public void settings() {
		super.settings();
		setSize(screenWidth, screenHeight);

	}

	@Override
	public void setup() {
		super.setup();
		bord = new Board(this, 0, 0, screenWidth, screenHeight);
	}

	@Override
	public void draw() {
		super.draw();

		background(200);
		bord.draw(this);
	}

	public static void main(String[] args) {
		PApplet.main("game.Program");
	}

}
