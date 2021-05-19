package fr.umontpellier.iut.bang.characters;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.cards.Card;

public class ElGringo extends BangCharacter {

    public ElGringo() {
        super("El Gringo", 3);
    }

    @Override
    public boolean collect(Player player, Player target) {
        Card card = target.removeRandomCardFromHand();
        if(card!=null) {
            player.addToHand(card);
        }
        return card!=null;
    }
}
