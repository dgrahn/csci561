package us.grahn.mancala;

import us.grahn.mancala.agents.Agent;
import us.grahn.mancala.agents.AlphaBetaAgent;
import us.grahn.mancala.agents.HumanAgent;

/**
 * A "Game" of mancala which has players and a board.
 *
 * @author Dan Grahn
 * @status FINISHED
 */
public class Game {

	/**
	 * A simple method which can be customized to play any number of games with
	 * any number of agents.
	 *
	 * @param args NOT USED
	 */
    public static void main(final String[] args) {

        int player = 0;
        int opponent = 0;
        final int MAX = 1;

        for(int i = 1; i < MAX + 1; i++) {
            //final Game game = new Game(Board.random(), Player.PLAYER);
        	final Game game = new Game();
            game.setAgent(Player.PLAYER, new HumanAgent());
            game.setAgent(Player.OPPONENT, new AlphaBetaAgent(10));
            game.setDebug(true);

            try {
                game.play();
                if(game.getWinner() == Player.PLAYER) player++;
                else opponent++;
            } catch(final Exception e) {
                System.out.println(game.getBoard());
                System.out.println("Player = " + game.getTurn());
                e.printStackTrace();
                return;
            }

            final double playerPerc = player * 100.0 / i;
            final double opponentPerc = opponent * 100.0 / i;

            System.out.println(String.format("%.1f (%d) to %.1f (%d) - %d",
                    playerPerc, player, opponentPerc, opponent,
                    game.getBoard().getSize()));
        }

    }
    private Board board;

    private boolean debug = false;
    private Agent opponent = null;

    private Agent player = null;

    private Player turn = Player.PLAYER;

    /**
     * Constructs a new game with a size of 6 and 4 stones in each pit.
     */
    public Game() {
        this(6, 4);
    }

    /**
     * Constructs a new game.
     *
     * @param board the board for the game
     * @param turn  the players whose turn it is
     */
    public Game(final Board board, final Player turn) {
        this.board = board;
        this.turn = turn;
    }

    /**
     * Constructs a new game with a new board of the specified size with the
     * specified number of stones in each pit.
     *
     * @param size   the size of the board
     * @param stones the stones in each pit
     */
    public Game(final int size, final int stones) {

        this.board = new Board(size);

        for(int i = 0; i < size; i++)  {
            board.setStones(Player.PLAYER, i, stones);
            board.setStones(Player.OPPONENT, i, stones);
        }
    }

    /**
     * Gets the current board.
     *
     * @return the current board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the player whose turn it is.
     *
     * @return the player whose turn it is
     */
    public Player getTurn() {
        return turn;
    }

    /**
     * Gets the winner of the current board.
     *
     * @return the winner of the current board
     */
    public Player getWinner() {
        return board.getWinner();
    }

    /**
     * Makes the specified move.
     *
     * @param pit the pit to distribute
     */
    public void move(final int pit) {
    	move(new Move(pit));
    }

    /**
     * Make the specified move.
     *
     * @param move makes the specified move
     */
    public void move(final Move move) {
        new Mover(this).move(move);
    }

    /**
     * Play the game until it is done.
     */
    public void play() {

        while(!board.isComplete()) {
        	playTurn();
        }
    }

    /**
     * Play a single turn of the game.
     */
    public void playTurn() {

    	Move thisMove;

        if(debug) System.out.println("---------");
        if(debug) System.out.println(board);
        if(turn == Player.PLAYER) {
            if(debug) System.out.println("Player = " + player.getClass().getSimpleName());
            thisMove = player.getMove(board, Player.PLAYER);
        } else {
            if(debug) System.out.println("Opponent = " + opponent.getClass().getSimpleName());
            thisMove = opponent.getMove(board, Player.OPPONENT);
        }

        move(thisMove);
    }

    /**
     * Sets the agent for a specific player.
     *
     * @param player the player
     * @param agent  the agent
     */
    public void setAgent(final Player player, final Agent agent) {
    	if(player == Player.OPPONENT) {
    		this.opponent = agent;
    	} else {
    		this.player = agent;
    	}
    }

    /**
     * Sets the board.
     *
     * @param board the new board
     */
    public void setBoard(final Board board) {
        this.board = board;
    }

    /**
     * Sets whether debug mode should be enabled.
     *
     * @param debug true to enable debug mode
     */
    public void setDebug(final boolean debug) {
        this.debug = debug;
    }

    /**
     * Sets the player whose turn it is.
     *
     * @param turn the player whose turn it is
     */
    public void setTurn(final Player turn) {
        this.turn = turn;
    }

    @Override
	public String toString() {
    	final StringBuilder b = new StringBuilder();
    	b.append("-- Game --\n");
    	b.append(board);
    	b.append("Player = " + player + "\n");
    	b.append("Opponent = " + opponent + "\n");
    	b.append("Move = " + turn);
    	return b.toString();
    }
}
