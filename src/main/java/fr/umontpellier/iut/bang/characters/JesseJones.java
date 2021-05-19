package fr.umontpellier.iut.bang.characters;

import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;

public class JesseJones extends BangCharacter {

    public JesseJones() {
        super("Jesse Jones", 4);
    }

    @Override
    public void onStartTurn(Player player) {
        ArrayList<Player> others = new ArrayList<>(player.getOtherPlayers());
        others.removeIf(player1 -> player1.getHand().isEmpty());
        Player target = player.choosePlayer("A qui voulez-vous prendre une carte ? ", others, true);
        if(target!=null) {
            player.addToHand(target.removeRandomCardFromHand());
        } else {
            player.drawToHand();
        }
        player.drawToHand();
    }
}
