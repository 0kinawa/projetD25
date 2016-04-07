package fr.univtln.sgrassell418.d25.projet.rest;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;


import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

/**
 * Created by stephane on 05/04/16.
 */
public class RESTServer {

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

    public static final URI BASE_URI = UriBuilder.fromUri("http://"+App.getServerHost()).port(getPort(App.getServerPortRest(), "jersey.test.port")).build();

    public static HttpServer startServer() throws IOException {
        ResourceConfig resourceConfig = new PackagesResourceConfig("fr.univtln.sgrassell418.d25.projet.rest");

        System.out.println("Starting grizzly2...");

        return GrizzlyServerFactory.createHttpServer(BASE_URI, resourceConfig);
    }

}
