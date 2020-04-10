package student_player;

import boardgame.Move;

import Saboteur.SaboteurPlayer;

import Saboteur.SaboteurBoardState;

/** A player file submitted by a student. */
public class StudentPlayer extends SaboteurPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260802941");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(SaboteurBoardState boardState) {
        // Move myMove = boardState.getRandomMove();
    	Move myMove = MyTools.getInitialGameMove(boardState);
    	
        return myMove;
    }
    
    /*
     * Server: java -cp bin boardgame.Server -p 8123 - - 300000
     * Player (client): java -cp bin boardgame.Client student_player.StudentPlayer
     * Autoplay: java -cp bin autoplay.Autoplay 100
     */
}