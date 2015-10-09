package us.grahn.mancala;

import us.grahn.mancala.agents.Agent;
import us.grahn.mancala.agents.AlphaBetaAgent;
import us.grahn.mancala.agents.GreedyAgent;

public class Game {

	private Player move = Player.PLAYER;
	private Board board;

	private Agent player = null;
	private Agent opponent = null;

	public Game(final Board board, final Player move) {
		this.board = board;
		this.move = move;
	}

	public Game(final int size, final int stones) {

		this.board = new Board(size);

		for(int i = 0; i < size; i++)  {
			board.setStones(Player.PLAYER, i, stones);
			board.setStones(Player.OPPONENT, i, stones);
		}
	}

	public Game() {
		this(6, 4);
	}

	public Player getMove() {
		return move;
	}

	public void move(final int pit) {
		new Mover(this).move(pit);
	}

	public void setPlayer(final Agent player) {
		this.player = player;
	}

	public void setOpponent(final Agent opponent) {
		this.opponent = opponent;
	}

	public void setBoard(final Board board) {
		this.board = board;
	}

	public void setMove(final Player move) {
		this.move = move;
	}

	public void play() {

		while(!board.isComplete()) {

			int pit;

			if(debug) System.out.println("---------");
			if(debug) System.out.println(board);
			if(move == Player.PLAYER) {
				if(debug) System.out.println("Player = " + player.getClass().getSimpleName());
				pit = player.getMove(board, Player.PLAYER);
			} else {
				if(debug) System.out.println("Opponent = " + opponent.getClass().getSimpleName());
				pit = opponent.getMove(board, Player.OPPONENT);
			}

			if(debug) System.out.println("Move = " + pit);
			move(pit);
		}
	}

	public Player getWinner() {
		return board.getWinner();
	}

	public Player getPlayer() {
		return move;
	}

	public Board getBoard() {
		return board;
	}

	private boolean debug = false;

	public void setDebug(final boolean debug) {
		this.debug = debug;
	}

	public static void main(final String[] args) {

		int player = 0;
		int opponent = 0;
		final int MAX = 10000000;

		for(int i = 1; i < MAX + 1; i++) {
			final Game game = new Game(Board.random(), Player.PLAYER);
			//final Game game = new Game();
			game.setPlayer(new AlphaBetaAgent(5));
			game.setOpponent(new GreedyAgent());
			//game.setDebug(true);

			try {
				game.play();
				if(game.getWinner() == Player.PLAYER) player++;
				else opponent++;
			} catch(final Exception e) {
				System.out.println(game.getBoard());
				System.out.println("Player = " + game.getMove());
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
}
