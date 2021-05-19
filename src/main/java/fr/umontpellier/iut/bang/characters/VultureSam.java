package fr.umontpellier.iut.bang.characters;

import fr.umontpellier.iut.bang.Player;

public class VultureSam extends BangCharacter {

    public VultureSam() {
        super("Vulture Sam", 4);
    }

    @Override
    public boolean collect(Player samVulture, Player target) {
        for(int i=0;i<target.getHand().size();i++) {    //vide la main
            samVulture.addToHand(target.getHand().get(i));
        }
        for(int i=0;i<target.getInPlay().size();i++) {  //vide les cartes en jeu
            samVulture.addToHand(target.getInPlay().get(i));
        }
        int s = target.getHand().size() + target.getInPlay().size();
        System.out.println(samVulture.getName()+" vous avez récupéré(e) "+s+" cartes !");
        return s>0;
    }
}
