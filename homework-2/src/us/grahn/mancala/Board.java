package us.grahn.mancala;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A Mancala board.
 *
 * @author Dan Grahn
 * @status FINISHED
 */
public class Board implements Cloneable {

    private final static Random random = new Random(System.nanoTime());

    private static String pad(final int number) {
        return String.format("%4d", number);
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    private static int randInt(final int min, final int max) {


        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Generates a random board.
     *
     * @return the random board. Never {@code null}.
     */
    public static Board random() {

        final Board board = new Board(randInt(3, 10));

        for(int i = 0; i < board.getSize(); i++) {
            board.setStones(Player.PLAYER, i, randInt(0, 1000));
            board.setStones(Player.OPPONENT, i, randInt(0, 1000));
        }

        return board;
    }

    private final int[] board;

    private final int size;

    /**
     * Constructs a new Mancala {@code Board}.
     *
     * @param size the size of the board in terms of the number of pits for a
     *             single player, not including mancalas
     */
    public Board(final int size) {

        this.size  = size;
        this.board = new int[size * 2 + 2];
    }

    /**
     * Constructs a new Mancala {@code Board} as a clone of a previous board.
     *
     * @param board
     */
    private Board(final int[] board) {

        this.size  = (board.length - 2) / 2;
        this.board = board;
    }

    /**
     * Add a number of stones to the mancala.
     *
     * @param player the player to whose mancala the stones will be added
     * @param stones the stones which should be added
     */
    public void addMancala(final Player player, final int stones) {
        board[getMancalaLocation(player)] += stones;
    }

    /**
     * Adds a stone to the specified pit for the player.
     *
     * @param player the player whose pit will be added to
     * @param pit	 the pit to add the stone to
     */
    public void addStone(final Player player, final int pit) {
        board[getOffset(player) + pit] += 1;
    }

    /**
     * Clears the board.
     */
    public void clear() {
        for(int i = 0; i < board.length; i++) board[i] = 0;
    }

    @Override
    public Board clone() {

        return new Board(board.clone());
    }

    /**
     * Checks if two boards are equals.
     * @param  b the board to compare
     * @return   true if the boards are equal
     */
	public boolean equals(final Board b) {

		return Arrays.equals(board, b.board);
    }


    /**
     * Gets the number of stones in the mancala for the player.
     *
     * @param  player the player to get
     * @return        the number of stones in the player's mancala
     */
    public int getMancala(final Player player) {
        return board[getMancalaLocation(player)];
    }

    private int getMancalaLocation(final Player player) {
        return board.length - (Player.PLAYER == player ? 2 : 1);
    }

    private int getOffset(final Player player) {
        return Player.PLAYER == player ? 0 : getSize();
    }

    /**
     * Gets the size of the board in terms of the number of pits for a single
     * player not including mancalas.
     *
     * @return the size of the board
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the number of stones for the entire player.
     *
     * @param  player the player
     * @return        the number of stones for the player
     */
    public int getStones(final Player player) {

        int count = 0;

        for(int i = 0; i < getSize(); i++) {
            count += getStones(player, i);
        }

        return count;
    }

    /**
     * Gets the number of stones in the pit for the player.
     *
     * @param  player the player for which to get the stones
     * @param  pit    the pit in which to get the stones
     * @return        the number of stones in the pit
     */
    public int getStones(final Player player, final int pit) {
        if(pit < 0 || getSize() <= pit) {
            throw new IllegalArgumentException("Invalid pit = " + pit);
        }
        return board[getOffset(player) + pit];
    }

    /**
     * Gets the valid moves for a player.
     *
     * @param  player the player for whom to get the valid moves
     * @return        the valid moves
     */
    public List<Integer> getValidMoves(final Player player) {

    	final List<Integer> moves = new ArrayList<>();

    	for(int i = 0; i < getSize(); i++) {
    		if(isValidMove(player, i)) moves.add(i);
    	}

    	return moves;
    }

    /**
     * Gets the current winner for the game. Only compares mancalas.
     *
     * @return the winner of the game
     */
    public Player getWinner() {
        if(getMancala(Player.PLAYER) == getMancala(Player.OPPONENT)) {
            return null;
        } else if(getMancala(Player.PLAYER) > getMancala(Player.OPPONENT)) {
            return Player.PLAYER;
        } else {
            return Player.OPPONENT;
        }
    }

    /**
     * Checks if the game is complete.
     *
     * @return {@code true} if the game is complete
     */
    public boolean isComplete() {
    	return getStones(Player.PLAYER) == 0 || getStones(Player.OPPONENT) == 0;
    }

    /**
     * Checks if the specified player's side is empty.
     *
     * @param  player the player whose side should be checked
     * @return        true if the players side is empty
     */
    public boolean isSideEmpty(final Player player) {

        for(int i = 0; i < getSize(); i++) {
            if(getStones(player, i) != 0) return false;
        }

        return true;
    }

    /**
     * Checks if the specified pit is a valid move for the player.
     *
     * @param  player the player whose move it is
     * @param  pit    the pit to move
     * @return        true if it is a valid move
     */
    public boolean isValidMove(final Player player, final int pit) {
        return 0 <= pit && pit < getSize() && getStones(player, pit) != 0;
    }

    /**
     * Sets the number of stones in the mancala for the player.
     *
     * @param player the player to set
     * @param stones the number of stones to put in the player's mancala
     */
    public void setMancala(final Player player, final int stones) {
        board[getMancalaLocation(player)] = stones;
    }

    /**
     * Sets the number of stones in the pit for the player.
     *
     * @param player the player to set. Must not be {@code null}.
     * @param pit    the pit to in which to set the stones. 0..size - 1
     * @param stones the number of stones 0..1000
     */
    public void setStones(final Player player, final int pit, final int stones) {
        if(pit < 0 || getSize() <= pit) {
            throw new IllegalArgumentException("Invalid pit = " + pit);
        }
        board[getOffset(player) + pit] = stones;
    }

    @Override
    public String toString() {

        final StringBuilder b = new StringBuilder();

        b.append("    |");
        for(int i = 0; i < getSize(); i++) {
            b.append(pad(i));
            b.append("|");
        }
        b.append("\n");

        b.append(pad(getMancala(Player.OPPONENT)));
        b.append("|");

        for(int pit = 0; pit < getSize(); pit++) {
            final int stones = getStones(Player.OPPONENT, pit);
            b.append(pad(stones));
            b.append("|");
        }

        b.append(pad(getMancala(Player.PLAYER)));
        b.append("\n");
        b.append("    |");

        for(int pit = 0; pit < getSize(); pit++) {
            final int stones = getStones(Player.PLAYER, pit);
            b.append(pad(stones));
            b.append("|");
        }

        b.append("\n");

        return b.toString();
    }

}
