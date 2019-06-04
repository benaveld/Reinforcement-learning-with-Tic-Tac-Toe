//Gruppnummer: 8
//Benjamin Avery
//Ludvig Svee
//Sebastian Gieser

package game;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

public class Board {
	private static final int SPACE_WIDTH = 3;
	private static final float STROKE_WEIGHT = 10f;
	private static final int COLOR = 120;

	private final int X_COLOR;
	private final int O_COLOR;

	private int posX, posY, width, height;
	private XOEnum[] spaces = new XOEnum[9];

	public Board(PApplet p, int posX, int posY, int width, int height) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;

		clear();

		X_COLOR = p.color(255, 0, 0);
		O_COLOR = p.color(0, 0, 255);
	}

	@SuppressWarnings("incomplete-switch")
	public void draw(PApplet p) {
		int spaceWidth = Math.floorDiv(width, SPACE_WIDTH);
		int spaceHeight = Math.floorDiv(height, SPACE_WIDTH);

		PShape gridShape = p.createShape(PApplet.GROUP);
		for (int dx = spaceWidth; dx <= width - SPACE_WIDTH; dx += spaceWidth) {
			PShape line = p.createShape(PConstants.LINE, posX + dx, posY, posX + dx, posY + height);
			line.setStroke(COLOR);
			line.setStrokeWeight(STROKE_WEIGHT);
			gridShape.addChild(line);
		}

		for (int dy = spaceHeight; dy <= height - SPACE_WIDTH; dy += spaceHeight) {
			PShape line = p.createShape(PConstants.LINE, posX, posY + dy, posX + width, posY + dy);
			line.setStroke(COLOR);
			line.setStrokeWeight(STROKE_WEIGHT);
			gridShape.addChild(line);
		}

		int spacing = (int) (Math.ceil(STROKE_WEIGHT) * 2);
		for (int i = 0; i < spaces.length; i++) {
			int x = Math.floorMod(i, SPACE_WIDTH) * spaceWidth + posX;
			int y = Math.floorDiv(i, SPACE_WIDTH) * spaceHeight + posY;

			switch (spaces[i]) {
			case X:
				PShape line1 = p.createShape(PConstants.LINE, x + spacing, y + spacing, x + spaceWidth - spacing,
						y + spaceHeight - spacing);
				line1.setStroke(X_COLOR);
				line1.setStrokeWeight(STROKE_WEIGHT);
				gridShape.addChild(line1);

				PShape line2 = p.createShape(PConstants.LINE, x + spaceHeight - spacing, y + spacing, x + spacing,
						y + spaceHeight - spacing);
				line2.setStroke(X_COLOR);
				line2.setStrokeWeight(STROKE_WEIGHT);
				gridShape.addChild(line2);
				break;

			case O:
				PShape circal = p.createShape(PConstants.ELLIPSE, x + Math.floorDiv(spaceWidth, 2),
						y + Math.floorDiv(spaceHeight, 2), spaceWidth - spacing * 2, spaceHeight - spacing * 2);
				circal.setStroke(O_COLOR);
				circal.setFill(false);
				circal.setStrokeWeight(STROKE_WEIGHT);
				gridShape.addChild(circal);
				break;
			}
		}

		p.shape(gridShape);
	}

	public void clear() {
		for (int i = 0; i < spaces.length; i++) {
			spaces[i] = XOEnum.empty;
		}
	}

	public void set(int space, XOEnum state) {
		spaces[space] = state;
	}

	public void set(int x, int y, XOEnum state) {
		spaces[x + y * SPACE_WIDTH] = state;
	}

	public XOEnum get(int space) {
		return spaces[space];
	}

	public XOEnum get(int x, int y, XOEnum state) {
		return spaces[x + y * SPACE_WIDTH];
	}
}
