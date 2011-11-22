package mware_lib;

import java.io.IOException;
import java.net.UnknownHostException;

import namensdienst.RemoteObject;
import tcp_advanced.Client;

public class MWCommunication {

	private String host;
	private int port;
	private Client client;

	public MWCommunication(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public Object sendRequest(String name) {
		Object result = null;
		try {
			this.client = new Client(this.host, this.port);
			client.send(name);
			Object resMsg = client.receive();
			System.out.println("Message: " + resMsg);
			if (resMsg instanceof RemoteObject) {
				result = ((RemoteObject) resMsg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public Object sendSync(String name) {
		Object result = null;
		
		try {
			this.client = new Client(this.host, this.port);
			client.send(name);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
