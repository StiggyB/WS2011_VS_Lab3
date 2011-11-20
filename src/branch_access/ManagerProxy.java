package branch_access;

import java.io.IOException;
import java.net.UnknownHostException;

import namensdienst.InvokeMessage;

import tcp_advanced.Client;
import mware_lib.NameService;

public class ManagerProxy extends Manager {

	private String host;
	private int port;
	private Client client;
	
	public ManagerProxy(NameService nameService, String hostName, int port) {
		this.host = hostName;
		this.port = port;
		try {
			this.client = new Client(this.host, this.port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String createAccount(String owner) {
		System.out.println("remote call");
		String result = null;
		try {
			InvokeMessage iMsg = new InvokeMessage("Account", this.getClass()
					.getMethod("createAccount", String.class), owner);
			client.send(iMsg);
			Object resultMsg = client.receive();
			if (resultMsg instanceof String) {
				result = (String)resultMsg;
			} 
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean removeAccount(String accountID) {
		System.out.println("remote call");
		return false;
	}

}

