package us.grahn.mancala;

/**
 * A class which makes moves on a mancala board.
 *
 * @author Dan Grahn
 * @status FINISHED
 */
public class Mover {

    private final Game game;

    /**
     * Constructs a mover for the specified game.
     *
     * @param game the game to create the mover for
     */
    public Mover(final Game game) {
        this.game = game;
    }

    /**
     * Makes a move.
     *
     * @param pit the pit which should be distributed
     */
    public void move(final int pit) {
        final Player player = game.getTurn();
        final Board board = game.getBoard();
        int stones = board.getStones(player, pit);

        if(!board.isValidMove(player, pit)) {
            throw new IllegalArgumentException("Invalid move = " + pit);
        }

        // Clear the stones from the pit.
        board.setStones(player, pit, 0);

        // Distribute the stones
        Player side = player;
        int position = pit;

        while(stones != 0) {

            if(side == Player.PLAYER) {
                position++;

                if(position < board.getSize()) {
                    // We have a valid move, keep going.
                    board.addStone(Player.PLAYER, position);
                    stones--;
                } else {
                    // We are in the mancala. If we are the player, add to it.
                    // Otherwise, skip.
                    if(player == Player.PLAYER) {
                        board.addMancala(Player.PLAYER, 1);
                        stones--;
                    }

                    side = Player.OPPONENT;
                }

            } else if(side == Player.OPPONENT) {
                position--;

                if(0 <= position) {
                    // We have a valid move, keep going.
                    board.addStone(Player.OPPONENT, position);
                    stones--;
                } else {
                    // We are in the mancala. If we are the player, add to it.
                    // Otherwise, skip.
                    if(player == Player.OPPONENT) {
                        board.addMancala(Player.OPPONENT, 1);
                        stones--;
                    }

                    side = Player.PLAYER;
                }
            }
        }

        // If the last position
        if(side == player && board.getStones(player, position) == 1) {
            final int captured = board.getStones(player.other(), position);
            board.setStones(player.other(), position, 0);
            board.setStones(player, position, 0);
            board.addMancala(player, captured + 1);
        }

        // If either side of the board is empty, clear the board.
        for(final Player p : Player.values()) {
            if(board.isSideEmpty(p)) {
                int count = 0;
                for(int i = 0; i < board.getSize(); i++) {
                    count += board.getStones(p.other(), i);
                    board.setStones(p.other(), i, 0);
                }

                board.addMancala(p.other(), count);
            }
        }

        // Set the next player. If the last move was the mancala, don't change.
        if(player == Player.PLAYER) {
            if(position != board.getSize()) game.setTurn(Player.OPPONENT);
        } else {
            if(position != -1) game.setTurn(Player.PLAYER);
        }

    }

    /**
     * Makes a move.
     *
     * @param move the move sequence which should be made
     */
    public void move(final Move move) {

    	final Player player = game.getTurn();

    	for(final int pit : move.actions) {
    		move(pit);
    		if(game.getTurn() != player) break;
    	}

    }

}
