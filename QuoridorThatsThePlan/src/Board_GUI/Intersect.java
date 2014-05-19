package Board_GUI;
/* 
 * name of file: Intersect.java
 * Authors: David Rice
 * Last updated: March 12, 7:27 David Rice
 */ 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;

/** This File will create an Intersect for the Quoridor game.  It will control where a wall can be placed on the board. */
public class Intersect extends JButton {
	private String id; // the name of the intersect
	private int wall = 0; // which state the wall is in

	// list of walls that it controls 
	private VWall top;
	private VWall bottom;
	private HWall left;
	private HWall right;

	private boolean clicked = false; // show or hide the button
	private Color color = Color.lightGray;

	/** Construct an Intersect by passing it 2 Strings, 2 VWalls and 2 HWalls */
	public Intersect(String n, String id, VWall top, VWall bottom, HWall left, HWall right){
		super(n);
		this.id = id;
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		setPreferredSize(new Dimension(8,12));
	}

	/** set if Intersect is visible or not by passing a boolean */
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	/** check if Intersect is visible or not */
	public boolean isClicked() {
		return this.clicked;
	}
	
	@Override
	/** hide or show the intersect */
	public void paintComponent(Graphics g) {
		if (!clicked) {
			super.paintComponent(g);
		} else {
			g.setColor(color);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	/** set what style the wall is(int): 0 = nothing, 1 = horizontal, 2 = vertical */
	public void setWall(int wall) {
		this.wall = wall;
	}
	
	/** get the state of the wall */
	public int getWall(){
		return wall;
	}

	/** get the name of the Intersect */
	public String getId() {
		return id;
	}

	/** set the name of the Intersect by passing a String */
	public void setId(String id) {
		this.id = id;
	}

	/** get the wall above the intersect */
	public VWall getTopWall() {
		return this.top;
	}

	/** get the wall below the intersect */
	public VWall getBottomWall() {
		return this.bottom;
	}

	/** get the wall to the left of the intersect */
	public HWall getLeftWall() {
		return this.left;
	}

	/** get the wall to the right of the intersect */
	public HWall getRightWall() {
		return this.right;
	}
}
