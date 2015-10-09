package us.grahn.mancala.agents;

import us.grahn.mancala.Board;
import us.grahn.mancala.Player;

public interface Agent {

	public int getMove(Board board, Player player);

}
