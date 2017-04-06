package Tests.UnitTests;

import GameImplementation.Card;
import GameImplementation.Pile;
import GameImplementation.StandardDeck;
import org.junit.Test;
import org.junit.Assert;

public class StandardDeck_Tests {

    @Test
    public void testPopulateStandardDeck() {

        // Given
        Pile testPile = new Pile();
        StandardDeck populatedDeck = new StandardDeck();

        // Diamonds
        String diamonds = "Diamonds";
        Card diamondsAce = new Card(diamonds,"Ace");
        Card diamonds2 = new Card(diamonds,"2");
        Card diamonds3 = new Card(diamonds,"3");
        Card diamonds4 = new Card(diamonds,"4");
        Card diamonds5 = new Card(diamonds,"5");
        Card diamonds6 = new Card(diamonds,"6");
        Card diamonds7 = new Card(diamonds,"7");
        Card diamonds8 = new Card(diamonds,"8");
        Card diamonds9 = new Card(diamonds,"9");
        Card diamonds10 = new Card(diamonds,"10");
        Card diamondsJack = new Card(diamonds,"Jack");
        Card diamondsQueen = new Card(diamonds,"Queen");
        Card diamondsKing = new Card(diamonds,"King");

        // Spades
        String spades = "Spades";
        Card spadesAce = new Card(spades,"Ace");
        Card spades2 = new Card(spades,"2");
        Card spades3 = new Card(spades,"3");
        Card spades4 = new Card(spades,"4");
        Card spades5 = new Card(spades,"5");
        Card spades6 = new Card(spades,"6");
        Card spades7 = new Card(spades,"7");
        Card spades8 = new Card(spades,"8");
        Card spades9 = new Card(spades,"9");
        Card spades10 = new Card(spades,"10");
        Card spadesJack = new Card(spades,"Jack");
        Card spadesQueen = new Card(spades,"Queen");
        Card spadesKing = new Card(spades,"King");

        // Hearts
        String hearts = "Hearts";
        Card heartsAce = new Card(hearts,"Ace");
        Card hearts2 = new Card(hearts,"2");
        Card hearts3 = new Card(hearts,"3");
        Card hearts4 = new Card(hearts,"4");
        Card hearts5 = new Card(hearts,"5");
        Card hearts6 = new Card(hearts,"6");
        Card hearts7 = new Card(hearts,"7");
        Card hearts8 = new Card(hearts,"8");
        Card hearts9 = new Card(hearts,"9");
        Card hearts10 = new Card(hearts,"10");
        Card heartsJack = new Card(hearts,"Jack");
        Card heartsQueen = new Card(hearts,"Queen");
        Card heartsKing = new Card(hearts,"King");

        // Clubs
        String clubs = "Clubs";
        Card clubsAce = new Card(clubs,"Ace");
        Card clubs2 = new Card(clubs,"2");
        Card clubs3 = new Card(clubs,"3");
        Card clubs4 = new Card(clubs,"4");
        Card clubs5 = new Card(clubs,"5");
        Card clubs6 = new Card(clubs,"6");
        Card clubs8 = new Card(clubs,"8");
        Card clubs7 = new Card(clubs,"7");
        Card clubs9 = new Card(clubs,"9");
        Card clubs10 = new Card(clubs,"10");
        Card clubsJack = new Card(clubs,"Jack");
        Card clubsQueen = new Card(clubs,"Queen");
        Card clubsKing = new Card(clubs,"King");

        // Diamonds
        testPile.addCard(diamondsAce);
        testPile.addCard(diamonds2);
        testPile.addCard(diamonds3);
        testPile.addCard(diamonds4);
        testPile.addCard(diamonds5);
        testPile.addCard(diamonds6);
        testPile.addCard(diamonds7);
        testPile.addCard(diamonds8);
        testPile.addCard(diamonds9);
        testPile.addCard(diamonds10);
        testPile.addCard(diamondsJack);
        testPile.addCard(diamondsQueen);
        testPile.addCard(diamondsKing);

        // Spades
        testPile.addCard(spadesAce);
        testPile.addCard(spades2);
        testPile.addCard(spades3);
        testPile.addCard(spades4);
        testPile.addCard(spades5);
        testPile.addCard(spades6);
        testPile.addCard(spades7);
        testPile.addCard(spades8);
        testPile.addCard(spades9);
        testPile.addCard(spades10);
        testPile.addCard(spadesJack);
        testPile.addCard(spadesQueen);
        testPile.addCard(spadesKing);

        // Hearts
        testPile.addCard(heartsAce);
        testPile.addCard(hearts2);
        testPile.addCard(hearts3);
        testPile.addCard(hearts4);
        testPile.addCard(hearts5);
        testPile.addCard(hearts6);
        testPile.addCard(hearts7);
        testPile.addCard(hearts8);
        testPile.addCard(hearts9);
        testPile.addCard(hearts10);
        testPile.addCard(heartsJack);
        testPile.addCard(heartsQueen);
        testPile.addCard(heartsKing);

        // Clubs
        testPile.addCard(clubsAce);
        testPile.addCard(clubs2);
        testPile.addCard(clubs3);
        testPile.addCard(clubs4);
        testPile.addCard(clubs5);
        testPile.addCard(clubs6);
        testPile.addCard(clubs7);
        testPile.addCard(clubs8);
        testPile.addCard(clubs9);
        testPile.addCard(clubs10);
        testPile.addCard(clubsJack);
        testPile.addCard(clubsQueen);
        testPile.addCard(clubsKing);

        // Tests
        /*
        These tests fail, but I am not sure why. When manually looking at the data, everything looks the same to me,
        but the Intellij analysis shows that they are the same if white spaces are trimmed or removed. But I don't know
        where these white spaces are coming from.
        Maybe need to look further into LinkedList class to see if there's something I could be missing about
        the add() method

        Update
        Tests now pass. Previously, by using Assert.assertEquals, we were essentially calling Object.equals()
        which will just run a reference check (obj1 == obj2), which will not pass since these are both separate objects.
        The new tests just verify that all the data in the two cards are the same, which is what we really want to
        verify here.

        Help from the following two StackOverflow questions:
        http://stackoverflow.com/questions/16069106/how-to-compare-two-java-objects?noredirect=1&lq=1 (see Daniel Kaplan's answer)
        http://stackoverflow.com/questions/27605714/test-two-instances-of-object-are-equal-junit (for why we did not override the equals() method)
         */


        for (int i = 0; i < testPile.getCurrentSize(); ++i) {
            Card topPile = testPile.drawTopCard();
            Card topStandard = populatedDeck.drawTopCard();

            Assert.assertEquals(topPile.getSuitIcon(),topStandard.getSuitIcon());
            Assert.assertEquals(topPile.getSuit(),topStandard.getSuit());
            Assert.assertEquals(topPile.getValue(),topStandard.getValue());
            Assert.assertEquals(topPile.getANSI_reset(),topStandard.getANSI_reset());

        }
    }
}
