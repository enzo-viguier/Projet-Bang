package fr.umontpellier.iut.bang.gui;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/")
public class WebSocketClient {
    @OnOpen
    public void onOpen(Session session) {
        BangServer.addClient(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        BangServer.addInput(message);
    }

    @OnClose
    public void onClose(Session session) {
        BangServer.removeClient(session);
    }

    @OnError
    public void onError(Throwable exception, Session session) {
        exception.printStackTrace();
        System.err.println("Error for client: " + session.getId());
    }
}
