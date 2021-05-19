package fr.umontpellier.iut.bang.characters;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.cards.Card;

public abstract class BangCharacter {
    /**
     * Le nom du personnage
     */
    private String name;
    /**
     * Le nombre de points de vie du personnage (détermine le nombre max de points de vie du joueur)
     */
    private int healthPoints;

    public BangCharacter(String name, int healthPoints) {
        this.name = name;
        this.healthPoints = healthPoints;
    }

    public String getName() {
        return name;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * Cette méthode est appelée au début du tour d'un joueur pour lui faire piocher deux cartes.
     * Elle peut être redéfinie dans les sous-classes si le personnage modifie cette action.
     *
     * @param player le joueur associé au personnage
     */
    public void onStartTurn(Player player) {
        player.drawToHand();
        player.drawToHand();
    }

    /**
     * Cette méthode est exécutée lorsque le joueur "dégaine" (il retourne la carte du dessus de la pioche pour un
     * tirage aléatoire (Jail, Barrel, etc.)).
     * Elle peut être redéfinie dans les sous-classes si le personnage modifie cette action
     *
     * @param player le joueur associé au personnage
     * @return la carte qui a été tirée
     */
    public Card randomDraw(Player player) {
        Card card = player.drawCard();
        player.discard(card);
        return card;
    }

    public boolean collect(Player player, Player target) { return true; }

}
