package fr.umontpellier.iut.bang.characters;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.cards.Card;

import java.util.ArrayList;

public class KitCarlson extends BangCharacter {

    public KitCarlson() {
        super("Kit Carlson", 4);
    }

    @Override
    public void onStartTurn(Player player) {
        ArrayList<Card> draw = new ArrayList<>();
        for(int i=0;i<3;i++) {
            draw.add(player.drawCard());
        }
        Card back = player.chooseCard("Choisissez la carte à rendre : ",draw,true,false);
        player.backToDraw(back); draw.remove(back);
        player.addToHand(draw.get(0));
        player.addToHand(draw.get(1));
    }
}
