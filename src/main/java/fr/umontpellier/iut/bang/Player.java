package fr.umontpellier.iut.bang;

import fr.umontpellier.iut.bang.cards.*;
import fr.umontpellier.iut.bang.characters.*;

import java.util.*;

public class Player {
    /**
     * Nom du joueur
     */
    private String name;
    /**
     * Points de vie courants
     */
    private int healthPoints;
    /**
     * Rôle dans la partie (Shériff, adjoint, hors-la-loi ou renégat)
     */
    private Role role;
    /**
     * Personnage
     */
    private BangCharacter bangCharacter;
    /**
     * Partie à laquelle le joueur appartient
     */
    private Game game;
    /**
     * Cartes en main
     */
    private List<Card> hand;
    /**
     * Cartes bleues actuellement posées devant le joueur (hors arme)
     */
    private List<BlueCard> inPlay;
    /**
     * Arme posée devant le joueur
     */
    private WeaponCard weapon;

    public Player(String name, BangCharacter bangCharacter, Role role) {
        this.name = name;
        this.role = role;
        this.bangCharacter = bangCharacter;
        healthPoints = getHealthPointsMax();
        inPlay = new ArrayList<>();
        hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public Role getRole() {
        return role;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Card> getHand() {
        return hand;
    }

    public List<BlueCard> getInPlay() {
        return inPlay;
    }

    public WeaponCard getWeapon() {
        return weapon;
    }

    /**
     * @return la portée de l'arme équipée (1 si aucune arme équipée)
     */
    public int getWeaponRange() {
        if (weapon == null) {
            return 1;
        }
        return weapon.getRange();
    }

    /**
     * @return le nombre maximum de points de vie que le joueur peut avoir. Dépend des points de vie de son personnage
     * et de son rôle (le Shériff a un point de vie max de plus que les autres)
     */
    public int getHealthPointsMax() {
        int hp = bangCharacter.getHealthPoints();
        if (role == Role.SHERIFF)
            return hp + 1;
        return hp;
    }

    /**
     * @return la liste des autres joueurs encore en jeu, dans l'ordre de jeu (le premier est le joueur
     * immédiatement après le joueur courant)
     */
    public List<Player> getOtherPlayers() {
        List<Player> gameplayers = game.getPlayers();
        int index = gameplayers.indexOf(this);
        List<Player> players = new ArrayList<>();
        for (int i = index+1; i < gameplayers.size(); i++)
            players.add(gameplayers.get(i));

        for (int i = 0; i < index; i++)
            players.add(gameplayers.get(i));

        return players;
    }

    /**
     * @param range portée considérée
     * @return la liste des autres joueurs encore en jeu que le joueur courant voit à une distance inférieure ou égale
     * à {@code range}.
     */
    public List<Player> getPlayersInRange(int range) {
        List<Player> inRange = getOtherPlayers();
        inRange.removeIf(player -> distanceTo(player) > range);
        return inRange;
    }

    /**
     * @return true si le joueur est mort
     */
    public boolean isDead() {
        return healthPoints <= 0;
    }

    /**
     * Change l'arme du joueur
     * Si le joueur a déjà une arme, celle-ci est défaussée
     * <p>
     * Remarque : pour retirer l'arme du joueur, il faut appeler {@code p.setWeapon(null);}
     *
     * @param weapon nouvelle arme à équiper
     */
    public void setWeapon(WeaponCard weapon) {
        if (this.weapon != null) {
            discard(this.weapon);
        }
        this.weapon = weapon;
        discardFromHand(weapon);
    }

    /**
     * @param cardName nom de la carte à renvoyer
     * @return la première carte parmi les cartes en jeu du joueur ayant le nom passé en argument, ou {@code null}
     * si aucune carte ne correspond
     */
    public BlueCard getCardInPlay(String cardName) {
        for (BlueCard c : inPlay) {
            if (c.getName().equals(cardName)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Augmente le nombre de points de vie du joueur de la valeur indiquée, sans toutefois dépasser la valeur maximale
     *
     * @param n nombre de points de vie à ajouter (doit être positif)
     */
    public void incrementHealth(int n) {
        healthPoints += n;
        if (healthPoints > getHealthPointsMax()) {
            healthPoints = getHealthPointsMax();
        }
    }

    /**
     * Décrémente le nombre de points de vie du joueur de la valeur indiquée. Si les points de vie du joueur passent à
     * 0 ou moins mais que le joueur a des Bières en main et qu'il peut les utiliser, elles sont automatiquement
     * utilisées pour le maintenir en vie.
     * <p>
     * Si les points de vie du joueur sont à 0 ou moins, le joueur est mort et retiré du jeu.
     *
     * @param n        nombre de points de vie à retirer (doit être positif)
     * @param attacker joueur qui est responsable des dégâts subis ou {@code null} si aucun joueur n'est directement
     *                 responsable (p.ex. Dynamite)
     */
    public void decrementHealth(int n, Player attacker) {
        healthPoints -= n;
        System.out.println(name + " vous avez pris " + n + " points de dégâts !");

        while (isDead()) {

            for (int i = 0; i < hand.size(); i++) {    //obligé d'utiliser un fori parce que foreach donne une erreur
                Card card = hand.get(i);
                if (card.getClass().equals(Beer.class)) {
                    playFromHand(card);
                    System.out.println(name + " vous avez utilisé(e) une bière !");
                    if (healthPoints == 1) {
                        System.out.println(name + " vous avez survécu(e) !");
                        break;
                    }
                }
            }
            if (!isDead()) {
                break;    //break la boucle si la bière a sauvé le player, évite la duplication de code
            }

            for (Player sam : getOtherPlayers()) {    //règle VultureSam
                if (sam.bangCharacter.getClass().equals(VultureSam.class)) {
                    sam.getBangCharacter().collect(sam, this);
                    break;
                }
            }

            clearHandInPlay();
            game.removePlayer(this);    //suppression du joueur

            if (attacker == null) { //message de mort
                System.out.println(name + " vous vous êtes fait tué(e)");
            } else {
                System.out.println(name + " vous vous êtes fait tué(e) par " + attacker.getName());
            }
            System.out.println(name + " vous êtiez un " + role); //Annonce rôle


            if (attacker.role == Role.SHERIFF && role == Role.DEPUTY) {    //Défaussement Sherrif quand il tue un adjoint
                attacker.clearHandInPlay();
                System.out.println("Sherrif, vous avez tué(e) un(e) adjoint(e)");
            }

            if (role == Role.OUTLAW) { //pioche de 3 cartes pour avoir tué un hors-la-loi
                for (int i = 0; i < 3; i++) {
                    attacker.drawCard();
                }
                System.out.println(attacker.name + " vous avez tué(e) un hors-la-loi et pioché(e) 3 cartes");
            }
            return;
        }
        //règle ElGringo et BartCassidy
        if (bangCharacter.getClass().equals(ElGringo.class) && attacker != null && !attacker.hand.isEmpty() || bangCharacter.getClass().equals(BartCassidy.class)) {
            int i = 0;
            for (i = 0; i < n; i++) {
                if (!getBangCharacter().collect(this, attacker)) {
                    break;
                }
            }
            System.out.println(name + " vous avez pioché(e) " + i + " cartes !");
        }
    }

    public void attack(Player target, int n) {

        ArrayList<Card> choices = new ArrayList<>();
        for (Card card : target.getHand()) {
            if (card.getClass().equals(Missed.class)) {
                choices.add(card);
            }
            if (card.getClass().equals(Bang.class) && target.getBangCharacter().getClass().equals(CalamityJanet.class)) {
                choices.add(card);
            }
        }
        boolean empty = choices.isEmpty();
        while (true) {
            if(target.bangCharacter.getClass().equals(Jourdonnais.class) && degainer(target, CardSuit.HEART)) {
                if (getBangCharacter().getClass().equals(SlabTheKiller.class)) {
                    if(empty) {
                        break;
                    }
                    for(BlueCard blueCard: target.getInPlay()) {
                        if(blueCard.getClass().equals(Barrel.class)) {
                            return;
                        }
                    }
                    Card choice = target.chooseCard("Voulez-vous jouer votre deuxième Missed! ? ", choices, false, true);
                    if (choice != null) {
                        target.discardFromHand(choice);
                        return;
                    }
                }
                return;
            }
            for (BlueCard blueCard : target.getInPlay()) {
                if (blueCard.getClass().equals(Barrel.class)) {
                    if (degainer(target, CardSuit.HEART)) {
                        if (getBangCharacter().getClass().equals(SlabTheKiller.class)) {
                            if(empty) {
                                break;
                            }
                            Card choice = target.chooseCard("Voulez-vous jouer votre deuxième Missed! ? ", choices, false, true);
                            if (choice != null) {
                                target.discardFromHand(choice);
                                return;
                            }
                        }
                        return;
                    }
                }
            }
            if (getBangCharacter().getClass().equals(SlabTheKiller.class)) {
                System.out.println("Voulez vous jouer deux Missed! ? ");
                Card choice = null;
                for (int i = 0; i < 2; i++) {
                    if(empty) {
                        break;
                    }
                    choice = target.chooseCard("Jouez vos deux Missed! ", choices, false, true);
                    if (choice != null) {
                        choices.remove(choice);
                        target.discardFromHand(choice);
                    } else {
                        break;
                    }
                }
                if (choice == null) {
                    break;
                } else {
                    System.out.println("Missed ! " + name);
                    return;
                }
            }
            if(empty) {
                break;
            }
            Card choice = target.chooseCard("Voulez-vous jouer Missed! ? ", choices, false, true);
            if (choice != null) {
                target.discardFromHand(choice);
                System.out.println("Missed ! " + name);
                return;
            } else {
                break;
            }
        }
        target.decrementHealth(n, this);
    }

    public boolean degainer(Player player, CardSuit cardSuit) {
        System.out.println("Vous dégainez !!");
        Card draw = null;
        if (player.getBangCharacter().getClass().equals(LuckyDuke.class)) {
            String poker = "";
            ArrayList<String> choices = new ArrayList<>();

            Card card1 = player.drawCard();
            player.discard(card1);
            Card card2 = player.drawCard();
            player.discard(card2);
            choices.add(card1.getPokerString());
            choices.add(card2.getPokerString());

            poker = player.choose("Quelle carte sélectionnez-vous ? ", choices, true, false);
            if (card1.getPokerString().equals(poker)) {
                draw = card1;
            } else {
                draw = card2;
            }
        } else {
            draw = player.drawCard();
            player.discard(draw);
        }
        switch (cardSuit) {
            case HEART:
                return draw.getSuit() == CardSuit.HEART;
            case SPADE:
                return !((2 <= draw.getValue()) && (draw.getValue() <= 9));
        }
        return false;
    }

    public void clearHandInPlay() {
        for (int i = 0; i < hand.size(); i++) {    //vide la main
            discardFromHand(hand.get(i));
        }
        for (int i = 0; i < inPlay.size(); i++) {  //vide les cartes en jeu
            discard(inPlay.get(i));
        }
    }

    public BangCharacter getBangCharacter() {
        return bangCharacter;
    }

    /**
     * @param player autre joueur
     * @return distance à laquelle le joueur courant voit le joueur passé en paramètre
     */
    public int distanceTo(Player player) {
        int distance = game.getPlayerDistance(this, player);
        if (player.bangCharacter.getClass().equals(PaulRegret.class)) {
            distance++;
        }
        if (this.bangCharacter.getClass().equals(RoseDoolan.class)) {
            if (distance > 1) {
                distance--;
            }
        }
        for (Card bluecard : inPlay) {
            if (bluecard.getClass().equals(Scope.class)) {
                if (distance > 1) {
                    distance--;
                }
            }
        }
        for (Card bluecard : player.inPlay) {
            if (bluecard.getClass().equals(Mustang.class)) {
                distance++;
            }
        }
        return distance;
    }

    /**
     * Pioche une carte
     *
     * @return la carte qui a été piochée
     */
    public Card drawCard() {
        return game.drawCard();
    }

    /**
     * Défausse une carte
     *
     * @param card carte à défausser
     */
    public void discard(Card card) {
        if (bangCharacter.getClass().equals(SuzyLafayette.class) && hand.isEmpty()) {
            drawToHand();
        }
        game.addToDiscard(card);
    }

    /**
     * Ajoute une carte à la main du joueur
     *
     * @param card carte à ajouter
     */
    public void addToHand(Card card) {
        hand.add(card);
    }

    /**
     * Pioche une carte, qui est mise dans la main du joueur
     *
     * @return la carte qui a été piochée
     */
    public Card drawToHand() {
        Card card = game.drawCard();
        hand.add(card);
        return card;
    }

    /**
     * Retire une carte de la main du joueur
     *
     * @param card carte à retirer
     * @return true si la carte a bien été retirée, false sinon (la carte n'était pas dans la main du joueur)
     */
    public boolean removeFromHand(Card card) {
        return hand.remove(card);
    }

    /**
     * Défausse une carte de la main du joueur
     * <p>
     * La fonction ne fait rien si la carte n'est pas dans la main du joueur
     *
     * @param c carte à défausser
     */
    public void discardFromHand(Card c) {
        if (removeFromHand(c)) {
            game.addToDiscard(c);
        }
    }

    public void backToDraw(Card c) {
        game.addToDrawPile(c);
    }

    /**
     * Retire une carte aléatoire de la main du joueur
     *
     * @return la carte qui a été retirée (ou null si le joueur n'a pas de carte en main)
     */
    public Card removeRandomCardFromHand() {
        if (hand.size() > 0) {
            Random random = new Random();
            Card card = hand.get(random.nextInt(hand.size()));
            removeFromHand(card);
            return card;
        }
        return null;
    }

    /**
     * Renvoie une carte retournée sur la pioche pour un tirage aléatoire (action "dégainer").
     * Cette action peut être modifiée par le personnage du joueur.
     *
     * @return la carte qui a été dégainée
     */
    public Card randomDraw() {
        return bangCharacter.randomDraw(this);
    }

    /**
     * Attend une entrée de la part du joueur (au clavier) et renvoie le choix du joueur.
     * Cette méthode lit l'entrée clavier jusqu'à ce qu'un choix valide (un élément de {@code choices}
     * ou éventuellement la chaîne vide si l'utilisateur est autorisé à passer) soit
     * entré par l'utilisateur. Lorsqu'un choix valide est obtenu, il est renvoyé par la fonction.
     * <p>
     * Si l'ensemble {@code choices} ne comporte qu'un seul élément et que {@code canPass} est faux,
     * l'unique choix valide est automatiquement renvoyé sans lire l'entrée de l'utilisateur.
     * <p>
     * Si l'ensemble des choix est vide, la chaîne vide ("") est automatiquement renvoyée par la méthode
     * (indépendamment de la valeur de {@code canPass}).
     * <p>
     * Exemple d'utilisation pour demander à un joueur de répondre à une question par "oui" ou "non" (en affichant
     * des boutons pour les deux options) :
     * <p>
     * {@code
     * List<String> choices = Arrays.asList("oui", "non");
     * String input = p.choose("Voulez vous faire ceci ?", choices, true, false);
     * }
     *
     * @param instruction message à afficher à l'écran pour indiquer au joueur
     *                    la nature du choix qui est attendu
     * @param choices     une liste de chaînes de caractères correspondant aux
     *                    choix valides attendus du joueur (la liste sera convertie en ensemble
     *                    par la fonction pour éliminer les doublons, ce qui permet de compter
     *                    correctement le nombre d'options disponibles)
     * @param showButtons indique s'il faut afficher des boutons pour permettre au joueur de choisir une des options.
     *                    Si c'est le cas, un bouton sera affiché pour chaque valeur dans `choices` (en mode console
     *                    les noms des boutons sont affichés après l'instruction séparés par "/" pour indiquer au
     *                    joueur les choix valides)
     * @param canPass     booléen indiquant si le joueur a le droit de passer sans
     *                    faire de choix. S'il est autorisé à passer, c'est la chaîne de
     *                    caractères vide ("") qui signifie qu'il désire passer.
     * @return le choix de l'utilisateur.
     */
    public String choose(String instruction, List<String> choices, boolean showButtons, boolean canPass) {
        // on retire les doublons de la liste des choix
        HashSet<String> distinctChoices = new HashSet<>(choices);
        // Aucun choix disponible
        if (distinctChoices.isEmpty())
            return "";
        else // Un seul choix possible (renvoyer cet unique élément)
            if (distinctChoices.size() == 1 && !canPass)
                return distinctChoices.iterator().next();
            else {
                String input;
                List<String> buttons = showButtons ? choices : new ArrayList<>();
                // Lit l'entrée de l'utilisateur jusqu'à obtenir un choix valide
                while (true) {
                    game.prompt(this, instruction, buttons, canPass);
                    input = game.readLine();
                    // si une réponse valide est obtenue, elle est renvoyée
                    if (distinctChoices.contains(input) || (canPass && input.equals("")))
                        return input;
                }
            }
    }

    /**
     * Attend une entrée de la part du joueur et renvoie le choix du joueur.
     * Dans cette méthode, la liste des choix est donnée sous la forme d'une liste de cartes et le joueur doit
     * choisir le nom d'une de ces cartes.
     *
     * @param instruction message à afficher à l'écran pour indiquer au joueur la nature du choix qui est attendu
     * @param choices     liste de cartes parmi lesquelles l'utilisateur doit choisir
     * @param showButtons indique s'il faut afficher des boutons pour permettre au joueur de choisir une des options.
     *                    Si c'est le cas, un bouton sera affiché pour chaque valeur dans `choices` (en mode console
     *                    les noms des boutons sont affichés après l'instruction séparés par "/" pour indiquer au
     *                    joueur les choix valides)
     * @param canPass     booléen indiquant si le joueur a le droit de passer sans faire de choix.
     *                    S'il est autorisé à passer, c'est la chaîne de caractères vide ("") qui signifie
     *                    qu'il désire passer (dans ce cas la fonction renvoie {@code null})
     * @return une carte de la liste {@code choices} dont le nom correspond au nom entré par le joueur (ou {@code null}
     * si le joueur passe ou si aucun choix n'est donné).
     */
    public Card chooseCard(String instruction, List<Card> choices, boolean showButtons, boolean canPass) {
        List<String> cardNames = new ArrayList<>();
        for (Card card : choices) {
            cardNames.add(card.getName());
        }
        String cardName = choose(
                instruction,
                cardNames,
                showButtons,
                canPass);
        for (Card card : choices) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }

    /**
     * Attend une entrée de la part du joueur et renvoie le choix du joueur.
     * Dans cette méthode, la liste des choix est donnée sous la forme d'une liste de joueurs et le joueur doit
     * choisir le nom d'un de ces joueurs.
     *
     * @param instruction message à afficher à l'écran pour indiquer au joueur la nature du choix qui est attendu
     * @param choices     liste de joueurs parmi lesquels l'utilisateur doit choisir
     * @param canPass     booléen indiquant si le joueur a le droit de passer sans faire de choix.
     *                    S'il est autorisé à passer, c'est la chaîne de caractères vide ("") qui signifie
     *                    qu'il désire passer (dans ce cas la fonction renvoie {@code null})
     * @return un joueur de la liste {@code choices} dont le nom correspond au nom entré par le joueur (ou {@code null}
     * si le joueur passe ou si aucun choix n'est donné).
     */
    public Player choosePlayer(String instruction, List<Player> choices, boolean canPass) {
        List<String> playerNames = new ArrayList<>();
        for (Player player : choices) {
            playerNames.add(player.name);
        }
        String playerName = choose(
                instruction,
                playerNames,
                false,
                canPass);
        for (Player player : choices) {
            if (player.name.equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Renvoie une représentation de l'état du joueur sous forme d'une chaîne de caractères.
     * <p>
     * Cette représentation comporte
     * <ul>
     *   <li> le nom du joueur et le nom de son personnage
     *   <li> son rôle et son nombre de points de vie
     *   <li> la liste des cartes qu'il a en jeu
     *   <li> la liste des cartes dans sa main
     * </ul>
     * <p>
     * On pourrait par exemple avoir l'affichage suivant:
     * <pre>
     *   - John (Paul Regret)
     *   Rôle: SHERIFF      HP: ♥♥♥♥
     *   Arme: Remington (3)     En Jeu: Barrel (K♠)
     *   Main: Bang! (A♦), Bang! (6♣), Duel (8♣), Panic! (A♥)
     * </pre>
     *
     * @return une chaîne de caractères décrivant le joueur
     */
    @Override
    public String toString() {
        char[] hpChars = new char[healthPoints];
        Arrays.fill(hpChars, '♥');
        char[] missingHpChars = new char[getHealthPointsMax() - healthPoints];
        Arrays.fill(missingHpChars, '.');
        String weaponString = weapon == null ? "--" : weapon.getName();

        StringJoiner inPlayJoiner = new StringJoiner(", ");
        for (Card card : inPlay) {
            inPlayJoiner.add(card.toString());
        }
        StringJoiner handJoiner = new StringJoiner(", ");
        for (Card card : hand) {
            handJoiner.add(card.toString());
        }
        return String.format("  - %s (%s)\n", name, bangCharacter.getName())
                + String.format("  Rôle: %s      HP: %s%s\n", role, new String(hpChars), new String(missingHpChars))
                + String.format("  Arme: %s (%d)     En Jeu: %s\n", weaponString, getWeaponRange(), inPlayJoiner.toString())
                + String.format("  Main: %s\n", handJoiner.toString());
    }

    /**
     * @return une chaîne de caractères représentant les informations du joueur au format JSON
     * (utilisée par l'interface graphique)
     */
    public String toJSON() {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add(String.format("\"name\": \"%s\"", name));
        joiner.add(String.format("\"hp\": %d", healthPoints));
        joiner.add(String.format("\"hp_max\": %d", getHealthPointsMax()));
        joiner.add(String.format("\"character\": \"%s\"", bangCharacter.getName()));
        joiner.add(String.format("\"role\": \"%s\"", role));
        if (weapon != null) {
            joiner.add(String.format("\"weapon\": %s", weapon.toJSON()));
        }

        StringJoiner cardsJoiner;
        // main du joueur
        cardsJoiner = new StringJoiner(", ");
        for (Card c : hand) {
            cardsJoiner.add(c.toJSON());
        }
        joiner.add(String.format("\"hand\": [%s]", cardsJoiner.toString()));

        // cartes en jeu
        cardsJoiner = new StringJoiner(", ");
        for (Card c : inPlay) {
            cardsJoiner.add(c.toJSON());
        }
        joiner.add(String.format("\"in_play\": [%s]", cardsJoiner.toString()));

        return "{" + joiner.toString() + "}";
    }

    /**
     * Joue une carte de la main du joueur (ne fait rien si la carte n'est pas dans la main du joueur)
     *
     * @param card la carte à jouer
     */
    public void playFromHand(Card card) {
        if (hand.remove(card)) {
            card.playedBy(this);
        }
    }

    /**
     * Ajoute une carte à la liste des cartes que le joueur a devant lui ("en jeu")
     *
     * @param card carte à ajouter à la liste
     */
    public void addToInPlay(BlueCard card) {
        inPlay.add(card);
    }

    /**
     * Retire une carte de la liste des cartes que le joueur a devant lui ("en jeu")
     *
     * @param card carte à retirer de la liste
     */
    public void removeFromInPlay(BlueCard card) {
        inPlay.remove(card);
    }

    /**
     * Exécute un tour complet du joueur. Le tour comprend plusieurs phases :
     * <ul>
     *     <li> Résolution des effets préliminaires (Dynamite et Jail)
     *     <li> Piocher des cartes en main (en général 2 cartes, mais peut être modifié par le personnage)
     *     <li> Jouer des cartes que le joueur a en main
     *     <li> Défausser des cartes si le joueur a plus de cartes qu'il a de points de vie restants
     * </ul>
     */
    public void playTurn() {
        // phase 0: setup et résolution des effets préliminaires (dynamite, prison, etc...)
        for (int i = 0; i < inPlay.size(); i++) {
            if (inPlay.get(i).getClass().equals(Dynamite.class)) {
                BlueCard dynamite = inPlay.get(i);
                removeFromInPlay(dynamite);
                if (degainer(this, CardSuit.SPADE)) {
                    getOtherPlayers().get(0).addToInPlay(dynamite);
                    break;
                } else {
                    discard(dynamite);
                    decrementHealth(3, null);
                    if(isDead()) {
                        return;
                    }
                }
            }
        }
        for (int i = 0; i < inPlay.size(); i++) {
            if (inPlay.get(i).getClass().equals(Jail.class)) {
                BlueCard jail = inPlay.get(i);
                removeFromInPlay(jail);
                discard(jail);
                if (degainer(this, CardSuit.HEART)) {
                    break;
                } else {
                    return;
                }
            }
        }

        // phase 1: piocher des cartes
        bangCharacter.onStartTurn(this);

        // phase 2: jouer des cartes
        while (true) {
            List<Card> possibleCards = new ArrayList<>();
            for (Card c : hand) {
                if (c.canPlayFromHand(this)) {
                    possibleCards.add(c);
                }
            }
            Card card = chooseCard("Choisissez une carte à jouer", possibleCards, false, true);
            if (card == null) break;
            playFromHand(card);
        }

        // phase 3: défausser les cartes en trop
        while (hand.size() > healthPoints) {
            Card card = chooseCard(String.format("Défaussez pour n'avoir que %d carte(s) en main", healthPoints), hand, false, false);
            if (card != null) {
                discardFromHand(card);
            }
        }
    }
}
