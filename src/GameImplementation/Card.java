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

    /**
     * Returns ASCII code for card suits based on the suit string.
     * Apparently can cause problems if used with non-ascii locales: http://stackoverflow.com/questions/16810663/how-to-use-equalsignorecase-for-multiple-elements-in-java
     * @param suit card suit - valid for clubs, hearts, diamonds, spades. case insensitive
     * @return char code for suit icon, or 0 if not a standard suit
     */
    public char getSuitIcon(String suit) {
        switch(suit.toLowerCase()) {
            case "spades": return 9824;
            case "hearts": return 9828;
            case "diamonds": return 9830;
            case "clubs": return 9827;
            default: return 0; // 0 if using a non-standard suit
        }
    }
    public char getSuitIcon() {
        return this.suitIcon;
    }
    public String getSuit() { return suit; }
    public void setSuit(String suit) {
        this.suit = suit;
        this.suitIcon = getSuitIcon(suit);
    }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getANSI_reset() { return ANSI_reset; }

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
     * Returns the value for ANSI black or ANSI red, depending on this card's suit. If invalid suit, empty string is
     * returned.
     * @return String value of ANSI colors red or black.
     *  Black: \u001B[30m
     *  Red: \u001B[31m
     */
    public String getColor() {
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
    public String getColor(String suit) {
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
