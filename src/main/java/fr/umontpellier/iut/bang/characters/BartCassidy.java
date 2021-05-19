package fr.umontpellier.iut.bang.characters;

import fr.umontpellier.iut.bang.Player;

public class BartCassidy extends BangCharacter {

    public BartCassidy() {
        super("Bart Cassidy", 4);
    }

    @Override
    public boolean collect(Player player, Player target) {
        player.drawToHand();
        return true;
    }
}
