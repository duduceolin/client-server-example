package client;

import java.io.IOException;

public class LeilaoClient {

    public static void main(String args[]) throws IOException {
        Client client = new Client();

        client.startConnection("127.0.0.1", 5000);

        System.out.println(client.sendMessage("cavajo biba"));

        System.out.println(client.sendMessage("."));

        client.stopConnection();

    }
}
