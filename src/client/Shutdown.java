package client;

import java.io.PrintWriter;
import java.net.*;

public class Shutdown {
	public static void main(String args[]) throws UnknownHostException{
		InetAddress localIP = InetAddress.getLocalHost();
		
		System.out.println("Conectando ao server");
		try(Socket clientC = new Socket(localIP.getHostAddress(), 1234);
				Socket clientD = new Socket(localIP.getHostAddress(), 1235);){
			PrintWriter canalC = new PrintWriter(clientC.getOutputStream(), true);

			System.out.println("Requisitando Shutdown...");
			canalC.println("@shutdown");
			canalC.close();
			
			System.out.println("OK!");
		} catch (Exception e){
			System.out.println("Erro!");
			e.printStackTrace();
		}
	}
}
