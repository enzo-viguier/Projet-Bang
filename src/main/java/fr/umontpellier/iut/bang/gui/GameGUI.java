package fr.umontpellier.iut.bang.gui;

import fr.umontpellier.iut.bang.Game;
import fr.umontpellier.iut.bang.Player;

import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.LinkedBlockingQueue;

public class GameGUI extends Game implements Runnable {

    private LinkedBlockingQueue<String> inputQueue;

    public GameGUI(List<Player> players) {
        super(players);
        this.inputQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void prompt(Player activePlayer, String instruction, List<String> buttons, boolean canPass) {
        super.prompt(activePlayer, instruction, buttons, canPass);
        StringJoiner jsonJoiner = new StringJoiner(", ");
        StringJoiner buttonsJoiner = new StringJoiner(", ");
        for (String buttonLabel : buttons) {
            buttonsJoiner.add("\"" + buttonLabel + "\"");
        }

        jsonJoiner.add(String.format("\"game\": %s", toJSON()));
        jsonJoiner.add(String.format("\"active_player\": \"%s\"", activePlayer.getName()));
        jsonJoiner.add(String.format("\"instruction\": \"%s\"", instruction));
        jsonJoiner.add(String.format("\"buttons\": [%s]", buttonsJoiner));
        jsonJoiner.add(String.format("\"can_pass\": %s", canPass));
        BangServer.updateGameState("{" + jsonJoiner + "}");
    }

    public void updateUI() {
        BangServer.updateGameState(toJSON());
    }

    public void addInput(String message) {
        inputQueue.add(message);
    }

    @Override
    public String readLine() {
        try {
            return inputQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
