package tcp_advanced;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import namensdienst.InvokeMessage;
import namensdienst.ResultMessage;

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
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		System.out.println("SERVER");
		Server server = new Server(14001);
		Connection conn = server.getConnection();
		
		Object message = conn.receive();
		if(message instanceof InvokeMessage) {
			InvokeMessage iMsg = (InvokeMessage)message;
			System.out.println(iMsg.getClassName());
		} else if(message instanceof ResultMessage) {
			ResultMessage rMsg = (ResultMessage)message;
			System.out.println(rMsg.getResult());
		}
		
		server.shutdown();
		System.out.println("SERVER... closed");
	}
}
