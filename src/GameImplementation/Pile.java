package GameImplementation;

import java.util.LinkedList;
import java.util.Random;

/**
 * Basic methods and data for any group of cards. Named "Pile" because games often have various places to put or
 * discard cards, often called "discard pile" or "draw pile", etc. Allows us to work with groups of cards without having
 * to worry about what kind of cards are actually there, or if there are any at all.
 *
 * Uses a stack structure for adding and removing cards, since card games frequently add or remove cards to or from
 * the top of the pile or deck of cards. Also includes the ability to return a random card from within the deck.
 */
public class Pile {

    protected LinkedList<Card> cards;

    // Constructor
    public Pile() {
        cards = new LinkedList<Card>(); // Used as a stack
    }

    public LinkedList<Card> getCards() { return cards; }
    public void removeCard(Card card) { cards.remove(card); }

    /** Gets the current number of cards in the deck/pile */
    public int getCurrentSize() { return cards.size(); }

    /** Draws a random card and removes it from the deck. Returns drawn card. */
    public Card drawRandCard() {
        Random rnd = new Random();

        Card drawnCard = cards.get(rnd.nextInt(getCurrentSize()));
        cards.remove(drawnCard);
        return drawnCard;
    }

    /** Adds card to the top of the deck */
    public void addCard(Card card) {
        cards.push(card); // put on top
    }

    /**
     * Looks at top card of pile (returns the card, but it also remains in the pile)
     * @return card on top of pile (stack), or null if empty
     */
    public Card seeTopCard() {
        return cards.peek();
    }

    /**
     * Draws the top card of the pile (returns the card and removes it from the pile)
     * @return card on top of pile (stack), or null if empty
     */
    public Card drawTopCard() {
        if (this.getCurrentSize() != 0) {
            return cards.pop();
        } else {
            return null;
        }

    }

    /** Removes all cards from pile */
    public LinkedList<Card> empty() {
        LinkedList<Card> allCards;
        allCards = (LinkedList<Card>)cards.clone();
        cards.clear();

        return allCards;
    }

    /** Shuffles deck using current cards */
    public void shuffle() {
        int size = getCurrentSize();

        Card[] cardList = new Card[size];

        for (int i = 0; i < size; ++i) {
            cardList[i] = drawRandCard();
        }

        for (int i = 0; i < size; ++i) {
            addCard(cardList[i]);
        }
    }

}
