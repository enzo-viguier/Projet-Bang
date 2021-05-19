package fr.umontpellier.iut.bang.cards;

public class RevCarabine extends WeaponCard {
    public RevCarabine(int value, CardSuit suit) {
        super("Rev. Carabine", value, suit);
    }

    @Override
    public int getRange() {
        return 4;
    }
}
