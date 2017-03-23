package GameImplementation;

import java.util.LinkedList;

/** Handles tracking player turns */
public class PlayerManager {

    protected LinkedList<Player> playerList;
    private int turnCounter;

    public PlayerManager(LinkedList<Player> playerList) {
        this.playerList = playerList;
        turnCounter = 0;
    }

    protected int incrementTurn() {
        ++turnCounter;

        return turnCounter;
    }

    /** Manages turns. Alternates between however many players there are */
    protected Player whoseTurn() {
        int currentTurn = turnCounter % playerList.size();

        return playerList.get(currentTurn);
    }

}