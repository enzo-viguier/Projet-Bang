package fr.umontpellier.iut.bang.cards;

/**
 * Représentation des couleurs de poker (pique, cœur, carreau et trèfle)
 */
public enum CardSuit {
    SPADE("♠"),     // pique
    HEART("♥"),     // cœur
    DIAMOND("♦"),   // carreau
    CLUB("♣");      // trèfle

    private final String symbol;

    CardSuit(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return this.symbol;
    }

    public String toJSON() {
        switch (this) {
            case SPADE:
                return "S";
            case HEART:
                return "H";
            case DIAMOND:
                return "D";
            case CLUB:
                return "C";
        }
        return "";
    }
}
