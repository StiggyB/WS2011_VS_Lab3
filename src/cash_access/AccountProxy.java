package cash_access;

import java.io.IOException;
import java.net.UnknownHostException;

import mware_lib.NameService;
import namensdienst.InvokeMessage;
import tcp_advanced.Client;

public class AccountProxy extends Account {

	private String host;
	private int port;
	private Client client;
//	private NameService nameService;

	public AccountProxy(NameService nameService, String hostName, int port) {
//		this.nameService = nameService;
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
	public void deposit(double amount) {
		try {
			InvokeMessage iMsg = new InvokeMessage("Account", this.getClass()
					.getMethod("deposit", Double.class), amount);
			client.send(iMsg);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void withdraw(double amount) throws OverdraftException {
		try {
			InvokeMessage iMsg = new InvokeMessage("Account", this.getClass()
					.getMethod("withdraw", Double.class), amount);
			client.send(iMsg);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public double getBalance() {
		Double result = null;
		try {
			InvokeMessage iMsg = new InvokeMessage("Account", this.getClass()
					.getMethod("getBalance", double.class), (Object[])null);
			client.send(iMsg);
			Object resultMsg = client.receive();
			if (resultMsg instanceof Double) {
				result = (Double)resultMsg;
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

}
