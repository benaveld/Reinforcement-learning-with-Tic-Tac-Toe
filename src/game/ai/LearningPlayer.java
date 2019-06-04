//Gruppnummer: 8
//Benjamin Avery
//Ludvig Svee
//Sebastian Gieser

package game.ai;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import game.Board;
import game.Player;
import game.Program;
import game.XOEnum;

public class LearningPlayer extends Player {

	private static final int START_NR = 1;
	private static final int WIN_INCRES = 3;
	private static final int LOSE_DECRES = 1;

	private Collection<ProbiliblityTable> states = new LinkedList<>();
	private Collection<ProbiliblityTable> history = new LinkedList<>();

	public LearningPlayer(XOEnum me) {
		super(me);
	}

	@Override
	public boolean makeMove(Program p, Board board) {
		ProbiliblityTable pro = null;
		for(ProbiliblityTable prob : states) {
			if(Arrays.equals(prob.boardState, board.getState())) {
				pro = prob;
				break;
			}
		}
		
		if (pro == null) {
			pro = new ProbiliblityTable(board.getState());
			states.add(pro);
		}

		int sum = 0;
		for (int nr : pro.probs) {
			sum += nr;
		}

		int choise = (int) Math.floor(Math.random() * (sum - 1)) + 1;
		if (choise > sum) {
			choise = sum;
		}

		board.set(pro.getMatch(choise), me);
		history.add(pro);

		for (int i = 0; i < 9; i += 3) {
			System.out.format("%d %d %d%n", pro.probs[i], pro.probs[i + 1], pro.probs[i + 2]);
		}
		System.out.println();

		return true;
	}

	@Override
	public void reset(XOEnum winner) {
		if (winner != XOEnum.draw) {
			for (ProbiliblityTable pro : history) {
				if (winner == me) {
					pro.probs[pro.lastPick] += WIN_INCRES;
				} else {
					pro.probs[pro.lastPick] -= LOSE_DECRES;
				}
			}
		}

		history = new LinkedList<>();
	}

	private class ProbiliblityTable {
		int[] probs;
		int lastPick = -1;
		XOEnum[] boardState;

		// Fills the array with START_NR where ther is a leigal move.
		ProbiliblityTable(XOEnum[] state) {
			this.boardState = state.clone();
			probs = new int[state.length];
			for (int i = 0; i < state.length; i++) {
				if (state[i] == XOEnum.empty) {
					probs[i] = START_NR;
				} else {
					probs[i] = 0;
				}
			}
		}

		int getMatch(int choise) {
			for (int i = 0; i < probs.length; i++) {
				choise -= probs[i];
				if (choise <= 0) {
					lastPick = i;
					return i;
				}
			}
			throw new RuntimeException("Choise is heigher then the sum of probs.");
		}
	}
}