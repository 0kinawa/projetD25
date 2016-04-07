package fr.univtln.sgrassell418.d25.projet.rest;


import org.glassfish.grizzly.http.server.HttpServer;

import java.io.IOException;
import java.text.ParseException;

public class App {

    private static String SERVER_HOST = "10.42.4.118";
    private static int SERVER_PORT_REST = 9998;
    private static int SERVER_PORT_WS = 8080;

    public static void main(String[] args) throws IOException {

        try{
            EventManager.getInsance().insertRandom();
        }catch (ParseException e){
            e.printStackTrace();
        }

        // Grizzly 2 initialization
        HttpServer httpServerRest = RESTServer.startServer();

        System.out.println(String.format("App avaiable at "
                        + "%s\nHit enter to stop it...",
                        RESTServer.BASE_URI));
//        WsServer.start();
        System.in.read();
        httpServerRest.stop();
    }

    public static String getServerHost() {
        return SERVER_HOST;
    }

    public static int getServerPortRest() {
        return SERVER_PORT_REST;
    }

    public static int getServerPortWs() {
        return SERVER_PORT_WS;
    }
}