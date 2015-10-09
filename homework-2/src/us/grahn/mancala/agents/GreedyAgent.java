package us.grahn.mancala.agents;

import us.grahn.mancala.Board;
import us.grahn.mancala.Game;
import us.grahn.mancala.Player;

public class GreedyAgent implements Agent {

	@Override
	public int getMove(Board board, Player player) {


		int bestMove = 0;
		int bestEval = Integer.MIN_VALUE;


		for(int i = 0; i < board.getSize(); i++) {

			if(!board.isValidMove(player, i)) continue;

			final Game game = new Game(board.clone(), player);
			game.move(i);

			int eval = evaluate(game.getBoard());
			if(player == Player.OPPONENT) eval *= -1;

			if(bestEval < eval) {
				bestEval = eval;
				bestMove = i;
			}
		}

		return bestMove;
	}

	public int evaluate(final Board board) {
		return board.getMancala(Player.PLAYER) - board.getMancala(Player.OPPONENT);
	}

}
