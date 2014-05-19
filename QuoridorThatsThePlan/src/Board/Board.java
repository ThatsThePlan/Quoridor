/* board.jav - quoridor backend storage
 * Authors: Edward Pryor
 * This is intended to be the computational storage for Quoridor
 * Intended to hold the board state of the game.
 */
package Board;
import java.util.ArrayList;
import java.util.HashMap;

import Players.Players;

/** This is the Back-end of the game, every wall, and move and space is acknowledged by this board, and updated accordingly*/
public class Board {
	//board will consist of spaces, as defined by inner class
	/**
	 * Space: Represents each square of the board; each one holds information regarding possible movements to another space
	 * as well as if there is a pawn occupying the space.
	 */
	class space{
		boolean hasPawn;
		boolean visited; 
		boolean visited2;
		boolean [] canGo = new boolean[4];
		public space(){
			//isWall = false;
			hasPawn = false;
			visited = false;
			visited2 = false;
			canGo[0] = true; // north
			canGo[1] = true; // south
			canGo[2] = true; // west
			canGo[3] = true; // east
		}
	}
	/**wall: Represents a wall on the board. there is a limit of 20 walls on one game board*/
	class wall{
		int[] spacesAffected;
		String name;
		// name stored in the protocol form.
		public wall (String n, int nw, int ne, int sw, int se){
			spacesAffected = new int[4];
			spacesAffected[0] = nw;
			spacesAffected[1] = ne;
			spacesAffected[2] = sw;
			spacesAffected[3] = se;
			name = n;
		}
		public String toString(){
			return name;
		}
	}
	Players [] playerList;
	// player list is set up so that p1 @0, p2 @ 1, p3 @ 2, and p4 @ 3. 
	space playingGrid[];
	int numPlayers;
	private int playerNow;
	ArrayList<wall> wallList= new ArrayList<wall>();
	// for tracking, describe walls as the 4 spaces they modify?
	// every time a wall is placed, add it to the list, and also modify the spaces such that they reflect the no-go status

	public Board (Board b){
		this.playerList = b.playerList;
		this.playingGrid = b.playingGrid;
		this.numPlayers = b.numPlayers;
		this.playerNow = b.playerNow;
		this.wallList = b.wallList;
	}
	public Board (int players) {
		playerList = new Players[players];
		playingGrid = new Board.space[81];
		numPlayers = players;
		playerNow = 0;
		for(int i = 0; i < 81; i++){
			playingGrid[i] = new space();
		}
		for(int i = 0; i < playingGrid.length; i++){
			if(i >=0 && i <=8)
				playingGrid[i].canGo[0] = false;
			if(i >=72 && i <= 80)
				playingGrid[i].canGo[1] = false;
			if(i % 9 == 8)
				playingGrid[i].canGo[3] = false;
			if (i % 9 == 0)
				playingGrid[i].canGo[2] = false;
		}
		for(int i = 0; i < players; i++ )
			// list of players goes 0 - 3
			// location, walls, ect set in construction
			playerList[i] = new Players(i, players);
		if(players == 2){
			//player 1
			//playingGrid[4].hasPawn = true; 
			playingGrid[playerList[0].getPos()].hasPawn = true;
			//player 2
			//playingGrid[76].hasPawn = true;
			playingGrid[playerList[1].getPos()].hasPawn = true;
		}
		else if(players == 4){
			// player 1
			playingGrid[4].hasPawn = true;
			playingGrid[playerList[0].getPos()].hasPawn = true;
			// player 2
			//playingGrid[76].hasPawn = true;
			playingGrid[playerList[1].getPos()].hasPawn = true;
			// player 3
			//playingGrid[36].hasPawn = true;
			playingGrid[playerList[2].getPos()].hasPawn = true;
			// player 4
			//playingGrid[44].hasPawn = true;
			playingGrid[playerList[3].getPos()].hasPawn = true;
		}
		else{
			//System.out.println("Invalid player configuration.");
		}
	}
	//  adding special testing constructor
	// call theis with (int num, int a, int b, int c, int d)
	// the goal of this constructor is to place players in
	// specific places. that way we can test specific interactions better
	// going to modify this to also put walls in place.
	public Board(int playerCount, int a, int b, int c, int d){
		playerList = new Players[playerCount];
		playingGrid = new Board.space[81];
		numPlayers = playerCount;
		playerNow = 0;
		for(int i = 0; i < 81; i++){
			playingGrid[i] = new space();
		}
		for(int i = 0; i < playingGrid.length; i++){
			if(i >=0 && i <=8)
				playingGrid[i].canGo[0] = false;
			else if(i >=72 && i <= 80)
				playingGrid[i].canGo[1] = false;
			else if(i % 9 == 8)
				playingGrid[i].canGo[3] = false;
			else if (i % 9 == 0)
				playingGrid[i].canGo[2] = false;
		}
		for(int i = 0; i < playerCount; i++ )
			// list of players goes 0 - 3
			// location, walls, ect set in construction
			playerList[i] = new Players(i, playerCount);
		if(playerCount == 2){
			//player 1
			// test only 
			if(a >= 0 && a < 81){
				playingGrid[a].hasPawn = true; 
				playerList[0].move(a);
			}
			if(b >=0 && b < 81){
				//player 2
				playingGrid[b].hasPawn = true;
				playerList[1].move(b);
			}
		}
		else if(playerCount == 4){
			// player 1
			if(a >=0 && a < 81){
				playingGrid[a].hasPawn = true;
				playerList[0].move(a);
			}
			// player 2
			if(b >= 0 && b < 81){
				playingGrid[b].hasPawn = true;
				playerList[1].move(b);
			}
			// player 3
			if(c >= 0 && c < 81){
				playingGrid[c].hasPawn = true;
				playerList[2].move(c);
			}
			// player 4
			if(d >= 0 && d < 81){
				playingGrid[d].hasPawn = true;
				playerList[3].move(d);
			}
		}
		// end test constructor.		
	}

	/** checkSpace: Checks to see if the space at index (x) is occupied by a pawn, then returns true if occupied, and false if not.*/
	public boolean checkSpace(int x){
		if(playingGrid[x].hasPawn)
			return true;
		else
			return false;
	}

	/**getPlayer: Returns the Player in the list, based off of the id.*/
	public Players getPlayer(int playerId){

		return playerList[playerId];
	}

	// POSSIBLE SPOT FOR PLAYERid ERROR
	/**getPos: Returns the position of the (playerId), if the player has not been kicked.*/
	public int getPos(int playerId){
		if(!playerList[playerId].getKickStatus())
			return (playerList[playerId].getPos());
		else
			return -1;	
	}

	// POTENTIAL PLAYERID ERROR
	/**setPos: Checks the position to see if a move by a player is legal. if it is, then it will move them there*/
	public void setPos(int playerId, int pos){
		// add move legality check
		// if legal, then move
		if(!playerList[playerId].getKickStatus()){
			if(checkMove(playerId, pos)){
				if(!playerList[playerId].getKickStatus()){
					playingGrid[playerList[playerId].getPos()].hasPawn = false;
					playerList[playerId].move(pos);
					playingGrid[playerList[playerId].getPos()].hasPawn = true;
				}
			}
		}
	}

	/**getPlayerCount: Returns the number of players*/
	public int getPlayerCount(){
		return numPlayers;
	}

	/**getCurrPlayer: Returns the player that is currently being observed*/
	public int getCurrPlayer(){
		return playerNow;
	}

	/**setCurrPlayer: sets the next player to be observed*/
	public void setCurrPlayer(int playerId) {
		playerNow = playerId;
	}

	public int[] getAllPlayerPos(){
		int [] temp = new int[playerList.length];
		for(int i = 0; i < temp.length; i++){
			temp[i] = playerList[i].getPos();
		}
		return temp;
	}

	// POTENTIAL PLAYERID ERROR
	/**getPossible: constructs an array of possible moves if the player has not been kicked. If the player has been kicked, returns null*/
	public int[] getPossibleold(int playerId){
		// THIS IS AN IMPORTANT METHOD	
		// players have some number of spaces around them that are possible destinations
		if(playerList[playerId].getKickStatus()){
			int[] nullReturn = {-1};
			return nullReturn;
		}
		// assuming the player is actually in the game
		else{
			// curent position is the position of the player we wonder about.
			int pos = playerList[playerId].getPos();
			// since we don't know how many possible spaces there will be, use array list
			ArrayList <Integer> possibilities = new ArrayList<Integer>();
			// loop over cardinal directions. 0 = north, 2 = west
			for(int i = 0; i < 4; i++){
				// mark pos as visited so we can ignore it later
				playingGrid[pos].visited2 = true;
				// look for walls in the way
				if(playingGrid[pos].canGo[i]){
					// going north
					// recursive call for if we need to quantum leap
					// only do this if there is a pawn next door and we have not visited that square
					// populate make a new array with the returned possible values
					// dump that into an array list
					if(i == 0){
						// space above pos, north wise
						int newPos = pos-9;

						if(playingGrid[newPos].hasPawn && !playingGrid[newPos].visited2){
							int[] possi = getPossible(playerAt(newPos));
							for(int j = 0; j < possi.length; j++)
								possibilities.add(possi[j]);
							unvisit2();
						}
						// no pawns? this space is where we stop, providing we didnt look here already
						else{
							if(!playingGrid[newPos].visited2)
								possibilities.add(newPos);
						}	
					}
					// same as above, only look south
					else if(i == 1){
						int newPos = pos+9;
						if(playingGrid[newPos].hasPawn && !playingGrid[newPos].visited2){
							int[] possi = getPossible(playerAt(newPos));
							for(int j = 0; j < possi.length; j++)
								possibilities.add(possi[j]);
							unvisit2();
						}
						else{
							if(!playingGrid[newPos].visited2)
								possibilities.add(newPos);
						}	
					}
					// looking west this time
					else if(i == 2){
						int newPos = pos-1;
						if(playingGrid[newPos].hasPawn && !playingGrid[newPos].visited2){
							int[] possi = getPossible(playerAt(newPos));
							for(int j = 0; j < possi.length; j++)
								possibilities.add(possi[j]);
							unvisit2();
						}
						else{
							if(!playingGrid[newPos].visited2)
								possibilities.add(newPos);
						}	
					}
					// looking east
					else if(i == 3){
						int newPos = pos+1;
						if(playingGrid[newPos].hasPawn && !playingGrid[newPos].visited2){
							int[] possi = getPossible(playerAt(newPos));
							for(int j = 0; j < possi.length; j++)
								possibilities.add(possi[j]);
							unvisit2();
						} 
						else{
							if(!playingGrid[newPos].visited2)
								possibilities.add(newPos);
						}	
					}
				}
			}
			// make sure that all spaces marked as unvisited. 
			// dont want to clutter up the board and skew other data
			// make an array, and return it to whoever wanted the possible moves. 
			unvisit2();
			int[] p = new int[possibilities.size()];
			for(int i = 0; i < p.length; i++)
				p[i] = possibilities.get(i);
			return p;
		}
	}
	
	public int[] getPossible (int playerId){
		if(playerList[playerId].getKickStatus()){
			int[] nullReturn = {-1};
			return nullReturn;
		}
		// assuming the player is actually in the game
		else{
			ArrayList<Integer> p = new ArrayList<Integer>();
			int pos = playerList[playerId].getPos();
			// north
			if(playingGrid[pos].canGo[0] ){
				p.add(pos-9);
			}
			// south
			if(playingGrid[pos].canGo[1] ){
				p.add(pos+9);
			}
			// west
			if(playingGrid[pos].canGo[2] ){
				p.add(pos-1);
			}
			// east
			if(playingGrid[pos].canGo[3] ){
				p.add(pos+1);
			}
			// p contains all the possible for this space, now check for other pawns
			for(int i = 0; i < playerList.length; i++){
				int newPos = playerList[i].getPos();
				if(p.contains(newPos)){
					if(playingGrid[newPos].canGo[0] ){
						p.add(newPos-9);
					}
					// south
					if(playingGrid[newPos].canGo[1]){
						p.add(newPos+9);
					}
					// west
					if(playingGrid[newPos].canGo[2] ){
						p.add(newPos-1);
					}
					// east
					if(playingGrid[newPos].canGo[3] ){
						p.add(newPos+1);
					}
				}
			}
			// remove pawn positions
			ArrayList<Integer> noPlayers = new ArrayList<Integer>();
			for(int i = 0; i < p.size(); i ++){
				if(!playingGrid[p.get(i)].hasPawn)
					noPlayers.add(p.get(i));
			}
			int [] ret = new int[noPlayers.size()];
			for(int i = 0; i < noPlayers.size(); i ++){
				ret[i] = noPlayers.get(i);
			}
			return ret;
		}
	}

	// POTENTIAL PLAYERID ERROR
	/**getPossible: constructs an array of possible moves if the player has not been kicked. If the player has been kicked, returns null*/
	public HashMap<String, ArrayList<Integer>> getPossibleHash(int playerId){
		// THIS IS AN IMPORTANT METHOD	
		// players have some number of spaces around them that are possible destinations
		int [] possibleArr = getPossible(playerId);
		HashMap <String, ArrayList<Integer>> possibilities = new HashMap<String, ArrayList<Integer>>();
		ArrayList<Integer> north = new ArrayList<Integer>();
		ArrayList<Integer> south = new ArrayList<Integer>();
		ArrayList<Integer> east = new ArrayList<Integer>();
		ArrayList<Integer> west = new ArrayList<Integer>();
		
		int location = playerList[playerId].getPos();
		for(int i = 0; i < possibleArr.length; i ++){
			int possible = possibleArr[i];
			if(possible < location){
				// north west
				if(possible/9 == location/9){
					west.add(possible);
				}
				else{
					north.add(possible);
				}
			}
			else if(possible > location){
				// south east
				if(possible/9 == location/9){
					east.add(possible);
				}
				else{
					south.add(possible);
				}
			}
		}
		// add arraylists to hashmap
		possibilities.put("N", north);
		possibilities.put("S",  south);
		possibilities.put("W", west);
		possibilities.put("E",  east);
		return possibilities;
	}



	// POTENTIAL PLAYERID ERROR
	/**setWall: Returns a String, and places a wall if legal.*/
	public String setWall(int playerId, String w){
		if(playerList[playerId].getKickStatus())
			return "player "+playerId+" is out";
		else if(playerList[playerId].getWallCount() <= 0)
			return "player "+playerId+" has no walls";
		else{
			boolean canPlace = checkLegal(playerId, w);
			if(canPlace){
				placeWall(w);
				playerList[playerId].wallDec();
				System.out.println("wall "+ w +" placed");
				return "wall "+w+ "placed";

				// wall list updated when placewall called
			}
			else
				return "wall " + w + " is not legal";
		}
	}
	/**getWall: Returns the wall count of the player requested*/
	// POTENTIAL PLAYERID ERROR
	public int getWallCount(int playerId){
		if(playerList[playerId].getKickStatus())
			return -1;
		else
			return playerList[playerId].getWallCount();
	}

	// putting kick function here. making it public for now, but will talk to group about who dose kicking
	// in this form. it is a possible playerId error.
	/**kick: Kicks the requested player, and sets haspawn of the space they were on to false.*/
	public void kick(int playerId){
		int pos = playerList[playerId].getPos();
		playingGrid[pos].hasPawn = false;
		playerList[playerId].makeKicked();
		playerList[playerId].move(-1);
		numPlayers --;
	}

	// returns an array of 20 strings. 
	// each of those strings is the encoded version of the walls
	// i.e. e7h for wall with northwest corner at e7 h or v 
	// invalid or empty walls are shown as z0
	// TEST THIS MUCH
	/** getWalls: Returns an Array of Strings containing the positions of walls*/
	public String[] getWalls(){
		// 20 walls is the total number of walls in any game
		// not all can be in play
		int wallNumber = 0;
		String [] wlist = new String[20];
		int inPlay = wallList.size();
		for(int i = 0; i < inPlay; i++){
			wlist[i] = wallList.get(i).name;
			wallNumber ++;
		}
		for(int i = wallNumber; i < 20; i++){
			wlist[i] = "z0";
		}
		return wlist;

	}
	// this method exists to pass a string representation of the board to the AI
	// each space will be represented by a set of 0s and 1s
	// representing north, south, east and west in that order
	// w = wall in that direction
	// 0 = no wall
	// important note!
	// the sides of the board treat the edges like walls
	// all spaces are separated by a space.
	// northWSouthWWestWEastW northWSouthWWestWEastW#### ... ####
	// need to modify code below in order to make that format happen
	/** getBoardState: Returns a String representation of the board with 'w' representing walls and '0' representing empty space.*/
	public String getBoardState(){
		String boardBuffer = "";
		for(int i = 0; i < playingGrid.length; i++){
			for(int j = 0; j < playingGrid[i].canGo.length; j++){
				if(playingGrid[i].canGo[j])
					boardBuffer += 0;
				else
					boardBuffer += "w";
			}
			boardBuffer+= " ";
		}
		return boardBuffer;
	}
	/** playerAt: Returns the player number of the player that is at the specified position. If there are no players
	 * at the position it returns -1.
	 */
	public int playerAt(int pos){
		int playerYouWant = -1;
		for( int i = 0; i < playerList.length; i++){
			if(playerList[i].getPos() == pos)
				playerYouWant = i;
		}
		return playerYouWant;
	}
	// call this on a player to check that they have won.
	// looks at the array of win positions in the player object
	// compare the current position to the array of win positions. 
	// return true if the player passed in has reached a win square
	// return false if the player passed has not reached a win state
	public boolean playerHasWon(int playerId){
		boolean winFlag = false;
		int [] winList = playerList[playerId].getEnd();
		for(int i = 0; i < winList.length; i++){
			if(playerList[playerId].getPos() == winList[i])
				winFlag = true;
		}
		return winFlag;
	}
	// add method to check to see if a space is in a player's win set.
	public boolean victorySoon(int playerId, int space){
		int [] winSpaces = playerList[playerId].getEnd();
		boolean flag = false;
		for(int i = 0; i < winSpaces.length; i++){
			if(space == winSpaces[i])
				flag = true;
		}
		return flag;
	}

	public boolean legalCheck(int playerID, String m){
		// find if m is a wall or move
		char endVal = m.charAt(m.length()-1);
		if(endVal == 'h' || endVal == 'H' || endVal == 'v' || endVal == 'V')
			return checkLegal(playerID, m);

		else{
			// requires that m be only an int in string form
			int move = 0;
			try{
				move = Integer.parseInt(m);
			}
			catch(Exception NumberFormatException ){
				return false;
			}
			return checkMove(playerID, move);
		}
	}

	// here there be helper functions. all private. 
	// from here on, all player id are form internal function, all coded knowing that
	// players are indexed 0 - 3

	// string w is the location of the northwest corner of the wall and H or V
	private boolean checkLegal(int playerId, String w){
		// rules for quoridor walls:
		// walls must not box a player in
		// walls must not intersect other walls
		// walls must not go off the edge
		// checking boxed in last.
		// first, make sure it is on the board
		char direction = w.charAt(w.length()-1);
		String temp = w.substring(0, w.length()-1);
		int northWestSpace = Integer.parseInt(temp);
		if(northWestSpace  < 0)
			return false;
		if(northWestSpace > 70)
			return false;
		if(direction== 'h' ||direction == 'H'){
			// walls mod 9 == 8 means that a horizontal wall can't fit there
			if(northWestSpace % 9 == 8 ){
				//System.out.println("failed at northwest space");
				return false;
			}
		}
		else if(direction == 'v' || direction == 'V'){
			if(northWestSpace >=72 && northWestSpace <=80){
				//System.out.println("failed at northwest space");
				return false;	
			}
		}
		// done checking that walls don't fall off board
		// don't need to check the other edges
		// walls can't fall off those edges since walls start at northeast space
		// now check that walls dont intersect all catawumpus
		// so walls can't cross through other walls
		// 4 spaces get modified by placing a wall.
		// if walls intersect, they will be modifying the same spaces. 
		int [] wallTouchingSpaces = new int[4];
		wallTouchingSpaces[0] = northWestSpace;
		wallTouchingSpaces[1] = northWestSpace+1;
		wallTouchingSpaces[2] = northWestSpace+9;
		wallTouchingSpaces[3] = northWestSpace+10;
		ArrayList<Integer> newAffected = new ArrayList<Integer>();
		newAffected.add(wallTouchingSpaces[0]);
		newAffected.add(wallTouchingSpaces[1]);
		newAffected.add(wallTouchingSpaces[2]);
		newAffected.add(wallTouchingSpaces[3]);
		for(int i = 0; i < wallList.size(); i ++){
			ArrayList<Integer> oldAffected = new ArrayList<Integer>();
			int[] oldAff = wallList.get(i).spacesAffected;
			for(int j = 0; j< oldAff.length; j++)
				oldAffected.add(oldAff[j]);
			int affectedCount = newAffected.size()+oldAffected.size();
			for(int j = 0; j < newAffected.size(); j++){
				if(oldAffected.contains(newAffected.get(j))){
					affectedCount--;
				}
			}
			if(affectedCount <= 4){
				//System.out.println("Failed wall colision Check" + w);
				return false;
			}
		}

		// that should check for intersecting walls.
		// now to check for boxed in players
		// thinking place walls, then try to find a way out
		// palce the wall
		placeWall(w);
		boolean [] nonBoxedPlayers = new boolean[4];
		for(int i = 0; i < 4; i ++)
			nonBoxedPlayers[i] = checkBoxStat(i);
		int count = 0;
		for(int i = 0; i < 4; i ++){
			if(nonBoxedPlayers[i]){
				count++;
			}
		}
		removeWall(w);
		if(count != numPlayers){
			//System.out.println("failed at # boxed in players" + w);
			//System.out.println(count +" "+numPlayers);
			return false;
		}

		return true;

	}
	private boolean checkBoxStat(int playerID){
		boolean playerCanWin = false;
		//System.out.println(playerID +" "+numPlayers);
		if(playerID >= playerList.length)
			return false;
		if(playerList[playerID].getKickStatus()){
			return playerCanWin;
		}
		else {

			ArrayList<Integer> listPossibles = paths(playerList[playerID].getPos());
			int [] playerPossibles = new int[listPossibles.size()];
			for(int i = 0; i < playerPossibles.length; i ++){
				playerPossibles[i] = listPossibles.get(i);	
			}
			//System.out.println(playerPossibles.length);
			unvisit();
			int [] win = playerList[playerID].getEnd();
			for(int i = 0; i < playerPossibles.length; i++){
				for(int j = 0; j < win.length; j++){
					if(win[j] == playerPossibles[i]){
						playerCanWin = true;
					}
				}
			}
			return playerCanWin;
		}
	}

	public boolean checkMove(int playerId, int newPos){
		// why wouldnt a player be able to move somewhere?
		// 1. it isnt on the board
		// 2. a wall is in the way
		// 3. they are trying to move more than one space at a time
		//		in this case, check for jumps
		// 4. they are moving diagonal
		// i think this whole method can just check the palyer's possible moves.
		// if a move isn't on the possible list, return false.
		int[] possible = getPossible(playerId);
		boolean possibleFlag = false;
		for(int i = 0; i < possible.length; i++){

			if(newPos == possible[i])
				possibleFlag = true;
		}
		return possibleFlag;
	}

	private void placeWall(String w){
		char direction = w.charAt(w.length()-1);
		String temp = w.substring(0, w.length()-1);
		int northWestSpace = Integer.parseInt(temp);
		wall t = new wall(w, northWestSpace, northWestSpace+1, northWestSpace+9, northWestSpace+10);
		wallList.add(t);
		if(direction == 'h' || direction == 'H'){
			playingGrid[northWestSpace].canGo[1] = false;
			playingGrid[northWestSpace+1].canGo[1] = false;
			playingGrid[northWestSpace+9].canGo[0] = false;
			playingGrid[northWestSpace+10].canGo[0] = false;
		}
		else if(direction == 'v' || direction == 'V'){
			playingGrid[northWestSpace].canGo[3] = false;
			playingGrid[northWestSpace+1].canGo[2] = false;
			playingGrid[northWestSpace+9].canGo[3] = false;
			playingGrid[northWestSpace+10].canGo[2] = false;
		}
		//else
			//System.out.println("Somebody tried to set some kind of bad wall");
	}

	private void removeWall(String w){
		char direction = w.charAt(w.length()-1);
		String temp = w.substring(0, w.length()-1);
		int northWestSpace = Integer.parseInt(temp);
		wallList.remove(wallList.size()-1);
		//System.out.println("derpy hooves");
		if(direction == 'h' || direction == 'H'){
			playingGrid[northWestSpace].canGo[1] = true;
			playingGrid[northWestSpace+1].canGo[1] = true;
			playingGrid[northWestSpace+9].canGo[0] = true;
			playingGrid[northWestSpace+10].canGo[0] = true;
		}
		else if(direction == 'v' || direction == 'V'){
			playingGrid[northWestSpace].canGo[3] = true;
			playingGrid[northWestSpace+1].canGo[2] = true;
			playingGrid[northWestSpace+9].canGo[3] = true;
			playingGrid[northWestSpace+10].canGo[2] = true;
		}
		//else
			//System.out.println("Somebody tried to remove some kind of bad wall");
	}
	// need a method to find the possible spaces form a given space
	public int[] possibleFromSpace(int s){
		// scratch that. visit first from calling method, then once that method is done, have it do the unvisit
		//playingGrid[s].visited = true;
		ArrayList<Integer> posSpaces = new ArrayList<Integer>();
		if((s-9)>=0 && (s-9) <=80 && !playingGrid[s-9].visited && playingGrid[s].canGo[0]){
			posSpaces.add(s-9);
		}
		if((s+9)<=80 && (s+9)>=0 && !playingGrid[s+9].visited && playingGrid[s].canGo[1]){
			posSpaces.add(s+9);
		}
		if((s-1)>=0 && (s-1)<=80 && !playingGrid[s-1].visited && playingGrid[s].canGo[2]){
			posSpaces.add(s-1);
		}
		if((s+1)>=0 && (s+1) <=80 && !playingGrid[s+1].visited && playingGrid[s].canGo[3]){
			posSpaces.add(s+1);
		}
		int [] ret = new int[posSpaces.size()];
		for(int i = 0; i< posSpaces.size(); i++){
			ret[i] = posSpaces.get(i);
		}
		return ret;
	}

	public ArrayList<Integer> paths (int space){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(space);
		playingGrid[space].visited = true;
		int [] future = this.possibleFromSpace(space);
		if(future.length ==0){
			return temp;
		}
		else{
			for(int i = 0; i < future.length; i++){
				temp.addAll(this.paths(future[i]));
			}
		}
		//System.out.println(temp.toString());
		return temp;
	}
	
	public String[] pathBlocker(int firstOfThePath, int playerId){
		// treat first of the path as the point with which to block place walls
		// four importatn spaces: f, f-9, f-1, f-10
		String []outBuffer = new String[8];
		
		String f = (firstOfThePath-1) +"H";
		if(checkLegal(playerId, f)){
			outBuffer[0] = f +" ";
		}
		f = (firstOfThePath-1)+"V";
		if(checkLegal(playerId, f)){
			outBuffer[1] = f +" ";
		}
		f = (firstOfThePath)+"H";
		if(checkLegal(playerId, f)){
			outBuffer[2] = f +" ";
		}
		f = (firstOfThePath)+"V";
		if(checkLegal(playerId, f)){
			outBuffer[3] = f +" ";
		}
		f = (firstOfThePath-10)+"H";
		if(checkLegal(playerId, f)){
			outBuffer[4] = f +" ";
		}
		f = (firstOfThePath -10)+"V";
		if(checkLegal(playerId, f)){
			outBuffer[5] = f +" ";
		}
		f = (firstOfThePath -9)+"H";
		if(checkLegal(playerId, f)){
			outBuffer[6] = f +" ";
		}
		f = (firstOfThePath -9)+"V";
		if(checkLegal(playerId, f)){
			outBuffer[7] = f +" ";
		}
		return outBuffer;
	}
	// returns the indexes of the players in the game
	public int[] getPlayersInGame(){
		ArrayList<Integer> p = new ArrayList<Integer>();
		for(int i = 0; i < numPlayers; i++){
			if(!playerList[i].getKickStatus()){
				p.add(i);
			}
		}
		int [] ret  = new int[p.size()];
		for(int i = 0; i < ret.length; i ++){
			ret[i] = p.get(i);
		}
		return ret;
	}
	public int distToWin(int playerId){
		int[] win = playerList[playerId].getEnd();
		int sum = 0;
		for(int i = 0; i < win.length; i ++){
			sum = sum + win[i];
		}
		sum = sum/win.length;
		return playerList[playerId].getPos() - sum;
	}
	private void unvisit(){
		for(int i = 0; i < playingGrid.length; i++)
		{
			playingGrid[i].visited = false;
		}
	}
	private void unvisit2(){
		for(int i = 0; i < playingGrid.length; i++)
		{
			playingGrid[i].visited2 = false;
		}
	}
	public int getSizeOfBoard(){
		return playingGrid.length;
	}
}
