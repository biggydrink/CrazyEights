package GameImplementation;

import java.util.Scanner;

/** Handles all user inputs for Crazy Eights. */
public class GameInterface {

    protected static Scanner scanner = new Scanner(System.in); // global scanner

    /** User selection to continue playing or not */
    protected boolean playGame() {
        System.out.println("1. Yes");
        System.out.println("2. No");
        int selection = getPositiveIntInput(1,2);

        return (selection == 1); // true for 1, false for 2
    }

    /** Player's choice to play a card from their hand or draw. Can also choose to look at the current top card */
    protected int playOrDraw(Player player) {
        System.out.println(player.getName() + ": Please make a selection");
        System.out.println("1: Play a card from your hand");
        System.out.println("2: Draw a card");
        System.out.println("3: View top card");

        return getPositiveIntInput(1,3);
    }

    /**
     * Displays cards in hand and gets choice from player
     * Displayed list of hand starts with 1, since this is more normal for most people. But this means that we have
     * to return 1 less than the selected number, so that it matches with the actual hand cardlist index.
     * @param player
     * @return
     */
    protected int chooseFromHand(Player player) {
        int size = player.hand.getCurrentSize();
        System.out.println(player.getName() + ": Choose a card from your hand");
        System.out.println("0: Back");
        for (int i = 0; i < size; ++i) {
            System.out.println((i+1) + ": " + player.hand.cards.get(i)); // Shows list of cards starting at 1
        }

        return getPositiveIntInput(0,size)-1; // Subtract 1 to account for list counting from 0
    }

    /**
     * Called when player plays an 8 card. Rules allow player to choose whatever card suit they want to be valid next turn
     * ascii art from https://gist.github.com/staringispolite/e504f207ce11bcc3b618
     */
    protected String chooseSuit(Card card) {
        System.out.println("Crazy Eights!");
        System.out.println("(•_•)");
        System.out.println("( •_•)>⌐■-■");
        System.out.println("(⌐■_■)");
        System.out.println("Choose a new suit:");
        System.out.println("1: " + card.getColor("Clubs") + card.getSuitIcon("Clubs") + card.getANSI_reset() + "(Clubs)");
        System.out.println("2: " + card.getColor("Diamonds") + card.getSuitIcon("Diamonds") + card.getANSI_reset() + "(Diamonds");
        System.out.println("3: " + card.getColor("Spades") + card.getSuitIcon("Spades") + card.getANSI_reset() + "(Spades)");
        System.out.println("4: " + card.getColor("Hearts") + card.getSuitIcon("Hearts") + card.getANSI_reset() + "(Hearts)");
        int selection = getPositiveIntInput(1,4);
        switch (selection) {
            case 1: return "Clubs";
            case 2: return "Diamonds";
            case 3: return "Spades";
            case 4: return "Hearts";
            default: return card.getSuit();
        }
    }

    /** Get a player's name. Can't use "Computer", since that is reserved for the AI player */
    public String getPlayerName() {
        String name = "";
        int answer = 0;
        while (answer != 1) {
            System.out.println("Enter player's name: ");
            name = getStringInput();
            if (name.equalsIgnoreCase("Computer")) { // can't use name "computer"
                System.out.println("Sorry, " + name + " is reserved for your opponent.");
            } else {
                System.out.println("Player's name will be " + name + ". OK?");
                System.out.println("1: Yes");
                System.out.println("2: No");
                answer = getPositiveIntInput(1,2);
            }

        }
        return name;

    }

    /** Get any positive int from user */
    public static int getPositiveIntInput() {
        String userInput;
        int userNumber = -1;

        while (userNumber < 0) {
            try {
                userInput = scanner.nextLine();
                userNumber = Integer.parseInt(userInput);
                if (userNumber < 0) {
                    System.out.println("Please enter a positive number");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a number");
            }
        }

        return userNumber;
    }

    /** Overload of getPositiveIntInput, only allows options between lower and upper bound */
    public static int getPositiveIntInput(int lowerBound, int upperBound) {
        String userInput;
        int userNumber = -1;

        while ((userNumber < 0) || (userNumber < lowerBound || userNumber > upperBound)) {
            try {
                userInput = scanner.nextLine();
                userNumber = Integer.parseInt(userInput);
                if ((userNumber < 0) || (userNumber < lowerBound || userNumber > upperBound)) {
                    System.out.println("Please enter a number between " + lowerBound + " and " + upperBound);
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a number");
            }
        }

        return userNumber;
    }

    /** any string input */
    public static String getStringInput() {
        String userString = scanner.nextLine();
        return userString;
    }
}
