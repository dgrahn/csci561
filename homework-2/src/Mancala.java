

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import us.grahn.mancala.Board;
import us.grahn.mancala.Game;
import us.grahn.mancala.Player;
import us.grahn.mancala.agents.Agent;
import us.grahn.mancala.agents.AlphaBetaAgent;
import us.grahn.mancala.agents.GreedyAgent;
import us.grahn.mancala.agents.MinimaxAgent;
import us.grahn.mancala.io.NextState;

/**
 * The mancala homework entry file.
 *
 * @author Dan Grahn
 * @status FINISHED
 */
public class Mancala {

  private List<String> lines = null;
  private final Game game = new Game();

  private static Agent getAgent(final int task, final int cutOff) {

	    switch(task) {
	    	case 1:
	    		System.out.println("Using Greedy Agent");
	    		return new GreedyAgent();
	    	case 2:
	    		System.out.println("Using Minimax Agent");
	    		return new MinimaxAgent(cutOff);
	    	case 3:
	    		System.out.println("Using Alpha-Beta Agent");
	    		return new AlphaBetaAgent(cutOff);
	    	default:
	    		throw new IllegalArgumentException("Invalid Task");
	    }

  }

  public Mancala(final String file) {

    try {
      this.lines = Files.readAllLines(Paths.get(file));
    } catch (final IOException e) {
      e.printStackTrace();
      return;
    }

    final int      task       = Integer.parseInt(lines.get(0));
    final int      playerNum  = Integer.parseInt(lines.get(1));
    final int	   cutOff     = Integer.parseInt(lines.get(2));
    final String[] stonesTop  = lines.get(3).split("\\s");
    final String[] stonesBot  = lines.get(4).split("\\s");
    final int	   topMancala = Integer.parseInt(lines.get(5));
    final int	   botMancala = Integer.parseInt(lines.get(6));

    // Get the player and agent
    final Player player = Player.getPlayer(playerNum);
    final Agent  agent  = getAgent(task, cutOff);

    // Set the move and the agent
    game.setTurn(player);
    game.setAgent(player, agent);

    // Build the board
    final Board board = new Board(stonesTop.length);

    for(int i = 0; i < stonesTop.length; i++) {
    	board.setStones(Player.OPPONENT, i, Integer.parseInt(stonesTop[i]));
    }

    for(int i = 0; i < stonesBot.length; i++) {
    	board.setStones(Player.PLAYER, i, Integer.parseInt(stonesBot[i]));
    }

    board.setMancala(Player.OPPONENT, topMancala);
    board.setMancala(Player.PLAYER, botMancala);

    game.setBoard(board);
    System.out.println(game);

    // Play until it's not your turn
	game.playTurn();
	System.out.println(game);

    NextState.write(game.getBoard());
  }

  /**
   * The entry point for the homework.
   *
   * @param args "-i [INPUT_FILE]"
   */
  @SuppressWarnings("unused")
public static void main(final String[] args) {
	  new Mancala(args[1]);
  }

}
