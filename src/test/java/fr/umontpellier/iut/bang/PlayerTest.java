package fr.umontpellier.iut.bang;

import fr.umontpellier.iut.bang.cards.Card;
import fr.umontpellier.iut.bang.cards.CardSuit;
import fr.umontpellier.iut.bang.cards.Mustang;
import fr.umontpellier.iut.bang.cards.Scope;
import fr.umontpellier.iut.bang.characters.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Game minimalGame;
    private Player p1, p2, p3, p4;
    private List<Player> players;

    /*@BeforeEach
    void setup() {
        players = Game.makePlayers(new String[]{"Toto", "Titi", "Tutu"});
    }*/

    @Test
    void test_distanceTo_PaulRegret() {
        players = Game.makePlayers(new String[]{"Toto", "Titi", "Tutu"});
        p4 = new Player("Tata", new PaulRegret(), Role.SHERIFF);
        players.add(3,p4);

        minimalGame = new Game(players);

        p1 = minimalGame.getPlayers().get(0);
        p2 = minimalGame.getPlayers().get(1);
        p3 = minimalGame.getPlayers().get(2);

        assertEquals(2,p3.distanceTo(p4));
        assertEquals(2,p1.distanceTo(p4));
        assertEquals(3,p2.distanceTo(p4));
    }

    @Test
    void test_distanceTo_RoseDoolan() {
        players = Game.makePlayers(new String[]{"Toto", "Titi", "Tutu"});
        p4 = new Player("Tata", new RoseDoolan(), Role.SHERIFF);
        players.add(3,p4);

        minimalGame = new Game(players);

        p1 = minimalGame.getPlayers().get(0);
        p2 = minimalGame.getPlayers().get(1);
        p3 = minimalGame.getPlayers().get(2);

        assertEquals(1,p4.distanceTo(p3));
        assertEquals(1,p4.distanceTo(p2));
        assertEquals(1,p4.distanceTo(p1));
    }

    @Test
    void test_distanceTo_Scope() {
        players = Game.makePlayers(new String[]{"Toto", "Titi", "Tutu"});
        p4 = new Player("Tata", new BlackJack(), Role.SHERIFF);
        players.add(3,p4);

        minimalGame = new Game(players);

        p1 = minimalGame.getPlayers().get(0);
        p2 = minimalGame.getPlayers().get(1);
        p3 = minimalGame.getPlayers().get(2);

        Card scope = new Scope(10, CardSuit.SPADE);
        p4.getHand().add(scope);
        p4.playFromHand(scope);

        assertEquals(1,p4.distanceTo(p3));
        assertEquals(1,p4.distanceTo(p2));
        assertEquals(1,p4.distanceTo(p1));
    }

    @Test
    void test_distanceTo_Mustang() {
        players = Game.makePlayers(new String[]{"Toto", "Titi", "Tutu"});
        p4 = new Player("Tata", new BlackJack(), Role.SHERIFF);
        players.add(3,p4);

        minimalGame = new Game(players);

        p1 = minimalGame.getPlayers().get(0);
        p2 = minimalGame.getPlayers().get(1);
        p3 = minimalGame.getPlayers().get(2);

        Card mustang = new Mustang(10, CardSuit.SPADE);
        p4.getHand().add(mustang);
        p4.playFromHand(mustang);

        assertEquals(2,p3.distanceTo(p4));
        assertEquals(3,p2.distanceTo(p4));
        assertEquals(2,p1.distanceTo(p4));
    }

    @Test
    void test_getPlayersInRange() {
        players = new ArrayList<>();
        p1 = new Player("toto", new CalamityJanet(), Role.OUTLAW);
        p2 = new Player("titi", new Jourdonnais(), Role.SHERIFF);
        p3 = new Player("tutu", new BartCassidy(), Role.RENEGADE);
        p4 = new Player("tata", new BlackJack(), Role.OUTLAW);
        players.add(0,p1);
        players.add(1,p2);
        players.add(2,p3);
        players.add(3,p4);

        minimalGame = new Game(players);

        List<Player> data = new ArrayList<>();
        data.add(p1);
        data.add(p3);

        List<Player> test = p2.getPlayersInRange(1);

        assertTrue(test.containsAll(data)&&data.containsAll(test));

        players.remove(p3);
        data.remove(p3);
        data.add(p4);

        test = p2.getPlayersInRange(1);

        assertTrue(test.containsAll(data)&&data.containsAll(test));
        }
}
