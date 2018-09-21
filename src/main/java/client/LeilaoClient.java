package client;

import com.sun.jmx.snmp.tasks.ThreadService;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class LeilaoClient {

    public static void main(String args[]) throws IOException {
        Client client = new Client();


        int op = 0;
        Double valor = 0.0;
        Scanner ler = new Scanner(System.in);

        System.out.println("Sistema do Leilao:");
        System.out.println("Digite seu nome:");
        final String nome = ler.nextLine();

        client.startConnection("127.0.0.1", 5000);

        String userMode = "";

        while (!userMode.equals("NORMAL") && !userMode.equals("LEILOEIRO")) {
            System.out.println("Por favor, digite o modo desejado (NORMAL | LEILOEIRO)");
            userMode = ler.nextLine();
        }

        if (userMode.equals("NORMAL")) {
            runNormalClient(ler, client, nome);
        } else {
            runLeiloeiroClient(ler, client, nome);
        }

    }

    private static void runNormalClient(final Scanner ler, final Client client, final String nome) throws IOException {
        int op = 0;

        System.out.println(client.sendMessage("[NORMAL]".concat(nome)));

        do {
            System.out.println("Escolha uma opção:");
            System.out.println("\t" + "1 - Enviar lance" + "\t" + "2 - Ver lance atual" + "\t" + "3 - Sair do Leilão");
            op = ler.nextInt();

            switch (op) {
                case 1:
                    System.out.println("Digite o valor do seu lance:");
                    Double valorDoLance = ler.nextDouble();
                    System.out.println(client.sendMessage(createMessage(nome).concat("DARLANCE(".concat(valorDoLance.toString()).concat(")"))));
                    break;

                case 2:
                    System.out.println(client.sendMessage(commandGetLast()));
                    break;

                case 3:
                    System.out.println("Você saiu do leilão!");
                    client.stopConnection();
                    op = 4;
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (op != 4);
        ler.close();
    }

    private static void runLeiloeiroClient(final Scanner ler, final Client client, final String nome) throws IOException {
        int op = 0;

        System.out.println(client.sendMessage("[LEILOEIRO]".concat(nome)));

        do {
            System.out.println("Escolha uma opcao:");
            System.out.println("\t" + "1 - Ver lance atual" + "\t" + "2 - Acabar leilao" + "\t" + "3 - Sair do Leilao");
            op = ler.nextInt();

            switch (op) {
                case 1:
                    System.out.println(client.sendMessage(commandGetLast()));
                    break;

                case 2:
                    System.out.println(client.sendMessage(createMessage(nome).concat("ACABAR")));
                    break;

                case 3:
                    System.out.println(client.sendMessage(createMessage(nome).concat("ACABAR")));
                    op = 4;
                    break;

                default:
                    System.out.println("Opcao invalida!");
                    break;
            }

        } while (op != 4);
        ler.close();
    }

    private static final String createMessage(final String username) {
        return "[".concat(username).concat("]");
    }

    private static final String commandGetLast() {
        return "VERLANCE";
    }
}
