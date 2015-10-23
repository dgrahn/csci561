package us.grahn.mancala;

/**
 * An enumeration of the different players.
 *
 * @author Dan Grahn
 * @status FINISHED
 */
public enum Player {

    /**
     * The player.
     */
    PLAYER("B"),

    /**
     * The opponent.
     */
    OPPONENT("A");

	private final String row;

	Player(final String row) {
		this.row = row;
	}

	/**
	 * Gets the name of a pit for the player
	 *
	 * @param  pit the pit
	 * @return	   the name of the pit
	 */
	public String pit(final int pit) {
		return row + (pit + 2);
	}

	/**
	 * Checks if the name of the pit is for the player.
	 *
	 * @param  pit the pit which should be checked
	 * @return     true if the pit is for the player
	 */
	public boolean moveForPlayer(final String pit) {
		return pit.contains(row);
	}

	/**
	 * Gets the opposite player.
	 *
	 * @return the opposite player
	 */
    public Player other() {
    	return (this == PLAYER) ? OPPONENT : PLAYER;
    }

    /**
     * Gets the player where 1 is PLAYER and 2 is OPPONENT.
     *
     * @param  player the number of the player to retrieve
     * @return		  the player
     */
    public static Player getPlayer(final int player) {
    	return (player == 1) ? PLAYER : OPPONENT;
    }

}
