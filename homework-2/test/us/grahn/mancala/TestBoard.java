package us.grahn.mancala;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestBoard {

    final int MIN_BOARD = 3;
    final int MAX_BOARD = 10;
    final int MIN_STONE = 0;
    final int MAX_STONE = 1000;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSize() {

        // Test all the possible board sizes
        for(int i = MIN_BOARD; i <= MAX_BOARD; i++) {
            Assert.assertEquals(i, new Board(i).getSize());
        }
    }

    @Test
    public void testSetStones() {

        // Test all the possible board sizes
        for(int i = MIN_BOARD; i <= MAX_BOARD; i++) {

            final Board board = new Board(i);

            // Test the player
            for(int j = 0; j < i; j++) {
                board.setStones(Player.PLAYER, j, j + 1);
                Assert.assertEquals(j + 1, board.getStones(Player.PLAYER, j));
            }

            // Test the opponent
            for(int j = 0; j < i; j++) {
                board.setStones(Player.OPPONENT, j, i + j + 1);
                Assert.assertEquals(i + j + 1, board.getStones(Player.OPPONENT, j));
            }
        }
    }

    @Test
    public void testSetMancala() {

        // Test all the possible board sizes
        for(int i = MIN_BOARD; i <= MAX_BOARD; i++) {

            final Board board = new Board(i);

            board.setMancala(Player.PLAYER, 11);
            board.setMancala(Player.OPPONENT, 33);

            Assert.assertEquals(11, board.getMancala(Player.PLAYER));
            Assert.assertEquals(33, board.getMancala(Player.OPPONENT));
        }
    }

    @Test
    public void testSetAllPositions() {

        final Board board = new Board(3);
        board.setMancala(Player.PLAYER, 7);
        board.setMancala(Player.OPPONENT, 8);
        board.setStones(Player.PLAYER, 0, 1);
        board.setStones(Player.PLAYER, 1, 2);
        board.setStones(Player.PLAYER, 2, 3);
        board.setStones(Player.OPPONENT, 0, 4);
        board.setStones(Player.OPPONENT, 1, 5);
        board.setStones(Player.OPPONENT, 2, 6);

        Assert.assertEquals(1, board.getStones(Player.PLAYER, 0));
        Assert.assertEquals(2, board.getStones(Player.PLAYER, 1));
        Assert.assertEquals(3, board.getStones(Player.PLAYER, 2));
        Assert.assertEquals(4, board.getStones(Player.OPPONENT, 0));
        Assert.assertEquals(5, board.getStones(Player.OPPONENT, 1));
        Assert.assertEquals(6, board.getStones(Player.OPPONENT, 2));
        Assert.assertEquals(7, board.getMancala(Player.PLAYER));
        Assert.assertEquals(8, board.getMancala(Player.OPPONENT));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidSet() {
        new Board(3).setStones(Player.PLAYER, 4, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidGet() {
        new Board(3).getStones(Player.PLAYER, 4);
    }

    @Test
    public void testAdd() {

        final Board board = new Board(3);

        for(int i = 1; i < 1000; i++) {
            board.addStone(Player.PLAYER, 1);
            Assert.assertEquals(i, board.getStones(Player.PLAYER, 1));
        }
    }

    @Test
    public void testAddMancala() {

        final Board board = new Board(3);

        board.addMancala(Player.PLAYER, 5);
        Assert.assertEquals(5, board.getMancala(Player.PLAYER));

        board.addMancala(Player.PLAYER, 5);
        Assert.assertEquals(10, board.getMancala(Player.PLAYER));
    }

}
