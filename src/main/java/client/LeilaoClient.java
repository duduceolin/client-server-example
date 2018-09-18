package client;

import java.io.IOException;
import java.util.Scanner;

public class LeilaoClient {

    public static void main(String args[]) throws IOException {
        Client client = new Client();

        client.startConnection("127.0.0.1", 5000);

        int op = 0;
        Double valor = 0.0;
		Scanner ler = new Scanner(System.in);
		
		System.out.println("Sistema do Leilão:");

		do {
			System.out.println("Escolha uma opção:");
			System.out.println("\t" + "1 - Enviar lance" + "\t" + "2 - Encerrar Leilão");
			op = ler.nextInt();

			switch (op) {
			case 1:
				/**valor = ler.nextDouble();
				if (valorNoservidor > valor){
				 * client.sendMessage("2-")
				System.out.println("Já existe uma forta maior. Dê um lance maior que:" + valorNoServidor);
				}
				else {
					client.sendMessage(valor);
				System.out.println("Lance efetuado com sucesso!");
				}*/
				break;

			case 2:
				System.out.println("Sistema encerrado!");
				client.stopConnection();
				break;

			default:
				System.out.println("Opção inválida!");
				break;
			}

		} while (op != 2);
		ler.close();

        
        /**System.out.println(client.sendMessage("cavajo biba"));

        System.out.println(client.sendMessage("."));
		*/

    }
}
