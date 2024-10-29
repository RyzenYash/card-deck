import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

// Enum for Suit
enum Suit {
    SPADES, CLUBS, HEARTS, DIAMONDS
}

// Enum for Rank
enum Rank {
    A, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, J, Q, K
}

// Card class
class Card {
    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int getValue() {
        return rank.ordinal(); // A=0, 2=1, ..., K=12
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

// Deck class
class Deck {
    private List<Card> cards;
    private Random random;

    public Deck() {
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        random = new Random();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public List<Card> drawRandomCards(int numberOfCards) {
        List<Card> drawnCards = new ArrayList<>();
        for (int i = 0; i < numberOfCards && !cards.isEmpty(); i++) {
            int index = random.nextInt(cards.size());
            drawnCards.add(cards.remove(index));
        }
        return drawnCards;
    }

    public int size() {
        return cards.size();
    }
}

// Custom Comparator for sorting cards
class CardComparator implements Comparator<Card> {
    @Override
    public int compare(Card card1, Card card2) {
        // Compare by color first (Red: Hearts, Diamonds; Black: Spades, Clubs)
        int colorComparison = getColor(card1).compareTo(getColor(card2));
        if (colorComparison != 0) {
            return colorComparison;
        }
        // Compare by suit
        int suitComparison = card1.getSuit().ordinal() - card2.getSuit().ordinal();
        if (suitComparison != 0) {
            return suitComparison;
        }
        // Compare by rank value
        return card1.getValue() - card2.getValue();
    }

    private Color getColor(Card card) {
        if (card.getSuit() == Suit.HEARTS || card.getSuit() == Suit.DIAMONDS) {
            return Color.RED;
        } else {
            return Color.BLACK;
        }
    }
}

// Enum for Color
enum Color {
    RED, BLACK
}

// Main class to run the simulation
public class CardDeckSimulation {
    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.shuffle();

        List<Card> drawnCards = deck.drawRandomCards(20);
        System.out.println("Drawn Cards:");
        for (Card card : drawnCards) {
            System.out.println(card);
        }

        Collections.sort(drawnCards, new CardComparator());
        System.out.println("\nSorted Cards:");
        for (Card card : drawnCards) {
            System.out.println(card);
        }

        System.out.println("\nRemaining cards in deck: " + deck.size());
    }
}