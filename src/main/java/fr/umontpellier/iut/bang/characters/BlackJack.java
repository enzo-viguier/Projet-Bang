package fr.umontpellier.iut.bang.characters;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.cards.Card;
import fr.umontpellier.iut.bang.cards.CardSuit;

// l'implémentation de cette classe vous est donnée pour illustrer l'implémentation des personnages
public class BlackJack extends BangCharacter {

    public BlackJack() {
        super("Black Jack", 4);
    }

    @Override
    public void onStartTurn(Player player) {
        player.drawToHand();
        Card card = player.drawToHand();
        if (card.getSuit() == CardSuit.HEART || card.getSuit() == CardSuit.DIAMOND) {
            // si la deuxième carte piochée est un cœur ou un carreau, le joueur pioche une troisième carte
            player.drawToHand();
        }
    }
}
