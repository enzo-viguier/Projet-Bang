package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

// cette classe est déjà implémentée et vous est donnée en guise d'exemple
public class Stagecoach extends OrangeCard {
    public Stagecoach(int value, CardSuit suit) {
        super("Stagecoach", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        player.drawToHand();
        player.drawToHand();
    }
}
