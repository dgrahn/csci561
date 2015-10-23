package us.grahn.mancala.agents;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import us.grahn.mancala.Board;
import us.grahn.mancala.Game;
import us.grahn.mancala.Move;
import us.grahn.mancala.Player;

/**
 * An agent which uses MiniMax to determine which move to make.
 *
 * @author Dan Grahn
 * @status FINISHED
 */
public class MinimaxAgent implements Agent {

    private final int maxDepth;
    private Player currentPlayer;
    private PrintWriter log;

    /**
     * Constructs a new minimax agent with the specified cutoff.
     *
     * @param cutoff the cutoff depth
     */
    public MinimaxAgent(final int cutoff) {
        this.maxDepth = cutoff;
    }

    private void resetLog() {
    	try {
			this.log = new PrintWriter("traverse_log.txt");
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
    }

    private void writeLog(final String pit, final int depth, final String value) {
    	log.println(pit + "," + depth + "," + value);
    }

    private void writeLog(final String pit, final int depth, final int value) {
    	writeLog(pit, depth, Integer.toString(value));
    }

    @Override
    public Move getMove(final Board board, final Player player) {

    	resetLog();
    	log.println("Node,Depth,Value");

    	// Save the player so that we don't have to pass it through all the
    	// different methods.
        this.currentPlayer = player;

        final Move move = maxValue(board, player, 0, "root");

        log.flush();
        log.close();

        return move;
    }

    private Move maxValue(final Board board, final Player player, final int depth, final String lastMove) {

    	// If we are terminating because the board is complete or we passed the
    	// max depth, then we will just return the evaluation. There are no
    	// additional moves.
        if(board.isComplete() || depth >= maxDepth && !player.moveForPlayer(lastMove)) {
        	final Move move = new Move();
        	move.eval = Evaluator.basic(board, currentPlayer);
        	writeLog(lastMove, depth, move.eval);
            return move;
        }

        writeLog(lastMove, depth, "-Infinity");

        // We are going to find the maximum move
        Move maxMove = new Move();

        // Loop through all the valid moves
        for(final int pit : board.getValidMoves(player)) {

        	// Build the move
        	final Move move = new Move(pit);

        	// Make the move
        	final Game game = new Game(board.clone(), player);
        	game.move(move);

        	// Increase the depth whenever we transition between players
        	final int newDepth = depth + (player.moveForPlayer(lastMove) ? 0 : 1);

        	// If it is the same player's move again, we want to keep using the
        	// max function. We will string together the moves and use the final
        	// evaluation function. The depth should NOT be increased, because
        	// we aren't changing turns. Otherwise, we want to run minimax as
        	// usual and NOT add the moves, because they are for the opponent
        	if(game.getTurn() == player) {
        		final Move nextMove = maxValue(game.getBoard(), player, newDepth, player.pit(pit));
        		move.actions.addAll(nextMove.actions);
        		move.eval = nextMove.eval;
        	} else {
        		move.eval = minValue(game.getBoard(), player.other(), newDepth, player.pit(pit)).eval;
        	}

        	// Use the best move
        	if(maxMove.eval < move.eval) {
        		maxMove = move;
        	}

        	writeLog(lastMove, depth, maxMove.eval);
        }

        return maxMove;

    }

    private Move minValue(final Board board, final Player player, final int depth, final String lastMove) {


    	// If we are terminating because the board is complete or we passed the
    	// max depth, then we will just return the evaluation. There are no
    	// additional moves.
        if(board.isComplete() || depth >= maxDepth && !player.moveForPlayer(lastMove)) {
        	final Move move = new Move();
        	move.eval = Evaluator.basic(board, currentPlayer);
        	writeLog(lastMove, depth, move.eval);
            return move;
        }

        writeLog(lastMove, depth, "Infinity");

        // We are going to find the minimum node
        Move minMove = new Move();
        minMove.eval = Integer.MAX_VALUE;

        // Loop through all the valid moves
        for(final int pit : board.getValidMoves(player)) {

        	// Build the move
        	final Move move = new Move(pit);

        	// Make the move
        	final Game game = new Game(board.clone(), player);
        	game.move(move);

        	// Increase the depth whenever we transition between players
        	final int newDepth = depth + (player.moveForPlayer(lastMove) ? 0 : 1);

        	// If it is the same player's move again, we want to keep using the
        	// min function. We will string together the moves and use the final
        	// evaluation function. The depth should NOT be increased, because
        	// we aren't changing turns. Otherwise, we want to run minimax as
        	// usual and NOT add the moves, because they are for the opponent
        	if(game.getTurn() == player) {
        		final Move nextMove = minValue(game.getBoard(), player, newDepth, player.pit(pit));
        		move.actions.addAll(nextMove.actions);
        		move.eval = nextMove.eval;
        	} else {
        		move.eval = maxValue(game.getBoard(), player.other(), newDepth, player.pit(pit)).eval;
        	}

        	// Use the worst move
        	if(move.eval < minMove.eval) {
        		minMove = move;
        	}

        	writeLog(lastMove, depth, minMove.eval);
        }

        return minMove;
    }

}
