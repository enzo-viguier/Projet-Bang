package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public abstract class BlueCard extends Card {
    public BlueCard(String name, int value, CardSuit suit) {
        super(name, value, suit);
    }

    @Override
    public boolean canPlayFromHand(Player player) {
        // le joueur ne peut jouer une carte bleue que s'il n'en a pas déjà une copie en jeu devant lui
        return player.getCardInPlay(getName()) == null;
    }

    @Override
    public void playedBy(Player player) {
        player.addToInPlay(this);
    }
}
