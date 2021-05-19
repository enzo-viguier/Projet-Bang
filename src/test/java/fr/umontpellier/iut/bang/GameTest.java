package fr.umontpellier.iut.bang;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game minimalGame;
    private Player p1, p2, p3, p4, sherrif, renegat;
    ArrayList<Player> horslaloi;

    @BeforeEach
    void disableConsole() {
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int arg0) {

            }
        }));

    }

    @BeforeEach
    void setUp() {
        List<Player> players = Game.makePlayers(new String[]{"Toto", "Titi", "Tutu", "Tata"});
        minimalGame = new Game(players);

        p1 = minimalGame.getPlayers().get(0);
        p2 = minimalGame.getPlayers().get(1);
        p3 = minimalGame.getPlayers().get(2);
        p4 = minimalGame.getPlayers().get(3);

    }

    //@Disabled
    @Test
    void testGetPlayerDistance() {
        assertEquals(1, minimalGame.getPlayerDistance(p1, p2));
        assertEquals(2, minimalGame.getPlayerDistance(p1, p3));
        assertEquals(1, minimalGame.getPlayerDistance(p1, p4));
        assertEquals(2, minimalGame.getPlayerDistance(p2, p4));
    }

    @BeforeEach
    void test_updateGameFinished() {
        horslaloi = new ArrayList<>();
        for (int i=0;i<4;i++) {
            Player current = minimalGame.getPlayers().get(i);
            if(current.getRole()==Role.OUTLAW) {
                horslaloi.add(current);
            }
            if(current.getRole()==Role.SHERIFF) {
                sherrif = current;
            }
            if(current.getRole()==Role.RENEGADE) {
                renegat = current;
            }
        }
    }

    @Test
    void updateGameFinished_case1() {
        minimalGame.removePlayer(sherrif);
        assertEquals(horslaloi,minimalGame.getWinners());
        assertTrue(minimalGame.isFinished());
    }


    @Test
    void updateGameFinished_case2() {
        for(int i=0;i< horslaloi.size();i++) {
            minimalGame.removePlayer(horslaloi.get(i));
        }
        minimalGame.removePlayer(sherrif);
        assertEquals(renegat,minimalGame.getWinners().get(0));
        assertTrue(minimalGame.isFinished());
    }

    @Test
    void updateGameFinished_case3() {
        for(int i=0;i<horslaloi.size();i++) {
            minimalGame.removePlayer(horslaloi.get(i));
        }
        minimalGame.removePlayer(renegat);
        assertEquals(sherrif,minimalGame.getWinners().get(0));
        assertTrue(minimalGame.isFinished());
    }

    @Test
    void updateGameFinished_case4() {
        minimalGame.removePlayer(horslaloi.get(0));
        minimalGame.removePlayer(renegat);
        assertTrue(minimalGame.getWinners().isEmpty());
        assertFalse(minimalGame.isFinished());
    }
}