package AI;

import java.util.ArrayList;

import Board.Board;
// the state is what will make up the node "data" of each node in the tree
		// each node will also contin a list that is the next states
		// we will pick a move by traversing the list of possibles in the root
		// then evaluating each of them
		// then sort the root node's list
		// take the move field out of the winner of the sort.
		// represent move made as the chess notation that got there. 
		// write a move translator that can turn an int or wall string into a poropely formatted move

public class State implements Comparable<State>{

	// player pos that define this state
	// wall pos that define this state
	// the move made to get here
	// the weight of this node. dependent on the diferences in lenghts of fastest paths.	
	
	private String moveMade;
	public int weight;
	public int boss;
	public int us;
	int[] movesCanMake;
	String[] wallsCanPlace;
	int[] fastestFirsts;
	/*
	ArrayList<Integer>fastOne;
	ArrayList<Integer>fastTwo;
	ArrayList<Integer>fastThree;
	ArrayList<Integer>fastFour;
	*/
	public Board stateData;
	
	// use movesCanMake, wallsCanPlace to make next states
	// fastest paths is used for picing the elements of wallsCanplace, and for calculating weight.
	// construct a state by giving it array of positions, walls, and the move that got there
	
	public State(Board b, String m, int playerInControl, int u){
		this.stateData = b;
		this.moveMade = m;
		this.boss = playerInControl;
		this.us = u;
		int [] t = stateData.getPlayersInGame();
		/*
		this.fastFour = new ArrayList<Integer>();
		this.fastThree = new ArrayList<Integer>();
		this.fastTwo = new ArrayList<Integer>();
		this.fastOne = new ArrayList<Integer>();
		*/
		Truth thinker = new Truth();
		this.fastestFirsts = new int[t.length];
		for(int i =0; i < t.length; i ++){
			if(i == 0)
				//this.fastOne = thinker.left(i,  stateData);
				fastestFirsts[i] = stateData.getPossible(i)[0];
			if(i == 1)
				//this.fastTwo = thinker.left(i,  stateData);
				fastestFirsts[i] = stateData.getPossible(i)[0];
			if(i == 2)
				//this.fastThree = thinker.left(i,  stateData);
				fastestFirsts[i] = stateData.getPossible(i)[0];
			if(i == 3)
				//this.fastFour = thinker.left(i,  stateData);
				fastestFirsts[i] = stateData.getPossible(i)[0];
			//System.out.println(fastOne);
			
		}
		
		this.movesCanMake = movesOut();
		this.wallsCanPlace = wallsToPlace();
		this.weight = 1;
		//weightCalc();
		
	}
	// get the possible moves out of this state
	public int[] movesOut(){
		int[] outMoves = stateData.getPossible(boss);
		return outMoves;
	}
	public String[] wallsToPlace(){
		// if player has no walls, return empty array;
		// else, get the fastest path for the player not passed
		// then find what walls can be placed along it
		// make a list of those walls and return it
		int [] t = stateData.getPlayersInGame();
		ArrayList<String> temp = new ArrayList<String>();
		for(int i = 0; i < t.length; i ++){
			if(i !=us){
				if(i == 0){
					//String [] wallBuffer = stateData.pathBlocker(fastOne.get(0), 0);
					String [] wallBuffer = stateData.pathBlocker(fastestFirsts[i], 0);
					for(int j = 0; j < wallBuffer.length; j++){
						if(wallBuffer[j] != null)
							temp.add(wallBuffer[j]);
					}
				}
				if(i == 1){
					//String [] wallBuffer = stateData.pathBlocker(fastTwo.get(0), 1);
					String [] wallBuffer = stateData.pathBlocker(fastestFirsts[i], 1);
					for(int j = 0; j < wallBuffer.length; j++){
						if(wallBuffer[j] != null)
							temp.add(wallBuffer[j]);
					}
				}
				if(i == 2){
					//String [] wallBuffer = stateData.pathBlocker(fastThree.get(0), 2);
					String [] wallBuffer = stateData.pathBlocker(fastestFirsts[i], 2);
					for(int j = 0; j < wallBuffer.length; j++){
						if(wallBuffer[j] != null)
							temp.add(wallBuffer[j]);
					}
				}
				if(i == 3){
					//String [] wallBuffer = stateData.pathBlocker(fastFour.get(0), 3);
					String [] wallBuffer = stateData.pathBlocker(fastestFirsts[i], 3);
					for(int j = 0; j < wallBuffer.length; j++){
						if(wallBuffer[j] != null)
							temp.add(wallBuffer[j]);
					}
				}
			}
		}
		String [] doublet = new String[temp.size()];
		for(int i =0; i < temp.size(); i++){
			doublet[i] = temp.get(i);
		}

		return doublet;
	}
		// calculate the weight
	private void weightCalc(){
		/*
		int oneWin = fastOne.size();
		int twoWin = fastTwo.size();
		int threeWin = fastThree.size();
		int fourWin = fastFour.size();
		if(us == 0){
			int minOpDist = Math.min(twoWin, threeWin);
			minOpDist = Math.min(minOpDist, fourWin);
			int usWin = oneWin;
			this.weight  = minOpDist - usWin;
		}
		if(us == 1){
			int minOpDist = Math.min(oneWin, threeWin);
			minOpDist = Math.min(minOpDist, fourWin);
			int usWin = twoWin;
			this.weight  = minOpDist - usWin;
		}
		if(us == 2){
			int minOpDist = Math.min(twoWin, oneWin);
			minOpDist = Math.min(minOpDist, fourWin);
			int usWin = threeWin;
			this.weight  = minOpDist - usWin;
		}
		if(us == 3){
			int minOpDist = Math.min(twoWin, threeWin);
			minOpDist = Math.min(minOpDist, oneWin);
			int usWin = fourWin;
			this.weight  = minOpDist - usWin;
		}
		*/
		if(us != boss){
			this.weight = stateData.distToWin(us) - stateData.distToWin(boss);
		}
		if(us == boss){
			this.weight = stateData.distToWin(us);
		}
		
	}
	public String getMoveMade(){
		return this.moveMade;
	}
	public String[] allTheOut(){
		ArrayList<String> temp= new ArrayList<String>();
		for(int i = 0; i < movesCanMake.length; i ++){
			temp.add(""+movesCanMake[i]);
		}
		for(int i = 0; i < wallsCanPlace.length; i++){
			temp.add(wallsCanPlace[i]);
		}
		String [] t = new String[temp.size()];
		for(int i = 0; i < t.length; i++){
			t[i] = temp.get(i);
		}
		return t;
	}
	@Override
	public int compareTo(State s){
		return (s.weight - this.weight);
	}
	
	public Board getData(){
		return this.stateData;
	}
}

