package student_player;

import java.util.ArrayList;

import Saboteur.SaboteurBoardState;
import Saboteur.SaboteurMove;
import Saboteur.cardClasses.SaboteurTile;
import boardgame.Move;

public class MyTools {
    public static double getSomething() {
        return Math.random();
    }
    
    
    /**
     * This methods focuses on finding a good move for the initial phase of the game.
     * @param boardState the boardState of the current turn
     * @return a move
     */
    public static Move getInitialGameMove(SaboteurBoardState boardState) {	
    	ArrayList<SaboteurMove> ArrLegalMoves = boardState.getAllLegalMoves();
    			
    	int[] chosenObj = targetObjective(boardState); // returns the x,y coordinate of the chosen objective
    	Move chosenMove = pickTheBestHeuristics(ArrLegalMoves, chosenObj);
    	return chosenMove;
    }
    
    
    /**
     * Given a collection of moves, the methods picks the best move according a heuristic based approach. 
     * In this case, the heuristic is the length of a path to an objective.
     * @param arrLegalMoves an array containing all legal moves from the current player
     * @param coordObj the coordinate in an array format of an objective
     * @return a move according to a heuristic
     */
    private static Move pickTheBestHeuristics(ArrayList<SaboteurMove> arrLegalMoves, int[] coordObj) {
    	//ArrayList<SaboteurMove> pathMove = getPathMoves(arrLegalMoves);
    	
    	return arrLegalMoves.get(0);	// Placeholder
    }
    
    
    /**
     * This methods chooses which objective an AI should prioritize. 
     * The choice is determined by which objective is revealed, and if the gold nugget is revealed.
     * @param boardState the boardState of the current turn
     * @return the coordinates of the chosen objective in [x,y] format
     */
    private static int[] targetObjective(SaboteurBoardState boardState) {
    	//Case 1: none of the three objectives are revealed
    	//Case 2: golden nugget is revealed
    	//Case 3: one of the objectives is a bust
    	int i = 0;
    	int[][] coordObjs= boardState.hiddenPos;
    	SaboteurTile[][] hiddenBoard = boardState.getHiddenBoard();
    	int[] coordObj1 = {coordObjs[0][0], coordObjs[1][0]};
    	int[] coordObj2 = {coordObjs[0][1], coordObjs[1][1]};
    	int[] coordObj3 = {coordObjs[0][2], coordObjs[1][2]};
    	SaboteurTile[] objectives = {hiddenBoard[coordObj1[0]][coordObj1[1]], 
    			hiddenBoard[coordObj2[0]][coordObj2[1]], 
    			hiddenBoard[coordObj3[0]][coordObj3[1]]};
    	ArrayList<SaboteurTile> goodObjs = new ArrayList<SaboteurTile>();

    	// check for the nugget
    	for (SaboteurTile tile: objectives) {
    		if (isNugget(tile)) {
    			return new int[] {coordObjs[0][i], coordObjs[1][i]};
    		}
    		i++;
    	}
    	
    	// check for all revealed (not nugget) tiles 
    	i = 0;
    	for (SaboteurTile tile: objectives) {
    		if (!isRevealed(tile)) {
    			goodObjs.add(tile);	
    		}
    		i++;
    	}
    	

    	return null; // @James
    }
    
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
    



}