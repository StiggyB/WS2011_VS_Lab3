package mware_lib.tcp_advanced;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private ServerSocket mySvrSocket;
	
	public Server(int listenPort) throws IOException {
		mySvrSocket = new ServerSocket(listenPort);	
		mySvrSocket.setReuseAddress(true);
	}
	
	public Connection getConnection() throws IOException {
		return new Connection(accept());
	}
	
	public Socket accept() throws IOException {
		return mySvrSocket.accept();
	}
	
	public void shutdown() throws IOException {
		mySvrSocket.close();
		mySvrSocket = null;
	}
}
