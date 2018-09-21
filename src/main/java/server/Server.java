package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


class Lance {
    private String nome;
    private Double valor;

    public Lance(String nome, Double valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Lance{");
        sb.append("nome='").append(nome).append('\'');
        sb.append(", valor=").append(valor);
        sb.append('}');
        return sb.toString();
    }
}


public class Server {

    private static ServerSocket serverSocket;

    private static Boolean isActive;
    private static Lance last;
    private static List<String> connectedUsers;
    private static List<String> connectedAdmins;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        connectedUsers = new ArrayList<String>();
        connectedAdmins = new ArrayList<String>();
        isActive = true;

        while (true)
            new EchoClientHandler(serverSocket.accept()).start();
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                   switchCommand(inputLine, out);
                }

                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Deu treta" + e);
            }
        }
    }

    private static void switchCommand(final String command, final PrintWriter out) throws IOException {

        if (!isActive){
            responseToUser(command, "O leilão não está ativo.");
            return;
        }

        if (command.contains("[NORMAL]")) {
            connectedUsers.add(command.split("]")[1]);
            out.println(responseToUser(command, "Você entrou no leilão no modo PARTICIPANTE"));
        } else if (command.contains("[LEILOEIRO]")) {
            connectedAdmins.add(command.split("]")[1]);
            out.println(responseToUser(command, "Você entrou no leilão no modo LEILOEIRO"));
        } else if (command.contains("VERLANCE")) {
            out.println(getWinner());
        } else if (command.contains("ACABAR")) {
            final String name = getUsername(command);
            if (connectedAdmins.contains(name)) {
                out.println(responseToAll("Leilão cancelado pelo "+name+"!"));
                out.println(responseToAll("Lance vencedor: ".concat(getWinner())));
                isActive = false;
            } else {
                out.println(responseToUser(command,"Você não tem permissão para acabar com o leilão."));
            }
        } else if (command.contains("DARLANCE")) {
            saveLance(command, out);
        } else {
            System.out.println("Commando desconhecido: ".concat(command));
        }
    }

    private static String getWinner() {
        return last != null ? last.toString() : "Sem lances registrados!";
    }

    private static String getUsername(final String command) {
        return command.split("\\[")[1].split("]")[0];
    }

    private static void saveLance(final String command, final PrintWriter out) {
        try {
            Lance lance = new Lance(getUsername(command), new Double(command.split("DARLANCE\\(")[1].split("\\)")[0]));

            if (last == null || last.getValor() < lance.getValor()) {
                last = lance;
                out.println(responseToUser(command, "O seu lance foi recebido com sucesso."));
            } else {

                out.println(responseToUser(command, "O lance que você tentou não foi aceito."));
            }
        } catch(Exception e) {
            out.println(responseToUser(command, "Não foi possível salvar o lance do usuário."));
        }
    }

    private static String responseToUser(final String command, final String message) {
        return "[".concat(getUsername(command)).concat("]").concat(message);
    }

    private static String responseToAll(final String message) {
        return "[".concat("ALL").concat("]").concat(message);
    }
}