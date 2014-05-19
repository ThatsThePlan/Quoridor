package AI;
import java.io.*;
import java.util.ArrayList;
import Board.Board;
/** AI_Methods: The bulk of the AI, including fastest and longest paths along with AI strategies */
public class AI_Methods {
	//String Array to represent board
	static String board[][]={
		{" "," "," ","","1"," "," "," "," "},
		{" "," "," "," w","w ","w "," "," "," "},
		{" "," "," "," w"," ","w "," "," "," "},
		{" "," "," "," w"," ","w "," "," "," "},
		{"4"," "," "," w"," ","w "," "," ","3"},
		{" "," "," "," w"," ","w "," "," "," "},
		{" "," "," "," w"," ","w"," "," "," "},
		{" "," "," "," w"," ","w "," "," "," "},
		{" "," "," "," ","2"," "," "," "," "},
	};
	// static ints for positioning and move calculation
	static int pPositionC, pPositionL,lastMove,x, r = -1, f = 0, fl = 0, fr = 0, l = -1;
	
	// ints to keep track of each players spots for quick recalls
	int Player1,Player2,Player3,Player4;
	
	//int array to keep track of moves made and moves that failed
	static ArrayList<Integer> failedMovesLeft = new ArrayList<Integer>();
	static ArrayList<Integer> failedMovesRight = new ArrayList<Integer>();
	static ArrayList<Integer> reverseL = new ArrayList<Integer>();
	static ArrayList<Integer> reverseR = new ArrayList<Integer>();
	
	//list of all possible moves
	static String list,fastestPathRight="",fastestPathLeft="";
	
	/** allPossibleMoves: Takes in the string name of a pawn finds it and returns all its possible moves as a String */
	public String allPossibleMoves(String pawn){
		list = "";
		for(int i=0; i<81; i++)
			if (board[i/9][i%9].equals(pawn)){
			    list = "";
				list+=possiblePawnMoves(i);
			}
		return list;
	}
	
	/** currentPositionOfPawn: Takes in a string of the pawn to be found and returns its Int location */
	public static int currentPositionOfPawn(String pawn){
		int pos = -1;
		for(int i=0; i<81; i++)
			if (board[i/9][i%9].equals(pawn)){
				pos = i;
		}
		return pos;
	}

	/** allPossibleMovesInt: Takes in the string name of a pawn finds it and returns all its possible moves as an Int */
	public static int[] possiblePawnMovesInt(int i){
		int[] moves = new int[5];
		moves[0] = i;
		moves[1] = -1;
		moves[2] = -1;
		moves[3] = -1;
		moves[4] = -1;
		
		int r = i/9, c = i%9;
		for(int j = 0; j<9; j++){
			if (j == 1||j == 3||j == 5||j == 7){
				try{
					if(" ".equals(board[r-1+j/3][c-1+j%3])){
						if(viableMove()){
							if(j == 1 && i-9>0)
								moves[1] = i-9;
							if(j == 3 && i-1>0)
								moves[3] = i-1;
							if(j == 5 && i+1<81)
								moves[4] = i+1;
							if(j == 7 && i+9<81)
								moves[2] = i+9;
						}
					}
				}catch (Exception e) {}
			}
		}
		return moves;
	}
	/** possiblePawnMoves: Takes in the integer position of the pawn and returns all its moves as a String */
	public static String possiblePawnMoves(int i){
		list="";
		String oldPiece;
		int r = i/9, c = i%9;
		for(int j = 0; j<9; j++){
			if (j == 1||j == 3||j == 5||j == 7){
				try{
					if(" ".equals(board[r-1+j/3][c-1+j%3])){
						oldPiece = board[r-1+j/3][c-1+j%3];
						if(viableMove()){
							list = list+r+c+(r-1+j/3)+(c-1+j%3)+oldPiece;
						}
						board[r-1+j/3][c-1+j%3]=oldPiece;
					}
				}catch (Exception e) {}
			}
		}
		//need to add Jumps
		return list;
	}
	
	public static boolean arrayContains(int[] a, int c){
		for(int y = 0; y < a.length; y++){
			if (a[y] == c)
				return true;
		}
		return false;
	}
	/** fastestPathLeft: Takes the integer position of the pawn and returns the fastest path to its destination favoring moving up and left*/
	public static ArrayList<Integer> fastestPathLeft(int position, int playerNumber){
		int moves[] = possiblePawnMovesInt(position);
		
		if (playerNumber == 1){
			if( position == 72 || position == 73 || position == 74 || position == 75 || position == 76 || position == 77 || position == 78 || position == 79 || position == 80){
			reverseL.add(position);
			return reverseL;}//Should be if lastMove == winCondition
			else if(moves[2]!=-1  && reverseL.contains(moves[2]) == false && failedMovesLeft.contains(moves[2]) == false){//moving south
				reverseL.add(position);
				l++;
				fastestPathLeft += position+"v"+moves[2]+" ";
				fastestPathLeft(moves[2], playerNumber);
				return reverseL;
			}
			else if(moves[3]!=-1  && reverseL.contains(moves[3]) == false && failedMovesLeft.contains(moves[3]) == false){//moving west
				reverseL.add(position);
				l++;
				fastestPathLeft += position+"<"+moves[3]+" ";
				fastestPathLeft(moves[3], playerNumber);
				return reverseL;
			}
			else if(moves[4]!=-1  && reverseL.contains(moves[4]) == false && failedMovesLeft.contains(moves[4]) == false){//moving east
				reverseL.add(position);
				l++;
				fastestPathLeft += position+">"+moves[4]+" ";
				fastestPathLeft(moves[4], playerNumber);
				return reverseL;
			}
			else if(moves[1]!=-1 && reverseL.contains(moves[1]) == false && failedMovesLeft.contains(moves[1]) == false){//moving north
				reverseL.add(position);
				l++;
				fastestPathLeft += position+"^"+moves[1]+" ";
				fastestPathLeft(moves[1], playerNumber);
				return reverseL;
			}
			else{
				failedMovesLeft.add(position);
				fl++;
				reverseL.remove(l);
				l--;
				fastestPathLeft(reverseL.get(l), playerNumber);
			}
		}
		if (playerNumber == 2){
			if(position == 0 || position == 1 || position == 2 || position == 3 || position == 4 || position == 5 || position == 6 || position == 7 || position == 8){System.out.println("Fastest Path Left:");
			reverseL.add(position);
			return reverseL;}//Should be if lastMove == winCondition
			else if(moves[1]!=-1 && reverseL.contains(moves[1]) == false && failedMovesLeft.contains(moves[1]) == false){//moving north
				reverseL.add(position);
				l++;
				fastestPathLeft(moves[1], playerNumber);
				return reverseL;
			} 
			else if(moves[3]!=-1  && reverseL.contains(moves[3]) == false &&failedMovesLeft.contains(moves[3]) == false){//moving west
				reverseL.add(position);
				l++;
				fastestPathLeft += position+"<"+moves[3]+" ";
				fastestPathLeft(moves[3], playerNumber);
				return reverseL;
			}
			else if(moves[4]!=-1  && reverseL.contains(moves[4]) == false && failedMovesLeft.contains(moves[4]) == false){//moving east
				reverseL.add(position);
				l++;
				fastestPathLeft += position+">"+moves[4]+" ";
				fastestPathLeft(moves[4], playerNumber);
				return reverseL;
			}
			else if(moves[2]!=-1  && reverseL.contains(moves[2]) == false && failedMovesLeft.contains(moves[2]) == false){//moving south
				reverseL.add(position);
				l++;
				fastestPathLeft += position+"v"+moves[2]+" ";
				fastestPathLeft(moves[2], playerNumber);
				return reverseL;
			}
			else{
				failedMovesLeft.add(position);
				fl++;
				reverseL.remove(l);
				l--;
				fastestPathLeft(reverseL.get(l), playerNumber);
			}
		}
		if (playerNumber == 3){
			if(position == 0 || position == 9 || position == 18 || position == 27 || position == 36 || position == 45 || position == 54 || position == 63 || position == 72){System.out.println("Fastest Path Left:");
			reverseL.add(position);
			return reverseL;}//Should be if lastMove == winCondition
 
			else if(moves[3]!=-1  && reverseL.contains(moves[3]) == false && failedMovesLeft.contains(moves[3]) == false){//moving west
				reverseL.add(position);
				l++;
				fastestPathLeft += position+"<"+moves[3]+" ";
				fastestPathLeft(moves[3], playerNumber);
				return reverseL;
			}
			else if(moves[2]!=-1  && reverseL.contains(moves[2]) == false && failedMovesLeft.contains(moves[2]) == false){//moving south
				reverseL.add(position);
				l++;
				fastestPathLeft += position+"v"+moves[2]+" ";
				fastestPathLeft(moves[2], playerNumber);
				return reverseL;
			}
			else if(moves[1]!=-1 && reverseL.contains(moves[1]) == false && failedMovesLeft.contains(moves[1]) == false){//moving north
				reverseL.add(position);
				l++;
				fastestPathLeft += position+"^"+moves[1]+" ";
				fastestPathLeft(moves[1], playerNumber);
				return reverseL;
			}
			else if(moves[4]!=-1  && reverseL.contains(moves[4]) == false && failedMovesLeft.contains(moves[4]) == false){//moving east
				reverseL.add(position);
				l++;
				fastestPathLeft += position+">"+moves[4]+" ";
				fastestPathLeft(moves[4], playerNumber);
				return reverseL;
			}
			else{
				failedMovesLeft.add(position);
				fl++;
				reverseL.remove(l);
				l--;
				fastestPathLeft(reverseL.get(l), playerNumber);
			}
		}
		if (playerNumber == 4){
			if(position == 8 || position == 17 || position == 26 || position == 35 || position == 44 || position == 53 || position == 62 || position == 71 || position == 80){System.out.println("Fastest Path Left:");
			reverseL.add(position);
			return reverseL;}//Should be if lastMove == winCondition
		
			else if(moves[4]!=-1  && reverseL.contains(moves[4]) == false && failedMovesLeft.contains(moves[4]) == false){//moving east
				reverseL.add(position);
				l++;
				fastestPathLeft += position+">"+moves[4]+" ";
				fastestPathLeft(moves[4], playerNumber);
				return reverseL;
			}

			else if(moves[2]!=-1  && reverseL.contains(moves[2]) == false && failedMovesLeft.contains(moves[2]) == false){//moving south
				reverseL.add(position);
				l++;
				fastestPathLeft += position+"v"+moves[2]+" ";
				fastestPathLeft(moves[2], playerNumber);
				return reverseL;
			}
			else if(moves[1]!=-1 && reverseL.contains(moves[1]) == false && failedMovesLeft.contains(moves[1]) == false){//moving north
				reverseL.add(position);
				l++;
				fastestPathLeft += position+"^"+moves[1]+" ";
				fastestPathLeft(moves[1], playerNumber);
				return reverseL;
			}
			else if(moves[3]!=-1  && reverseL.contains(moves[3]) == false && failedMovesLeft.contains(moves[3]) == false){//moving west
				reverseL.add(position);
				l++;
				fastestPathLeft += position+"<"+moves[3]+" ";
				fastestPathLeft(moves[3], playerNumber);
				return reverseL;
			}
			else{
				failedMovesLeft.add(position);
				fl++;
				reverseL.remove(l);
				l--;
				fastestPathLeft(reverseL.get(l), playerNumber);
			}
		}
		return null;
	}
	
	/** fastestPathRight: Takes the integer position of the pawn and returns the fastest path to its destination favoring moving up and right*/
	public static ArrayList<Integer> fastestPathRight(int position, int playerNumber){
		int moves[] = possiblePawnMovesInt(position);
		if (playerNumber == 1){
			if( position == 72 || position == 73 || position == 74 || position == 75 || position == 76 || position == 77 || position == 78 || position == 79 || position == 80){System.out.println("Fastest Path Right:");
			reverseR.add(position);
			return reverseR;}//Should be if lastMove == winCondition

			else if(moves[2]!=-1  && reverseR.contains(moves[2]) == false && failedMovesRight.contains(moves[2]) == false){//moving south
				reverseR.add(position);;
				r++;
				fastestPathRight += position+"v"+moves[2]+" ";
				fastestPathRight(moves[2], playerNumber);
				return reverseR;
			}
			else if(moves[4]!=-1 && reverseR.contains(moves[4]) == false && failedMovesRight.contains(moves[4]) == false){//moving east
				reverseR.add(position);
				r++;
				fastestPathRight += position+">"+moves[4]+" ";
				fastestPathRight(moves[4], playerNumber);
				return reverseR;
			}
			else if(moves[3]!=-1 && reverseR.contains(moves[3]) == false && failedMovesRight.contains(moves[3]) == false){//moving west
				reverseR.add(position);
				r++;
				fastestPathRight += position+"<"+moves[3]+" ";
				fastestPathRight(moves[3], playerNumber);
				return reverseR;
			}
			else if(moves[1]!=-1 && reverseR.contains(moves[1]) == false && failedMovesRight.contains(moves[1]) == false){//moving north
				reverseR.add(position);
				r++;
				fastestPathRight += position+"^"+moves[1]+" ";
				fastestPathRight(moves[1], playerNumber);
				return reverseR;
			}
			else{
				failedMovesRight.add(position);
				fr++;
				reverseR.remove(r);
				r--;
				fastestPathRight(reverseR.get(r), playerNumber);
			}
		}
		if (playerNumber == 2){
			if( position == 0 || position == 1 || position == 2 || position == 3 || position == 4 || position == 5 || position == 6 || position == 7 || position == 8){System.out.println("Fastest Path Right:");
			reverseR.add(position);
			return reverseR;}//Should be if lastMove == winCondition
		
			else if(moves[1]!=-1 &&reverseR.contains(moves[1]) == false && failedMovesRight.contains(moves[1]) == false){//moving north
				reverseR.add(position);
				r++;
				fastestPathRight += position+"^"+moves[1]+" ";
				fastestPathRight(moves[1], playerNumber);
				return reverseR;
			}
			else if(moves[4]!=-1 && reverseR.contains(moves[4]) == false && failedMovesRight.contains(moves[4]) == false){//moving east
				reverseR.add(position);
				r++;
				fastestPathRight += position+">"+moves[4]+" ";
				fastestPathRight(moves[4], playerNumber);
				return reverseR;
			}
			else if(moves[3]!=-1 && reverseR.contains(moves[3]) == false && failedMovesRight.contains(moves[3]) == false){//moving west
				reverseR.add(position);
				r++;
				fastestPathRight += position+"<"+moves[3]+" ";
				fastestPathRight(moves[3], playerNumber);
				return reverseR;
			}
			else if(moves[2]!=-1  && reverseR.contains(moves[2]) == false && failedMovesRight.contains(moves[2]) == false){//moving south
				reverseR.add(position);
				r++;
				fastestPathRight += position+"v"+moves[2]+" ";
				fastestPathRight(moves[2], playerNumber);
				return reverseR;
			}
			else{
				failedMovesRight.add(position);
				fr++;
				reverseR.remove(r);
				r--;
				fastestPathRight(reverseR.get(r), playerNumber);
			}
		}
			if (playerNumber == 3){
				if( position == 0 || position == 9 || position == 18 || position == 27 || position == 36 || position == 45 || position == 54 || position == 63 || position == 72){System.out.println("Fastest Path Right:");
				reverseR.add(position);
				return reverseR;}//Should be if lastMove == winCondition
				
				else if(moves[4]!=-1 && reverseR.contains(moves[4]) == false && failedMovesRight.contains(moves[4]) == false){//moving east
					reverseR.add(position);
					r++;
					fastestPathRight += position+">"+moves[4]+" ";
					fastestPathRight(moves[4], playerNumber);
					return reverseR;
				}
				else if(moves[1]!=-1 && reverseR.contains(moves[1]) == false && failedMovesRight.contains(moves[1]) == false){//moving north
					reverseR.add(position);
					r++;
					fastestPathRight += position+"^"+moves[1]+" ";
					fastestPathRight(moves[1], playerNumber);
					return reverseR;
				}
				else if(moves[2]!=-1  && reverseR.contains(moves[2]) == false &&failedMovesRight.contains(moves[2]) == false){//moving south
					reverseR.add(position);
					r++;
					fastestPathRight += position+"v"+moves[2]+" ";
					fastestPathRight(moves[2], playerNumber);
					return reverseR;
				}
				else if(moves[3]!=-1 && reverseR.contains(moves[3]) == false && failedMovesRight.contains(moves[3]) == false){//moving west
					reverseR.add(position);
					r++;
					fastestPathRight += position+"<"+moves[3]+" ";
					fastestPathRight(moves[3], playerNumber);
					return reverseR;
				}

				else{
					failedMovesRight.add(position);
					fr++;
					reverseR.remove(r);
					r--;
					fastestPathRight(reverseR.get(r), playerNumber);
				}
			}
			if (playerNumber == 4){
				if( position == 8 || position == 17 || position == 26 || position == 35 || position == 44 || position == 53 || position == 62 || position == 71 || position == 80){System.out.println("Fastest Path Right:");
				reverseR.add(position);
				return reverseR;}//Should be if lastMove == winCondition
				
				else if(moves[3]!=-1 && reverseR.contains(moves[3]) == false && failedMovesRight.contains(moves[3]) == false){//moving west
					reverseR.add(position);
					r++;
					fastestPathRight += position+"<"+moves[3]+" ";
					fastestPathRight(moves[3], playerNumber);
					return reverseR;
				}
				else if(moves[2]!=-1  && reverseR.contains(moves[2]) == false && failedMovesRight.contains(moves[2]) == false){//moving south
					reverseR.add(position);
					r++;
					fastestPathRight += position+"v"+moves[2]+" ";
					fastestPathRight(moves[2], playerNumber);
					return reverseR;
				}
				else if(moves[1]!=-1 && reverseR.contains(moves[1]) == false && failedMovesRight.contains(moves[1]) == false){//moving north
					reverseR.add(position);
					r++;
					fastestPathRight += position+"^"+moves[1]+" ";
					fastestPathRight(moves[1], playerNumber);
					return reverseR;
				}
				else if(moves[4]!=-1 && reverseR.contains(moves[4]) == false && failedMovesRight.contains(moves[4]) == false){//moving east
					reverseR.add(position);
					r++;
					fastestPathRight += position+">"+moves[4]+" ";
					fastestPathRight(moves[4], playerNumber);
					return reverseR;
				}
				else{
					failedMovesRight.add(position);
					fr++;
					reverseR.remove(r);
					r--;
					fastestPathRight(reverseR.get(r), playerNumber);
				}
			}
			return null;
		}
	
	/** viableMove: Is passed a move and returns true or false based on its viability*/
	public static boolean viableMove(){
		return true;
	}
	/** Main method to test AI functionality alone*/
	public static void main(String args[]){
		String a = "Shit";
		System.out.println("Enter a pawn to find moves for:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			a = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
			System.out.println(fastestPathLeft(currentPositionOfPawn("2"),2));
			System.out.println(fastestPathRight(currentPositionOfPawn("2"), 2));
	}
	
	// adding in update methods and tools to get fastest path for the decsion tree
	// should not affect the functionality of anything else here. 
	public void updateState(Board b){
		// need to use the toString in Board to get a string representation, then make this board match it
		// board to srting returns: 
		String boardString = b.getBoardState();
		String [] boardAndPlayers = boardString.split(" ");
		// parse string array here
		// use it to update board.
		// get player positions and update next.
	}
}