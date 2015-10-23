package us.grahn.mancala.agents;

import us.grahn.mancala.Board;
import us.grahn.mancala.Move;
import us.grahn.mancala.Player;

/**
 * An agent interface which takes a board and player and returns a move.
 *
 * @author Dan Grahn
 * @status FINISHED
 */
public interface Agent {

	/**
	 * Gets a move from the agent.
	 *
	 * @param  board  the board to calculate the move from
	 * @param  player the player to make the move for
	 * @return        the move which should be made
	 */
    public Move getMove(Board board, Player player);

}
