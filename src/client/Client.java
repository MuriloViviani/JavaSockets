package client;

import java.io.ObjectInputStream;
import java.net.*;

public class Client {
	public static void main(String[] args) throws UnknownHostException {
		System.out.println("Conectando ao servidor...");
		
		// Endereço do servidor local
		InetAddress localIP = InetAddress.getLocalHost();
		
		try(Socket client = new Socket(localIP.getHostAddress(), 1234);)
		{
			System.out.println("Conexão aceita por -> " + client.getInetAddress().toString());
			System.out.println("Recebendo mensagens...");
			
			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			
			// recebendo os dados do servidor...
			String msg;
			do
			{
				msg = (String)entrada.readObject();
				System.out.println(msg);
			}
			while(!msg.equals("EOT"));
			
			System.out.println("Fim de conexão...");
		} catch (Exception e) {
			System.out.println("Erro -> " + e);
		}
	}
}
