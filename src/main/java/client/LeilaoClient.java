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
		
		System.out.println("Sistema do Leil�o:");

		do {
			System.out.println("Escolha uma op��o:");
			System.out.println("\t" + "1 - Enviar lance" + "\t" + "2 - Encerrar Leil�o");
			op = ler.nextInt();

			switch (op) {
			case 1:
				/**valor = ler.nextDouble();
				if (valorNoservidor > valor){
				 * client.sendMessage("2-")
				System.out.println("J� existe uma forta maior. D� um lance maior que:" + valorNoServidor);
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
				System.out.println("Op��o inv�lida!");
				break;
			}

		} while (op != 2);
		ler.close();

        
        /**System.out.println(client.sendMessage("cavajo biba"));

        System.out.println(client.sendMessage("."));
		*/

    }
}
