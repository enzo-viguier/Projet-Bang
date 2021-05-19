package fr.umontpellier.iut.bang.gui;

import fr.umontpellier.iut.bang.Game;
import fr.umontpellier.iut.bang.Player;
import org.glassfish.tyrus.server.Server;

import javax.websocket.DeploymentException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BangServer {
    private static ArrayList<Session> clients = new ArrayList<>();
    private static String gameState = "";
    private static GameGUI game;

    public static void main(String[] args) {
        // Création des joueurs
        List<Player> players = Game.makePlayers(new String[]{"Guybrush", "Largo", "LeChuck", "Elaine", "Herman", "Stan"});

        // Lancement de la partie
        game = new GameGUI(players);

        // Prépare le serveur websocket
        Server server = new Server("localhost", 3232, "/", WebSocketClient.class);
        try {
            server.start(); // lance le serveur
            new Thread(game).start();   // démarre le jeu

            Scanner scanner = new Scanner(System.in);
            while (true) {
                game.addInput(scanner.nextLine());
            }
        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }

    protected static void addInput(String message) {
        game.addInput(message);
    }

    protected static void updateGameState(String message) {
        gameState = message;
        // Envoie l'état de la partie à tous les clients
        try {
            for (Session session : clients) {
                session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void addClient(Session session) {
        BangServer.clients.add(session);
        // Envoie l'état actuel de la partie au nouveau client
        try {
            session.getBasicRemote().sendText(gameState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void removeClient(Session session) {
        BangServer.clients.remove(session);
    }
}
