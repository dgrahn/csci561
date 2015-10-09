package us.grahn.mancala;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestMover {

	private Game game;
	private Board board;
	private Mover mover;

	@Before
	public void setUp() throws Exception {
		this.game = new Game(3, 2);
		this.board = game.getBoard();
		this.mover = new Mover(game);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidMove() {
		game.getBoard().clear();
		mover.move(0);
	}

	@Test
	public void testValidPlayerMove() {

		mover.move(2);
		Assert.assertEquals(2, game.getBoard().getStones(Player.PLAYER, 0));
		Assert.assertEquals(2, game.getBoard().getStones(Player.PLAYER, 1));
		Assert.assertEquals(0, game.getBoard().getStones(Player.PLAYER, 2));
		Assert.assertEquals(2, game.getBoard().getStones(Player.OPPONENT, 0));
		Assert.assertEquals(2, game.getBoard().getStones(Player.OPPONENT, 1));
		Assert.assertEquals(3, game.getBoard().getStones(Player.OPPONENT, 2));
		Assert.assertEquals(1, game.getBoard().getMancala(Player.PLAYER));
		Assert.assertEquals(0, game.getBoard().getMancala(Player.OPPONENT));
		Assert.assertEquals(Player.OPPONENT, game.getMove());
	}

	@Test
	public void testValidOpponentMove() {

		game.setMove(Player.OPPONENT);
		mover.move(0);
		Assert.assertEquals(3, board.getStones(Player.PLAYER, 0));
		Assert.assertEquals(2, board.getStones(Player.PLAYER, 1));
		Assert.assertEquals(2, board.getStones(Player.PLAYER, 2));
		Assert.assertEquals(0, board.getStones(Player.OPPONENT, 0));
		Assert.assertEquals(2, board.getStones(Player.OPPONENT, 1));
		Assert.assertEquals(2, board.getStones(Player.OPPONENT, 2));
		Assert.assertEquals(0, board.getMancala(Player.PLAYER));
		Assert.assertEquals(1, board.getMancala(Player.OPPONENT));
		Assert.assertEquals(Player.PLAYER, game.getMove());
	}

	@Test
	public void testSecondMovePlayer() {

		mover.move(1);
		Assert.assertEquals(2, board.getStones(Player.PLAYER, 0));
		Assert.assertEquals(0, board.getStones(Player.PLAYER, 1));
		Assert.assertEquals(3, board.getStones(Player.PLAYER, 2));
		Assert.assertEquals(2, board.getStones(Player.OPPONENT, 0));
		Assert.assertEquals(2, board.getStones(Player.OPPONENT, 1));
		Assert.assertEquals(2, board.getStones(Player.OPPONENT, 2));
		Assert.assertEquals(1, board.getMancala(Player.PLAYER));
		Assert.assertEquals(0, board.getMancala(Player.OPPONENT));
		Assert.assertEquals(Player.PLAYER, game.getMove());
	}

	@Test
	public void testSecondMoveOpponent() {

		game.setMove(Player.OPPONENT);
		mover.move(1);
		Assert.assertEquals(2, board.getStones(Player.PLAYER, 0));
		Assert.assertEquals(2, board.getStones(Player.PLAYER, 1));
		Assert.assertEquals(2, board.getStones(Player.PLAYER, 2));
		Assert.assertEquals(3, board.getStones(Player.OPPONENT, 0));
		Assert.assertEquals(0, board.getStones(Player.OPPONENT, 1));
		Assert.assertEquals(2, board.getStones(Player.OPPONENT, 2));
		Assert.assertEquals(0, board.getMancala(Player.PLAYER));
		Assert.assertEquals(1, board.getMancala(Player.OPPONENT));
		Assert.assertEquals(Player.OPPONENT, game.getMove());
	}

	@Test
	public void testSkipOpponentMancala() {

		board.setStones(Player.PLAYER, 2, 5);
		mover.move(2);
		Assert.assertEquals(3, board.getStones(Player.PLAYER, 0));
		Assert.assertEquals(2, board.getStones(Player.PLAYER, 1));
		Assert.assertEquals(0, board.getStones(Player.PLAYER, 2));
		Assert.assertEquals(3, board.getStones(Player.OPPONENT, 0));
		Assert.assertEquals(3, board.getStones(Player.OPPONENT, 1));
		Assert.assertEquals(3, board.getStones(Player.OPPONENT, 2));
		Assert.assertEquals(1, board.getMancala(Player.PLAYER));
		Assert.assertEquals(0, board.getMancala(Player.OPPONENT));
		Assert.assertEquals(Player.OPPONENT, game.getMove());
	}


	@Test
	public void testSkipPlayerMancala() {

		board.setStones(Player.OPPONENT, 0, 5);
		game.setMove(Player.OPPONENT);
		mover.move(0);
		Assert.assertEquals(3, board.getStones(Player.PLAYER, 0));
		Assert.assertEquals(3, board.getStones(Player.PLAYER, 1));
		Assert.assertEquals(3, board.getStones(Player.PLAYER, 2));
		Assert.assertEquals(0, board.getStones(Player.OPPONENT, 0));
		Assert.assertEquals(2, board.getStones(Player.OPPONENT, 1));
		Assert.assertEquals(3, board.getStones(Player.OPPONENT, 2));
		Assert.assertEquals(0, board.getMancala(Player.PLAYER));
		Assert.assertEquals(1, board.getMancala(Player.OPPONENT));
		Assert.assertEquals(Player.PLAYER, game.getMove());
	}

	@Test
	public void testPlayerSweep() {

		board.setStones(Player.PLAYER, 2, 0);
		mover.move(0);
		Assert.assertEquals(0, board.getStones(Player.PLAYER, 0));
		Assert.assertEquals(3, board.getStones(Player.PLAYER, 1));
		Assert.assertEquals(0, board.getStones(Player.PLAYER, 2));
		Assert.assertEquals(2, board.getStones(Player.OPPONENT, 0));
		Assert.assertEquals(2, board.getStones(Player.OPPONENT, 1));
		Assert.assertEquals(0, board.getStones(Player.OPPONENT, 2));
		Assert.assertEquals(3, board.getMancala(Player.PLAYER));
		Assert.assertEquals(0, board.getMancala(Player.OPPONENT));
		Assert.assertEquals(Player.OPPONENT, game.getMove());
	}

	@Test
	public void testOpponentSweep() {

		board.setStones(Player.OPPONENT, 0, 0);
		game.setMove(Player.OPPONENT);
		mover.move(2);
		Assert.assertEquals(0, board.getStones(Player.PLAYER, 0));
		Assert.assertEquals(2, board.getStones(Player.PLAYER, 1));
		Assert.assertEquals(2, board.getStones(Player.PLAYER, 2));
		Assert.assertEquals(0, board.getStones(Player.OPPONENT, 0));
		Assert.assertEquals(3, board.getStones(Player.OPPONENT, 1));
		Assert.assertEquals(0, board.getStones(Player.OPPONENT, 2));
		Assert.assertEquals(0, board.getMancala(Player.PLAYER));
		Assert.assertEquals(3, board.getMancala(Player.OPPONENT));
		Assert.assertEquals(Player.PLAYER, game.getMove());
	}

	@Test
	public void testPlayerEnd() {

		board.setStones(Player.PLAYER, 0, 0);
		board.setStones(Player.PLAYER, 1, 0);
		board.setStones(Player.PLAYER, 2, 1);
		mover.move(2);

		Assert.assertEquals(0, board.getStones(Player.PLAYER, 0));
		Assert.assertEquals(0, board.getStones(Player.PLAYER, 1));
		Assert.assertEquals(0, board.getStones(Player.PLAYER, 2));
		Assert.assertEquals(0, board.getStones(Player.OPPONENT, 0));
		Assert.assertEquals(0, board.getStones(Player.OPPONENT, 1));
		Assert.assertEquals(0, board.getStones(Player.OPPONENT, 2));
		Assert.assertEquals(1, board.getMancala(Player.PLAYER));
		Assert.assertEquals(6, board.getMancala(Player.OPPONENT));
		Assert.assertTrue(board.isComplete());
	}

	@Test
	public void testOponentEnd() {

		board.setStones(Player.OPPONENT, 0, 1);
		board.setStones(Player.OPPONENT, 1, 0);
		board.setStones(Player.OPPONENT, 2, 0);
		game.setMove(Player.OPPONENT);
		mover.move(0);

		Assert.assertEquals(0, board.getStones(Player.PLAYER, 0));
		Assert.assertEquals(0, board.getStones(Player.PLAYER, 1));
		Assert.assertEquals(0, board.getStones(Player.PLAYER, 2));
		Assert.assertEquals(0, board.getStones(Player.OPPONENT, 0));
		Assert.assertEquals(0, board.getStones(Player.OPPONENT, 1));
		Assert.assertEquals(0, board.getStones(Player.OPPONENT, 2));
		Assert.assertEquals(6, board.getMancala(Player.PLAYER));
		Assert.assertEquals(1, board.getMancala(Player.OPPONENT));
		Assert.assertTrue(board.isComplete());
	}

}