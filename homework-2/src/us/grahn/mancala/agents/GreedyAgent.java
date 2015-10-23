package us.grahn.mancala.agents;

import us.grahn.mancala.Board;
import us.grahn.mancala.Game;
import us.grahn.mancala.Move;
import us.grahn.mancala.Player;

/**
 * Greedy Agent.
 *
 * @author Dan Grahn
 * @status FINISHED
 */
public class GreedyAgent implements Agent {

    @Override
    public Move getMove(final Board board, final Player player) {

    	// We are going to find the best move.
    	Move bestMove = new Move();

        // Loop through all the valid moves
        for(final int pit : board.getValidMoves(player)) {

            // Build the move
            final Move move = new Move(pit);

            // Make the move
            final Game game = new Game(board.clone(), player);
            game.move(move);

            // If it is the same player's move again, we need to rerun the
            // algorithm on the next board and use the evaluation from the next
            // move, otherwise just use the evaluation function.
            if(game.getTurn() == player) {
            	final Move nextMove = getMove(game.getBoard(), player);
            	move.actions.addAll(nextMove.actions);
            	move.eval = nextMove.eval;
            } else {
            	move.eval = Evaluator.basic(game.getBoard(), player);
            }

            // Use the best move
            if(bestMove.eval < move.eval) {
            	bestMove = move;
            }
        }

        return bestMove;
    }

}
