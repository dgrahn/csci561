package us.grahn.mancala.agents;

import us.grahn.mancala.Board;
import us.grahn.mancala.Game;
import us.grahn.mancala.Player;

public class AlphaBetaAgent implements Agent {

	private final int maxDepth;
	private Player player;

	public AlphaBetaAgent(final int depth) {
		this.maxDepth = depth;
	}

	public int evaluate(final Board board) {
		return board.getMancala(player) - board.getMancala(player.other());
	}


	@Override
	public int getMove(final Board board, final Player player) {

		this.player = player;

		int bestMove = 0;
		int bestEval = Integer.MIN_VALUE;


		for(int i = 0; i < board.getSize(); i++) {

			if(!board.isValidMove(player, i)) continue;

			final Board result = result(board, player, i);

			final int eval = minValue(result, player.other(), 1, Integer.MIN_VALUE, Integer.MAX_VALUE);

			if(bestEval < eval) {
				bestEval = eval;
				bestMove = i;
			}
		}

		//System.out.println("Alpha-Beta Evaluations = " + evaluations);

		return bestMove;
	}

	private int maxValue(final Board board, final Player player, final int depth, int alpha, final int beta) {

		if(board.isComplete() || depth >= maxDepth) {
			return evaluate(board);
		}

		int value = Integer.MIN_VALUE;

		for(int i = 0; i < board.getSize(); i++) {
			if(!board.isValidMove(player, i)) continue;

			final Board result = result(board, player, i);
			final int min = minValue(result, player.other(), depth + 1, alpha, beta);
			value = Math.max(value, min);

			if(beta <= value) return value;
			alpha = Math.max(alpha, value);
		}

		return value;

	}

	private int minValue(final Board board, final Player player, final int depth, final int alpha, int beta) {

		if(board.isComplete() || depth >= maxDepth) {
			return evaluate(board);
		}

		int value = Integer.MAX_VALUE;

		for(int i = 0; i < board.getSize(); i++) {
			if(!board.isValidMove(player, i)) continue;

			final Board result = result(board, player, i);
			final int max = maxValue(result, player.other(), depth + 1, alpha, beta);
			value = Math.min(value, max);

			if(value <= alpha) return value;
			beta = Math.min(beta, value);
		}

		return value;
	}

	private Board result(final Board board, final Player player, final int move) {
		final Game game = new Game(board.clone(), player);
		game.move(move);
		return game.getBoard();
	}

}
