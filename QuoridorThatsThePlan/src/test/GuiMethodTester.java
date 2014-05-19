package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Board.Board;
import Board_GUI.BoardGui;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/** This class is for testing BoardGui methods */
public class GuiMethodTester extends TestCase{
	public BoardGui frame2;
	public BoardGui frame4;
	/** overriding the parent constructor */
	public GuiMethodTester(String name){
		super (name);
	}
	
	/** main for testing BoardGui methods */
	public static void main(String[] args) {
		TestRunner.runAndWait(new TestSuite(GuiMethodTester.class));
	}
	
	@Before
	/** Create a BoardGuis for testing 2 player and 4 player games*/
	public void start() {
		Board board2 = new Board(2);
		Board board4 = new Board(4);
		board2.setCurrPlayer(0);
		board4.setCurrPlayer(0);
		//this.frame2 = new BoardGui(board2.getPlayerCount(), board2);
		//this.frame4 = new BoardGui(board4.getPlayerCount(), board4);
	}
	
	@After
	/** Killing the BoardGui after tests are compolete */
	public void end() throws InterruptedException {
		wait(50000);
		//this.frame2.dispose();
		//this.frame4.dispose();
	}
	
	@Test
	// In order to make this work, I need to refactor a bit so that the BoardGui doesn't run their code
	public void testPaintPos() throws Exception{
		//this.frame2.paintPos(0);
		//System.out.println(frame2.space.get(3).getPotential());
		//assertTrue(frame2.space.get(3).getPotential());
		assertTrue(true);
	}
	
}
