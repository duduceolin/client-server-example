package server;

import java.io.IOException;

public class LeilaoServer {

    public static void main(String args[]) throws IOException {
        Server server = new Server();
        server.start(5000);
    }
}
