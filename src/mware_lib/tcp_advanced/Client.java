package mware_lib.tcp_advanced;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private Socket mySocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private OutputStream os;
	private InputStream is;

	public Client(String host, int port) throws UnknownHostException,
			IOException {
		this.mySocket = new Socket(host, port);
		this.mySocket.setReuseAddress(true);
		this.os = mySocket.getOutputStream();
		this.out = new ObjectOutputStream(os);
		this.is = mySocket.getInputStream();
		this.in = new ObjectInputStream(is);
	}

	public Object receive() throws IOException, ClassNotFoundException {
		return in.readObject();
	}

	public void send(Object message) throws IOException {
		out.writeObject(message);
	}

	public void close() throws IOException {
		in.close();
		is.close();
		out.close();
		os.close();
		mySocket.close();
		mySocket = null;
	}
}
