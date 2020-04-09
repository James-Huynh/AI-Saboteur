package student_player;

import java.util.ArrayList;

import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;
import Saboteur.cardClasses.SaboteurCard;
import Saboteur.cardClasses.SaboteurTile;
import boardgame.Move;
import java.lang.Math;
import java.util.Random;

public class MyTools {
    
	// James
    /**
     * This method focuses on finding a good move for the initial phase of the game.
     * @param boardState is the boardState of the current turn
     * @return a move
     */
    public static Move getInitialGameMove(SaboteurBoardState boardState) {	
    	ArrayList<SaboteurMove> ArrLegalMoves = boardState.getAllLegalMoves();	
    	int[] chosenObj = targetObjective(boardState);

    	Move chosenMove = pickTheBestHeuristics(boardState, ArrLegalMoves, chosenObj);
    	
    	if (chosenMove == null) {
    		// What do we do?

    		ArrayList<SaboteurMove> alternateMoves = filterGoodAlternateMoves(boardState);
    		
    		//checking for the case where alternateMoves is empty
    		if(alternateMoves.size() == 0){
    			System.out.println("returning a random move because alternate was empty!");
    			return boardState.getRandomMove();
    		}
    		
    		//generating random number
    		Random rand = new Random();
    		int randInt = rand.nextInt(alternateMoves.size());
    		return alternateMoves.get(randInt);
    	}
    	
    	return chosenMove;
    }
    
    
 	// James
    /**
     * Given a collection of moves, the methods picks the best move according a heuristic based approach. 
     * In this case, the heuristic is the length of a path to an objective.
     * @param arrLegalMoves is an array containing all legal moves from the current player
     * @param coordObj is the coordinates in an array format of an objective
     * @return a move according to a heuristic
     */
    private static Move pickTheBestHeuristics(SaboteurBoardState boardState, ArrayList<SaboteurMove> arrLegalMoves, int[] coordObj) {
    	ArrayList<SaboteurMove> pathMoves = filterMoves(getPathMoves(boardState, arrLegalMoves)); //checking if the pathMoves is empty
    	int curPathLength = 0;
    	
    	if(pathMoves.size()>0) {
    		SaboteurMove bestMove = pathMoves.get(0);
        	
        	// curate the move with the best heuristic
        	for (SaboteurMove curMove : pathMoves) {
        		
        		// check for a winning move
        		curPathLength = calculatePathLength(curMove, coordObj);
        		if (curPathLength == 1) {
        			if (isWinningMove(curMove, coordObj)) {
        				System.out.println("winning move returned");
        				return curMove;
        			}
        		}
        		
        		// compare the current move and the best move so far
        		if (curPathLength < calculatePathLength(bestMove, coordObj)) {
        			bestMove = curMove;
        		}
        	}
   
        	return bestMove;
    	}
    	
    	return null;
    }
    
    
    // James
    /**
     * This methods chooses which objective an AI should prioritize. 
     * The choice is determined by which objective is revealed, and if the gold nugget is revealed.
     * @param boardState is the boardState of the current turn
     * @return the coordinates of the chosen objective in [x,y] format
     */
    private static int[] targetObjective(SaboteurBoardState boardState) {
    	//Case 1: none of the three objectives are revealed
    	//Case 2: golden nugget is revealed
    	//Case 3: one of the objectives is a bust
    	int i = 0;
       	SaboteurTile[][] hiddenBoard = boardState.getHiddenBoard();
    	int[][] coordObjs= SaboteurBoardState.hiddenPos.clone();
    	int[] coordObj1 = {coordObjs[0][0], coordObjs[0][1]};
    	int[] coordObj2 = {coordObjs[1][0], coordObjs[1][1]};
    	int[] coordObj3 = {coordObjs[2][0], coordObjs[2][1]};
    	SaboteurTile[] objectives = {hiddenBoard[coordObj1[0]][coordObj1[1]], 
    			hiddenBoard[coordObj2[0]][coordObj2[1]], 
    			hiddenBoard[coordObj3[0]][coordObj3[1]]};
    	ArrayList<int[]> potentialCoord = new ArrayList<int[]>();
    	int[] bestCoord = new int[2];

    	// check for the nugget
    	for (SaboteurTile tile: objectives) {
    		if (isNugget(tile)) {
    			return (new int[] {coordObjs[0][i], coordObjs[1][i]});
    		}
    		i++;
    	}
    	
    	// check for all revealed (not nugget) tiles 
    	i = 0;
    	for (SaboteurTile tile: objectives) {
    		if (!isRevealed(tile)) {
    			potentialCoord.add(new int[] {coordObjs[i][0], coordObjs[i][1]});	
    		}
    		i++;
    	}
    	 	
    	// pick the best objective out of the remaining ones
    	bestCoord = pickRemainingObj(potentialCoord);
     	return bestCoord;
    }
    
    
    // James
    /**
     * based on an arbitrary condition, pick the best objective
     * @param potentialCoord are the coordinates of the remaining hidden objectives
     * @return coordinates of the best objective
     */
    private static int[] pickRemainingObj(ArrayList<int[]> potentialCoord) {
    	// can be modified for improvement
		return potentialCoord.get(0);
	}


	// James
    /**
     * check if the tile is a nugget (also revealed)
     * @param tile
     * @return boolean
     */
    private static boolean isNugget(SaboteurTile tile) {
    	String idx = tile.getIdx();
    	
    	if (idx  == "nugget") {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    
    // James
    /**
     * check if the tile is revealed (may or not be a nugget)
     * @param tile
     * @return boolean
     */
    private static boolean isRevealed(SaboteurTile tile) {
    	String idx = tile.getIdx();
    	
    	if (idx  == "8") {
    		return false;
    	} else {
    		return true;
    	}
    }
 

    /**
     * @param moves = returned ArrayList of all possible moves from the getAllLegalMoves method
     * @return ArrayList of only tile moves filtered from the input of all possible moves
     */
    public static ArrayList<SaboteurMove> getPathMoves(SaboteurBoardState boardState, ArrayList<SaboteurMove> moves) {
    	ArrayList<SaboteurMove> returnVal = new ArrayList<SaboteurMove>();
    	int moves_size = moves.size();
    	
    	for(int i=0; i < moves_size; i++) {
    		if(moves.get(i).getCardPlayed().getName().matches("Tile(.*)") && !(isFakePath(boardState, moves.get(i)))) {
    			returnVal.add(moves.get(i));
    		}
    	}
    	
    	return returnVal;
    }
    
    
    /**
     * @param move = an instance of SaboteurMove
     * @param objective = the coordinates of the objective
     * @return the sum of delta x and y
     */
    public static int calculatePathLength(SaboteurMove move, int[] objective){
    	int[] val = move.getPosPlayed().clone();
    	int retVal = Math.abs(val[0] - objective[0]) +  Math.abs(val[1] - objective[1]);
    	
    	return retVal;
    }
    
    /**
     * returns true for dead-end tile card and returns false if not 
     * @param tile - an instance of SaboteurTile
     * @return a boolean value corresponding to whether the tile is a dead-end or not
     */
    private static boolean isDeadEnd(SaboteurTile tile){
    	String tileIdx = tile.getIdx();
    	if(tileIdx.equals("1") || tileIdx.equals("2") || tileIdx.equals("2_flip") || tileIdx.equals("3") || tileIdx.equals("3_flip") || tileIdx.equals("4") || tileIdx.equals("4_flip") || tileIdx.equals("11") || tileIdx.equals("11_flip") || tileIdx.equals("12") || tileIdx.equals("12_flip") || tileIdx.equals("13") || tileIdx.equals("13_flip") || tileIdx.equals("14") || tileIdx.equals("14_flip") || tileIdx.equals("15") || tileIdx.equals("15_flip")){
    		return true;
    	}else return false;    	
    }
    
    /**
     * filter out the moves that are using the dead-end cards
     * @param moves - ArrayList of moves
     * @return ArrayList of moves that don't use dead-end cards
     */
    private static ArrayList<SaboteurMove> filterMoves(ArrayList<SaboteurMove> moves){
    	ArrayList<SaboteurMove> returnVal = new ArrayList<SaboteurMove>();
    	for(int i=0; i < moves.size(); i++){
    		if(isDeadEnd((SaboteurTile)moves.get(i).getCardPlayed()) == false){
    			returnVal.add(moves.get(i));
    		}
    	}
    	return returnVal;
    }
    
    

    

    
    // James
    /**
     * Verify if the given move creates a valid path to the specified coordinates 
     * @param move a move
     * @param coordinates the coordinates of a nugget (assumed)
     * @return a boolean
     */
    private static boolean isWinningMove(SaboteurMove move, int[] coordinates) {
    	SaboteurTile cardPlayed = (SaboteurTile) move.getCardPlayed();	// no choice to type cast
    	int[] posPlayed = move.getPosPlayed();
    	int[] targetPos = coordinates;
    	int[][] cardPath = cardPlayed.getPath();
    	char c = '0';
    	
    	if (posPlayed[0] < targetPos[0]) { c = 'n'; }	// north
    	if (posPlayed[0] > targetPos[0]) { c = 's'; }	// south
    	if (posPlayed[1] < targetPos[1]) { c = 'w'; }	// west
    	if (posPlayed[1] > targetPos[1]) { c = 'e'; }	// east

    	switch (c) {
    	
    	case 'n': 
    	if (cardPath[1][0] == 1 && cardPath[1][1] == 1) { 
    		return true;
    	} else {
    		return false;
    	}
    		
    	case 's':
    		if (cardPath[1][2] == 1 && cardPath[1][1] == 1) { 
        		return true;
        	} else {
        		return false;
        	}
    		
    	case 'w':
    		if (cardPath[2][1] == 1 && cardPath[1][1] == 1) { 
        		return true;
        	} else {
        		return false;
        	}
    		
    	case 'e':
    		if (cardPath[0][1] == 1 && cardPath[1][1] == 1) { 
        		return true;
        	} else {
        		return false;
        	}
    	}
    	
    	
    	System.out.println("error: winning case wrong");
    	return false;
    }
    
    
    
    
    /**
     * checks for an existence of a dead end card in the surrounding 8 tiles around the passed saboteurMove's coordinate
     * @param boardState current instance of SaboteurBoardState
     * @param move instance of a SaboteurMove
     * @return true if one of the surrounding tiles is a dead end path
     */
    private static boolean isFakePath(SaboteurBoardState boardState, SaboteurMove move){
    	SaboteurTile[][] board =  boardState.getHiddenBoard().clone();
    	
    	int[] position = move.getPosPlayed().clone();
        int x = position[0];
        int y = position[1];
        boolean retVal = false;
        int i = x;
        int j = y+1;
        if(i<14 && i>=0 && j<14 && j>=0 && board[i][j] != null){
        	String tileIdx = board[i][j].getIdx();
			if(tileIdx.equals("1") || tileIdx.equals("2") || tileIdx.equals("2_flip") || tileIdx.equals("3") || tileIdx.equals("3_flip") || tileIdx.equals("4") || tileIdx.equals("4_flip") || tileIdx.equals("11") || tileIdx.equals("11_flip") || tileIdx.equals("12") || tileIdx.equals("12_flip") || tileIdx.equals("13") || tileIdx.equals("13_flip") || tileIdx.equals("14") || tileIdx.equals("14_flip") || tileIdx.equals("15") || tileIdx.equals("15_flip")){
				retVal = true;
	    	}
        }
        i=x+1;
        j=y;
        if(i<14 && i>=0 && j<14 && j>=0 && board[i][j] != null){
        	String tileIdx = board[i][j].getIdx();
			if(tileIdx.equals("1") || tileIdx.equals("2") || tileIdx.equals("2_flip") || tileIdx.equals("3") || tileIdx.equals("3_flip") || tileIdx.equals("4") || tileIdx.equals("4_flip") || tileIdx.equals("11") || tileIdx.equals("11_flip") || tileIdx.equals("12") || tileIdx.equals("12_flip") || tileIdx.equals("13") || tileIdx.equals("13_flip") || tileIdx.equals("14") || tileIdx.equals("14_flip") || tileIdx.equals("15") || tileIdx.equals("15_flip")){
				retVal = true;
	    	}
        }
       i=x;
       j=y-1;
       if(i<14 && i>=0 && j<14 && j>=0 && board[i][j] != null){
       	String tileIdx = board[i][j].getIdx();
			if(tileIdx.equals("1") || tileIdx.equals("2") || tileIdx.equals("2_flip") || tileIdx.equals("3") || tileIdx.equals("3_flip") || tileIdx.equals("4") || tileIdx.equals("4_flip") || tileIdx.equals("11") || tileIdx.equals("11_flip") || tileIdx.equals("12") || tileIdx.equals("12_flip") || tileIdx.equals("13") || tileIdx.equals("13_flip") || tileIdx.equals("14") || tileIdx.equals("14_flip") || tileIdx.equals("15") || tileIdx.equals("15_flip")){
				retVal = true;
	    	}
       }
       i=x-1;
       j=y;
       if(i<14 && i>=0 && j<14 && j>=0 && board[i][j] != null){
          	String tileIdx = board[i][j].getIdx();
   			if(tileIdx.equals("1") || tileIdx.equals("2") || tileIdx.equals("2_flip") || tileIdx.equals("3") || tileIdx.equals("3_flip") || tileIdx.equals("4") || tileIdx.equals("4_flip") || tileIdx.equals("11") || tileIdx.equals("11_flip") || tileIdx.equals("12") || tileIdx.equals("12_flip") || tileIdx.equals("13") || tileIdx.equals("13_flip") || tileIdx.equals("14") || tileIdx.equals("14_flip") || tileIdx.equals("15") || tileIdx.equals("15_flip")){
   				retVal = true;
   	    	}
       }
    	return retVal;
    }
    
    

    /** filters out good alternate moves in the worst case scenario
     * @param boardState
     * @return arraylist of possible alternate moves
     */
    private static ArrayList<SaboteurMove> filterGoodAlternateMoves(SaboteurBoardState boardState){
    	ArrayList<SaboteurMove> legalMoves = boardState.getAllLegalMoves();	
    	ArrayList<SaboteurCard> playerHand = boardState.getCurrentPlayerCards();
    	ArrayList<SaboteurMove> retVal = new ArrayList<SaboteurMove>();
    	
    	//tester printer for playerHand
    	for(int i=0; i<playerHand.size(); i++){
    		System.out.println("hand index: " + i + " Card: " + playerHand.get(i).getName());
    	}
    	
    	//adding drop moves with deadend tiles
    	//assumption: the player hand index matches
    	for(int i=0; i<legalMoves.size(); i++){
    		int[] dropIndex = legalMoves.get(i).getPosPlayed(); 
    		if(legalMoves.get(i).getCardPlayed().getName().matches("Drop")){
    			System.out.println("Drop move: " + legalMoves.get(i).getPosPlayed()[0]);
    			if(playerHand.get(dropIndex[0]) instanceof SaboteurTile){
    				if(isDeadEnd((SaboteurTile)playerHand.get(dropIndex[0]))){
        				retVal.add(legalMoves.get(i));
        			}
    			}
    		}
    	}
    	
    	//adding Malus moves
    	for(int i=0; i<legalMoves.size(); i++){
    		if(legalMoves.get(i).getCardPlayed().getName().matches("Malus")){
    			retVal.add(legalMoves.get(i));
    		}
    	}
    	
    	//test printer
    	System.out.println("filterGoodAlternate retVal size: " + retVal.size());
    	for(int i=0; i<retVal.size(); i++){
    		System.out.println(retVal.get(i).getCardPlayed().getName() + " " + retVal.get(i).getPosPlayed()[0]);
    	}
    	
    	return retVal;
    }
    
    
}

