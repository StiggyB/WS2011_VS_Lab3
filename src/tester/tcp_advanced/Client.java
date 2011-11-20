package tester.tcp_advanced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket MySocket;
	private BufferedReader In;
	private OutputStream Out;
	
	public Client(String host, int port) throws UnknownHostException, IOException {
		MySocket = new Socket(host, port);
		
		In = new BufferedReader(new InputStreamReader(MySocket.getInputStream()));
		Out = MySocket.getOutputStream();
	}
	
	public String receive() throws IOException {
		return In.readLine();
	}
	
	public void send(String message) throws IOException {
		Out.write((message + "\n").getBytes());
	}
	
	public void close() throws IOException {
		In.close();
		Out.close();
		MySocket.close();
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		// Verbindung aufbauen
		Client myClient = new Client("localhost", 14001);
		
		// Kommunikation
		myClient.send("Knock, knock!");
		System.out.println(myClient.receive());
		
		// Verbindung schliessen
		myClient.close();
	}

}
