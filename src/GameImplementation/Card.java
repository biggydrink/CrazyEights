package GameImplementation;

/**
 * Basic playing card that has a suit and a value, e.x. suit Diamonds and value 9. Value is a string though, since
 * there can be various kinds of cards, such as "Jack" or "Queen".
 *
 * If using regular playing card suits, will also take care of printing these cards in the associated colors of
 * red or black.
 */
public class Card {

    // Card Data
    private char suitIcon;
    private String suit;
    private String value;
    private String ANSI_reset;

    // Constructor
    public Card(String suit, String value) {
        this.suit = suit;
        this.suitIcon = getSuitIcon(suit);
        this.value = value;
        this.ANSI_reset = "\u001B[0m";

    }

    // Getters and setters
    protected char getSuitIcon(String suit) {
        switch(suit) {
            case "Spades": return 9824;
            case "Hearts": return 9828;
            case "Diamonds": return 9830;
            case "Clubs": return 9827;
            default: return 0; // 0 if using a non-standard suit
        }
    }
    protected char getSuitIcon() {
        return this.suitIcon;
    }
    protected String getSuit() { return suit; }
    protected void setSuit(String suit) {
        this.suit = suit;
        this.suitIcon = getSuitIcon(suit);
    }
    protected String getValue() { return value; }
    protected void setValue(String value) { this.value = value; }
    protected String getANSI_reset() { return ANSI_reset; }

    /**
     * Prints card with red/black colors, format "[suit icon][value]"
     * If using an unconventional suit, format is "[value] of [suit]"
     */
    @Override
    public String toString() {
        if (suitIcon == 0) {
            return getColor() + this.value + " of " + this.suit + this.ANSI_reset;
        } else {
            return getColor() + this.suitIcon + this.value + this.ANSI_reset;
        }
    }

    /**
     * Returns the value for ANSI black or ANSI red, depending on the card's suit. If invalid suit, empty string is
     * returned.
     * @return String value of ANSI colors red or black.
     *  Black: \u001B[30m
     *  Red: \u001B[31m
     */
    protected String getColor() {
        String ANSI_red = "\u001B[31m";
        String ANSI_black = "\u001B[30m";

        if (this.suit.equalsIgnoreCase("spades") || this.suit.equalsIgnoreCase("clubs")) {
            return ANSI_black;
        } else if (this.suit.equalsIgnoreCase("hearts") || this.suit.equalsIgnoreCase("diamonds")) {
            return ANSI_red;
        }
        return "";
    }

    /**
     * Overloaded getColor that allows user to specify suit
     * @param suit One of the four card suits - spades, clubs, hearts, or diamonds
     * @return String value of ANSI colors red or black.
     *  Black: \u001B[30m
     *  Red: \u001B[31m
     */
    protected String getColor(String suit) {
        String ANSI_red = "\u001B[31m";
        String ANSI_black = "\u001B[30m";

        if (suit.equalsIgnoreCase("spades") || suit.equalsIgnoreCase("clubs")) {
            return ANSI_black;
        } else if (suit.equalsIgnoreCase("hearts") || suit.equalsIgnoreCase("diamonds")) {
            return ANSI_red;
        }
        return "";
    }
}
