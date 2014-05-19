package Board_GUI;
/* 
 * name of file: BoardSpace.java
 * Authors: David Rice
 * Last updated: March 12, 7:08 David Rice
 */ 

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** This File will create a BoardSpace for the Quoridor game.  It will represent where a given player is on the board. */
public class BoardSpace extends JButton{
	private String id; // the name of the BoardSpace
	private boolean clicked = false; // is there a player in the space
	private boolean potential = false; // is the space a potential move
	private boolean lastSpace = false; // is the old space
	private Color color; // the color of the player
	private Color lastSpaceColor = Color.DARK_GRAY;

	/** Construct a BoardSpace by passing it 2 Strings and a boolean variable */
	public BoardSpace(String n, String id, boolean clicked){
		super(n);
		this.id = id;
		this.clicked = clicked;
		color = Color.BLUE;
		setForeground(color);
		setBackground(new Color(100,50,50));
		setPreferredSize(new Dimension(22,30));
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
	}

	/** set the color of a player by passing a Color */
	public void setColor(Color color) {
		this.color = color;
	}

	/** set the field to be a player by passing a boolean */
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	/** check if the field is a player */
	public boolean isClicked() {
		return this.clicked;
	}

	/** set the field to be where a player was before a submit by passing a boolean */ 
	public void setLastSpace(boolean lastSpace) {
		this.lastSpace = lastSpace;
	}

	/** check if the field is where a player was before a submit */
	public boolean isLastSpace() {
		return this.lastSpace;
	}

	/** set the field to be a possible by passing a boolean */
	public void setPotential(boolean potential) {
		this.potential = potential;
	}

	/** check if the is a possible */ 
	public boolean getPotential() {
		return this.potential;
	}

	@Override
	/** paint the state of the space */
	public void paintComponent(Graphics g) {
		// handle player's location
		if (!clicked) {
			super.paintComponent(g);
		} else {
			g.setColor(color);
			g.fillOval(3, 3, 19, 19);
		}

		// handle the possible locations
		if (potential) {
			if (color.equals(Color.BLUE)){
				g.setColor(new Color(0f, 0f, 1f, 0.60f));
				g.fillRect(0, 0, getWidth(), getHeight());
			} else if (color.equals(Color.RED)){
				g.setColor(new Color(1f, 0f, 0f, 0.60f));
				g.fillRect(0, 0, getWidth(), getHeight());
			} else if (color.equals(Color.GREEN)){
				g.setColor(new Color(.5f, 1f, .5f, 0.60f));
				g.fillRect(0, 0, getWidth(), getHeight());
			} else if (color.equals(Color.CYAN)){
				g.setColor(new Color(.25f, .25f, .75f, 0.60f));
				g.fillRect(0, 0, getWidth(), getHeight());
			} else if (color.equals(Color.MAGENTA)){
				g.setColor(Color.MAGENTA);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		}
		if (!lastSpace) {
			super.paintComponent(g);
		} else {
			g.setColor(new Color(1f, 0f, 1f, 0.60f));
			g.fillRect(0, 0, getWidth(), getHeight());
		}

	}

	/** get the space's name */
	public String getId() {
		return id;
	}
	
	/** set the space's name by passing a String */
	public void setId(String id) {
		this.id = id;
	}
}
