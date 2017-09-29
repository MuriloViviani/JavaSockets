package client;

import java.io.*;
import java.net.*;
import java.net.Socket;

public class SendFile {
	public static void main(String[] args) throws UnknownHostException{
		System.out.println("Conectando-se ao servidor...");
		
		InetAddress localIP = InetAddress.getLocalHost();
		
		try(Socket clientC = new Socket(localIP.getHostAddress(), 1234);
				Socket clientD = new Socket(localIP.getHostAddress(), 1235)){
			System.out.println("Conex�o aceita de -> " + clientC.getInetAddress().toString());
			
			// Obten��o do arquivo e sua abertura
			File file = new File("C:\\Users\\Murilo Viviani\\Downloads\\fece\\bum.jpg");
			
			System.out.println("Enviando o arquivo -> " + file.getName());
			BufferedInputStream arquivo = new BufferedInputStream(new FileInputStream(file));
			
			// Abrindo o canal de comunica��o (modo texto)
			PrintWriter canalC = new PrintWriter(clientC.getOutputStream(), true);
			
			long sent = 0, total = file.length();
			System.out.println("Total de bytes no arquivo -> " + total);
			
			// Enviando os dados do arquivo
			canalC.println(file.getName());
			canalC.println(total + "");
			// Fechando o canal modo texto
			canalC.close();
			
			// Abrindo canal Bin�rio	
			BufferedOutputStream canalD = new BufferedOutputStream(clientD.getOutputStream());
			while(sent + 256 < total){
				byte dados[] = new byte[256];// Array para o bloco de dados
				arquivo.read(dados, 0, 256);// L� dados para bloco
				canalD.write(dados, 0, 256);// Envia o bloco de dados
				sent += 256;// Totaliza os blocos
			}
			if(sent < total){
				int fim = (int) (total - sent);
				byte dados[] = new byte[fim];// Array para bloco final
				arquivo.read(dados, 0, fim);// L� dados para bloco final
				canalD.write(dados, 0, fim);// Envia o bloco final
			}
			canalD.close();
			arquivo.close();
			System.out.println("Arquivo enviado... Total de bytes -> " + total);
		}catch(IOException e){
			System.out.println("Problema ao envia arquivo...");
			e.printStackTrace();
		}
		
		System.out.println("Fim de conex�o.");
	}
}




