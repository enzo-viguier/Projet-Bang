package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;

public class Panic extends OrangeCard {

    public Panic(int value, CardSuit suit) {
        super("Panic!", value, suit);
    }

    @Override
    public boolean canPlayFromHand(Player player) {
        return player.getPlayersInRange(1).size()>0;
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        Player target = player.choosePlayer("A qui allez vous retirer une carte ? ",player.getPlayersInRange(1),false);
        ArrayList<Card> choices = new ArrayList<>(target.getHand());
        choices.addAll(target.getInPlay());
        if (target.getWeapon()!=null) {
            choices.add(target.getWeapon());
        }
        Card choiceCard = player.chooseCard("Quelle carte allez-vous prendre ? ",choices,true,true);
        if(choiceCard!=null) {
            if(target.getHand().contains(choiceCard)) {
                target.removeFromHand(choiceCard);
            } else if(target.getInPlay().contains((BlueCard) choiceCard)) {
                target.removeFromInPlay((BlueCard) choiceCard);
            } else if(target.getWeapon().equals(choiceCard)) {
                target.setWeapon(null);
            }
            player.addToHand(choiceCard);
            target.discard(choiceCard);
        } else if(!target.getHand().isEmpty()) {
            Card remove = target.removeRandomCardFromHand();
           player.addToHand(remove);
        }
    }
}
