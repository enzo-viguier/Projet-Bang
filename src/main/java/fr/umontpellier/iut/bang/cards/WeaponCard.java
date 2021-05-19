package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public abstract class WeaponCard extends BlueCard {
    public WeaponCard(String name, int value, CardSuit suit) {
        super(name, value, suit);
    }

    /**
     * @return la portée de l'arme
     */
    public abstract int getRange();

    @Override
    public void playedBy(Player player) {
        player.setWeapon(this);
    }

}
