package us.grahn.mancala;

/**
 * An enumeration of the different players.
 *
 * @author Dan Grahn
 */
public enum Player {

	/**
	 * The player.
	 */
	PLAYER,

	/**
	 * The opponent.
	 */
	OPPONENT;

	public Player other() {
		if(this == PLAYER) return OPPONENT;
		else return PLAYER;
	}

}
