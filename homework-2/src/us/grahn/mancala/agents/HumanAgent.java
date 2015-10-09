package us.grahn.mancala.agents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import us.grahn.mancala.Board;
import us.grahn.mancala.Player;

public class HumanAgent implements Agent {

	@Override
	public int getMove(final Board board, final Player player) {

		System.out.print("Your Move = ");

		try {
			final BufferedReader br = new BufferedReader(
					new InputStreamReader(System.in));
			return Integer.parseInt(br.readLine());
		} catch(final IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

}
