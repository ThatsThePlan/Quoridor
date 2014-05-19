package test;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import AI.DecisionTree;
import AI.State;
//import AI.DecisionTree.Node;
import Board.Board;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;


public class DecisionTreeTester extends TestCase{
	public DecisionTreeTester(String name){
		super (name);
	}
	public static void main(String[] args) {
		TestRunner.runAndWait(new TestSuite(DecisionTreeTester.class));
	}
	@Test
	public void testTreeInitialize() throws Exception{
		Board grid = new Board(2);
		DecisionTree t = new DecisionTree(0, 2);
		t.initialize(grid);
		ArrayList<String> outs = t.outGetter();
		String tn = "";
		for(int i = 0; i< outs.size(); i++){
			tn+= outs.get(i)+ " ";
		}
		System.out.println(t + " " + grid.getPos(0));
	}
	@Test
	public void testPlayerOneFirstMove() throws Exception{
		Board grid = new Board(2);
		assertEquals("13", testPlayerOneFirstMove(grid));
	}
	@Test
	public void testPlayerOneMoveAroundWall() throws Exception{
		Board grid = new Board(2);
		grid.setWall(1, "4H");
		assertEquals("3", testPlayerOneMoveAroundWall(grid));
	}
	@Test
	public void testSequentialMoves() throws Exception{
		Board grid = new Board(2);
		grid.setWall(0, "4H");
		assertEquals("12", testSequentialMoves(grid));
	}
	@Test
	public void testPlayerTwoFail() throws Exception{
		Board grid = new Board(2);
		grid.setPos(0,  13);
		grid.setPos(1,  67);
		DecisionTree n = new DecisionTree(1, 2);
		n.initialize(grid);
		ArrayList<String> outs = n.outGetter();
		String t = "";
		for(int i = 0; i< outs.size(); i++){
			t+= outs.get(i)+ " ";
		}t = t.trim();
		
		assertEquals(t, "58 76 66 68 3H 3V 4H 4V");
	}
	////////////////////
	public String testPlayerOneFirstMove(Board grid){
		DecisionTree t = new DecisionTree(0, 2);
		t.initialize(grid);
		return t.getOptimalMove();
	}
	public String testPlayerOneMoveAroundWall(Board grid){
		DecisionTree t = new DecisionTree(0, 2);
		t.initialize(grid);
		return t.getOptimalMove();
	}
	public String testSequentialMoves(Board grid){
		DecisionTree t = new DecisionTree(0, 2);
		t.initialize(grid);
		ArrayList<String> outs = t.outGetter();
		String a = "";
		for(int i = 0; i< outs.size(); i++){
			a+= outs.get(i)+ " ";
		}
		System.out.println(a);
		String m = t.getOptimalMove();
		grid.legalCheck(0, m);
		m= m.trim();
		char comp =m.charAt(m.length()-1);
		if(comp !='h' && comp !='H' && comp!='v' && comp!='V'){
			int move = Integer.parseInt(m);
			grid.setPos(0, move);
		}
		else{
			grid.setWall(0, m);
		}
		grid.setPos(1,  67);
		t.initialize(grid);
		m = t.getOptimalMove();
		
		return m;
	}
}
