package GameImplementation;

import java.util.LinkedList;

/**
 * Represents a player in the game. Has a hand of cards and a score.
 * Computer and human players use the same class, since there is really only one method different - the computer
 * player automatically chooses what card it wants to play. Having only one player type also makes the rest of the
 * project a little simpler. Could even let the human player choose to have the game automate their turns if desired.
 *
 * Currently computer players are differentiated by their name, "Computer", which is unavailable as a name for human
 * players.
 */

public class Player {

    protected Pile hand;
    protected String name;
    protected int score;

    // Constructor
    public Player(String name) {
        hand = new Pile();
        this.name = name;
        score = 0;
    }

    public String getName() { return name; }
    public int getScore() { return score; }

    /** Plays a card from hand to the destination pile, prints out notice. No check on if the given card index is
     * valid - this should be checked before running this method. */
    protected void playCardFromHand(int cardIndex, Pile destination) {
        Card card = hand.cards.get(cardIndex);
        destination.addCard(card);
        hand.removeCard(card);
        System.out.println(name + " plays " + card + ". " + hand.getCurrentSize() + " remaining cards in hand");
    }

    /* Get a new card from any pile */
    protected void drawCard(Pile deck) {
        if (!deck.cards.isEmpty()) {
            Card card = deck.drawTopCard();
            hand.cards.add(card);
            if (!name.equals("Computer")) {
                System.out.println(name + " drew " + card);
            } else {
                System.out.println(name + " drew a card");
            }
        } else {
            System.out.println("Cannot draw, draw pile is empty");
        }
    }

}