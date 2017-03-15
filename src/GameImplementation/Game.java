package GameImplementation;

import java.util.LinkedList;


public class Game {

    protected GameInterface gInterface;
    protected PlayerManager playerManager;
    protected StandardDeck drawPile;
    protected Pile discardPile;

    // Constructor
    public Game(LinkedList<Player> playerList, GameInterface gInterface) {
        // Initializations
        this.gInterface = gInterface;
        playerManager = new PlayerManager(playerList);
        drawPile = new StandardDeck();
        discardPile = new Pile();

        // Game setup
        drawPile.shuffle();
        deal(playerList);

        // Set top card of discard pile
        discardPile.addCard(drawPile.drawTopCard());
        System.out.println("Starting card is " + discardPile.seeTopCard());
        // drawPile.empty(); // un-comment to quickly test winning before a player's hand is empty
        while ((discardPile.seeTopCard()).getValue().equals("8")) {
            System.out.println("Drawing again");
            discardPile.addCard(drawPile.drawTopCard());
            System.out.println("Starting card is " + discardPile.seeTopCard());
        }
    }

    /**
     * Main loop of the game. takeTurn is where most of the game action is, and this loop alternates players with the
     * help of the player manager. Checks for game ending (if there is a winner, from either player) at each turn.
     * After an individual game has ended, player scores are added up.
     */
    public void playGame() {
        while (!isWinner(playerManager.playerList)) {
            takeTurn(playerManager.whoseTurn());
        }
        for (Player player : playerManager.playerList) {
            player.tallyScore();
        }
    }

    /**
     * Main game method of Crazy Eights. Ends when the player plays a card, but also ends if the player cannnot make
     * a play or draw a card.
     * Choosing a card is handled by the interface, which returns an int index, which is then used to determine which
     * card in a player's hand to play. For the computer, this means we only need to get the index, and can use the
     * same method for computer or human players to actually play the card.
     * @param player
     */
    private void takeTurn(Player player) {
        boolean playedCard = false;
        while (!playedCard) {
            if (!playerCanPlay(player) && drawPile.cards.isEmpty()) {
                // This is inside the loop and not outside, in case a player's hand is unplayable, and all remaining
                // cards in the deck are also unplayable
                System.out.println("Draw pile is empty and " + player.getName() + " has no playable cards - ending turn");
                break;
            }
            if (player.getName().equalsIgnoreCase("Computer")) { // Computer player is always named "Computer"
                int cardIndex = -1;
                while (cardIndex < 0) {
                    cardIndex = player.automateChooseCard(discardPile.seeTopCard());
                    if (cardIndex == -1) { // -1 is default return if no cards are valid, so get new card
                        player.drawCard(drawPile);
                    } else {
                        if (player.hand.cards.get(cardIndex).getValue().equals("8")) {
                            // TODO have computer choose suit when playing an 8
                        }
                        player.playCardFromHand(cardIndex,discardPile);

                        playedCard = true;
                    }
                }
            } else {
                int selection = gInterface.playOrDraw(player);
                switch (selection) {
                    case 1:
                        // Play a card from hand
                        selection = gInterface.chooseFromHand(player);
                        if (selection == -1) break; // 0 selects "Back", but returned int is 1 less
                        Card selectedCard = player.hand.cards.get(selection);
                        if (isFairCard(selectedCard, discardPile.seeTopCard())) {
                            player.playCardFromHand(selection,discardPile);
                            if (selectedCard.getValue().equals("8")) {
                                // Rules state you get to choose whatever suit you want to be the suit the next player has
                                // to match. Chose to go this way since there's no harm in having extra cards in the
                                // discard pile, and this is an easy and logical solution that works, and is visible
                                // to both players
                                String newSuit = gInterface.chooseSuit(selectedCard);
                                System.out.println(player.getName() + " chooses " + newSuit);
                                discardPile.addCard(new Card(newSuit,"8"));
                            }
                            playedCard = true;
                        } else {
                            System.out.println(selectedCard + " is not a valid play on top of " + discardPile.seeTopCard());
                        }
                        break;
                    case 2:
                        // Draw a card
                        player.drawCard(drawPile);
                        break;
                    case 3:
                        // View top card
                        System.out.println("Top card is " + discardPile.seeTopCard());
                }
            }

        }

        ++playerManager.turnCounter;
    }

    /**
     * Checks for a winner in two ways.
     * 1. If either player has an empty hand, they win that round.
     * 2. If the draw pile is empty and neither player can play any of the cards in their hand.
     * @param playerList to cycle through players
     * @return
     */
    private boolean isWinner(LinkedList<Player> playerList) {
        for (Player player : playerList) {
            // Order doesn't matter here, because this method is called before every turn, and only one player can
            // play cards from hand per turn
            if (player.hand.cards.isEmpty()) {
                return true;
            }
        }
        // Only get to this point if neither players hands are empty
        if (drawPile.cards.isEmpty()) {
            // Cycle through players
            for (Player player : playerList) {
                if (playerCanPlay(player)) {
                    return false;
                }
            }
            // Only get to this point if the draw pile is empty and neither player can play anything
            System.out.println("The deck is empty and neither player has any cards they can play.");
            return true;
        }
        return false;
    }

    /**
     * Card is fair to play if the suit or the value matches the top card of the discard pile.
     * 8's are crazy and can be played at any time.
     * @param selectedCard card to check
     * @param topCard card to compare to
     * @return
     */
    protected boolean isFairCard(Card selectedCard, Card topCard) {
        if (selectedCard.getValue().equals("8")) return true;
        else if (topCard.getSuit().equals(selectedCard.getSuit())) return true;
        else if (topCard.getValue().equals(selectedCard.getValue())) return true;
        else return false;
    }

    /** deals 7 cards to each player from the draw pile */
    protected void deal(LinkedList<Player> playerList) {
        for (Player player : playerList) {
            for (int i = 0; i < 7; ++i) {
                player.hand.addCard(drawPile.drawTopCard());
            }
        }
    }

    /** Checks if player's hand has any playable cards */
    protected boolean playerCanPlay(Player player) {
        for (Card card : player.hand.cards) {
            if (isFairCard(card,discardPile.seeTopCard())) {
                return true;
            }
        }
        // Only get here if no cards in hand are playable
        return false;
    }

}
