package tester.tcp_advanced;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	private ServerSocket MySvrSocket;
	
	public Server(int listenPort) throws IOException {
		MySvrSocket = new ServerSocket(listenPort);		
	}
	
	public Connection getConnection() throws IOException {
		return new Connection(MySvrSocket.accept());
	}
	
	public void shutdown() throws IOException {
		MySvrSocket.close();
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Server theServer = new Server(14001);

		// Auf Verbindungsanfrage warten.
		Connection myConnection = theServer.getConnection();
		
		// Kommunikation
		System.out.println(myConnection.receive());
		myConnection.send("Who's there?");
		
		// Verbindung schliessen
		myConnection.close();

		// Server runterfahren
		theServer.shutdown();
	}
}
