package us.grahn.mancala.agents;

import java.util.Random;

import us.grahn.mancala.Board;
import us.grahn.mancala.Move;
import us.grahn.mancala.Player;

/**
 * An agent which makes random moves.
 *
 * @author Dan Grahn
 * @status 3.14.3
 */
public class RandomAgent implements Agent {

    private final Random random = new Random(System.nanoTime());

    @Override
    public Move getMove(final Board board, final Player player) {

        while(true) {
            final int pit = random.nextInt(board.getSize());
            if(board.isValidMove(player, pit)) return new Move(pit);
        }
    }

}
