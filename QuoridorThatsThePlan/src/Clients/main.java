package Clients;
/*
 * main.java
 * Authors: David Rice
 */ 

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import Board_GUI.BoardGui;
import Board_GUI.GameEnd;
import Board.Board;

/** This is the main for the Quoridor game */
public class main {

	/** @author David Rice This is the main for the Quoridor game */
	public static void main (String [] args) throws AWTException {

		Board board = new Board(2);

		board.setCurrPlayer(0);

		BoardGui frame = new BoardGui(board.getPlayerCount(), board.getCurrPlayer(), board);
		frame.setSize(700,410);
		frame.setResizable( false );
		frame.setVisible(true);

		/*
		Robot bot = new Robot();

		// Rest for a little while 
		try{Thread.sleep(900,000);}catch(InterruptedException e){}
		clickSpace(frame, bot, board, 3);

		clickHWall(frame, bot, board, 17);

		clickVWall(frame, bot, board, 10);
		*/
	}

	public static void clickSpace(BoardGui frame, Robot bot, Board board, int space){
		int xButtonOffset = 16;
		int yButtonOffset = 63;
		// Rest for a little while 
		try{Thread.sleep(900,000);}catch(InterruptedException e){}
		// Spaces
		bot.mouseMove(frame.space.get(space).getX()+xButtonOffset,
				frame.space.get(space).getY()+yButtonOffset);

		bot.mousePress(InputEvent.BUTTON1_MASK);
		//add time between press and release or the input event system may 
		//not think it is a click
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mouseRelease(InputEvent.BUTTON1_MASK);

		frame.control.iteratePlayers(board, board.getPlayerCount());
	}

	public static void clickHWall(BoardGui frame, Robot bot, Board board, int sectNum){
		int xInstersectOffset = 7;
		int yInstersectOffset = 51;
		// Rest for a little while 
		try{Thread.sleep(900,000);}catch(InterruptedException e){}

		// Intersects
		bot.mouseMove(frame.sect.get(sectNum).getX()+xInstersectOffset,
				frame.sect.get(sectNum).getY()+yInstersectOffset);

		bot.mousePress(InputEvent.BUTTON1_MASK);
		//add time between press and release or the input event system may 
		//not think it is a click
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mouseRelease(InputEvent.BUTTON1_MASK);

		try{Thread.sleep(250);}catch(InterruptedException e){}
		frame.submitHWall();

		frame.labels[board.getCurrPlayer()].setText("\t\t\t " + board.getWallCount(board.getCurrPlayer()) + " walls remain");
		frame.labels[board.getCurrPlayer()].repaint();

		frame.control.iteratePlayers(board, board.getPlayerCount());
	}

	public static void clickVWall(BoardGui frame, Robot bot, Board board, int sectNum){
		int xInstersectOffset = 7;
		int yInstersectOffset = 51;
		// Rest for a little while 
		try{Thread.sleep(900,000);}catch(InterruptedException e){}

		// Intersects
		bot.mouseMove(frame.sect.get(sectNum).getX()+xInstersectOffset,
				frame.sect.get(sectNum).getY()+yInstersectOffset);

		bot.mousePress(InputEvent.BUTTON1_MASK);
		//add time between press and release or the input event system may 
		//not think it is a click
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mouseRelease(InputEvent.BUTTON1_MASK);

		// Rest for a little while 
		try{Thread.sleep(300,000);}catch(InterruptedException e){}

		// Intersects
		bot.mouseMove(frame.sect.get(sectNum).getX()+xInstersectOffset,
				frame.sect.get(sectNum).getY()+yInstersectOffset);

		bot.mousePress(InputEvent.BUTTON1_MASK);
		//add time between press and release or the input event system may 
		//not think it is a click
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mouseRelease(InputEvent.BUTTON1_MASK);

		try{Thread.sleep(250);}catch(InterruptedException e){}
		frame.submitVWall();

		frame.labels[board.getCurrPlayer()].setText("\t\t\t " + board.getWallCount(board.getCurrPlayer()) + " walls remain");
		frame.labels[board.getCurrPlayer()].repaint();

		frame.control.iteratePlayers(board, board.getPlayerCount());
	}
}

/*
 * Possible fix for the board issues,
 * 		after each move, have a method go through and recheck each space and wall
 * 		to repaint things, this could help fix some of the issues.
 * 		This technique should also help things like walls, to force them to know 
 * 		what their syblings are doing, so they know if they should exist or not. 
 */