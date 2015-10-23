package us.grahn.mancala.io;

import java.io.PrintWriter;
import java.nio.file.Paths;

import us.grahn.mancala.Board;
import us.grahn.mancala.Player;

/**
 * Utility class which writes boards to the next state file.
 *
 * @author Dan Grahn
 * @status FINISHED
 */
public class NextState {

	private static final String FILE_NAME = "next_state.txt";

	/**
	 * Write the board to the next state file.
	 *
	 * @param board the board to write
	 */
	@SuppressWarnings("resource")
	public static void write(final Board board) {

		// Delete the old file
		deleteFile();

		// Get the writer or short-circuit
		final PrintWriter writer = getWriter();
		if(writer == null) return;

		// Print the top
		for(int pit = 0; pit < board.getSize(); pit++) {
			if(pit != 0) writer.print(" ");
			final int stones = board.getStones(Player.OPPONENT, pit);
			writer.print(stones);
		}
		writer.println();

		// Print the bottom
		for(int pit = 0; pit < board.getSize(); pit++) {
			if(pit != 0) writer.print(" ");
			final int stones = board.getStones(Player.PLAYER, pit);
			writer.print(stones);
		}
		writer.println();

		// Print the mancalas
		writer.println(board.getMancala(Player.OPPONENT));
		writer.println(board.getMancala(Player.PLAYER));

		writer.flush();
		writer.close();
	}

	private static void deleteFile() {
		try {
			Paths.get(FILE_NAME).toFile().delete();
		} catch(final Exception e) {
			e.printStackTrace();
		}
	}

	private static PrintWriter getWriter() {
		try {
			return new PrintWriter(FILE_NAME);
		} catch(final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
