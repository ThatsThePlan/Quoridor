package Board_GUI;
/* 
 * name of file: HWall.java
 * Authors: David Rice
 * Last updated: March 12, 7:16 David Rice
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;

/** This File builds a horizontal wall for the Quoridor game. It will represent a wall on the board. */
public class HWall extends JButton{
	private String id; // the name of the id
	private boolean clicked = false; // is there a player in the space
	private Color color = Color.lightGray; // the color of the player
	
	/** Construct a horizontal wall by passing it 2 Strings and a boolean variable */
	public HWall(String n, String id, boolean clicked){
		super(n);
		this.id = id;
		this.clicked = clicked;
		this.setPreferredSize(new Dimension(20,12));
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setEnabled(false);
	}
	
	/** set if this is a wall by passing it a boolean */
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
	
	/** set the wall id by passing it a String */
	public void setId(String id) {
		this.id = id;
	}
}
