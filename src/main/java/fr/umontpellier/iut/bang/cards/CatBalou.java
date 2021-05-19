package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;

public class CatBalou extends OrangeCard {

    public CatBalou(int value, CardSuit suit) {
        super("Cat Balou", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        Player target = player.choosePlayer("A qui allez vous retirer une carte ? ",player.getOtherPlayers(),false);
        ArrayList<Card> choices = new ArrayList<>(target.getHand());
        choices.addAll(target.getInPlay());
        if (target.getWeapon()!=null) {
            choices.add(target.getWeapon());
        }
        Card choiceCard = player.chooseCard("Quelle carte allez-vous prendre ? ",choices,true,true);
        if(choiceCard!=null) {
            if(target.getHand().contains(choiceCard)) {
                target.discardFromHand(choiceCard);
            } else if(target.getInPlay().contains((BlueCard) choiceCard)) {
                target.removeFromInPlay((BlueCard) choiceCard);
                target.discard(choiceCard);
            } else if(target.getWeapon().equals(choiceCard)) {
                target.setWeapon(null);
                target.discard(choiceCard);
            }
        } else if(!target.getHand().isEmpty()) {
            Card remove = target.removeRandomCardFromHand();
            target.discard(remove);
        }
    }
}
