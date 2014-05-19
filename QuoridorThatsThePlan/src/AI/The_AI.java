package AI;

import java.util.ArrayList;

import Board.Board;

public class The_AI {
	public ArrayList<Integer> fastestPathLeft0 = new ArrayList<Integer>();
	public ArrayList<Integer> fastestPathLeft1 = new ArrayList<Integer>();
	public ArrayList<Integer> fastestPathLeft2 = new ArrayList<Integer>();
	public ArrayList<Integer> fastestPathLeft3 = new ArrayList<Integer>();

	public ArrayList<Integer> fastestPathRight0 = new ArrayList<Integer>();
	public ArrayList<Integer> fastestPathRight1 = new ArrayList<Integer>();
	public ArrayList<Integer> fastestPathRight2 = new ArrayList<Integer>();
	public ArrayList<Integer> fastestPathRight3 = new ArrayList<Integer>();

	The_AI(){}
	public int fastestPathLeft(int playerNumber, Board board){
		if( playerNumber == 0){
			if(board.getPossibleHash(playerNumber).get("S").size() != 0){
				fastestPathLeft0.add(board.getPossibleHash(playerNumber).get("S").get(0));
				return board.getPossibleHash(playerNumber).get("S").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("E").size() != 0){
				fastestPathLeft0.add(board.getPossibleHash(playerNumber).get("E").get(0));
				return board.getPossibleHash(playerNumber).get("E").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("W").size() != 0){
				fastestPathLeft0.add(board.getPossibleHash(playerNumber).get("W").get(0));
				return board.getPossibleHash(playerNumber).get("W").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("N").size() != 0){
				fastestPathLeft0.add(board.getPossibleHash(playerNumber).get("N").get(0));
				return board.getPossibleHash(playerNumber).get("N").get(0);
			}
		}

		else if( playerNumber == 1){
			if(board.getPossibleHash(playerNumber).get("N").size() != 0){
				fastestPathLeft1.add(board.getPossibleHash(playerNumber).get("N").get(0));
				return board.getPossibleHash(playerNumber).get("N").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("W").size() != 0){
				fastestPathLeft1.add(board.getPossibleHash(playerNumber).get("W").get(0));
				return board.getPossibleHash(playerNumber).get("W").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("E").size() != 0){
				fastestPathLeft1.add(board.getPossibleHash(playerNumber).get("E").get(0));
				return board.getPossibleHash(playerNumber).get("E").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("S").size() != 0){
				fastestPathLeft1.add(board.getPossibleHash(playerNumber).get("S").get(0));
				return board.getPossibleHash(playerNumber).get("S").get(0);
			}
		}

		else if( playerNumber == 2){
			if(board.getPossibleHash(playerNumber).get("E").size() != 0){
				fastestPathLeft2.add(board.getPossibleHash(playerNumber).get("E").get(0));
				return board.getPossibleHash(playerNumber).get("E").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("S").size() != 0){
				fastestPathLeft2.add(board.getPossibleHash(playerNumber).get("S").get(0));
				return board.getPossibleHash(playerNumber).get("S").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("N").size() != 0){
				fastestPathLeft2.add(board.getPossibleHash(playerNumber).get("N").get(0));
				return board.getPossibleHash(playerNumber).get("N").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("W").size() != 0){
				fastestPathLeft2.add(board.getPossibleHash(playerNumber).get("W").get(0));
				return board.getPossibleHash(playerNumber).get("W").get(0);
			}
		}

		else if( playerNumber == 3){
			if(board.getPossibleHash(playerNumber).get("W").size() != 0){
				fastestPathLeft3.add(board.getPossibleHash(playerNumber).get("W").get(0));
				return board.getPossibleHash(playerNumber).get("W").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("S").size() != 0){
				fastestPathLeft3.add(board.getPossibleHash(playerNumber).get("S").get(0));
				return board.getPossibleHash(playerNumber).get("S").get(0);
			}	

			else if(board.getPossibleHash(playerNumber).get("N").size() != 0){
				fastestPathLeft3.add(board.getPossibleHash(playerNumber).get("N").get(0));
				return board.getPossibleHash(playerNumber).get("N").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("E").size() != 0){
				fastestPathLeft3.add(board.getPossibleHash(playerNumber).get("E").get(0));
				return board.getPossibleHash(playerNumber).get("E").get(0);
			}
		}

		return (Integer) null;
	}

	public int fastestPathRight(int playerNumber, Board board){
		ArrayList<String> fastestPath = new ArrayList<String>();
		int numberOfMoves = board.getPossible(playerNumber).length;
		if( playerNumber == 0){
			if(board.getPossibleHash(playerNumber).get("S").size() != 0){
				fastestPathRight0.add(board.getPossibleHash(playerNumber).get("S").get(0));
				return board.getPossibleHash(playerNumber).get("S").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("W").size() != 0){
				fastestPathRight0.add(board.getPossibleHash(playerNumber).get("W").get(0));
				return board.getPossibleHash(playerNumber).get("W").get(0);
			}	

			else if(board.getPossibleHash(playerNumber).get("E").size() != 0){
				fastestPathRight0.add(board.getPossibleHash(playerNumber).get("E").get(0));
				return board.getPossibleHash(playerNumber).get("E").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("N").size() != 0){
				fastestPathRight0.add(board.getPossibleHash(playerNumber).get("N").get(0));
				return board.getPossibleHash(playerNumber).get("N").get(0);
			}
		}

		else if( playerNumber == 1){
			if(board.getPossibleHash(playerNumber).get("N").size() != 0){
				fastestPathRight1.add(board.getPossibleHash(playerNumber).get("N").get(0));
				return board.getPossibleHash(playerNumber).get("N").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("E").size() != 0){
				fastestPathRight1.add(board.getPossibleHash(playerNumber).get("E").get(0));
				return board.getPossibleHash(playerNumber).get("E").get(0);
			}	

			else if(board.getPossibleHash(playerNumber).get("W").size() != 0){
				fastestPathRight1.add(board.getPossibleHash(playerNumber).get("W").get(0));
				return board.getPossibleHash(playerNumber).get("W").get(0);
			}



			else if(board.getPossibleHash(playerNumber).get("S").size() != 0){
				fastestPathRight1.add(board.getPossibleHash(playerNumber).get("S").get(0));
				return board.getPossibleHash(playerNumber).get("S").get(0);
			}
		}

		else if( playerNumber == 2){
			if(board.getPossibleHash(playerNumber).get("E").size() != 0){
				fastestPathRight2.add(board.getPossibleHash(playerNumber).get("E").get(0));
				return board.getPossibleHash(playerNumber).get("E").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("N").size() != 0){
				fastestPathRight2.add(board.getPossibleHash(playerNumber).get("N").get(0));
				return board.getPossibleHash(playerNumber).get("N").get(0);
			}	

			else if(board.getPossibleHash(playerNumber).get("S").size() != 0){
				fastestPathRight2.add(board.getPossibleHash(playerNumber).get("S").get(0));
				return board.getPossibleHash(playerNumber).get("S").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("W").size() != 0){
				fastestPathRight2.add(board.getPossibleHash(playerNumber).get("W").get(0));
				return board.getPossibleHash(playerNumber).get("W").get(0);
			}
		}

		else if( playerNumber == 3){
			if(board.getPossibleHash(playerNumber).get("W").size() != 0){
				fastestPathRight3.add(board.getPossibleHash(playerNumber).get("W").get(0));
				return board.getPossibleHash(playerNumber).get("W").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("N").size() != 0){
				fastestPathRight3.add(board.getPossibleHash(playerNumber).get("N").get(0));
				return board.getPossibleHash(playerNumber).get("N").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("S").size() != 0){
				fastestPathRight3.add(board.getPossibleHash(playerNumber).get("S").get(0));
				return board.getPossibleHash(playerNumber).get("S").get(0);
			}

			else if(board.getPossibleHash(playerNumber).get("E").size() != 0){
				fastestPathRight3.add(board.getPossibleHash(playerNumber).get("E").get(0));
				return board.getPossibleHash(playerNumber).get("E").get(0);
			}
		}
		return (Integer) null;
	}

	public ArrayList<Integer> trueFastestPath(int playerNumber, Board board){
		int numberOfPlayers = board.getPlayerCount();
		int currPosition, i = 0, x = 0;
		int[] numbers2 = new int[]{0,1};
		int[] numbers4 = new int[]{0,1,2,3};

		if (numberOfPlayers == 2){
			Board board2 = new Board(2);
			board2 = new Board(board);
			while (board2.playerHasWon(numbers2[0])!= true && board2.playerHasWon(numbers2[1])!= true){
				currPosition = fastestPathLeft(i, board2);
				board2.setPos(i, currPosition);
				System.out.println(i+": "+currPosition);
				i++;
				if(i>1)i=0;
			}
			System.out.println(board.toString());
			System.out.println(board2.toString());
			while (board.playerHasWon(numbers2[0]) != true && board.playerHasWon(numbers2[1])!= true){
				currPosition = fastestPathRight(0, board);
				board.setPos(0, currPosition);
				System.out.println(x+":"+currPosition);
				x++;
				if(x>1)x=0;
			}
		}

		else if (numberOfPlayers == 4){
			Board board4 = new Board(4);
			board4 = board;
			while (board.playerHasWon(numbers4[0])!= true && board.playerHasWon(numbers4[1])!= true && board.playerHasWon(numbers4[2])!= true && board.playerHasWon(numbers4[3])!= true){
				currPosition = fastestPathLeft(i, board);
				board.setPos(i, currPosition);
				System.out.println(i+": "+currPosition);
				i++;
				if(i>3)i=0;
			}
			while (board4.playerHasWon(numbers2[0]) != true && board4.playerHasWon(numbers2[1])!= true && board4.playerHasWon(numbers2[2]) != true && board4.playerHasWon(numbers2[3])!= true){
				currPosition = fastestPathRight(0, board4);
				board4.setPos(0, currPosition);
				System.out.println(x+":"+currPosition);
				x++;
				if(x>3)x=0;
			}
		}
		if (playerNumber == 0){

			if(fastestPathLeft0.size() < fastestPathRight0.size())
				return fastestPathLeft0;

			else 
				return fastestPathRight0;
		}
		if (playerNumber == 1){

			if(fastestPathLeft1.size() < fastestPathRight1.size())
				return fastestPathLeft1;

			else 
				return fastestPathRight1;
		}
		if (playerNumber == 2){

			if(fastestPathLeft2.size() < fastestPathRight2.size())
				return fastestPathLeft2;

			else 
				return fastestPathRight2;
		}
		if (playerNumber == 3){

			if(fastestPathLeft3.size() < fastestPathRight3.size())
				return fastestPathLeft3;

			else 
				return fastestPathRight3;
		}
		else 
			return null;
	}

	public boolean arrayContains(int[] a, int c){
		for(int y = 0; y < a.length; y++){
			if (a[y] == c)
				return true;
		}
		return false;
	}
	public static void main(String args[]){
		Board board = new Board(2);
		The_AI ai = new The_AI();
		System.out.print(ai.trueFastestPath(0, board));
		/*int currPosition = fastestPathLeft(0, board);
		board.setPos(0, currPosition);
		System.out.println("1: " + currPosition);

		currPosition = fastestPathLeft(3, board);
		board.setPos(3, currPosition);
		System.out.println("3: " + currPosition);

		currPosition = fastestPathLeft(1, board);
		board.setPos(1, currPosition);
		System.out.println("2: " + currPosition);

		currPosition = fastestPathLeft(2, board);
		board.setPos(2, currPosition);
		System.out.println("4: " + currPosition);*/

	//	System.out.println(trueFastestPath(0, board));
		//System.out.println(trueFastestPath(1, board));
		//System.out.println(trueFastestPath(2, board));
		//System.out.println(trueFastestPath(3, board));

	}
}
