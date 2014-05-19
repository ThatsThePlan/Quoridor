package AI;

import java.util.ArrayList;

import Board.Board;

public class Truth {
	Truth(){

	}

	public static ArrayList<Integer> left(int playerNumber, Board board){
		The_AI ai = new The_AI();
		int i = 0;
		int numberOfPlayers = board.getPlayerCount();
		int currPosition;
		if (numberOfPlayers == 2){
			while (board.playerHasWon(0)!= true && board.playerHasWon(1)!= true){
				currPosition = ai.fastestPathLeft(i, board);
				board.setPos(i, currPosition);	
				i++;
				if(i>1)i=0;
			}
			if (playerNumber == 0)
				return ai.fastestPathLeft0;
			else
				return ai.fastestPathLeft1;
		}
		else if (numberOfPlayers == 4){
			while (board.playerHasWon(0)!= true && board.playerHasWon(1)!= true && board.playerHasWon(2)!= true && board.playerHasWon(3)!= true){
				currPosition = ai.fastestPathLeft(i, board);
				board.setPos(i, currPosition);
				i++;
				if(i>3)i=0;
			}
			if (playerNumber == 0)
				return ai.fastestPathLeft0;
			else if(playerNumber == 1)
				return ai.fastestPathLeft1;
			else if(playerNumber == 2)
				return ai.fastestPathLeft2;
			else if(playerNumber == 3)
				return ai.fastestPathLeft3;
			else
				return null;

		}


		return null;
	}
	public static ArrayList<Integer> right(int playerNumber, Board board){
		The_AI ai = new The_AI();
		int i = 0;
		int numberOfPlayers = board.getPlayerCount();
		int currPosition;
		if (numberOfPlayers == 2){
			while (board.playerHasWon(0)!= true && board.playerHasWon(1)!= true){
				currPosition = ai.fastestPathRight(i, board);
				board.setPos(i, currPosition);	
				i++;
				if(i>1)i=0;
			}
			if (playerNumber == 0)
				return ai.fastestPathRight0;
			else
				return ai.fastestPathRight1;
		}
		else if (numberOfPlayers == 4){
			while (board.playerHasWon(0)!= true && board.playerHasWon(1)!= true && board.playerHasWon(2)!= true && board.playerHasWon(3)!= true){
				currPosition = ai.fastestPathRight(i, board);
				board.setPos(i, currPosition);
				i++;
				if(i>3)i=0;
			}
			if (playerNumber == 0)
				return ai.fastestPathRight0;
			else if(playerNumber == 1)
				return ai.fastestPathRight1;
			else if(playerNumber == 2)
				return ai.fastestPathRight2;
			else if(playerNumber == 3)
				return ai.fastestPathRight3;
			else
				return null;

		}


		return null;
	}
	public static ArrayList<Integer> fastestPath(int playerNumber, Board board){
		Truth ai1 = new Truth();
		Truth ai2 = new Truth();

		Board board1;
		board1 = new Board(board);
		Board board2 = new Board(board);
		ArrayList<Integer> L = ai1.left(playerNumber, board1);
		//System.out.println("left:"+L);
		ArrayList<Integer> R = ai2.right(playerNumber, board2);
		//System.out.println("Right:"+R);
		if(L.size()>=R.size())
			return L;
		else
			return R;	
	}

	public static void main(String args[]){
		Truth ai1 = new Truth();
		Truth ai2 = new Truth();

		Board board = new Board(4);
		Board board1 = new Board(4);
		System.out.print(ai1.fastestPath(1, board));
		//System.out.println(	ai1.left(0, board1));
		
		//System.out.println(	ai2.right(0, board));
		//System.out.println(	ai2.right(0, board1));
		/*System.out.println(fastestPath(0, board, board1));
		System.out.println(fastestPath(1, board, board1));
		System.out.println(fastestPath(2, board, board1));
		System.out.println(fastestPath(3, board, board1));*/
	}
}