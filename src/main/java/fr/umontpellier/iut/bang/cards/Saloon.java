package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

import java.util.List;

public class Saloon extends OrangeCard {

    public Saloon(int value, CardSuit suit) {
        super("Saloon", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        List<Player> others = player.getOtherPlayers();
        others.add(player);
        for (Player p: others) {
            p.incrementHealth(1);
        }
    }
}
