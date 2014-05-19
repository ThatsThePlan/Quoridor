/*Decision tree
 * Author: Edward Pryor
 * 
 * the goal of this class is to encapsulate all the methods needed to evaluate a board state
 * and find the optimal move out of it.
 */
package AI;
import Board.Board;

import java.util.*;
public class DecisionTree {
	
	private int ourPlayerNum;
	private int totalPlayers;
	class Node implements Comparable<Node>{
		public State data;
		public ArrayList <Node> nextPossibleStates;
	
		public Node(State s){
			this.data = s;
			
			this.nextPossibleStates = new ArrayList<Node>();
		}
		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return this.data.compareTo(o.data);
		}
	}
	public Node root;
	
	public DecisionTree(int o, int t){
		this.ourPlayerNum = o;
		this.totalPlayers = t;
		System.out.println(ourPlayerNum);
	}
	public int getOurNumber(){
		return this.ourPlayerNum;
	}
	public void initialize(Board b){
		root = new Node(new State(b, "create", ourPlayerNum, ourPlayerNum));
		System.out.print("Processing");
		this.populate(12,  root);
		System.out.println("Done.");
		this.evalTopLayer();
	}
	// times is how deep we go in.
	// n is the parent node. preforme moves on the boad in this node's state
	// m[] is the possible moves out of the previous node. parse them and preforme them on n
	// previousBoss is the player who we will be moving around
	// i think times needs to be a multipul of the number of players.
	public void populate(int times, Node n){
		if(times <= 0){
			return;
		}
		else if(n.data == null)
			return;
		else{
			
			// parse out moves and walls
			String [] m = n.data.allTheOut();
			for(int i = 0; i < m.length; i++){
				//System.out.println(n.data.boss+" "+  m[i]+", "+i);
				String moveWorkingOn = m[i];
				moveWorkingOn= moveWorkingOn.trim();
				char comp =moveWorkingOn.charAt(moveWorkingOn.length()-1);
				if(comp !='h' && comp !='H' && comp!='v' && comp!='V'){
					// dealing with a move
					// moves stored in 0-80 format
					int move = Integer.parseInt(moveWorkingOn);
					Board temp = n.data.getData();
					temp.setPos(n.data.boss, move);
					n.nextPossibleStates.add(new Node(new State(temp,move+"", (n.data.boss+1)%totalPlayers, ourPlayerNum)));
				}
				else{
					// dealing with a wall
					// 0-80HV format.
					Board tempBoard = n.data.getData();
					tempBoard.setWall(n.data.boss, moveWorkingOn);
					Node temp = new Node(new State(tempBoard,moveWorkingOn, (n.data.boss+1)%totalPlayers, ourPlayerNum));
					n.nextPossibleStates.add(temp);
				}
			}
			// once the for loop ends, we have processed all the moves passed in.
			// sort them and go deeper.
			Collections.sort(n.nextPossibleStates);
			//System.out.println(n.nextPossibleStates.size()+" moooooooo");
			for(int i = 0; i < n.nextPossibleStates.size();i++){
				//System.out.println(i);
				populate(times-1, n.nextPossibleStates.get(i));
			}
			// just went over the array list at this level and populated the lists further down. 
			// root should now have a full tree under it.
			return;
		}
	}
	// need to evaluate and sort each level, with respect tot eh moves below them.
	public void evalTopLayer(){
		for(int i = 0; i < root.nextPossibleStates.size(); i ++){
			root.nextPossibleStates.get(i).data.weight = evaluate(root.nextPossibleStates.get(i));
		}
		Collections.sort(root.nextPossibleStates);
	}
	private int evaluate(Node r){
		if(r.nextPossibleStates.size() == 0)
			return r.data.weight;
		else
			return r.data.weight +r.nextPossibleStates.get(0).data.weight;
		// when the tree is constructed, each layer's best move is sorted and the best ones are kept at index 0
		// take the best move out of each layer for best move overall.
		
	}
	public String getOptimalMove(){
		Node temp =root.nextPossibleStates.get(0);
		return temp.data.getMoveMade();
	}
	public ArrayList<String> outGetter (){
		ArrayList<String> outs = new ArrayList<String>();
		for(int i = 0; i < root.nextPossibleStates.size(); i++){
			outs.add (root.nextPossibleStates.get(i).data.getMoveMade());

		}
		return outs;
	}
	
}
