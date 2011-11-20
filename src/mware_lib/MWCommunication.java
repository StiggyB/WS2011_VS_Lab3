package mware_lib;

import java.io.IOException;

import tcp_advanced.Client;

public class MWCommunication {

	private String host;
	private int port;
	private Client client;
	
	public MWCommunication(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}
	
	public void sendRequest(String name) {
		try {
			
			client.send(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
