package student_player;

import java.util.ArrayList;

import Saboteur.SaboteurMove;
import java.lang.Math;

public class MyTools {
    public static double getSomething() {
        return Math.random();
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
    
}