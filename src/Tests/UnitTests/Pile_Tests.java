package Tests.UnitTests;

import org.junit.Test;
import org.junit.Assert;
import GameImplementation.Pile;
import GameImplementation.Card;

import java.util.LinkedList;

public class Pile_Tests {

    @Test
    /**
     * Tests adding and drawing cards
     * Pile.addCard(Card card), Pile.drawTopCard(), testPile.seeTopCard()
     */
    public void testDrawAndSee() {

        // Given
        Pile testPile = new Pile();
        Card testCard1 = new Card("hearts","Ace");
        Card testCard2 = new Card("hearts","2");

        // Tests
        testPile.addCard(testCard1);
        Assert.assertEquals(testPile.seeTopCard(),testCard1); // top card is card 1
        testPile.addCard(testCard2);
        Assert.assertEquals(testPile.drawTopCard(),testCard2); // top card is card 2, card 1 beneath
        Assert.assertEquals(testPile.seeTopCard(),testCard1); // top card should now be card 1

        //

    }

    @Test
    public void testGetCurrentSize() {

        // Given
        Pile testPile = new Pile();
        Card testCard1 = new Card("hearts","Ace");
        Card testCard2 = new Card("hearts","2");

        // Tests
        Assert.assertEquals(testPile.getCurrentSize(),0);

        testPile.addCard(testCard1);
        Assert.assertEquals(testPile.getCurrentSize(),1);
        testPile.addCard(testCard2);
        Assert.assertEquals(testPile.getCurrentSize(),2);

    }

    @Test
    public void testEmpty() {

        // Given
        Pile testPile = new Pile();
        Card testCard1 = new Card("hearts","Ace");
        Card testCard2 = new Card("hearts","2");

        // Adding cards
        testPile.addCard(testCard1);
        testPile.addCard(testCard2);
        testPile.empty();

        // Test
        Assert.assertEquals(testPile.getCurrentSize(),0);

    }

    @Test
    public void testRemoveCard() {

        // Given
        Pile testPile = new Pile();
        Card testCard1 = new Card("hearts","Ace");
        Card testCard2 = new Card("hearts","2");

        // Adding/removing
        testPile.addCard(testCard1);
        testPile.addCard(testCard2);
        testPile.removeCard(testCard2);

        // Tests
        Assert.assertEquals(testPile.seeTopCard(),testCard1);
    }

    @Test
    public void testGetCards() {

        // Given
        Pile testPile = new Pile();
        Card testCard1 = new Card("hearts","Ace");
        Card testCard2 = new Card("hearts","2");
        Card testCard3 = new Card("hearts","3");

        LinkedList<Card> cardList = new LinkedList<>();

        // Adding cards
        testPile.addCard(testCard1);
        testPile.addCard(testCard2);
        testPile.addCard(testCard3);
        cardList.push(testCard1);
        cardList.push(testCard2);
        cardList.push(testCard3);

        // Tests
        Assert.assertEquals(testPile.getCards(),cardList);

    }
}
