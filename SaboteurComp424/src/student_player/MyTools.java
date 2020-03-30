package student_player;

import java.util.ArrayList;

import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;
import Saboteur.cardClasses.SaboteurTile;
import boardgame.Move;
import java.lang.Math;

public class MyTools {
    public static double getSomething() {
        return Math.random();
    }
    
    
    // James
    /**
     * This method focuses on finding a good move for the initial phase of the game.
     * @param boardState is the boardState of the current turn
     * @return a move
     */
    public static Move getInitialGameMove(SaboteurBoardState boardState) {	
    	ArrayList<SaboteurMove> ArrLegalMoves = boardState.getAllLegalMoves();
    			
    	int[] chosenObj = targetObjective(boardState);
    	Move chosenMove = pickTheBestHeuristics(ArrLegalMoves, chosenObj);
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
    private static Move pickTheBestHeuristics(ArrayList<SaboteurMove> arrLegalMoves, int[] coordObj) {
    	ArrayList<SaboteurMove> pathMoves = getPathMoves(arrLegalMoves);
    	SaboteurMove bestMove = pathMoves.get(0);
    	
    	// curate the move with the best heuristic
    	for (SaboteurMove curMove : pathMoves) {
    		if (calculatePathLength(curMove, coordObj) < calculatePathLength(bestMove, coordObj)) {
    			bestMove = curMove;
    		}
    	}
    	
    	return bestMove;
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
    	int[][] coordObjs= boardState.hiddenPos;
    	int[] coordObj1 = {coordObjs[0][0], coordObjs[1][0]};
    	int[] coordObj2 = {coordObjs[0][1], coordObjs[1][1]};
    	int[] coordObj3 = {coordObjs[0][2], coordObjs[1][2]};
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
    			potentialCoord.add(new int[] {coordObjs[0][i], coordObjs[1][i]});	
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
    public static ArrayList<SaboteurMove> getPathMoves(ArrayList<SaboteurMove> moves) {
    	ArrayList<SaboteurMove> returnVal = new ArrayList<SaboteurMove>();
    	int moves_size = moves.size();
    	
    	for(int i=0; i < moves_size; i++) {
    		if(moves.get(i).getCardPlayed().getName().matches("Tile(.*)")) {
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
    		if(SaboteurTile.class.isInstance(moves.get(i).getCardPlayed())){
    			if(isDeadEnd((SaboteurTile)moves.get(i).getCardPlayed()) == false){
        			returnVal.add(moves.get(i));
        		}
    		}
    	}
    	return returnVal;
    }
}