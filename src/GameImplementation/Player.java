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
    private int score;

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

    /** Add up score based on player hand. Ace is 1, face cards are 10, all other values are standard card values */
    protected void tallyScore() {
        for (Card card : hand.cards) {
            if (card.getValue().equalsIgnoreCase("Ace")) {
                score += 1;
            } else if (card.getValue().equalsIgnoreCase("Queen") || card.getValue().equalsIgnoreCase("King") || card.getValue().equalsIgnoreCase("Jack")) {
                score += 10;
            } else {
                score += Integer.parseInt(card.getValue());
            }
        }
    }

    // TODO move automateChooseCard to Game class, and use determineCardValue (card value should be determined by Game, not Player)
    /**
     * Computer AI for determining which card in hand to play on a turn. If multiple options, plays the highest value.
     *
     * @param topCard current card being played on (must match suit or value, 8's are wild)
     * @return int index of chosen card in hand
     */
    protected int automateChooseCard(Card topCard) {
        LinkedList<Card> playableCards = new LinkedList<>();
        for (Card card : hand.cards) {
            if (card.getSuit().equals(topCard.getSuit())) {
                playableCards.add(card);
            } else if (card.getValue().equals(topCard.getValue())) {
                playableCards.add(card);
            } else if (card.getValue().equals("8")) {
                playableCards.add(card);
            }
        }
        if (playableCards.size() > 0) {
            int index = 0;
            int maxValue = 0; // no cards have value 0, so will always go up
            for (int i = 0; i < playableCards.size(); ++i) {
                int value;
                String strValue = playableCards.get(i).getValue();
                // Handle face cards - Ace is 1, other face cards are 10
                switch (strValue) {
                    case "Jack":
                    case "Queen":
                    case "King": value = 10; break;
                    case "Ace": value = 1; break;
                    default: value = Integer.parseInt(playableCards.get(i).getValue()); break;
                }
                if (value > maxValue) {
                    maxValue = value;
                    index = i;
                }
            }
            Card maxValueCard = playableCards.get(index);
            return hand.cards.indexOf(maxValueCard);
        } else return -1; // not a valid index, means no card was playable

    }

}