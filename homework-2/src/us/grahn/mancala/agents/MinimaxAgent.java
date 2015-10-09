package us.grahn.mancala.agents;

import us.grahn.mancala.Board;
import us.grahn.mancala.Game;
import us.grahn.mancala.Player;

public class MinimaxAgent implements Agent {

	private final int maxDepth = 3;
	private Player player;

	public int evaluate(final Board board) {
		return board.getMancala(player) - board.getMancala(player.other());
	}

	@Override
	public int getMove(Board board, Player player) {

		this.player = player;

		int bestMove = 0;
		int bestEval = Integer.MIN_VALUE;


		for(int i = 0; i < board.getSize(); i++) {

			if(!board.isValidMove(player, i)) continue;

			final Board result = result(board, player, i);

			final int eval = minValue(result, player.other(), 1);

			//System.out.println(i + " = " + eval);

			if(bestEval < eval) {
				bestEval = eval;
				bestMove = i;
			}
		}

		//System.out.println("   Minimax Evaluations = " + evaluations);

		return bestMove;
	}

	private int maxValue(Board board, Player player, int depth) {

		if(board.isComplete() || depth >= maxDepth) {
			return evaluate(board);
		}

		int value = Integer.MIN_VALUE;

		for(int i = 0; i < board.getSize(); i++) {
			if(!board.isValidMove(player, i)) continue;
			final Board result = result(board, player, i);
			value = Math.max(value, minValue(result, player.other(), depth + 1));
		}

		return value;

	}

	private int minValue(Board board, Player player, int depth) {

		if(board.isComplete() || depth >= maxDepth) {
			return evaluate(board);
		}

		int value = Integer.MAX_VALUE;

		for(int i = 0; i < board.getSize(); i++) {
			if(!board.isValidMove(player, i)) continue;
			final Board result = result(board, player, i);
			value = Math.min(value, maxValue(result, player.other(), depth + 1));
		}

		return value;
	}

	private Board result(Board board, Player player, int move) {
		final Game game = new Game(board.clone(), player);
		game.move(move);
		return game.getBoard();
	}

}
