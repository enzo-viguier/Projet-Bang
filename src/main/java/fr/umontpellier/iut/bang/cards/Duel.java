package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.characters.CalamityJanet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Duel extends OrangeCard {

    public Duel(int value, CardSuit suit) {
        super("Duel", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        Player target = player.choosePlayer("Qui voulez-vous défier en Duel ? ", player.getOtherPlayers(), false);
        while(true) {
            Card choiceTarget=null;
            Card choicePlayer=null;
            ArrayList<Card> targetList = new ArrayList<>();
            ArrayList<Card> playerList = new ArrayList<>();
            for(Card card: target.getHand()) {
                if(card.getClass().equals(Bang.class)||(target.getBangCharacter().getClass().equals(CalamityJanet.class)&&card.getClass().equals(Missed.class))) {
                    targetList.add(card);
                }
            }
            for(Card card: player.getHand()) {
                if (card.getClass().equals(Bang.class) || (target.getBangCharacter().getClass().equals(CalamityJanet.class) && card.getClass().equals(Missed.class))) {
                    playerList.add(card);
                }
            }
            if(!targetList.isEmpty()) {
                choiceTarget = target.chooseCard("Voulez-vous jouer un Bang! ? ", targetList,false,true);
                if(choiceTarget==null) {
                    target.decrementHealth(1,player);
                    return;
                } else {
                    target.discardFromHand(choiceTarget);
                }
            } else {
                target.decrementHealth(1,player);
                return;
            }

            if(!playerList.isEmpty()) {
                choicePlayer = target.chooseCard("Voulez-vous jouer un Bang! ? ", playerList,false,true);
                if(choicePlayer==null) {
                    player.decrementHealth(1,target);
                    return;
                }
                else {
                    player.discardFromHand(choicePlayer);
                }
            } else {
                player.decrementHealth(1,target);
                return;
            }

        }
    }
}
