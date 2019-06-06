//Gruppnummer: 8
//Benjamin Avery
//Ludvig Svee
//Sebastian Gieser

package game.ai;

import game.XOEnum;

public class ProbiliblityTable {
	int[] probs;
	int lastPick = -1;
	XOEnum[] boardState;

	// Fills the array with START_NR where ther is a leigal move.
	ProbiliblityTable(XOEnum[] state) {
		this.boardState = state.clone();
		probs = new int[state.length];
		for (int i = 0; i < state.length; i++) {
			if (state[i] == XOEnum.empty) {
				probs[i] = LearningPlayer.START_NR;
			} else {
				probs[i] = 0;
			}
		}
	}

	public ProbiliblityTable() {
		this.boardState = new XOEnum[9];
		this.probs = new int[this.boardState.length];
	}

	public int getMatch(int choise) {
		for (int i = 0; i < probs.length; i++) {
			choise -= probs[i];
			if (choise <= 0) {
				lastPick = i;
				return i;
			}
		}
		throw new RuntimeException("Choise is heigher then the sum of probs.");
	}

	public void update(XOEnum winner, XOEnum me) {
		if (winner == XOEnum.draw) {
			probs[lastPick] += LearningPlayer.DRAW_INCRES;
		} else if (winner == me) {
			probs[lastPick] += LearningPlayer.WIN_INCRES;
		} else {
			probs[lastPick] -= LearningPlayer.LOSE_DECRES;
		}
		if(probs[lastPick] < 0) {
			probs[lastPick] = 0;
		}
	}

	public int getRandom() {
		int choise = -1;
		do {
			choise = (int) Math.floor(Math.random() * boardState.length);
		} while (choise == boardState.length || boardState[choise] != XOEnum.empty);
		lastPick = choise;
		return choise;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (XOEnum e : boardState) {
			sb.append(e == XOEnum.empty ? " " : e.name());
		}
		sb.append(";");
		for (int p : probs) {
			sb.append(p + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
