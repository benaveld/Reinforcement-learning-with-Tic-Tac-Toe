//Gruppnummer: 8
//Benjamin Avery
//Ludvig Svee
//Sebastian Gieser

package game;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

public class Board {
	public static final int NR_SPACE_WIDTH = 3;
	private static final float STROKE_WEIGHT = 10f;
	private static final int COLOR = 120;

	private final int X_COLOR;
	private final int O_COLOR;

	private int posX, posY, width, height;
	private XOEnum[] spaces = new XOEnum[NR_SPACE_WIDTH * NR_SPACE_WIDTH];

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
		int spaceWidth = Math.floorDiv(width, NR_SPACE_WIDTH);
		int spaceHeight = Math.floorDiv(height, NR_SPACE_WIDTH);

		PShape gridShape = p.createShape(PApplet.GROUP);
		for (int dx = spaceWidth; dx <= width - NR_SPACE_WIDTH; dx += spaceWidth) {
			PShape line = p.createShape(PConstants.LINE, posX + dx, posY, posX + dx, posY + height);
			line.setStroke(COLOR);
			line.setStrokeWeight(STROKE_WEIGHT);
			gridShape.addChild(line);
		}

		for (int dy = spaceHeight; dy <= height - NR_SPACE_WIDTH; dy += spaceHeight) {
			PShape line = p.createShape(PConstants.LINE, posX, posY + dy, posX + width, posY + dy);
			line.setStroke(COLOR);
			line.setStrokeWeight(STROKE_WEIGHT);
			gridShape.addChild(line);
		}

		int spacing = (int) (Math.ceil(STROKE_WEIGHT) * 2);
		for (int i = 0; i < spaces.length; i++) {
			int x = Math.floorMod(i, NR_SPACE_WIDTH) * spaceWidth + posX;
			int y = Math.floorDiv(i, NR_SPACE_WIDTH) * spaceHeight + posY;

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

	public XOEnum getWinner() {
		XOEnum winner = XOEnum.empty;
		for (int i = 0; i < 3; i++) {
			if (spaces[i * 3] == spaces[i * 3 + 1] && spaces[i * 3 + 1] == spaces[i * 3 + 2]
					&& spaces[i * 3] != XOEnum.empty) {
				winner = spaces[i * 3];
				break;
			}

			if (spaces[i] == spaces[i + 3] && spaces[i + 3] == spaces[i + 6] && spaces[i] != XOEnum.empty) {
				winner = spaces[i];
				break;
			}

			if (i != 1) {
				if (spaces[i] == spaces[4] && spaces[4] == spaces[8 - i] && spaces[i] != XOEnum.empty) {
					winner = spaces[i];
					break;
				}
			}
		}
		return winner;
	}

	public boolean isDraw() {
		for (XOEnum e : spaces) {
			if (e == XOEnum.empty) {
				return false;
			}
		}
		return true;
	}

	public void clear() {
		for (int i = 0; i < spaces.length; i++) {
			spaces[i] = XOEnum.empty;
		}
	}

	public void set(int space, XOEnum state) {
		if (spaces[space] != XOEnum.empty) {
			throw new RuntimeException("Trying to fill an already filed board space.");
		} else if (state == XOEnum.empty) {
			throw new RuntimeException("Puting an empty in to the board.");
		} else {
			spaces[space] = state;
		}
	}

	public XOEnum[] getState() {
		return spaces.clone();
	}

	public void set(int x, int y, XOEnum state) {
		set(x + y * NR_SPACE_WIDTH, state);
	}

	public XOEnum get(int space) {
		return spaces[space];
	}

	public XOEnum get(int x, int y) {
		return spaces[x + y * NR_SPACE_WIDTH];
	}

	public int getX() {
		return posX;
	}

	public int getY() {
		return posY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
