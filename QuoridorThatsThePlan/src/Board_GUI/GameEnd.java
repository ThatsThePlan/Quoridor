package Board_GUI;
/* 
 * name of file: BoardSpace.java
 * Authors: David Rice
 * Last updated: March 12, 7:08 David Rice
 */ 

import javax.swing.JOptionPane;

import Board.Board;

/** This File will create an end of game dialogue for the Quoridor game.  It will create a new game or end the game based on user input. */
public class GameEnd {

	/** Create a win state by passing the constructor the player count(int), the winning player(int) and the BoardGui */
	public GameEnd(int playerCount, int winningPlayer, BoardGui frame) {
		int r = JOptionPane.showConfirmDialog (null, "Would you like to play again?",
				"Player " + winningPlayer +" You Win!", 0);
		
		frame.dispose();
		
		if (r == 0) {
			System.out.println("Player " + winningPlayer + " will play");
			
			Board board = new Board(2);

			board.setCurrPlayer(0);

			frame = new BoardGui(board.getPlayerCount(), board.getCurrPlayer(), board);
			frame.setSize(700,410);
			frame.setResizable( false );
			frame.setVisible(true);
		} else {
			System.out.println("Player " + winningPlayer + " will not play");
		}
	}
}
