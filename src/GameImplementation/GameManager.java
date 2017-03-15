package GameImplementation;

import java.util.LinkedList;

/** Manages series of games and determines eventual winner. Also creates players */
public class GameManager {

    public static void main(String[] args) {

        // Start interface
        GameInterface gInterface = new GameInterface();
        // Initialize players. Human vs AI, AI always named Computer
        LinkedList<Player> playerList = new LinkedList<>();
        Player p1 = new Player(gInterface.getPlayerName());
        Player p2 = new Player("Computer");
        playerList.add(p1);
        playerList.add(p2);


        boolean play = true;
        while (play) {
            Game game = new Game(playerList,gInterface);
            game.playGame();
            System.out.println("Good game!");
            readPlayerScores(playerList);
            System.out.println("Play again?");
            for (Player player : playerList) {
                player.hand.empty();
            }
            play = gInterface.playGame();
        }
        System.out.println("Games finished");
        readPlayerScores(playerList);
        Player winner = findWinner(playerList);
        System.out.println(winner.getName() + " is the winner!");

        GameInterface.scanner.close();

    }

    /** Prints out all player's scores */
    public static void readPlayerScores(LinkedList<Player> playerList) {
        for (Player player : playerList) {
            System.out.println(player.getName() + "'s score is " + player.getScore());
        }
    }

    /** Winner has smallest score */
    public static Player findWinner(LinkedList<Player> playerList) {
        int bestScore = playerList.get(0).getScore();
        Player winner = playerList.get(0);
        for (Player player : playerList) {
            if (player.getScore() < bestScore) {
                winner = player;
            }
        }
        return winner;
    }
}