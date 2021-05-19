package fr.umontpellier.iut.bang;

import fr.umontpellier.iut.bang.cards.*;
import fr.umontpellier.iut.bang.characters.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CharactersTest {
    Deque<Card> discardPile, drawPile;
    Player p1, p2, p3, p4;
    IOGame game;

    private void makeGameWithCharacter(BangCharacter character) {
        List<Player> players = new ArrayList<>();
        players.add(new Player("p1", character, Role.OUTLAW));
        players.add(new Player("p2", new Nobody(), Role.RENEGADE));
        players.add(new Player("p3", new Nobody(), Role.DEPUTY));
        players.add(new Player("p4", new Nobody(), Role.SHERIFF));
        game = new IOGame(players);
        p1 = game.getPlayers().get(0);
        p2 = game.getPlayers().get(1);
        p3 = game.getPlayers().get(2);
        p4 = game.getPlayers().get(3);
        for (Player p : game.getPlayers()) {
            p.getHand().clear();
        }
        discardPile = TestUtils.getDiscardPile(game);
        drawPile = TestUtils.getDrawPile(game);
    }

    @BeforeEach
    void disableConsole() {
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int arg0) {
            }
        }));
    }

    //@Disabled
    @Test
    void testBartCassidy() {
        makeGameWithCharacter(new BartCassidy());
        assertEquals(4, p1.getHealthPointsMax());
        game.setInput("p1");
        Card bang = new Bang(1, CardSuit.HEART);
        Card jail = new Jail(1, CardSuit.SPADE);

        p2.getHand().add(bang);
        drawPile.push(jail);
        p2.playFromHand(bang);
        assertTrue(p1.getHand().contains(jail));
    }

    @Test
    void testBlackJackPioche2Cartes() {
        makeGameWithCharacter(new BlackJack());
        assertEquals(4, p1.getHealthPointsMax());
        game.setInput("");
        Card bang = new Bang(1, CardSuit.HEART);
        Card beer = new Beer(1, CardSuit.SPADE);
        Card volcanic = new Volcanic(1, CardSuit.DIAMOND);

        drawPile.push(volcanic);
        drawPile.push(beer);
        drawPile.push(bang);
        p1.playTurn();

        assertEquals(2, p1.getHand().size());
        assertTrue(p1.getHand().contains(bang));
        assertTrue(p1.getHand().contains(beer));
    }

    @Test
    void testBlackJackPioche3Cartes() {
        makeGameWithCharacter(new BlackJack());
        assertEquals(4, p1.getHealthPointsMax());
        game.setInput("");
        Card bang = new Bang(1, CardSuit.SPADE);
        Card beer = new Beer(1, CardSuit.HEART);
        Card volcanic = new Volcanic(1, CardSuit.DIAMOND);

        drawPile.push(volcanic);
        drawPile.push(beer);
        drawPile.push(bang);
        p1.playTurn();

        assertEquals(3, p1.getHand().size());
        assertTrue(p1.getHand().contains(bang));
        assertTrue(p1.getHand().contains(beer));
        assertTrue(p1.getHand().contains(volcanic));
    }

    //@Disabled :
    @Test
    void testCalamityJanetJoueMissedCommeUnBang() {
        makeGameWithCharacter(new CalamityJanet());
        assertEquals(4, p1.getHealthPointsMax());
        game.setInput("Missed!", "p2", "");
        Card missed = new Missed(1, CardSuit.HEART);

        p1.getHand().add(missed);
        p1.playTurn();
        assertEquals(3, p2.getHealthPoints());
        assertTrue(discardPile.contains(missed));
    }

    //@Disabled
    @Test
    void testCalamityJanetJoueBangCommeUnMissed() {
        makeGameWithCharacter(new CalamityJanet());
        assertEquals(4, p1.getHealthPointsMax());
        game.setInput("p1", "Bang!");
        Card bang1 = new Bang(1, CardSuit.HEART);
        Card bang2 = new Bang(1, CardSuit.HEART);

        p1.getHand().add(bang1);
        p2.getHand().add(bang2);
        p2.playFromHand(bang2);
        assertEquals(4, p1.getHealthPoints());
        assertTrue(discardPile.contains(bang1));
        assertTrue(discardPile.contains(bang2));
    }

    //@Disabled
    @Test
    void testElGringo() {
        makeGameWithCharacter(new ElGringo());
        assertEquals(3, p1.getHealthPointsMax());
        game.setInput("p1");
        Card bang = new Bang(1, CardSuit.HEART);
        Card beer = new Beer(1, CardSuit.SPADE);

        p2.getHand().add(bang);
        p2.getHand().add(beer);
        p2.playFromHand(bang);
        assertEquals(2, p1.getHealthPoints());
        assertTrue(p1.getHand().contains(beer));
        assertTrue(p2.getHand().isEmpty());
    }

    //@Disabled
    @Test
    void testJesseJonesPiocheDansLaMainDunJoueur() {
        makeGameWithCharacter(new JesseJones());
        assertEquals(4, p1.getHealthPointsMax());
        game.setInput("p2", "");
        Card bang = new Bang(1, CardSuit.HEART);
        Card beer = new Beer(1, CardSuit.SPADE);
        Card barrel = new Barrel(1, CardSuit.DIAMOND);

        p2.getHand().add(bang);
        drawPile.push(beer);
        drawPile.push(barrel);
        p1.playTurn();
        assertEquals(2, p1.getHand().size());
        assertTrue(p2.getHand().isEmpty());
        assertTrue(p1.getHand().contains(barrel));
        assertTrue(p1.getHand().contains(bang));
    }

    //@Disabled
    @Test
    void testJesseJonesNePiochePasDansLaMainDunJoueur() {
        makeGameWithCharacter(new JesseJones());
        assertEquals(4, p1.getHealthPointsMax());
        game.setInput("", "");
        Card bang = new Bang(1, CardSuit.HEART);
        Card beer = new Beer(1, CardSuit.SPADE);
        Card barrel = new Barrel(1, CardSuit.DIAMOND);

        p2.getHand().add(bang);
        drawPile.push(beer);
        drawPile.push(barrel);
        p1.playTurn();
        assertEquals(2, p1.getHand().size());
        assertTrue(p2.getHand().contains(bang));
        assertTrue(p1.getHand().contains(barrel));
        assertTrue(p1.getHand().contains(beer));
    }

    //@Disabled
    @Test
    void testJoudonnaisDegaineCoeur() {
        makeGameWithCharacter(new Jourdonnais());
        assertEquals(4, p1.getHealthPointsMax());
        game.setInput("p1");
        Card bang = new Bang(1, CardSuit.CLUB);
        Card coeur = new Volcanic(5, CardSuit.HEART);

        drawPile.push(coeur);
        p2.getHand().add(bang);
        p2.playFromHand(bang);
        assertEquals(4, p1.getHealthPoints());
        assertTrue(discardPile.contains(bang));
        assertTrue(discardPile.contains(coeur));
    }

    //@Disabled
    @Test
    void testJoudonnaisDegainePique() {
        makeGameWithCharacter(new Jourdonnais());
        assertEquals(4, p1.getHealthPointsMax());
        game.setInput("p1");
        Card bang = new Bang(1, CardSuit.CLUB);
        Card pique = new Volcanic(5, CardSuit.SPADE);

        drawPile.push(pique);
        p2.getHand().add(bang);
        p2.playFromHand(bang);
        assertEquals(3, p1.getHealthPoints());
        assertTrue(discardPile.contains(bang));
        assertTrue(discardPile.contains(pique));
    }

    //@Disabled
    @Test
    void testKitCarlson() {
        makeGameWithCharacter(new KitCarlson());
        assertEquals(4, p1.getHealthPointsMax());
        game.setInput("Beer", "");
        Card bang = new Bang(1, CardSuit.HEART);
        Card beer = new Beer(1, CardSuit.SPADE);
        Card barrel = new Barrel(1, CardSuit.DIAMOND);

        drawPile.push(bang);
        drawPile.push(beer);
        drawPile.push(barrel);
        p1.playTurn();
        assertEquals(2, p1.getHand().size());
        assertTrue(p1.getHand().contains(bang));
        assertTrue(p1.getHand().contains(barrel));
        assertEquals(beer, drawPile.peek());
    }

    //@Disabled
    @Test
    void testLuckyDuke() {
        makeGameWithCharacter(new LuckyDuke());
        assertEquals(4, p1.getHealthPointsMax());
        Card barrel = new Barrel(1, CardSuit.SPADE);
        Card bang = new Bang(1, CardSuit.DIAMOND);
        Card coeur = new Beer(1, CardSuit.HEART);
        Card pique = new Bang(1, CardSuit.SPADE);
        game.setInput("p1", coeur.getPokerString());

        drawPile.push(coeur);
        drawPile.push(pique);
        p1.getHand().add(barrel);
        p1.playFromHand(barrel);
        p2.getHand().add(bang);
        p2.playFromHand(bang);
        assertEquals(4, p1.getHealthPoints());
        assertTrue(discardPile.contains(coeur));
        assertTrue(discardPile.contains(pique));
    }

    //@Disabled
    @Test
    void testPaulRegret() {
        makeGameWithCharacter(new PaulRegret());
        assertEquals(3, p1.getHealthPointsMax());

        assertFalse(p2.getPlayersInRange(1).contains(p1));
        assertTrue(p2.getPlayersInRange(2).contains(p1));
        assertTrue(p1.getPlayersInRange(1).contains(p2));
    }

    //@Disabled
    @Test
    void testPedroRamirezPiocheDansLaDefausse() {
        makeGameWithCharacter(new PedroRamirez());
        assertEquals(4, p1.getHealthPointsMax());
        game.setInput("Dynamite", "");
        Card dynamite = new Dynamite(1, CardSuit.CLUB);
        Card bang = new Bang(1, CardSuit.HEART);
        Card beer = new Beer(1, CardSuit.SPADE);

        discardPile.push(dynamite);
        drawPile.push(bang);
        drawPile.push(beer);
        p1.playTurn();
        assertEquals(2, p1.getHand().size());
        assertTrue(p1.getHand().contains(dynamite));
        assertTrue(p1.getHand().contains(beer));
        assertEquals(bang, drawPile.peek());
    }

    //@Disabled
    @Test
    void testPedroRamirezNePiochePasDansLaDefausse() {
        makeGameWithCharacter(new PedroRamirez());
        assertEquals(4, p1.getHealthPointsMax());
        game.setInput("", "");
        Card dynamite = new Dynamite(1, CardSuit.CLUB);
        Card bang = new Bang(1, CardSuit.HEART);
        Card beer = new Beer(1, CardSuit.SPADE);

        discardPile.push(dynamite);
        drawPile.push(bang);
        drawPile.push(beer);
        p1.playTurn();
        assertEquals(2, p1.getHand().size());
        assertTrue(p1.getHand().contains(bang));
        assertTrue(p1.getHand().contains(beer));
        assertEquals(dynamite, discardPile.peek());
    }

    //@Disabled
    @Test
    void testRoseDoolan() {
        makeGameWithCharacter(new RoseDoolan());
        assertEquals(4, p1.getHealthPointsMax());

        assertTrue(p1.getPlayersInRange(1).contains(p2));
        assertTrue(p1.getPlayersInRange(1).contains(p3));
        assertTrue(p1.getPlayersInRange(1).contains(p4));
        assertFalse(p3.getPlayersInRange(1).contains(p1));
    }

    //@Disabled
    @Test
    void testSlabTheKiller() {
        makeGameWithCharacter(new SlabTheKiller());
        assertEquals(4, p1.getHealthPointsMax());
        game.setInput("p2", "Missed!", "Missed!");
        Card bang = new Bang(1, CardSuit.HEART);
        Card missed1 = new Missed(1, CardSuit.HEART);
        Card missed2 = new Missed(1, CardSuit.HEART);

        p1.getHand().add(bang);
        p2.getHand().add(missed1);
        p2.getHand().add(missed2);
        p1.playFromHand(bang);
        assertEquals(4, p2.getHealthPoints());
        assertTrue(discardPile.contains(bang));
        assertTrue(discardPile.contains(missed1));
        assertTrue(discardPile.contains(missed2));
        assertTrue(p2.getHand().isEmpty());
    }

    //@Disabled
    @Test
    void testSuzyLafayette() {
        makeGameWithCharacter(new SuzyLafayette());
        assertEquals(4, p1.getHealthPointsMax());
        game.setInput("p2");
        Card bang = new Bang(1, CardSuit.HEART);
        Card beer = new Missed(1, CardSuit.HEART);

        drawPile.push(beer);
        p1.getHand().add(bang);
        p1.playFromHand(bang);
        assertEquals(1, p1.getHand().size());
        assertTrue(p1.getHand().contains(beer));
    }

    //@Disabled
    @Test
    void testVultureSam() {
        makeGameWithCharacter(new VultureSam());
        assertEquals(4, p1.getHealthPointsMax());
        game.setInput("p2");
        Card bang = new Bang(1, CardSuit.HEART);
        Card duel = new Duel(1, CardSuit.HEART);
        Card scope = new Scope(1, CardSuit.HEART);

        p2.getHand().add(duel);
        p2.getHand().add(scope);
        p2.playFromHand(scope);
        p2.decrementHealth(3, null);
        p3.getHand().add(bang);
        p3.playFromHand(bang);
        assertEquals(2, p1.getHand().size());
        assertTrue(p1.getHand().contains(duel));
        assertTrue(p1.getHand().contains(scope));
    }

    //@Disabled
    @Test
    void testWillyTheKid() {
        makeGameWithCharacter(new WillyTheKid());
        assertEquals(4, p1.getHealthPointsMax());
        game.setInput("Bang!", "p2", "Bang!", "p2", "Bang!", "p2", "");
        Card bang1 = new Bang(1, CardSuit.HEART);
        Card bang2 = new Bang(1, CardSuit.HEART);
        Card bang3 = new Bang(1, CardSuit.HEART);

        p1.getHand().add(bang1);
        p1.getHand().add(bang2);
        p1.getHand().add(bang3);
        p1.playTurn();
        assertEquals(1, p2.getHealthPoints());
        assertEquals(2, p1.getHand().size());
        assertTrue(discardPile.contains(bang1));
        assertTrue(discardPile.contains(bang2));
        assertTrue(discardPile.contains(bang3));
    }
}