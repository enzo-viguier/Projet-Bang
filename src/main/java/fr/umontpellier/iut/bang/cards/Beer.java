package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Beer extends OrangeCard {

    public Beer(int value, CardSuit suit) {
        super("Beer", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        if(player.getOtherPlayers().size()>1&&player.getHealthPoints()<player.getHealthPointsMax()) {
            player.incrementHealth(1);
        }
    }
}
