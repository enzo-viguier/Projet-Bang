package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.characters.WillyTheKid;

import java.util.List;

public class Bang extends OrangeCard {

    private static Player lastPlayer, currentPlayer;
    private static boolean canPlay;
    private static int compteur=0;

    public Bang(int value, CardSuit suit) {
        super("Bang!", value, suit);
    }

    @Override
    public boolean canPlayFromHand(Player player) {
        currentPlayer=player;
        playOnceATurn();
        if(currentPlayer.getBangCharacter().getClass().equals(WillyTheKid.class)||(currentPlayer.getWeapon()!=null&&currentPlayer.getWeapon().getClass().equals(Volcanic.class))) {
            return true;
        }
        return canPlay;
    }

    @Override
    public void playedBy(Player player) {
        if(getValue()!=-1) {
            super.playedBy(player);
        }

        lastPlayer=player;
        canPlay=false;
        List<Player> inRange = player.getPlayersInRange(player.getWeaponRange());
        Player target = player.choosePlayer("Choisissez sur qui tirer ", inRange, false);

        player.attack(target,1);

    }

    public void playOnceATurn() {
        if(!currentPlayer.equals(lastPlayer)) {
            canPlay =true;
        } else {
            compteur += 1;
            compteur %= 2;
            canPlay = compteur == 0;
        }
    }
}
