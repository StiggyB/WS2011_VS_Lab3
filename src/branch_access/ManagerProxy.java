package branch_access;

import java.io.IOException;

import mware_lib.messages.InvokeMessage;
import mware_lib.messages.ResultMessage;
import mware_lib.messages.UnbindMessage;
import mware_lib.tcp_advanced.Client;

public class ManagerProxy extends Manager {

	private String host;
	private int port;
	private Client client;
	private String remoteName;

	public ManagerProxy(String hostName, int port, String remoteName) {
		super();
		this.host = hostName;
		this.port = port;
		this.remoteName = remoteName;
	}

	@Override
	public String createAccount(String owner) {
		System.out.println("remote call createAccount");
		String result = null;
		try {
			this.client = new Client(this.host, this.port);
			InvokeMessage iMsg = new InvokeMessage(remoteName, "createAccount",
					owner);
			client.send(iMsg);
			Object resultMsg = client.receive();
			if (resultMsg instanceof ResultMessage) {
				Object remoteResult = ((ResultMessage) resultMsg).getResult();
				if (remoteResult instanceof String) {
					result = (String) remoteResult;
				}
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

	@Override
	public boolean removeAccount(String accountID) {
		System.out.println("remote call removeAccount");
		Boolean result = false;
		try {
			this.client = new Client(this.host, this.port);
			InvokeMessage iMsg = new InvokeMessage(remoteName, "removeAccount",
					accountID);
			client.send(iMsg);
			Object resultMsg = client.receive();
			if (resultMsg instanceof ResultMessage) {
				Object remoteResult = ((ResultMessage) resultMsg).getResult();
				if (remoteResult instanceof Boolean) {
					result = (Boolean)remoteResult;
				}
			}
			client.close();
			this.client = new Client(this.host, this.port);
			UnbindMessage ubMsg = new UnbindMessage(accountID);
			client.send(ubMsg);
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

}
