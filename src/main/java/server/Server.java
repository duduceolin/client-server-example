package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket serverSocket;
	public static Double valor = 1000.0;

	public void start(int port) throws IOException {
		serverSocket = new ServerSocket(port);
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

		public static void alterarLance(final Double lance) {
			if (lance > valor)
				valor = lance;
		}
		
        public void run() {

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    switchCommand(inputLine, out);
                	/**if (".".equals(inputLine)) {
                        out.println("bye");
                        break;
                    }
                    out.println("João é muito biba");*/
                }

                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Deu treta" + e);
            }
        }
	}

	public static void switchCommand(final String line, final PrintWriter out) {
		if (null == line || line.isEmpty())
			return;

		if (line.split("-")[0].equals("1")) {
			// recebe um lance
		} else if (line.split("-")[0].equals("2")) {
			// retorna maior lance atual
			out.println(valor);
		} else if (line.split("-")[0].equals("3")) {
			// sai do programa
		} else {
			out.println("comando inválido");
		}
	}
}