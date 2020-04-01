package Saboteur;

import boardgame.Move;
import student_player.MyTools;											//testing purposes
import java.util.ArrayList;

/**
 * @author mgrenander
 */
public class RandomSaboteurPlayer extends SaboteurPlayer {
    public RandomSaboteurPlayer() {
        super("RandomPlayer");
    }

    public RandomSaboteurPlayer(String name) {
        super(name);
    }

    @Override
    public Move chooseMove(SaboteurBoardState boardState) {
    	ArrayList<SaboteurMove> returnVal = (ArrayList<SaboteurMove>) MyTools.filterMoves(boardState.getAllLegalMoves()).clone();					//testing purposes
    	System.out.println("returnVal size = " + returnVal.size());
    	for(int i=0; i<returnVal.size(); i++){
    		System.out.println("filtered move: " + returnVal.get(i).toPrettyString());
    	}
    		
    		
    		
        System.out.println("random player acting as player number: "+boardState.getTurnPlayer());
        return  boardState.getRandomMove();
    }
}
