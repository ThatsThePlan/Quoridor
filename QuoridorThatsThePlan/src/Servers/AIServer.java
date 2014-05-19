package Servers;

import java.io.IOException;
import java.io.PrintStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.HashMap;
import java.util.Scanner;

import AI.DecisionTree;
import AI.The_AI;
import AI.The_Mimic;
import Board.Board;
import Board_GUI.BoardGui;


/**
 * A simple, single-threaded echo server with sequential servicing of
 * clients. That is, when a client connects, it is serviced to
 * completion (until it closes the connection or sends MSG_GOODBYE)
 * before another client connection is accepted. The server cannot be
 * stopped gracefully: use Ctrl-C to break the running program in a
 * terminal.
 *
 * "Echo server" means that it listens for a connection (on a
 * user-specifiable port), reads in the input on the wire, modifies
 * the information, and writes the modified information back to the
 * client, echoing the input it was given.
 */
public class AIServer {

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


	/** Default port number; used if none is provided. */
	public final static int DEFAULT_PORT_NUMBER = 3939;

	/** Default machine name is the local machine; used if none provided. */
	public final static String DEFAULT_MACHINE_NAME = "localhost";

	/** Command-line switches */
	public final static String ARG_PORT = "--port";
	public final static String ARG_MACHINE = "--machine";
	public final static String ARG_IDENTIFIER = "--id";
	public final static String ARG_AITYPE = "--ai";

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

	private static String aiIdentifier = "fried";
	private static String aiType = "decision";

	private int playerNumber;
	private int playerCount;

	private Board board;
	private Board board1;
	/** Port number of distant machine */
	private int portNumber;

	private static String player1LastMove = null;
	private static String player2LastMove = null;

	private static String player1CurrMove = null;
	private static String player2CurrMove = null;

	private static String player1LastWall = null;
	private static String player2LastWall = null;


	/**
	 * Creates a new <code>EchoServer</code> instance. EchoServer is
	 * a listening echo server (it responds with a slightly modified
	 * version of the same message it was sent).
	 *
	 * @param portNumber required port number where the server will
	 * listen.
	 */
	public AIServer(int portNumber) {
		this.portNumber = portNumber;
	}

	/**
	 * Processes the command-line parameters and then create and run
	 * the FirstClient object.
	 *
	 * @param args a <code>String</code> value
	 */
	public static void main(String[] args) {
		int port = DEFAULT_PORT_NUMBER;

		/* Parsing parameters. argNdx will move forward across the
		 * indices; remember for arguments that have their own parameters, you
		 * must advance past the value for the argument too.
		 */
		int argNdx = 0;

		while (argNdx < args.length) {
			String curr = args[argNdx];

			if (curr.equals(ARG_PORT)) {
				++argNdx;

				String numberStr = args[argNdx];
				port = Integer.parseInt(numberStr);
			} else if (curr.equals(ARG_IDENTIFIER)) {
				++argNdx;
				aiIdentifier = args[argNdx];			
			} else if (curr.equals(ARG_AITYPE)) {
				++argNdx;
				aiType = args[argNdx];
			} else {

				// if there is an unknown parameter, give usage and quit
				System.err.println("Unknown parameter \"" + curr + "\"");
				usage();
				System.exit(1);
			}

			++argNdx;
		}

		AIServer fc = new AIServer(port);
		fc.run();
	}

	/**
	 * Primary method of the server: Opens a listening socket on the
	 * given port number (specified when the object was
	 * constructed). It then loops forever, accepting connections from
	 * clients.
	 *
	 * When a client connects, it is assumed to be sending messages, one per line. The server will process
	 */
	public void run() {

		try {
			ServerSocket server = new ServerSocket(portNumber);
			System.out.format("Server now accepting connections on port %d\n",
					portNumber);

			Socket client;

			while ((client = server.accept()) != null) {
				System.out.format("Connection from %s\n", client);

				Scanner cin = new Scanner(client.getInputStream());
				PrintStream cout = new PrintStream(client.getOutputStream());


				String clientMessage = MSG_HELLO + " " + aiIdentifier;

				cout.format("%s\n", clientMessage);
				System.out.format("Server saw \"%s\"\n", clientMessage);

				while (cin.hasNextLine()) {
					clientMessage = cin.nextLine();
					if (clientMessage.contains(MSG_QUORIDOR)) {
						System.out.println(clientMessage);
						playerNumber = Integer.parseInt(clientMessage.split("\\s+")[1]);
						playerCount = clientMessage.split("\\s+").length - 1;

						board = new Board(playerCount);
						board1 = new Board(playerCount);
						
						board.setCurrPlayer(0);
						board1.setCurrPlayer(0);

						player1CurrMove = board.getPos(0)+"";
						player2CurrMove = board.getPos(1)+"";

						System.out.println("I am player: " + playerNumber + " and there are " + playerCount + " players");
						cout.format("%s\n", MSG_READY + " " + aiIdentifier);
						System.out.format("Server sent \"%s\"\n", MSG_READY + " " + aiIdentifier);	
					} else if (clientMessage.equals(MSG_MOVE_REQUEST)) {
						//System.out.println("MOVE: ");
						/* FIX HERE this is where the AI will do its work (replace the keyboard with ai) */

						if (aiType.equals("decision")) {
							DecisionTree tree = new DecisionTree(playerNumber-1, playerCount);
							tree.initialize(board);
							
							String move = tree.getOptimalMove();
							System.out.println("Decisions move: " + move);
							cout.format("%s\n", MSG_MOVE + " " + SIMPLETOCOMPLEX.get(move));
							
						} else if (aiType.equals("mimic")) {
							The_Mimic mimic = new The_Mimic();
							String move = null;
							if (playerNumber == 2) {
								if (player1LastWall != null)
									move = mimic.Mimic(board, player1LastMove, player1LastWall, playerNumber-1);
								else
									move = mimic.Mimic(board, player1LastMove, player1CurrMove, playerNumber-1);
							} else { 
								if (player1LastWall != null)
									move = mimic.Mimic(board, player2LastMove, player2LastWall, playerNumber-1);
								else
									move = mimic.Mimic(board, player2LastMove, player2CurrMove, playerNumber-1); 
							}
							cout.format("%s\n", MSG_MOVE + " " + move);
						}


					} else if (clientMessage.contains(MSG_REMOVED)) {
						String[] tmp = clientMessage.split("\\s+");
						System.out.println(clientMessage);
						if (Integer.parseInt(tmp[1]) == playerNumber) {
							break;
						}
						board.kick(Integer.parseInt(tmp[1])-1);
						board1.kick(Integer.parseInt(tmp[1])-1);
					} else if (clientMessage.startsWith(MSG_MOVED)) {
						String[] movePieces = clientMessage.split("\\s+");
						System.out.println(movePieces[0] + " " + movePieces[1] + " " + COMPLEXTOSIMPLE.get(movePieces[2]));
						updateBoard(Integer.parseInt(movePieces[1]), movePieces[2], board, board1);
					} else if (clientMessage.contains(MSG_WINNER)) {
						break;
					}

				}

				if (!clientMessage.isEmpty()) {
					System.out.format("Server saw \"%s\" and is exiting.\n",
							clientMessage);
				}

				cout.close();
				cin.close();
			}
		} catch (IOException ioe) {

			// there was a standard input/output error (lower-level from uhe)
			ioe.printStackTrace();
			System.exit(1);
		}
	}

	public static void updateBoard(int player, String move, Board board, Board board1) {
		System.out.println("raw move: " + move);
		System.out.println("converted move: " + COMPLEXTOSIMPLE.get(move));

		if (move.length() != 3){
			if (player == 1) {
				player1LastMove = player1CurrMove; 
				player1CurrMove = COMPLEXTOSIMPLE.get(move);
				player1LastWall = null;
			} else {
				player2LastMove = player2CurrMove;
				player2CurrMove = COMPLEXTOSIMPLE.get(move);
				player2LastWall = null;
			}
		} else {
			if (player == 1) {
				player1LastWall = COMPLEXTOSIMPLE.get(move)+move.charAt(2);
			} else {
				player2LastWall = COMPLEXTOSIMPLE.get(move)+move.charAt(2);
			} 
		}

		if(move.length() == 3) { 
			board.setWall(player-1, COMPLEXTOSIMPLE.get(move) + move.charAt(2));
			board1.setWall(player-1, COMPLEXTOSIMPLE.get(move) + move.charAt(2));
		} else {
			board.setPos(player-1, Integer.parseInt(COMPLEXTOSIMPLE.get(move)));
			board1.setPos(player-1, Integer.parseInt(COMPLEXTOSIMPLE.get(move)));
		}
	}


	/**
	 * Print the usage message for the program on standard error stream.
	 */
	private static void usage() {
		System.err.print("usage: java FirstClient [options]\n" +
				"       where options:\n" + "       --port port\n");
	}
}
