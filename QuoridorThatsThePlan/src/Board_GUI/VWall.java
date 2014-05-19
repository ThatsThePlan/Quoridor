package Board_GUI;
/* 
 * name of file: VWall.java
 * Authors: David Rice
 * Last updated: March 12, 7:25 David Rice
 */ 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;

/** This File builds a vertical wall for the Quoridor game. It will represent a wall on the board */
public class VWall extends JButton {
	private String id; // the name of the id
	private boolean clicked = false; // is there a player in the space
	private Color color = Color.lightGray; // the color of the player
	
	/** Construct a vertical wall by passing it 2 Strings and a boolean variable */
	public VWall(String n, String id, boolean clicked){
		super(n);
		this.id = id;
		this.clicked = clicked;
		setPreferredSize(new Dimension(10,30));
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setEnabled(false);
	}

	/** set the field to be a wall by passing a boolean */
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	/** check if the wall is set */
	public boolean isClicked() {
		return this.clicked;
	}

	@Override
	/** paint or remove paint from the wall */
	public void paintComponent(Graphics g) {
		if (!clicked) {
			super.paintComponent(g);
		} else {
			g.setColor(color);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	/** get the wall id */
	public String getId() {
		return id;
	}

	/** set the wall id */
	public void setId(String id) {
		this.id = id;
	}
}
