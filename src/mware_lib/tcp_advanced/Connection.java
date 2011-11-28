package mware_lib.tcp_advanced;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection {

	private ObjectInputStream in;
	private ObjectOutputStream out;

	public Connection(Socket mySock) throws IOException {
		OutputStream os = mySock.getOutputStream();
		out = new ObjectOutputStream(os);
		InputStream is = mySock.getInputStream();
		in = new ObjectInputStream(is);
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
