package fr.umontpellier.iut.bang;

import fr.umontpellier.iut.bang.cards.*;
import fr.umontpellier.iut.bang.characters.*;

import java.util.*;

import static fr.umontpellier.iut.bang.Role.*;
import static fr.umontpellier.iut.bang.cards.CardSuit.*;

public class Game {
    /**
     * Liste des joueurs encore en vie dans la partie
     */
    private List<Player> players;
    /**
     * Pile de défausse
     */
    private Deque<Card> discardPile;
    /**
     * Pile de pioche
     */
    private Deque<Card> drawPile;
    /**
     * Le joueur qui a le rôle du Shériff
     */
    private Player sheriffPlayer;
    /**
     * Le joueur qui a le rôle du renégat
     */
    private Player renegadePlayer;
    /**
     * Liste des joueurs qui ont le rôle d'adjoint
     */
    private List<Player> deputyPlayers = new ArrayList<>();
    /**
     * Liste des joueurs qui ont le rôle de hors-la-loi
     */
    private List<Player> outlawPlayers = new ArrayList<>();
    /**
     * Joueur dont c'est le tour
     */
    private Player currentPlayer;
    /**
     * Booléen indiquant si la partie est terminée (true) ou non
     */
    private boolean finished = false;
    /**
     * Liste des joueurs qui ont gagné la partie
     * (cette liste est vide pendant que la partie est en cours et remplie lorsque la partie se termine)
     */
    private List<Player> winners = new ArrayList<>();
    /**
     * Scanner permettant de lire les entrées au clavier
     */
    private Scanner scanner;


    /**
     * Constructeur de la classe
     *
     * @param players liste des joueurs qui participent à la partie
     */
    public Game(List<Player> players) {
        // Préparation des piles de cartes
        discardPile = new ArrayDeque<>();
        drawPile = new ArrayDeque<>();
        fillDrawPile();

        this.players = players;
        for (Player player : players) {
            player.setGame(this);
            switch (player.getRole()) {
                case SHERIFF:
                    sheriffPlayer = player;
                    currentPlayer = player;
                    break;
                case RENEGADE:
                    renegadePlayer = player;
                    break;
                case OUTLAW:
                    outlawPlayers.add(player);
                    break;
                case DEPUTY:
                    deputyPlayers.add(player);
                    break;
            }
        }

        scanner = new Scanner(System.in);
    }

    /**
     * @param playerNames tableau contenant les noms des joueurs à créer. La fonction renvoie une liste de `Player`
     *                    de même taille que `playerNames`.
     * @return une liste de joueurs dont les personnages et les rôles sont choisis aléatoirement selon les règles
     */
    public static List<Player> makePlayers(String[] playerNames) {
        int nbOfPlayers = playerNames.length;
        // Préparation des personnages à distribuer
        List<BangCharacter> possibleCharacters = getBangCharacters();
        Collections.shuffle(possibleCharacters);

        // Préparation des rôles à distribuer
        List<Role> possibleRoles = new ArrayList<>(Arrays.asList(SHERIFF, RENEGADE, OUTLAW, OUTLAW, DEPUTY, OUTLAW, DEPUTY));
        List<Role> rolesInGame = possibleRoles.subList(0, nbOfPlayers);
        Collections.shuffle(rolesInGame);

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < nbOfPlayers; i++) {
            players.add(new Player(playerNames[i], possibleCharacters.get(i), rolesInGame.get(i)));
        }
        return players;
    }

    /**
     * @return une liste contenant un exemplaire de chacun des personnages disponibles
     */
    private static List<BangCharacter> getBangCharacters() {
        List<BangCharacter> possibleCharacters = new ArrayList<>();
        possibleCharacters.add(new BartCassidy());
        possibleCharacters.add(new BlackJack());
        possibleCharacters.add(new CalamityJanet());
        possibleCharacters.add(new ElGringo());
        possibleCharacters.add(new JesseJones());
        possibleCharacters.add(new Jourdonnais());
        possibleCharacters.add(new KitCarlson());
        possibleCharacters.add(new LuckyDuke());
        possibleCharacters.add(new PaulRegret());
        possibleCharacters.add(new PedroRamirez());
        possibleCharacters.add(new RoseDoolan());
        possibleCharacters.add(new SlabTheKiller());
        possibleCharacters.add(new SuzyLafayette());
        possibleCharacters.add(new VultureSam());
        possibleCharacters.add(new WillyTheKid());
        return possibleCharacters;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Lance la partie (exécute les tours des joueurs jusqu'à ce que la partie soit finie)
     */
    public void run() {
        // Distribue les cartes aux joueurs
        for (Player player : players) {
            for (int i = 0; i < player.getHealthPoints(); i++) {
                player.drawToHand();
            }
        }

        while (!finished) {
            currentPlayer.playTurn();   // jouer le tour du joueur courant
            int index = players.indexOf(currentPlayer);
            index += 1;
            index %= players.size();
            currentPlayer = players.get(index); // passer au joueur suivant
        }
    }

    /**
     * Renvoie la distance entre deux joueurs en ne tenant compte que du nombre de joueurs qui les séparent (pas des
     * éventuels modificateurs de distance que les joueurs pourraient avoir). Cette fonction est toujours symétrique.
     *
     * @param player1 un joueur
     * @param player2 un joueur
     * @return entier représentant la distance qui sépare les deux joueurs
     */
    public int getPlayerDistance(Player player1, Player player2) {
        int m = players.indexOf(player1);
        int n = players.indexOf(player2);
        int distance = n-m;
        if (distance <= players.size() / 2 && -distance <= players.size() / 2) {
            if (distance<0) {
                distance = -distance;
            }
        } else {
            if (distance>players.size()/2) {
                distance = -distance;
            }
            distance += players.size();
            System.out.println(distance);
        }
        return distance;
    }

    /**
     * Construit la pile de cartes utilisées pendant la partie
     */
    private void fillDrawPile() {
        List<Card> cards = new ArrayList<>();
        for (int i = 2; i <= 9; i++)
            cards.add(new Bang(i, CLUB));
        for (int i = 2; i <= 14; i++)
            cards.add(new Bang(i, DIAMOND));
        cards.add(new Bang(12, HEART));
        cards.add(new Bang(13, HEART));
        cards.add(new Bang(14, HEART));
        cards.add(new Bang(14, SPADE));
        for (int i = 6; i <= 11; i++)
            cards.add(new Beer(i, HEART));
        cards.add(new CatBalou(9, DIAMOND));
        cards.add(new CatBalou(10, DIAMOND));
        cards.add(new CatBalou(11, DIAMOND));
        cards.add(new CatBalou(13, HEART));
        cards.add(new Duel(8, CLUB));
        cards.add(new Duel(11, SPADE));
        cards.add(new Duel(12, DIAMOND));
        cards.add(new Gatling(10, HEART));
        cards.add(new Indians(13, DIAMOND));
        cards.add(new Indians(14, DIAMOND));

        for (int i = 2; i <= 8; i++)
            cards.add(new Missed(i, SPADE));
        for (int i = 10; i <= 14; i++)
            cards.add(new Missed(i, CLUB));

        cards.add(new Panic(11, HEART));
        cards.add(new Panic(12, HEART));
        cards.add(new Panic(14, HEART));
        cards.add(new Panic(8, DIAMOND));
        cards.add(new Saloon(5, HEART));
        cards.add(new GeneralStore(9, CLUB));
        cards.add(new GeneralStore(12, SPADE));
        cards.add(new Stagecoach(9, SPADE));
        cards.add(new Stagecoach(9, SPADE));
        cards.add(new WellsFargo(3, HEART));
        cards.add(new Barrel(12, SPADE));
        cards.add(new Barrel(13, SPADE));
        cards.add(new Dynamite(2, HEART));
        cards.add(new Jail(4, HEART));
        cards.add(new Jail(10, SPADE));
        cards.add(new Jail(11, SPADE));
        cards.add(new Mustang(8, HEART));
        cards.add(new Mustang(9, HEART));
        cards.add(new Remington(13, CLUB));
        cards.add(new RevCarabine(14, CLUB));
        cards.add(new Schofield(11, CLUB));
        cards.add(new Schofield(12, CLUB));
        cards.add(new Schofield(13, SPADE));
        cards.add(new Scope(14, SPADE));
        cards.add(new Volcanic(10, CLUB));
        cards.add(new Volcanic(10, SPADE));
        cards.add(new Winchester(8, SPADE));
        Collections.shuffle(cards);
        drawPile = new ArrayDeque<>(cards);
    }

    /**
     * Retire et renvoie la première carte de la pioche.
     * Si la pioche est vide, toutes les cartes de la défausse sont placées dans la pioche, qui est ensuite mélangée
     * avant de renvoyer la première carte.
     *
     * @return la carte qui a été piochée
     */
    public Card drawCard() {
        if (drawPile.isEmpty()) {
            List<Card> cards = new ArrayList<>(discardPile);
            discardPile.clear();
            Collections.shuffle(cards);
            drawPile = new ArrayDeque<>(cards);
        }
        return drawPile.pop();
    }

    public void addToDrawPile(Card card) {
        drawPile.push(card);
    }

    /**
     * Ajoute une carte sur le dessus de la pile de défausse
     *
     * @param c la carte à ajouter
     */
    public void addToDiscard(Card c) {
        discardPile.push(c);
    }

    /**
     * Retire un joueur du jeu. Si le joueur à retirer est le joueur courant, on passe au joueur suivant.
     * Après avoir retiré un joueur, on met à jour la partie (ce qui déterminera si elle est terminée ou pas).
     */
    public void removePlayer(Player p) {
        if (p == currentPlayer) {
            int index = players.indexOf(p);
            currentPlayer = players.get((index + 1) % players.size());
        }
        players.remove(p);
        updateGameFinished();
    }

    public boolean isFinished() {
        return finished;
    }

    public List<Player> getWinners() {
        return winners;
    }

    /**
     * Teste si la partie est terminée et met à jour les attributs {@code finished} et {@code winners}.
     */
    private void updateGameFinished() {
        if(!players.contains(sheriffPlayer)) {
            if(players.contains(renegadePlayer)&&players.size()==1) {
                winners.add(renegadePlayer);
            } else {
                winners.addAll(outlawPlayers);
            }
            finished = true;
        }
        for(Player player : players) {
            if (outlawPlayers.contains(player)) {
                return;
            }
        }
        if(!players.contains(renegadePlayer)) {
            winners.add(sheriffPlayer);
            winners.addAll(deputyPlayers);
            finished = true;
        }
    }

    /**
     * Affiche une chaîne de caractères sur la sortie standard
     *
     * @param message chaîne de caractères à afficher
     */
    public void print(String message) {
        System.out.print(message);
    }

    /**
     * Affiche une ligne sur la sortie standard
     *
     * @param message chaîne de caractères à afficher (la fonction ajoute automatiquement un retour à la ligne après)
     */
    public void println(String message) {
        print(message + "\n");
    }

    /**
     * Lit une ligne de l'entrée standard
     * C'est cette méthode qui doit être appelée à chaque fois qu'on veut lire
     * l'entrée clavier de l'utilisateur (par exemple dans {@code Player.choose}), ce
     * qui permet de n'avoir qu'un seul Scanner pour tout le programme
     *
     * @return une chaîne de caractères correspondant à la ligne suivante de
     * l'entrée standard (sans le retour à la ligne final)
     */
    public String readLine() {
        return scanner.nextLine();
    }

    /**
     * Renvoie une représentation de l'état de la partie sous forme d'une chaîne de caractères.
     * Cette représentation comporte
     * - le nom du joueur dont c'est le tour
     * - la taille de la pile de pioche
     * - la taille de la pile de défausse
     * - les 5 cartes du dessus de la pile de défausse
     * <p>
     * On pourrait par exemple avoir l'affichage suivant:
     * <pre>
     * -- Tour de Toto --
     * Pioche (45)
     * Défausse (10): ..., Bang! (10♠), Missed! (8♦), Cat Balou (K♥), Beer (7♥), Wells Fargo (10♣)
     * </pre>
     */
    @Override
    public String toString() {
        int n = discardPile.size();
        String discardString;
        if (n > 5) {
            Iterator<Card> iterator = discardPile.iterator();
            StringJoiner joiner = new StringJoiner(", ");
            for (int i = 0; i < 5; i++) {
                joiner.add(iterator.next().toString());
            }
            discardString = String.format(": ..., %s", joiner.toString());
        } else if (n > 0) {
            StringJoiner joiner = new StringJoiner(", ");
            for (Card c : discardPile) {
                joiner.add(c.toString());
            }
            discardString = joiner.toString();
        } else {
            discardString = "";
        }

        String generalInfo = String.format("-- Tour de %s --\n", currentPlayer.getName()) +
                String.format("Pioche (%d)\n", drawPile.size()) +
                String.format("Défausse (%d)%s\n\n", discardPile.size(), discardString);

        StringJoiner joiner = new StringJoiner("\n");
        int index = players.indexOf(currentPlayer);
        for (Player p : players.subList(index, players.size())) {
            joiner.add(p.toString());
        }
        for (Player p : players.subList(0, index)) {
            joiner.add(p.toString());
        }
        return generalInfo + joiner;
    }

    /**
     * Renvoie une représentation de l'état de la partie au format JSON
     * <p>
     * La partie est représentée par un dictionnaire contenant les clés suivantes :
     * - "turn_player": le nom du joueur dont c'est le tour
     * - "draw_pile": la taille de la pile de pioche
     * - "discard_pile": une liste des cartes dans la pile de défausse
     * - "players": une liste des représentations JSON des joueurs de la partie
     */
    public String toJSON() {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add(String.format("\"turn_player\": \"%s\"", currentPlayer.getName()));
        joiner.add(String.format("\"draw_pile\": %d", drawPile.size()));
        // défausse
        StringJoiner cardsJoiner = new StringJoiner(", ");
        for (Card c : discardPile) {
            cardsJoiner.add(c.toJSON());
        }
        joiner.add(String.format("\"discard_pile\": [%s]", cardsJoiner));
        // joueurs
        StringJoiner playersJoiner = new StringJoiner(", ");
        for (Player p : players) {
            playersJoiner.add(p.toJSON());
        }
        joiner.add(String.format("\"players\": [%s]", playersJoiner));
        return "{" + joiner.toString() + "}";
    }

    /**
     * Envoie l'état de la partie pour affichage aux joueurs avant de faire un choix
     *
     * @param activePlayer le joueur qui doit faire un choix
     * @param instruction  l'instruction qui est donnée au joueur
     * @param buttons      labels des choix proposés s'il y en a
     * @param canPass      indique si le joueur peut passer sans faire de choix
     */
    public void prompt(Player activePlayer, String instruction, List<String> buttons, boolean canPass) {
        println("");
        println(toString());
        if (buttons.isEmpty()) {
            println(String.format(">>> %s: %s <<<", activePlayer.getName(), instruction));
        } else {
            StringJoiner joiner = new StringJoiner(" / ");
            for (int i = 0; i < buttons.size(); i++) {
                joiner.add(String.format("%d: %s", i + 1, buttons.get(i)));
            }
            println(String.format(">>> %s: %s [%s] <<<", activePlayer.getName(), instruction, joiner));
        }
    }

    /**
     * Retire une carte de la pile de défausse
     *
     * @param card Carte à retirer
     */
    public void removeFromDiscard(Card card) {
        discardPile.remove(card);
    }

    /**
     * @return la carte se trouvant sur le dessus de la pile de défausse (ou `null` si la pile est vide)
     */
    public Card getTopOfDiscardPile() {
        if (!discardPile.isEmpty())
            return discardPile.peek();
        return null;
    }
}