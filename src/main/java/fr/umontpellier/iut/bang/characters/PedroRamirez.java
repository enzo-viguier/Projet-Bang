package fr.umontpellier.iut.bang.characters;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.cards.Card;

import java.util.Arrays;

public class PedroRamirez extends BangCharacter {

    public PedroRamirez() {
        super("Pedro Ramirez", 4);
    }

    @Override
    public void onStartTurn(Player player) {
        Card card = player.chooseCard("Voulez-vous cette carte de la défausse ? ", Arrays.asList(player.getGame().getTopOfDiscardPile()),true,true);
        if(card!=null) {
            player.addToHand(card);
        } else {
            player.drawToHand();
        }
        player.drawToHand();
    }
}
