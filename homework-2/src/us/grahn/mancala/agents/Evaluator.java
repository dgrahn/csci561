package us.grahn.mancala.agents;

import us.grahn.mancala.Board;
import us.grahn.mancala.Player;

/**
 * Home of various evaluation functions.
 *
 * @author Dan Grahn
 * @status FINISHED
 */
public class Evaluator {

	/**
	 * Basic evaluation function. Compares number of stones in the player's
	 * mancalas.
	 *
	 * @param  board  the board to evaluate
	 * @param  player the current player
	 * @return        the evaluation value for the player
	 */
    public static int basic(final Board board, final Player player) {
    	return board.getMancala(player) - board.getMancala(player.other());
    }

}
