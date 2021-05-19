package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.characters.CalamityJanet;

import java.util.ArrayList;
import java.util.Arrays;

public class Indians extends OrangeCard {

    public Indians(int value, CardSuit suit) {
        super("Indians!", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        for(Player others: player.getOtherPlayers()) {
            ArrayList<Card> choices = new ArrayList<>();
            for(Card card: others.getHand()) {
                if(card.getClass().equals(Bang.class)||(others.getBangCharacter().getClass().equals(CalamityJanet.class)&&card.getClass().equals(Missed.class))) {
                    choices.add(card);
                }
            }
            Card choiceCard = others.chooseCard("Voulez-vous vous défausser d'un Bang! ou prendre 1 point de dégâts ?", choices,false,true);
            if (choiceCard!=null) {
                others.discardFromHand(choiceCard);
            } else {
                others.decrementHealth(1,player);
            }
        }
    }
}
