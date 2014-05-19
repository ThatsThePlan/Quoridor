package Board_GUI;

import java.awt.Color;
import java.util.ArrayList;
import Board.Board;

public class GuiControl {

	/** Remove paint from the position of a player, by passing it a player #(int) and passing the current space (int) */
	public void removeOldMoves(int player, int currSpace, Board board, ArrayList<BoardSpace> space) {
		// This sets the initial possible moves for player 1
		int[] pos = board.getPossible(player);
		for (int i = 0 ; i < pos.length ; i++) {
			space.get(pos[i]).setClicked(false);
			space.get(pos[i]).repaint();
		}
		space.get(currSpace).setClicked(false);
		space.get(currSpace).repaint();
	}
	
	/** Paint the position of a player, by passing it a player #(int) */
	public void paintPos(int player, Board board, ArrayList<BoardSpace> space) {
		// This sets the initial possible moves for player 1
		int[] pos = board.getPossible(player);

		for (int i = 0 ; i < pos.length ; i++) {
			if (board.victorySoon(board.getCurrPlayer(), pos[i])) {
				space.get(pos[i]).setColor(Color.MAGENTA);
				space.get(pos[i]).setPotential(true);
				space.get(pos[i]).repaint();
			} else { 
				space.get(pos[i]).setColor(board.getPlayer(board.getCurrPlayer()).getColor());
				space.get(pos[i]).setPotential(true);
				space.get(pos[i]).repaint();
			}
		}
	}
	
	/** Remove paint from the position of a player, by passing it a player #(int) */
	public void removePos(int player, Board board, ArrayList<BoardSpace> space) {
		// This sets the initial possible moves for player 1
		int[] pos = board.getPossible(player);
		for (int i = 0 ; i < pos.length ; i++) {
			space.get(pos[i]).setPotential(false);
			space.get(pos[i]).repaint();
		}
	}
	
	/** Remove paint from the position of a player, by passing it a player #(int) */
	public void removePlayer(int player, Board board, ArrayList<BoardSpace> space) {
		// This sets the initial possible moves for player 1
		int[] pos = board.getPossible(player);
		space.get(board.getPos(player)).setClicked(false);
		space.get(board.getPos(player)).repaint();
		for (int i = 0 ; i < pos.length ; i++) {
			space.get(pos[i]).setPotential(false);
			space.get(pos[i]).repaint();
		}
	}
	
	/** Switch current player */
	public void iteratePlayers(Board board, int players) {
		if (players == 4) {
			board.setCurrPlayer((board.getCurrPlayer()+1)%4);
		} else {
			board.setCurrPlayer((board.getCurrPlayer()+1)%2);
		}
	}
	
}
