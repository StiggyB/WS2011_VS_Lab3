package tcp_advanced;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket mySocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public Client(String host, int port) throws UnknownHostException, IOException {
		mySocket = new Socket(host, port);
		
		in = new ObjectInputStream(mySocket.getInputStream());
		out = new ObjectOutputStream(mySocket.getOutputStream());
	}
	
	public Object receive() throws IOException, ClassNotFoundException {
		return in.readObject();
	}
	
	public void send(Object message) throws IOException {
		out.writeObject(message);
	}
	
	public void close() throws IOException {
		in.close();
		out.close();
		mySocket.close();
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		// Verbindung aufbauen
		Client myClient = new Client("localhost", 14001);
		
		// Kommunikation
		myClient.send("Knock, knock!");
		System.out.println(myClient.receive());
		
		// Verbindung schliessen
		myClient.close();
	}

}
