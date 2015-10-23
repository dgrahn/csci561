package us.grahn.mancala.agents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import us.grahn.mancala.Board;
import us.grahn.mancala.Move;
import us.grahn.mancala.Player;

/**
 * A human agent.
 *
 * @author Dan Grahn
 * @status FINISHED
 */
public class HumanAgent implements Agent {

    @Override
    public Move getMove(final Board board, final Player player) {

        System.out.print("Your Move = ");

        try {
            final BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in));
            return new Move(Integer.parseInt(br.readLine()));
        } catch(final IOException e) {
            e.printStackTrace();
            return new Move(0);
        }
    }

}
