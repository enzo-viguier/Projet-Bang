package fr.umontpellier.iut.bang.cards;

public class Volcanic extends WeaponCard {
    public Volcanic(int value, CardSuit suit) {
        super("Volcanic", value, suit);
    }

    @Override
    public int getRange() {
        return 1;
    }
}
