//Gruppnummer: 8
//Benjamin Avery
//Ludvig Svee
//Sebastian Gieser

package game.ai;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import game.Board;
import game.Player;
import game.Program;
import game.XOEnum;

public class LearningPlayer extends Player {

	public static final int START_NR = 1;
	public static final int WIN_INCRES = 3;
	public static final int LOSE_DECRES = 1;
	public static final int DRAW_INCRES = 1;

	Collection<ProbiliblityTable> states = new LinkedList<>();
	private Collection<ProbiliblityTable> history = new LinkedList<>();

	public LearningPlayer(XOEnum me) {
		super(me);
	}
	
	public LearningPlayer(XOEnum me, boolean loadSave) {
		super(me);
		if(loadSave) {
			try {
				states = IOAI.load(me);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean makeMove(Program p, Board board) {
		ProbiliblityTable pro = null;
		for (ProbiliblityTable prob : states) {
			if (Arrays.equals(prob.boardState, board.getState())) {
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
		
		//if every probability is 0 then pick one at random.
		if (sum <= 0) {
			board.set(pro.getRandom(), me);
		} else {
			board.set(pro.getMatch(choise), me);
		}
		history.add(pro);

		//Printing for debuging purpeses
		for (int i = 0; i < 9; i += 3) {
			System.out.format("%d %d %d%n", pro.probs[i], pro.probs[i + 1], pro.probs[i + 2]);
		}
		System.out.println();

		return true;
	}

	@Override
	public void reset(XOEnum winner) {
		//Update the probiliblitys for the used move based on if the AI won.
		for (ProbiliblityTable pro : history) {
			pro.update(winner, me);
		}

		history = new LinkedList<>();
		try {
			IOAI.save(this);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	
}