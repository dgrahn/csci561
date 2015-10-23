package us.grahn.mancala;

import java.util.ArrayList;
import java.util.List;

/**
 * A single move for a mancala board.
 *
 * @author Dan Grahn
 * @status FINISHED
 */
public class Move {

	/**
	 * The evaluation value for the move. Defaults to {@code Integer#MIN_VALUE}
	 */
	public int eval = Integer.MIN_VALUE;

	/**
	 * The actions which should be taken on the move.
	 */
	public final List<Integer> actions = new ArrayList<>();

	/**
	 * Constructs a new move.
	 */
	public Move() {

	}

	/**
	 * Constructs a new move with the specified action.
	 *
	 * @param action the action to add
	 */
	public Move(final int action) {
		actions.add(action);
	}

	@Override
	public String toString() {

		final StringBuilder b = new StringBuilder();
		b.append("Eval =");
		b.append(eval);
		b.append("\nMove = ");

		for(final int move : actions) {
			b.append(move);
			b.append(" -> ");
		}

		return b.toString();
	}

}
