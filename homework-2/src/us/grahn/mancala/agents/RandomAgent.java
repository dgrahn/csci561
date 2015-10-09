package us.grahn.mancala.agents;

import java.util.Random;

import us.grahn.mancala.Board;
import us.grahn.mancala.Player;

public class RandomAgent implements Agent {

	private final Random random = new Random(System.nanoTime());

	@Override
	public int getMove(Board board, Player player) {

		while(true) {
			final int pit = random.nextInt(board.getSize());
			if(board.isValidMove(player, pit)) return pit;
		}
	}

}
