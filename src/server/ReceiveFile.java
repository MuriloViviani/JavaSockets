package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ReceiveFile {
	public static void main(String args[])
	{
		System.out.println("[Criando o servidor...]");
		try(ServerSocket servidorC = new ServerSocket(1234);
				ServerSocket servidorD = new ServerSocket(1235);){
			System.out.println("Servidor operando nas portas 1234/1235!");
			while(true){
				System.out.println("Aguardando por conexão...");
				try(Socket clientC = servidorC.accept();
						Socket clientD = servidorD.accept();){
					System.out.println("Conexão aberta por -> " + clientC.getInetAddress().toString());
					
					// Abre o canal de controle (modo texto)
					BufferedReader canalC = new BufferedReader(new InputStreamReader(clientC.getInputStream()));
					
					// Pegando os dados do arquivo
					String nomeArq = canalC.readLine() + ".rcv";
					if(nomeArq.startsWith("@shutdown")){
						System.out.println("Shutdown...");
						break;
					}
					
					System.out.println("Recebendo arquivo -> " + nomeArq);
					long recv = 0;
					long total = Integer.parseInt(canalC.readLine());
					canalC.close();// Encerrando canal de controle
					
					// Abrindo canal de dados (Binário)
					BufferedInputStream canalD = new BufferedInputStream(clientD.getInputStream());
					// Abrindo arquivo para dados transferidos
					BufferedOutputStream arquivo = new BufferedOutputStream(new FileOutputStream(nomeArq));
					
					// Recebendo os dados
					while(recv + 256 < total){
						byte dados[] = new byte[256];// Array para bloco de dados
						canalD.read(dados, 0, 256);// Lê o bloco de dados
						arquivo.write(dados, 0, 256);// Grava o bloco de dados
						recv += 256;
					}
					if(recv < total){
						int fim = (int) (total - recv);
						byte dados[] = new byte[fim];// Bloco final
						canalD.read(dados, 0, fim);// Lê bloco final
						arquivo.write(dados, 0, fim);// Grava bloco final
					}
					arquivo.close();
					canalD.close();
					System.out.println("Bytes recebidos -> " + total);
				} catch ( IOException e) {
					System.out.println("Problemas ao receber os dados...");
					e.printStackTrace();
				}
				
				System.out.println("[Fim de conexão...]");
			}
		} catch (Exception e) {
			System.out.println("Erro!!");
			e.printStackTrace();
		}
	}
}
