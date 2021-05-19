package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.characters.CalamityJanet;

public class Missed extends OrangeCard {

    public Missed(int value, CardSuit suit) {
        super("Missed!", value, suit);
    }

    @Override
    public boolean canPlayFromHand(Player player) {
        if(player.getBangCharacter().getClass().equals(CalamityJanet.class)) {
            return new Bang(-1,CardSuit.HEART).canPlayFromHand(player);
        }
        return true;
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        if(player.getBangCharacter().getClass().equals(CalamityJanet.class)) {
            new Bang(-1,CardSuit.HEART).playedBy(player);
        }
    }
}
