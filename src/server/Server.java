package server;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

public class Server {
	public static void main(String[] args) {
		System.out.println("Inicializando o servidor...");
		try(ServerSocket server = new ServerSocket(1234);){
			System.out.println("Servidor inicializado na porta [1234]");
			
			while (true) {
				System.out.println("Aguardando conexão...");
				
				Socket client = server.accept();
				
				System.out.println("Conexão aberta por - " + client.getInetAddress().toString());
				System.out.println("Enviando dados...");
				
				ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
				// Eviando o cabeçaçho para o endpoint
				output.flush();
				
				output.writeObject("Servidor conectado!");
				output.writeObject("Dados -> " + client.toString());
				output.writeObject("Até mais!");
				
				System.out.println("Fim da transmissão!");
				output.writeObject("EOT");
				
				// Encerrando a conexão
				client.close();
				System.out.println("Conexão encerrada");
			}
		} catch (IOException e){
			System.out.println("Erro -> " + e.getMessage());
		}
	}
}








