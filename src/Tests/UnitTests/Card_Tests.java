package Tests.UnitTests;

import GameImplementation.Card;
import org.junit.Test;
import org.junit.Assert;

public class Card_Tests {

    @Test
    public void testGetSuits() {

        // Given
        char spadesIcon = 9824;
        String spadesStr = "Spades";
        char heartsIcon = 9828;
        String heartsStr = "Hearts";
        char diamondsIcon = 9830;
        String diamondsStr = "Diamonds";
        char clubsIcon = 9827;
        String clubsStr = "Clubs";
        char bogusIcon = 0;
        String bogusStr = "Bogus";

        Card spadeCard = new Card(spadesStr , "Ace");
        Card heartsCard = new Card(heartsStr, "Ace");
        Card diamondsCard = new Card(diamondsStr, "Ace");
        Card clubsCard = new Card(clubsStr, "Ace");
        Card bogusSuitCard = new Card(bogusStr, "Ace");

        // Tests
        // Icons
        Assert.assertEquals(spadeCard.getSuitIcon(), spadesIcon);
        Assert.assertEquals(heartsCard.getSuitIcon(), heartsIcon);
        Assert.assertEquals(diamondsCard.getSuitIcon(), diamondsIcon);
        Assert.assertEquals(clubsCard.getSuitIcon(), clubsIcon);
        Assert.assertEquals(bogusSuitCard.getSuitIcon(), bogusIcon);

        // Strings
        Assert.assertTrue(spadeCard.getSuit().equals(spadesStr));
        Assert.assertTrue(heartsCard.getSuit().equals(heartsStr));
        Assert.assertTrue(diamondsCard.getSuit().equals(diamondsStr));
        Assert.assertTrue(clubsCard.getSuit().equals(clubsStr));
        Assert.assertTrue(bogusSuitCard.getSuit().equals(bogusStr));

    }

    @Test
    public void testSetSuits() {

        // Given
        Card badCard = new Card("bad suit","Ace");

        String spades = "Spades";
        String hearts = "Hearts";
        String clubs = "Clubs";
        String diamonds = "Diamonds";
        String bogusSuit = "Bogus";

        char spadesIcon = 9824;
        char heartsIcon = 9828;
        char diamondsIcon = 9830;
        char clubsIcon = 9827;
        char bogusIcon = 0;

        // Tests
        badCard.setSuit(spades);
        Assert.assertEquals(badCard.getSuit(),spades);
        Assert.assertEquals(badCard.getSuitIcon(),spadesIcon);

        badCard.setSuit(hearts);
        Assert.assertEquals(badCard.getSuit(),hearts);
        Assert.assertEquals(badCard.getSuitIcon(),heartsIcon);

        badCard.setSuit(diamonds);
        Assert.assertEquals(badCard.getSuit(),diamonds);
        Assert.assertEquals(badCard.getSuitIcon(),diamondsIcon);

        badCard.setSuit(clubs);
        Assert.assertEquals(badCard.getSuit(),clubs);
        Assert.assertEquals(badCard.getSuitIcon(),clubsIcon);

        badCard.setSuit(bogusSuit);
        Assert.assertEquals(badCard.getSuit(),bogusSuit);
        Assert.assertEquals(badCard.getSuitIcon(),bogusIcon);

    }

    @Test
    public void testGetValues() {

        // Given
        String suit = "hearts";
        String value = "Ace";
        Card card = new Card(suit,value);

        // Test
        Assert.assertEquals(value,card.getValue());
    }

    @Test
    public void testSetValues() {

        // Given
        String suit = "hearts";
        String badValue = "";
        String newValue = "Joker";
        Card card = new Card(suit,badValue);

        // Test
        card.setValue(newValue);
        Assert.assertEquals(newValue,card.getValue());
    }

    @Test
    public void testColors() {

        // Given
        String redSuit = "hearts";
        String blackSuit = "spades";
        String redSuit2 = "diamonds";
        String blackSuit2 = "clubs";
        String bogusSuit = "bogus";

        String value = "Ace";

        Card redCard = new Card(redSuit,value);
        Card redCard2 = new Card(redSuit2,value);
        Card blackCard = new Card(blackSuit,value);
        Card blackCard2 = new Card(blackSuit2,value);
        Card bogusSuitCard = new Card(bogusSuit,value);

        String red_ansi = "\u001B[31m";
        String black_ansi = "\u001B[30m";
        String ansi_reset = "\u001B[0m";

        // Tests

        // Get Color()
        // Returns red or black ansi code based on card's suit
        Assert.assertEquals(redCard.getColor(),red_ansi);
        Assert.assertEquals(redCard2.getColor(),red_ansi);
        Assert.assertEquals(blackCard.getColor(),black_ansi);
        Assert.assertEquals(blackCard2.getColor(),black_ansi);

        // Get Color(String suit)
        // Uses card with a non-standard suit, then still returns red or black ansi color based on parameters
        Assert.assertEquals(bogusSuitCard.getColor(redSuit),red_ansi);
        Assert.assertEquals(bogusSuitCard.getColor(redSuit2),red_ansi);
        Assert.assertEquals(bogusSuitCard.getColor(blackSuit),black_ansi);
        Assert.assertEquals(bogusSuitCard.getColor(blackSuit2),black_ansi);

        // Get ansi reset
        // Uses card with a non-standard suit, gets the ansi reset code (all cards should have this)
        Assert.assertEquals(bogusSuitCard.getANSI_reset(),ansi_reset);

    }
}
