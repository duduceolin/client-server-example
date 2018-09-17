package client;

import java.io.IOException;
import java.lang.invoke.SwitchPoint;
import java.util.Scanner;

import server.Server;

public class LeiloeiroClient {

	public static void main(String args[]) throws IOException {
		Client client = new Client();
		Server server = new Server();
		int op = 0;
		Scanner ler = new Scanner(System.in);
		client.startConnection("127.0.0.1", 5000);

		System.out.println("Sistema de Controle do Leilão:");

		do {
			System.out.println("Escolha uma opção:");
			System.out.println("\t" + "1 - Ver maior lance" + "\t" + "2 - Encerrar Leilão");
			op = ler.nextInt();

			switch (op) {
			case 1:
				System.out.println("O maior lance atual é:" + client.sendMessage("2-") + "\t" + "Efetuada por:");
				break;

			case 2:
				System.out.println("Leilão encerrado!" + "\n" + "O vencedor foi" + "\t" + "Vendido pelo valor de:");
				client.stopConnection();
				break;

			default:
				System.out.println("Opção inválida!");
				break;
			}

		} while (op != 2);
		ler.close();

		/**
		 * System.out.println(client.sendMessage("cavajo biba"));
		 * 
		 * System.out.println(client.sendMessage("."));
		 */

	}
}
