package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Barrel extends BlueCard {

    public Barrel(int value, CardSuit suit) {
        super("Barrel", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
    }
}
