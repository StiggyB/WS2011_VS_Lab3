package branch_access;

import java.io.IOException;
import java.io.Serializable;

import mware_lib.exceptions.RemoteException;
import mware_lib.messages.InvokeMessage;
import mware_lib.messages.ResultMessage;
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
		String result = null;
		try {
			this.client = new Client(this.host, this.port);
			InvokeMessage iMsg = new InvokeMessage(remoteName, "createAccount",
					owner);
			client.send(iMsg);
			Object resultMsg = client.receive();
			if (!(resultMsg instanceof ResultMessage)) {
				throw new RemoteException(
						"Recieved object is no instance of ResultMessage");
			}
			resultMsg = ((ResultMessage) resultMsg).getResult();
			if (!(resultMsg instanceof Serializable)) {
				throw (Exception) resultMsg;
			}
			result = (String) resultMsg;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getClass().toString() + ": "
					+ e.getMessage(), e);
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
					result = (Boolean) remoteResult;
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

}
