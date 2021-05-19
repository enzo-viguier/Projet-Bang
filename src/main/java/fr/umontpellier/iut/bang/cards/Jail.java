package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.Role;

import java.util.List;

public class Jail extends BlueCard {
    public Jail(int value, CardSuit suit) {
        super("Jail", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        List<Player> others = player.getOtherPlayers();
        Player target = player.choosePlayer("Qui voulez-vous mettre en prison ? ", others, false);
        if(target.getRole()!= Role.SHERIFF) {
            target.addToInPlay(this);
        }
    }
}
