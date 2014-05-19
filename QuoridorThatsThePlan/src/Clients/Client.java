package Clients;

import java.net.Socket;
import java.net.UnknownHostException;

import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;

import Board.Board;
import Board_GUI.BoardGui;


/**
 * This is a first client program. It demonstrates how to create a
 * client socket and how to read and write information to it. This
 * program determines what machine and port to connect to by reading
 * the command-line. This is the model that will be followed
 * throughout this semester.
 */
public class Client extends Thread{

	/** Default port number; used if none is provided. */
	public final static int DEFAULT_PORT_NUMBER = 3939;

	/** Default machine name is the local machine; used if none provided. */
	public final static String DEFAULT_MACHINE_NAME = "localhost";

	/** Command-line switches */
	public final static String ARG_PORT = "--port";
	public final static String ARG_MACHINE = "--machine";
	public final static String ARG_IDENTIFIER = "--id";

	/** Message op-codes */
	public final static String MSG_HELLO = "HELLO";	// HELLO <ai-identifier>
	public final static String MSG_QUORIDOR = "QUORIDOR";	// QUORIDOR <player-id>  <ai-id2> [<ai-id3> <ai-id4>]
	public final static String MSG_MOVE_REQUEST = "MOVE?"; // MOVE?
	public final static String MSG_MOVED = "MOVED"; // MOVED <player-id> <move-string>
	public final static String MSG_REMOVED = "REMOVED"; // REMOVED <player-id>
	public final static String MSG_WINNER = "WINNER"; // WINNER <player-id>
	public final static String MSG_WATCH = "WATCH"; // WATCH <player-id>

	/** Special board message */
	public final static String MSG_BOARD = "BOARD";	// BOARD player# of total player count

	/** Server Response Messages */
	public final static String MSG_READY = "READY";
	public final static String MSG_MOVE = "MOVE"; // MOVE <move-string>



	public final static String PROMPT = "Message> ";
	/*	
HELLO <ai-identifier>
QUORIDOR <player-id>  <ai-id2> [<ai-id3> <ai-id4>]
READY <display-name>
MOVE?
MOVE <move-string>
MOVED <player-id> <move-string>
REMOVED <player-id>
WINNER <player-id>
	 */

	/** This is the game board that everything will be based on */
	private static Board board;

	/** This is the game gui that everything will be based on */
	private static BoardGui frame = null;

	/** Scanner attached to keyboard for reading user input */
	private Scanner keyboard;

	/** Name of the machine where the server is running. */
	private String machineName;

	/** Port number of distant machine */
	private int portNumber;

	/** String to identify the customer by */
	private static String id;

	private static int playerNum;

	private static PrintStream[] sout;
	private static Scanner[] sin;

	public static String[] playerIds;

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
		for (int i = 0 ; i < 80 ; i++) {
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

	private static String IPP[] = null;

	/**
	 * Creates a new <code>LoopingClient</code> instance. An instance
	 * has both a machine and port to which it will connect. Both are
	 * stored in the class and used to initialize the connection.
	 *
	 * @param machineName the name of the machine where an compatible
	 * server is running.
	 * @param portNumber the port number on the machine where the
	 * compatible server is listening.
	 */
	public Client(String IPP1, String IPP2) {
		this.IPP = new String[2];
		this.IPP[0] = IPP1;
		this.IPP[1] = IPP2;
		this.keyboard = new Scanner(System.in);
		this.sout = new PrintStream[2];
		this.sin = new Scanner[2];
	}

	public Client(String IPP1, String IPP2, String IPP3, String IPP4) {
		this.IPP = new String[4];
		this.IPP[0] = IPP1;
		this.IPP[1] = IPP2;
		this.IPP[2] = IPP3;
		this.IPP[3] = IPP4;
		this.keyboard = new Scanner(System.in);
		this.sout = new PrintStream[4];
		this.sin = new Scanner[4];
	}

	/**
	 * Processes the command-line parameters and then create and run
	 * the LoopingClient object.
	 *
	 * @param args array of String; the command-line parameters.
	 */
	public static void main(String[] args) {
		int port = DEFAULT_PORT_NUMBER;
		String machine = DEFAULT_MACHINE_NAME;
		String id = "";

		/* Parsing parameters. argNdx will move forward across the
		 * indices; remember for arguments that have their own parameters, you
		 * must advance past the value for the argument too.
		 */
		int argNdx = 0;

		System.out.println(args.length);
		if (args.length == 4){
			Client fc = new Client(args[0], args[1], args[2], args[3]);
			fc.run();
		} else if(args.length == 2) {
			Client fc = new Client(args[0], args[1]);
			fc.run();
		} else {
			System.err.println("Incorrect number of parameters");
			usage();
			System.exit(1);
		}
	}

	/**
	 * Client program opens a socket to the server machine:port
	 * pair. It then sends the message "Hello", reads the line the
	 * server is expected to respond with, and then sends
	 * "Goodbye". After sending the final message it closes the socket
	 * stream.
	 */
	public void run() {

		try {
			Socket[] sockets = new Socket[IPP.length];

			System.out.print(PROMPT);

			for (int i = 0 ; i < sockets.length ; i ++) {
				System.out.println(IPP[i].split(":")[0] + " " + Integer.parseInt(IPP[i].split(":")[1]));
				sockets[i] = new Socket(IPP[i].split(":")[0], Integer.parseInt(IPP[i].split(":")[1]));
				sout[i] = new PrintStream(sockets[i].getOutputStream());
				sin[i] = new Scanner(sockets[i].getInputStream());
			}

			System.out.println("Receiving from servers");

			// Send QUORIDOR message
			String aiIds = "";
			for (int i = 0 ; i < sin.length ; i++) {
				String serverResponse = sin[i].nextLine();
				aiIds = aiIds + serverResponse.split("\\s+")[1] + " ";
			}
			
			String[] AIs; 
			if (IPP.length == 4) {
				AIs = new String[4]; 
			} else {
				AIs = new String[2];
			}
			String[] pieces = aiIds.split("\\s+");
			
			
			if (IPP.length == 4) {
				AIs[0] = pieces[1] + " " + pieces[2] + " " + pieces[3];
				AIs[1] = pieces[0] + " " + pieces[2] + " " + pieces[3];			
				AIs[2] = pieces[0] + " " + pieces[1] + " " + pieces[3];
				AIs[3] = pieces[0] + " " + pieces[1] + " " + pieces[2];
			} else {
				AIs[0] = pieces[1];
				AIs[1] = pieces[0];	
			}
				
			for (int i = 0 ; i < sin.length ; i++) {
				//sout[i].println(MSG_QUORIDOR + " " + (i+1) + " " + aiIds.split("\\s+")[i]);
				sout[i].println(MSG_QUORIDOR + " " + (i+1) + " " + AIs[i]);
				String serverResponse = sin[i].nextLine();
				System.out.println(serverResponse);
			}

			board = new Board(IPP.length);
			board.setCurrPlayer(0);

			frame = new BoardGui(board.getPlayerCount(), board.getCurrPlayer(), board);
			frame.setSize(700,410);
			frame.setResizable( false );
			frame.setVisible(true);

			ArrayList<Integer> order = new ArrayList<Integer>();
			if (IPP.length == 4) {
				order.add(0);
				order.add(3);
				order.add(1);
				order.add(2);
			} else {
				order.add(0);
				order.add(1);
			}
			int curr = 0;
			while(true) {
				System.out.println("CURRENT USER: " + curr);

				sout[order.get(curr)].println(MSG_MOVE_REQUEST);
				String serverResponse = sin[order.get(curr)].nextLine();
				System.out.println(serverResponse + " " + order.get(curr));

				String move = serverResponse.split("\\s+")[1];

				System.out.println("Player#" + (order.get(curr)+1) + " " + MSG_MOVED + " " + move);


				if (move.length() == 2){ 
					if (COMPLEXTOSIMPLE.get(move) == null || !board.legalCheck(order.get(curr), COMPLEXTOSIMPLE.get(move))) {

						frame.control.removePlayer(board.getCurrPlayer(), board, frame.space);
						JOptionPane.showMessageDialog(null, "player " + (order.get(curr)+1) + " has been removed!");

						board.kick(board.getCurrPlayer());
						for (PrintStream out : sout)
							out.println(MSG_REMOVED + " " + (order.get(curr)+1));
						order.remove(curr);

						if (order.size() == 1) {
							JOptionPane.showMessageDialog(null, "player " + (order.get(0)+1) + " has won!");
							
							sout[0].println(MSG_WINNER + " " + (order.get(0)+1));
							
							System.exit(0);
						}

					} else if (board.victorySoon(order.get(curr), Integer.parseInt(COMPLEXTOSIMPLE.get(move)))){
						JOptionPane.showMessageDialog(null, "player " + (order.get(curr)+1) + " has won!");
						
						for (PrintStream out : sout)
							out.println(MSG_WINNER + " " + (order.get(curr)+1));
						
						System.exit(0);
					} else {
						updateBoard(move, frame);
						for (int i = 0 ; i < sin.length ; i++) {	
							sout[i].println(MSG_MOVED + " " + (order.get(curr)+1) + " " + move);
						}
						frame.control.removePos(board.getCurrPlayer(), board, frame.space);
						if (false)
							break;
						if (curr+1 >= order.size())
							curr = 0;
						else 
							curr++;
					}
				} else {
					System.out.println(COMPLEXTOSIMPLE.get(move)+move.charAt(2));
					if (COMPLEXTOSIMPLE.get(move) == null || !board.legalCheck(order.get(curr), COMPLEXTOSIMPLE.get(move) + move.charAt(2))) {

						frame.control.removePlayer(board.getCurrPlayer(), board, frame.space);
						JOptionPane.showMessageDialog(null, "player " + (order.get(curr)+1) + " has been removed!");

						board.kick(board.getCurrPlayer());
						for (PrintStream out : sout)
							out.println(MSG_REMOVED + " " + (order.get(curr)+1));
						order.remove(curr);
						
						if (order.size() == 1) {
							JOptionPane.showMessageDialog(null, "player " + (order.get(0)+1) + " has won!");
							
							sout[0].println(MSG_WINNER + " " + (order.get(0)+1));
							
							System.exit(0);
						}
					} else {
						updateBoard(move, frame);
						for (int i = 0 ; i < sin.length ; i++) {	
							sout[i].println(MSG_MOVED + " " + (order.get(curr)+1) + " " + move);
							System.out.println("sending move to player: " + (order.get(curr)+1));
						}
						frame.control.removePos(board.getCurrPlayer(), board, frame.space);
						if (false)
							break;
						if (curr+1 >= order.size())
							curr = 0;
						else 
							curr++;
					}
				}
				/* FIX HERE, needs to REMOVE player from board*/
				/* FIX HERE, still need to make WINNER */
				board.setCurrPlayer(order.get(curr));
				frame.control.paintPos(board.getCurrPlayer(), board, frame.space);
			}

			for (Scanner in : sin)
				in.close();
			for (PrintStream out : sout)
				out.close();
		} catch (UnknownHostException uhe) {

			// the host name provided could not be resolved
			uhe.printStackTrace();
			System.exit(1);
		} catch (IOException ioe) {

			// there was a standard input/output error (lower-level)
			ioe.printStackTrace();
			System.exit(1);
		}
	}

	public static void updateBoard(String move, BoardGui frame) {
		System.out.println("raw move: " + move);
		System.out.println("converted move: " + COMPLEXTOSIMPLE.get(move));

		if(move.length() == 3) { 
			if(move.charAt(2) == 'v'){
				System.out.println("Making a VWall");
				frame.updateVWall(Integer.parseInt(COMPLEXTOSIMPLE.get(move)));
			} else if (move.charAt(2) == 'h'){
				System.out.println("Making a HWall");
				frame.updateHWall(Integer.parseInt(COMPLEXTOSIMPLE.get(move)));
			}
		} else {
			frame.updateSpace(Integer.parseInt(COMPLEXTOSIMPLE.get(move)));
		}
	}

	/**
	 * Print the usage message for the program on standard error stream.
	 */
	private static void usage() {
		System.err.format("usage: java LoopingClient [options]\n" +
				"       where options:\n" + "       %s port\n" +
				"       %s machineName\n", ARG_PORT, ARG_MACHINE);
	}
}
