package AI;

import java.io.IOException;
import java.util.HashMap;

import Board.Board;

public class The_Mimic {
	public static final HashMap<String, String> SIMPLETOCOMPLEX;
	public static final HashMap<String, String> COMPLEXTOSIMPLE;
	public static final HashMap<String, String> COMPLEXTOSIMPLEWITHWALLS;
	static {
		SIMPLETOCOMPLEX = new HashMap<String, String>();
		COMPLEXTOSIMPLE = new HashMap<String, String>();
		COMPLEXTOSIMPLEWITHWALLS = new HashMap<String, String>();
		String text = "a";
		int offset = 0;
		int wall = 0;
		for (int i = 0 ; i < 81 ; i++) {
			if (i%9 == 0) {
				text = "a";
				offset++;
				wall++;
			} else if(i%9 == 1) {
				text = "b";
				wall++;
			} else if(i%9 == 2) {
				text = "c";
				wall++;
			} else if(i%9 == 3) {
				text = "d";
				wall++;
			} else if(i%9 == 4) {
				text = "e";
				wall++;
			} else if(i%9 == 5) {
				text = "f";
				wall++;
			} else if(i%9 == 6) {
				text = "g";
				wall++;
			} else if(i%9 == 7) {
				text = "h";
				wall++;
			} else if(i%9 == 8) {
				text = "i";
			}
			if (i%9 >= 0 && i%9 <= 7) {
				SIMPLETOCOMPLEX.put(i+"v", text+offset+"v");
				COMPLEXTOSIMPLE.put(text+offset+"v", i+"");

				SIMPLETOCOMPLEX.put(i+"h", text+offset+"h");
				COMPLEXTOSIMPLE.put(text+offset+"h", i+"");

				COMPLEXTOSIMPLEWITHWALLS.put(text+offset+"v", i+"v");
				COMPLEXTOSIMPLEWITHWALLS.put(text+offset+"h", i+"h");

			}
			SIMPLETOCOMPLEX.put(i+"", text+offset);
			COMPLEXTOSIMPLE.put(text+offset, i+"");
		}
	}
	
	public static String Mimic(Board board, String opponentsLastMove, String opponentsCurrentMove, int playerNumber){
		The_AI ai = new The_AI();
		String newMove = "";
		HashMap<String, String> relate = new HashMap<String, String>();
		relate.put("a", "h");
		relate.put("b", "g");
		relate.put("c", "f");
		relate.put("d", "e");
		relate.put("e", "d");
		relate.put("f", "c");
		relate.put("g", "b");
		relate.put("h", "a");
		relate.put("1", "8");
		relate.put("2", "7");
		relate.put("3", "6");
		relate.put("4", "5");
		relate.put("5", "4");
		relate.put("6", "3");
		relate.put("7", "2");
		relate.put("8", "1");
		HashMap<String, String> relate1 = new HashMap<String, String>();
		relate1.put("v", "v");
		relate1.put("h", "h");


		if(opponentsCurrentMove.contains("h") || opponentsCurrentMove.contains("v")){
			for (int i = 0; i < SIMPLETOCOMPLEX.get(opponentsCurrentMove).length(); i++) {
				if(i ==2)
					newMove += relate1.get(SIMPLETOCOMPLEX.get(opponentsCurrentMove).charAt(i)+"");
				else
				newMove += relate.get(SIMPLETOCOMPLEX.get(opponentsCurrentMove).charAt(i)+"");
			}
			
			if (board.legalCheck(playerNumber, COMPLEXTOSIMPLE.get(newMove)+SIMPLETOCOMPLEX.get(opponentsCurrentMove).charAt(2)+""))
				return newMove;
			else
				return newMove = SIMPLETOCOMPLEX.get(ai.fastestPathLeft(playerNumber, board)+"");
		}
		else if (opponentsLastMove == null ){// FIX ME check for win state
			return SIMPLETOCOMPLEX.get(ai.fastestPathLeft(playerNumber, board)+"");
		}


		else{
			int Move = board.getPos(playerNumber) - ((Integer.parseInt(opponentsLastMove) - Integer.parseInt(opponentsCurrentMove)) *-1);
			if (board.legalCheck(playerNumber, Move+"") == true)
				return SIMPLETOCOMPLEX.get(Move+"");
			else
				return SIMPLETOCOMPLEX.get(ai.fastestPathLeft(playerNumber, board)+"");
		}

	}

	public static void main(String args[]){
		Board board = new Board(2);
		The_Mimic mimic = new The_Mimic();
		System.out.print(mimic.Mimic(board,"40", "i3h", 1));
	}
}
