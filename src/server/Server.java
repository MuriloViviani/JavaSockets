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
				System.out.println("Aguardando conex�o...");
				
				Socket client = server.accept();
				
				System.out.println("Conex�o aberta por - " + client.getInetAddress().toString());
				System.out.println("Enviando dados...");
				
				ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
				// Eviando o cabe�a�ho para o endpoint
				output.flush();
				
				output.writeObject("Servidor conectado!");
				output.writeObject("Dados -> " + client.toString());
				output.writeObject("At� mais!");
				
				System.out.println("Fim da transmiss�o!");
				output.writeObject("EOT");
				
				// Encerrando a conex�o
				client.close();
				System.out.println("Conex�o encerrada");
			}
		} catch (IOException e){
			System.out.println("Erro -> " + e.getMessage());
		}
	}
}








