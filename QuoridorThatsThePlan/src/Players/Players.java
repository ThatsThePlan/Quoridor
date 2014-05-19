package Players;

import java.awt.Color;

//this class is intended to represent the players

public class Players {

		private int playerName;
		private int numWalls;
		private boolean isKicked;
		private Color playerTint;
		// location is a single int now. 
		// board is numbers from 0 to 80. 
		private int location;
		// players want to get to end
		// this is a list of winning spaces
		private int[] end; 
		// if a player wins, this becomes true
		//	probably don't need, but whatever
		private boolean hasWon;
	//	private int [] possibleMoves;
		// format of possible moves: 
		// array of ints
		// pM[0] = North
		// pM[1] = south
		// pM[2] = west
		// pM[3] = east
		
		public Players(int playerId, int numPlayers){
			// player initial possible moves are hard coded in, i think i can make this not so later.
			playerName = playerId;
			numWalls = 20 / numPlayers;
			isKicked = false;
			hasWon = false;
			if(playerId == 0){
				// to test if the possible is working uncomment the following
				//this.location = 14;
				this.location = 4;
				playerTint = Color.BLUE;
				this.end = new int[8];
				int endLoc = 72;
				for(int i = 0; i < 8; i++){
					this.end[i] = endLoc;
					endLoc++;
				}
			/*	this.possibleMoves = new int[4];
				this.possibleMoves[0] = -1;
				this.possibleMoves[1] = 13;
				this.possibleMoves[2] = 3;
				this.possibleMoves[3] = 5;
			*/
			}
			else if(playerId == 1){
				// to test if the possible is working uncomment the following
				//this.location = 15;
				this.location = 76;
				playerTint = Color.GREEN;
				this.end = new int[8];
				int endLoc = 0;
				for(int i = 0; i < 8; i++){
					this.end[i] = endLoc;
					endLoc++;
				}
				/*
				this.possibleMoves = new int[4];
				this.possibleMoves[0] = 67;
				this.possibleMoves[1] = -1;
				this.possibleMoves[2] = 75;
				this.possibleMoves[3] = 77;
				*/
			}
			else if(playerId ==2){
				this.location = 36;
				playerTint = Color.RED;
				this.end = new int[8];
				int endLoc = 8;
				for(int i = 0; i < 8; i++){
					this.end[i] = endLoc;
					endLoc+=9;
				}
				/*
				this.possibleMoves = new int[4];
				this.possibleMoves[0] = 27;
				this.possibleMoves[1] = 45;
				this.possibleMoves[2] = -1;
				this.possibleMoves[3] = 37;
				*/
			}
			else if(playerId == 3){
				this.location = 44;
				playerTint = Color.CYAN;
				this.end = new int[8];
				int endLoc = 0;
				for(int i = 0; i < 8; i++){
					this.end[i] = endLoc;
					endLoc+=9;
				}
				/*
				this.possibleMoves = new int[4];
				this.possibleMoves[0] = 35;
				this.possibleMoves[1] = 53;
				this.possibleMoves[2] = 43;
				this.possibleMoves[3] = -1;
				*/
			}
			else{
				// make some kind of impossible player excetion here
				// impossible astronaut? 
				// eg. player 5, or num players !=2 or 4
				System.out.println("Not vaild player configuration");
			}
		}

		// getPos()
		// args: null
		// returns: int location
		// numeric value representing position on the board
		// the player is there right now.
		public int getPos(){
			return location;
		}
		
		//	getKickedStatus()
		//	args: null
		//	return: boolean isKicked
		//	return this player's status, kicked or playing.
		public boolean getKickStatus(){
			return isKicked;
		}
		
		// getWallCount()
		// args: null
		// returns: int numWalls
		// numeric value of number of walls this player has.
		public int getWallCount(){
			return numWalls;
		}
		
		// getEnd()
		//	args null
		// returns end[]
		// returns the set of win spaces for this player.
		public int[] getEnd(){
			return this.end;
		}
		
		// wallDec()
		// args: null
		// returns ntohing
		// simple method to take one wall away from a player when they place a wall
		public void wallDec(){
			this.numWalls--;
		}
		
		// move()
		// args: int newPos
		// returns: nothing.
		// changes the player's location to newPos
		// legality checked from board's side
		public void move(int newPos){
			this.location = newPos;
		}
		
		// makeKicked();
		// args: null
		// returns: null
		// sets the palyer to kicked.
		// board methods check this before making a move
		public void makeKicked(){
			this.isKicked = true;
		}
		public Color getColor(){
			return playerTint;
		}
}
