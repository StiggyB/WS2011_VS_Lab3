package tcp_advanced;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	
	public Connection(Socket mySock) throws IOException {
		in = new ObjectInputStream(mySock.getInputStream());
		out = new ObjectOutputStream(mySock.getOutputStream());
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
	}
}
