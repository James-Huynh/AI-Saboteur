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
     * @param boardState the boardState of the current game
     * @return a move
     */
    public static Move getInitialGameMove(SaboteurBoardState boardState) {	
    	ArrayList<SaboteurMove> ArrLegalMoves = boardState.getAllLegalMoves();
    	int[][] objectives = boardState.hiddenPos;
    			
    	int[] chosenObj = targetObjective(objectives); // returns the x,y coordinate fo the
    	Move chosenMove = pickTheBestHeuristics(ArrLegalMoves);
    	return chosenMove;
    }
    
    
    /**
     * Given a collection of moves, the methods picks the best move according a heuristic based approach. 
     * In this case, the heuristic is the length of a path to an objective.
     * @param arrLegalMoves an array containing all legal moves from the current player
     * @return a move according to a heuristic
     */
    private static Move pickTheBestHeuristics(ArrayList<SaboteurMove> arrLegalMoves) {
    	//ArrayList<SaboteurMove> pathMove = getPathMoves(arrLegalMoves);
    	
    	return arrLegalMoves.get(0);	// Placeholder
    }
    
    
    /**
     * This methods chooses which objective an AI should prioritize. 
     * The choice is determined by which objective is revealed, and if the gold nugget is revealed.
     * @param objPositions The position of the objectives on the board
     * @return the coordinate of the chosen objective in [x,y] format
     */
    public static int[] targetObjective(int[][] objPositions) {
    	//Case 1: none of the three objectives are revealed
    	//Case 2: golden nugget is revealed
    	//Case 3: one of the objectives is a bust
    	
    	//SaboteurTile[][]
    	

    	return null;
    }
    



}