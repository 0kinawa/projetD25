package fr.univtln.sgrassell418.d25.projet.rest;

import org.glassfish.tyrus.server.Server;

import javax.websocket.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephane on 05/04/16.
 */

public class WsServer {

    private static List<Session> sessions = new ArrayList<>();


    private static int getPort(int defaultPort, String systemProperty) {
        //grab port from environment, otherwise fall back to default port in param
        String httpPort = System.getProperty(systemProperty);
        if (null != httpPort) {
            try {
                return Integer.parseInt(httpPort);
            } catch (NumberFormatException e) {
            }
        }
        return defaultPort;
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("New client connected in session "+session.getId());
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("This client closed the session "+session.getId());
        sessions.remove(session);
    }

    public void pushAllSessions(){
        for (Session s : sessions){
            try {
                s.getBasicRemote().sendObject(0);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        }
    }


    public static void start(){
        Server server =
                new Server(App.getServerHost(), App.getServerPortWs(), "/", null, WsServer.class);

        try {
            server.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please press a key to stop the server.");
            reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }

}
