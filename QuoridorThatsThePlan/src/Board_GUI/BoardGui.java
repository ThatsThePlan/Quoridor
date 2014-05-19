package Board_GUI;
/* 
 * name of file: BoardGui.java
 * Authors: David Rice
 * Last updated: March 12, 6:38 David Rice
 */

import java.awt.*;
import Board.Board;
import Clients.Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** This File builds the GUI for the Quoridor game.  It passes and reads strings to the back end so the board state will change. */
public class BoardGui extends JFrame implements ActionListener, MouseListener{
	final static boolean RIGHT_TO_LEFT = false;
	private int players; //how many players will the game originally start with
	private JButton submit; //recording current move
	private Image wood; //the background image for a game
	private Graphics2D g2d;
	//The following are for specific game moves
	private String placed = null;
	private String submitMove = null;
	private BoardSpace button = null;
	private Intersect inter = null;
	public GuiControl control = new GuiControl();

	/** Space will holds an ArrayList of BoardSpace, it stores the state of all spaces on the board */
	public ArrayList<BoardSpace> space = new ArrayList<BoardSpace>();
	/** Space will holds an ArrayList of Intersect, it stores the state of all Intersect buttons (how a wall is set) on the board */
	private BlockingQueue<String> commandQueue; 

	public ArrayList<Intersect> sect = new ArrayList<Intersect>();
	private ArrayList<String> walls = new ArrayList<String>();
	public JLabel[] labels = new JLabel[4];
	private int currentPosition = 0; // this is how the board will keep track of board space
	private int newPosition = 0; // this is how the board will keep track of board space

	private Board board;
	private int myPlayerNum;

	/** create the BoardGui by passing # of players(int), and a Board object */
	public BoardGui(int players, int myPlayerNum, Board board) {
		super();
		this.setTitle("Quoridor");
		this.setName("Quoridor");
		this.players = players;
		this.board = board;
		this.myPlayerNum = myPlayerNum;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setting the background specific to the player count
		if (players == 4) {
			wood = new ImageIcon("board_4players.jpg").getImage();
		} else {
			wood = new ImageIcon("board_2players.jpg").getImage();
		}

		setContentPane(new ImagePanel(wood));
		addComponentsToPane(this.getContentPane());

		this.pack();
		this.setVisible(true);
	}

	// This method will construct the game board.
	// It will include BoardSpaces, Intersects, JLabels and a JButton
	// The BoardSpaces will represent player position
	// The Intersects will represent wall position
	// JLabels will keep track of player state
	// JButton will be used to submit the player move
	/** This will create the GUI Game board pane by being passed a Container */
	public void addComponentsToPane(Container pane) {
		if (RIGHT_TO_LEFT) {
			pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		}
		// Using a GridBagLayout so that the spaces can be uniquely sized
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		pane.setLayout(new GridLayout());

		GridBagConstraints c = new GridBagConstraints();

		// Create the BoardSpaces in a 9x9 grid
		for (int i = 0 ; i < 9 ; i++) {
			for (int j = 0 ; j < 9 ; j++) {
				BoardSpace tmp = new BoardSpace("", ""+ ((i*9) + j), false); 
				tmp.addActionListener(this);

				// Add the BoardSpace
				c.weightx = 0.5;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = j*2;
				c.gridy = (i * 2);
				space.add(tmp);
				panel.add(tmp, c);
			}
		}
		// Create the Intersects, VWalls and HWalls interlacing them between the
		// BoardSpaces
		for (int i = 0 ; i < 8 ; i++) {
			for (int j = 0 ; j < 8 ; j++) {
				// Create 4 walls
				VWall top = new VWall("", ((i-1) + "-" + (j)), false);
				VWall bottom = new VWall("", ((i-1) + "-" + (j)), false);
				HWall left = new HWall("", ((i) + "-" + (j-1)), false);
				HWall right = new HWall("", ((i) + "-" + (j-1)), false);

				// Create an Intersect using the 4 walls
				Intersect tmp = new Intersect("", ((i*9)+j) + "", top,bottom,left,right);
				tmp.addActionListener(this);

				// Add the Intersect
				c.weightx = 0.5;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = j*2+1;
				c.gridy = (i * 2)+1;
				sect.add(tmp);
				panel.add(tmp, c);

				// Add the walls
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = j*2+1;
				c.gridy = (i * 2);
				panel.add(top, c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = j*2+1;
				c.gridy = (i * 2)+2;
				panel.add(bottom, c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = j*2;
				c.gridy = (i * 2)+1;
				panel.add(left, c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = j*2+2;
				c.gridy = (i * 2)+1;
				panel.add(right, c);
			}
		}

		//this area calls a board method to get the current player positions
		space.get(board.getPos(0)).setColor(Color.BLUE);
		space.get(board.getPos(0)).setPotential(false);
		space.get(board.getPos(0)).setClicked(true);

		space.get(board.getPos(1)).setColor(Color.GREEN);
		space.get(board.getPos(1)).setPotential(false);
		space.get(board.getPos(1)).setClicked(true);

		if (players == 4) {
			space.get(board.getPos(2)).setColor(Color.RED);
			space.get(board.getPos(2)).setPotential(false);
			space.get(board.getPos(2)).setClicked(true);

			space.get(board.getPos(3)).setColor(Color.CYAN);
			space.get(board.getPos(3)).setPotential(false);
			space.get(board.getPos(3)).setClicked(true);
		}

		// This sets the initial possible moves for player 1
		control.paintPos(0, board, space);

		pane.add(panel);

		// Create 4 JLabels to hold player wall count
		JPanel grid = new JPanel(new GridLayout(5,1));
		grid.setOpaque(false);
		labels[0] = new JLabel("\t\t\t " + board.getWallCount(0) + " walls remain");
		labels[0].setForeground(Color.BLUE);
		grid.add(labels[0]);
		labels[1] = new JLabel("\t\t\t " + board.getWallCount(1) + " walls remain");
		labels[1].setForeground(Color.GREEN);
		grid.add(labels[1]);
		labels[2] = new JLabel("\t\t\t ");
		labels[2].setForeground(Color.RED);
		grid.add(labels[2]);
		labels[3] = new JLabel("\t\t\t ");
		labels[3].setForeground(Color.CYAN);
		// Add the JLabels
		grid.add(labels[3]);

		if (players == 4) {
			labels[2].setText("\t\t\t " + board.getWallCount(2) + " walls remain");
			labels[3].setText("\t\t\t " + board.getWallCount(3) + " walls remain");
		}

		// Create and add a Submit JButton
		submit = new JButton("Submit");
		submit.addMouseListener(this);
		submit.setForeground(Color.WHITE);
		submit.setOpaque(false);
		submit.setBorderPainted(false);
		submit.setOpaque(false);
		submit.setContentAreaFilled(false);
		submit.addActionListener(this);
		grid.add(submit);
		pane.add(grid);
	}

	/** This paint method will draw the background image onto the board */
	@Override
	public void paint(Graphics g){
		g2d = (Graphics2D)g;
		g2d.clearRect(0, 0, getWidth(), getHeight());
		g2d.drawImage(wood,0,0,getWidth(),getHeight(),null);
		super.paint(g2d);
	}

	/** Select a space by passing the current position(int) */
	public void selectSpace(int currentPosition){
		space.get(currentPosition).setClicked(false);
		space.get(currentPosition).setLastSpace(true);
		space.get(Integer.parseInt(button.getId())).setClicked(true);
		space.get(Integer.parseInt(button.getId())).setPotential(false);
		space.get(Integer.parseInt(button.getId())).repaint();
		space.get(currentPosition).repaint();
		submitMove = currentPosition + "";
		placed = button.getId();
	}

	/** Deselect a space by passing the current position(int) */
	public void deselectSpace(int currentPosition){	
		submitMove = null;
		placed = null;
		button = null;
		space.get(currentPosition).setPotential(false);
		space.get(currentPosition).setLastSpace(false);
		space.get(currentPosition).setClicked(true);
		space.get(currentPosition).repaint();
	}

	/** Select a horizontal wall */
	public void selectHWall() {
		placed = inter.getId();
		inter.setWall(1);
		HWall left = inter.getLeftWall();

		// the submit move is the intersect location followed by h
		submitMove = inter.getId() + "h";
		// setting true will make a wall appear
		left.setClicked(true);
		left.repaint();
		HWall right = inter.getRightWall();
		right.setClicked(true);
		right.repaint();
	}

	/** Select a vertical wall */
	public void selectVWall() {
		placed = inter.getId();
		inter.setWall(2);

		// This is how the vertical walls are cleared				
		HWall left = inter.getLeftWall();

		// the submit move is the intersect location followed by h
		submitMove = inter.getId() + "v";

		// paint walls
		left.setClicked(false);
		left.repaint();
		HWall right = inter.getRightWall();
		right.setClicked(false);
		right.repaint();

		// This is how the vertical walls are built
		VWall top = inter.getTopWall();
		top.setClicked(true);
		top.repaint();
		VWall bottom = inter.getBottomWall();
		bottom.setClicked(true);
		bottom.repaint();
	}

	/** Deselect all walls from an Intersect */
	public void deselectWall() {
		placed = null;
		submitMove = null;

		// This is how the vertical walls are cleared
		inter.setWall(0);
		VWall top = inter.getTopWall();
		top.setClicked(false);
		top.repaint();
		VWall bottom = inter.getBottomWall();
		bottom.setClicked(false);
		bottom.repaint();
	}

	/** Submit an HWall */
	public void submitHWall() {
		walls.add(inter.getId()+"h");

		board.setWall(board.getCurrPlayer(), inter.getId() + "h");

		labels[board.getCurrPlayer()].setText("\t\t\t " + board.getWallCount(board.getCurrPlayer()) + " walls remain");
		labels[board.getCurrPlayer()].repaint();

		inter.setBorderPainted(false);
		inter.setOpaque(false);
		inter.setContentAreaFilled(false);

		inter.setClicked(true);
		inter.setEnabled(false);

		submitMove = null;
		placed = null;
		control.iteratePlayers(board, players);

		//Client.sendMove(board.getCurrPlayer(), inter.getId() + "h");
		//Client.requestMove();
	}

	/** Submit an VWall */
	public void submitVWall() {
		walls.add(inter.getId() + "v");

		board.setWall(board.getCurrPlayer(), inter.getId() + "v");

		labels[board.getCurrPlayer()].setText("\t\t\t " + board.getWallCount(board.getCurrPlayer()) + " walls remain");
		labels[board.getCurrPlayer()].repaint();

		inter.setBorderPainted(false);
		inter.setOpaque(false);
		inter.setContentAreaFilled(false);

		inter.setClicked(true);
		inter.setEnabled(false);
		submitMove = null;
		placed = null;
		control.iteratePlayers(board, players);

		//Client.sendMove(board.getCurrPlayer(), inter.getId() + "v");
		//Client.requestMove();
	}

	/** Submit a space */
	public void submitSpace() {
		control.removePos(board.getCurrPlayer(), board, space);
		space.get(currentPosition).setPotential(false);
		space.get(currentPosition).setLastSpace(false);

		space.get(currentPosition).repaint();
		board.setPos(board.getCurrPlayer(), Integer.parseInt(button.getId()));
		space.get(board.getPos(board.getCurrPlayer())).setClicked(true);
		space.get(board.getPos(board.getCurrPlayer())).setColor(board.getPlayer(board.getCurrPlayer()).getColor());

		space.get(board.getPos(board.getCurrPlayer())).repaint();

		submitMove = null;
		placed = null;
		control.iteratePlayers(board, players);

		//Client.sendMove((board.getCurrPlayer()-1), "" + currentPosition);
		//Client.requestMove();
	}
	
	/** Submit an HWall */
	public void updateHWall(int sectNum) {
		System.out.println(sectNum+"h");
		walls.add(sectNum+"h");

		control.removePos(board.getCurrPlayer(), board, space);
		board.setWall(board.getCurrPlayer(), (sectNum) + "h");

		if(sectNum >= 9 && sectNum <= 17) {
			sectNum = sectNum - 1;
		} else if(sectNum >= 18 && sectNum <= 26) {
			sectNum = sectNum - 2;
		} else if(sectNum >= 27 && sectNum <= 35) {
			sectNum = sectNum - 3;
		} else if(sectNum >= 36 && sectNum <= 44) {
			sectNum = sectNum - 4;
		} else if(sectNum >= 45 && sectNum <= 53) {
			sectNum = sectNum - 5;
		} else if(sectNum >= 54 && sectNum <= 62) {
			sectNum = sectNum - 6;
		} else if(sectNum >= 63 && sectNum <= 71) {
			sectNum = sectNum - 7;
		}
		
		labels[board.getCurrPlayer()].setText("\t\t\t " + board.getWallCount(board.getCurrPlayer()) + " walls remain");
		labels[board.getCurrPlayer()].repaint();

		Intersect inter = sect.get(sectNum);
		inter.setBorderPainted(false);
		inter.setOpaque(false);
		inter.setContentAreaFilled(false);
		inter.setClicked(true);
		inter.setEnabled(false);
		inter.repaint();
		
		HWall left = inter.getLeftWall();
		left.setClicked(true);
		left.repaint();
		HWall right = inter.getRightWall();
		right.setClicked(true);
		right.repaint();

		submitMove = null;
		placed = null;
		
		//control.iteratePlayers(board, players);
		//control.paintPos(board.getCurrPlayer(), board, space);
	}
	
	/** Submit an VWall */
	public void updateVWall(int sectNum) {
		System.out.println(sectNum+"v");
		walls.add(sectNum+"v");

		control.removePos(board.getCurrPlayer(), board, space);
		board.setWall(board.getCurrPlayer(), (sectNum) + "v");

		if(sectNum >= 9 && sectNum <= 17) {
			sectNum = sectNum - 1;
		} else if(sectNum >= 18 && sectNum <= 26) {
			sectNum = sectNum - 2;
		} else if(sectNum >= 27 && sectNum <= 35) {
			sectNum = sectNum - 3;
		} else if(sectNum >= 36 && sectNum <= 44) {
			sectNum = sectNum - 4;
		} else if(sectNum >= 45 && sectNum <= 53) {
			sectNum = sectNum - 5;
		} else if(sectNum >= 54 && sectNum <= 62) {
			sectNum = sectNum - 6;
		} else if(sectNum >= 63 && sectNum <= 71) {
			sectNum = sectNum - 7;
		}
		
			
		for (String wal : board.getWalls())
			System.out.println(wal);		

		labels[board.getCurrPlayer()].setText("\t\t\t " + board.getWallCount(board.getCurrPlayer()) + " walls remain");
		labels[board.getCurrPlayer()].repaint();

		Intersect inter = sect.get(sectNum);
		inter.setBorderPainted(false);
		inter.setOpaque(false);
		inter.setContentAreaFilled(false);
		inter.setClicked(true);
		inter.setEnabled(false);
		inter.repaint();
		
		VWall top = inter.getTopWall();
		top.setClicked(true);
		top.repaint();
		VWall bottom = inter.getBottomWall();
		bottom.setClicked(true);
		bottom.repaint();

		submitMove = null;
		placed = null;
		
		//control.iteratePlayers(board, players);
		//control.paintPos(board.getCurrPlayer(), board, space);
	}

	/** Submit a space */
	public void updateSpace(int spaceNum) {
		control.removePos(board.getCurrPlayer(), board, space);
		
		space.get(board.getPos(board.getCurrPlayer())).setClicked(false);
		space.get(board.getPos(board.getCurrPlayer())).setPotential(false);
		space.get(board.getPos(board.getCurrPlayer())).repaint();

		board.setPos(board.getCurrPlayer(), spaceNum);
		space.get(board.getPos(board.getCurrPlayer())).setClicked(true);
		space.get(board.getPos(board.getCurrPlayer())).setColor(board.getPlayer(board.getCurrPlayer()).getColor());

		space.get(board.getPos(board.getCurrPlayer())).repaint();

		submitMove = null;
		placed = null;
		
		control.removePos(board.getCurrPlayer(), board, space);
		//control.iteratePlayers(board, players);
		//control.paintPos(board.getCurrPlayer(), board, space);
	}

	@Override
	/** This actionPerformed method will handle the state on the board */
	public void actionPerformed(ActionEvent e) {
		if (board.getCurrPlayer() == myPlayerNum) {
			// Handle BoardSpace clicking 
			if (e.getSource() instanceof BoardSpace) {
				button = (BoardSpace)e.getSource();
				// setting true will make a player appear, false will make them disappear
				newPosition = Integer.parseInt(button.getId());

				if (board.checkMove(board.getCurrPlayer(), newPosition) || 
						board.getPos(board.getCurrPlayer()) == newPosition) {
					button.setClicked(true);
					currentPosition = board.getPos(board.getCurrPlayer());

					control.removeOldMoves(board.getCurrPlayer(), currentPosition, board, space);
					control.paintPos(board.getCurrPlayer(), board, space);

					if (space.get(currentPosition).equals(space.get(newPosition))) {
						deselectSpace(currentPosition);	
					} else if (placed == null && button.getId() != submitMove) {
						selectSpace(currentPosition);
					} else {
						space.get(currentPosition).setClicked(true);
						space.get(currentPosition).repaint();
					}	
				}
			} else if (e.getSource() instanceof Intersect) { // Handle Intersect clicking
				inter = (Intersect)e.getSource(); 
				if (placed == null){
					// This is how the horizontal walls are built
					if(inter.getWall() == 0) { // handle horizontal wall
						selectHWall();
					} 
				} else {
					if(inter.getWall() == 1) { // handle vertical wall
						selectVWall();
					} else if(inter.getWall() == 2) {
						deselectWall();
					}
				}
			} else if (e.getSource().equals(submit)) { // handle Submit clicking
				if (submitMove != null) {

					if (button != null) { // handle spaces
						currentPosition = board.getPos(board.getCurrPlayer());
						if (!space.get(currentPosition).equals(space.get(newPosition))) {
							submitSpace();

							if (board.victorySoon(board.getCurrPlayer(), board.getPos(board.getCurrPlayer()))) {
								GameEnd end = new GameEnd(board.getPlayerCount(), board.getCurrPlayer(), this);
							} else {
								//control.iteratePlayers(board, players);
								control.paintPos(board.getCurrPlayer(), board, space);
							}
						}
					} else if (inter != null) {	// handle walls		
						System.out.println("got here");

						int curr = board.getPos(board.getCurrPlayer());
						control.removePos(board.getCurrPlayer(), board, space);
						space.get(curr).repaint();

						if (inter.getWall() == 1) { // set horizontal
							submitHWall();
						} else if (inter.getWall() == 2){ // set vertical
							submitVWall();
						}

						//control.iteratePlayers(board, players);
						control.paintPos(board.getCurrPlayer(), board, space);
					}
				}
			}
		}
	}

	/** this inner class will just create an image used as the background for our board */
	class ImagePanel extends JComponent {
		private Image image;

		/** create a ImagePangel by passing an Image */
		public ImagePanel(Image image) {
			this.image = image;
		}

		@Override
		/** This will paint the image it is provided */
		protected void paintComponent(Graphics g) {
			g.drawImage(image, 0, 0, null);
		}
	}

	@Override
	/** this will change the text color on the Submit button to green when the mouse is over the button */
	public void mouseEntered(MouseEvent e) {
		submit.setForeground(Color.GREEN);
	}

	@Override
	/** this will change the text color on the Submit button to white when the mouse is not over the button */
	public void mouseExited(MouseEvent e) {
		submit.setForeground(Color.WHITE);
	}

	@Override
	/** NOT CURRENTLY USED */
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	/** this will change the text color on the Submit button to blue when the mouse is clicking the button */
	public void mousePressed(MouseEvent e) {
		submit.setForeground(Color.BLUE);
	}

	@Override
	/** this will change the text color on the Submit button to green when the mouse is done clicking the button */
	public void mouseReleased(MouseEvent e) {
		submit.setForeground(Color.GREEN);
	}
}