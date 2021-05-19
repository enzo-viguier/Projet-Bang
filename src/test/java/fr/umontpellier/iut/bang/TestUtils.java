package fr.umontpellier.iut.bang;

import fr.umontpellier.iut.bang.cards.Card;

import java.lang.reflect.Field;
import java.util.Deque;

public class TestUtils {
    public static Deque<Card> getDrawPile(Game game) {
        try {
            Field field = Game.class.getDeclaredField("drawPile");
            field.setAccessible(true);
            return (Deque<Card>) field.get(game);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Deque<Card> getDiscardPile(Game game) {
        try {
            Field field = Game.class.getDeclaredField("discardPile");
            field.setAccessible(true);
            return (Deque<Card>) field.get(game);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
