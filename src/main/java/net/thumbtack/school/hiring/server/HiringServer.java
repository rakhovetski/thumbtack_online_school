package net.thumbtack.school.hiring.server;

import net.thumbtack.school.hiring.server.config.ServerConfig;
import net.thumbtack.school.hiring.server.config.ServerSettings;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class HiringServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(HiringServer.class);

    private static Server jettyServer;

    private static void attachShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(HiringServer::stopServer));
    }

    public static void createServer() {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(ServerSettings.getRestHTTPPort()).build();
        ServerConfig config = new ServerConfig();
        config.register(ServerConfig.class);
        jettyServer = JettyHttpContainerFactory.createServer(baseUri, config);
        LOGGER.info("Server started at port " + ServerSettings.getRestHTTPPort());
    }

    public static void stopServer() {
        LOGGER.info("Stopping server");
        try {
            jettyServer.stop();
            jettyServer.destroy();
        } catch (Exception e) {
            LOGGER.error("Error stopping service", e);
            System.exit(1);
        }
        LOGGER.info("Server stopped");
    }


    public static void main(String[] args) {
        attachShutDownHook();
        createServer();
    }
}
