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
        // Set player scores to 0
        for (Player player : playerManager.playerList) {
            player.resetIndividualGameScores();
        }
        // Play the game
        while (!isWinner(playerManager.playerList)) {
            takeTurn(playerManager.whoseTurn());
        }
        // Tally scores/points
        for (Player player : playerManager.playerList) {
            tallyScore(player);
            player.updateTotalScores();
        }
    }

    /**
     * Main game method of Crazy Eights. Ends when the player plays a card, but also ends if the player cannnot make
     * a play or draw a card.
     * Choosing a card is handled by the interface, which returns an int index, which is then used to determine which
     * card in a player's hand to play. For the computer, this means we only need to get the index, and can use the
     * same method for computer or human players to actually play the card.
     * @param player player who takes the next turn
     */
    private void takeTurn(Player player) {
        boolean playedCard = false;
        int cardIndex;

        while (!playedCard) {
            if (player.getName().equalsIgnoreCase("Computer")) { // Computer player is always named "Computer"
                if (playerCanPlay(player)) {
                    cardIndex = automateChooseCard(player, discardPile.seeTopCard());
                    Card selectedCard = player.hand.cards.get(cardIndex);
                    player.playCardFromHand(cardIndex,discardPile);
                    player.addPointsPlayedThisGame(determineCardIntValue(selectedCard));
                    player.addCardsPlayedThisGame(1);
                    playedCard = true;
                    if (selectedCard.getValue().equals("8")) {
                        gInterface.displayCrazyEightsMessage();
                        String newSuit = automateChooseSuit(player);
                        System.out.println(player.getName() + " chooses " + selectedCard.getColor(newSuit) + selectedCard.getSuitIcon(newSuit) + selectedCard.getANSI_reset());
                        discardPile.addCard(new Card(newSuit,"8"));
                    }
                } else
                    if (!drawPile.isEmpty()) {
                        player.drawCard(drawPile);
                    } else {
                        System.out.println("Draw pile is empty and " + player.getName() + " has no playable cards - ending turn");
                        playedCard = true;
                    }
            } else { // player is human
                if (!playerCanPlay(player) && drawPile.isEmpty()) {
                    System.out.println("Draw pile is empty and " + player.getName() + " has no playable cards - ending turn");
                    playedCard = true;
                    // break;
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
                                player.addPointsPlayedThisGame(determineCardIntValue(selectedCard));
                                player.addCardsPlayedThisGame(1);
                                if (selectedCard.getValue().equals("8")) {
                                    // Rules state you get to choose whatever suit you want to be the suit the next player has
                                    // to match. Chose to go this way since there's no harm in having extra cards in the
                                    // discard pile, and this is an easy and logical solution that works, and is visible
                                    // to both players
                                    String newSuit = gInterface.chooseSuit(selectedCard);
                                    System.out.println(player.getName() + " chooses " + selectedCard.getColor(newSuit) + selectedCard.getSuitIcon(newSuit) + selectedCard.getANSI_reset());
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
                            // Show top card for reference
                            gInterface.displayTopCard(discardPile);
                            break;
                        case 3:
                            // View top card
                            gInterface.displayTopCard(discardPile);
                    }
                }
            }
        }
        playerManager.incrementTurn();
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
            if (player.hand.isEmpty()) {
                return true;
            }
        }
        // Only get to this point if neither players hands are empty
        if (drawPile.isEmpty()) {
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

    /**
     * Returns integer values for Crazy Eights cards
     * @param card any standard card
     * @return returns value of card as an int. Cards 2-9 have same int values; face cards are 10; aces are 1
     */
    public int determineCardIntValue(Card card) {
        int value;
        // Handle face cards - Ace is 1, other face cards are 10
        switch (card.getValue()) {
            case "Jack":
            case "Queen":
            case "King": value = 10; break;
            case "Ace": value = 1; break;
            default: value = Integer.parseInt(card.getValue()); break;
        }
        return value;
    }

    /**
     * Simple AI for determining which card in hand to play on a turn. If multiple options, plays the highest value.
     *
     * @param topCard current card being played on (must match suit or value, 8's are wild)
     * @return int index of chosen card in hand
     */
    protected int automateChooseCard(Player player, Card topCard) {
        LinkedList<Card> playableCards = new LinkedList<>();
        for (Card card : player.hand.cards) {
            if (isFairCard(card,topCard)) {
                playableCards.add(card);
            }
        }
        if (playableCards.size() > 0) {
            int index = 0;
            int maxValue = 0; // no cards have value 0, so will always go up
            for (int i = 0; i < playableCards.size(); ++i) {
                int value = determineCardIntValue(playableCards.get(i));
                if (value == 8) { value = 1;} // devalue 8's, since they are best when played as the last of a suit
                if (value > maxValue) {
                    maxValue = value;
                    index = i;
                }
            }
            Card maxValueCard = playableCards.get(index);
            return player.hand.cards.indexOf(maxValueCard);
        } else return -1; // not a valid index, means no card was playable
    }

    protected String automateChooseSuit(Player player) {

        // order is Hearts, Spades, Clubs, Diamonds
        int sumPoints[] = new int[4];
        String suits[] = new String[4];
        suits[0] = "Hearts";
        suits[1] = "Spades";
        suits[2] = "Clubs";
        suits[3] = "Diamonds";
        int index = 0;

        for (Card card : player.hand.cards) {
            switch (card.getSuit()) {
                case "Hearts":
                    sumPoints[0] += determineCardIntValue(card);
                    break;
                case "Spades":
                    sumPoints[1] += determineCardIntValue(card);
                    break;
                case "Clubs":
                    sumPoints[2] += determineCardIntValue(card);
                    break;
                case "Diamonds":
                    sumPoints[3] += determineCardIntValue(card);
                    break;
            }
        }

        int max = -1;
        for (int i = 0; i < sumPoints.length; ++i) {
            if (sumPoints[i] > max) {
                max = sumPoints[i];
                index = i;
            }
        }

        return suits[index];

    }

    /**
     * Add up game value of cards in player's hand.
     * @param player player whose hand we are evaluating
     * @return total sum of player's hand card values
     */
    protected void tallyScore(Player player) {
        for (Card card : player.hand.cards) {
            player.scoreThisGame += determineCardIntValue(card);
        }

    }

}
