package GameImplementation;

/** Pile of cards that starts with the standard 52 card list, Ace - King of suits Clubs, Hearts, Diamonds, and Spades */
public class StandardDeck extends Pile {

    // Constructor
    public StandardDeck() {
        populateStandardDeck();
    }

    /**
     * Populates deck with the standard 52 cards of Ace, 2-10, Jack, Queen, and King of Spades, Diamonds, Hearts, and Clubs
     * Using a method rather than putting this in the constructor so that a deck object can be re-populated later
     */
    private void populateStandardDeck() {

        int deckSize = 52; // Standard 52 card deck

        // Remove any cards currently in deck
        while (! cards.isEmpty()) {
            cards.pop();
        }

        // Set suit names
        String[] allSuits = {"Diamonds","Spades","Hearts","Clubs"};

        // Begin adding cards
        for (int i = 0; i < allSuits.length; ++i) {
            String suit = allSuits[i];

            for (int j = 1; j < (deckSize/allSuits.length) + 1; ++j) {
                // Iterate through values
                String value = Integer.toString(j);
                switch (value) {
                    case "1": value = "Ace"; break;
                    case "11": value = "Jack"; break;
                    case "12": value = "Queen"; break;
                    case "13": value = "King"; break;
                }

                // Create new card and add to deck
                Card card = new Card(suit,value);
                addCard(card);
            }

        }
    }

}
