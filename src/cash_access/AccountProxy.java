package cash_access;

import java.io.IOException;
import java.io.Serializable;

import namensdienst.InvokeMessage;
import namensdienst.ResultMessage;
import tcp_advanced.Client;

public class AccountProxy extends Account {
	private String host;
	private int port;
	private Client client;
	private String accID;

	public AccountProxy(String hostName, int port, String accID) {
		super();
		this.host = hostName;
		this.port = port;
		this.accID = accID;
	}

	@Override
	public void deposit(double amount) {
		try {
			this.client = new Client(this.host, this.port);
			InvokeMessage iMsg = new InvokeMessage(accID, "deposit", amount);
			client.send(iMsg);
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void withdraw(double amount) throws OverdraftException {
		try {
			this.client = new Client(this.host, this.port);
			InvokeMessage iMsg = new InvokeMessage(accID, "withdraw", amount);
			client.send(iMsg);
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public double getBalance() {
		Double result = Double.NaN;
		try {
			this.client = new Client(this.host, this.port);
			InvokeMessage iMsg = new InvokeMessage(accID, "getBalance",
					(Serializable[]) null);
			client.send(iMsg);
			Object msg = client.receive();
			if (msg instanceof ResultMessage) {
				ResultMessage resultMsg = (ResultMessage) msg;
				if (resultMsg.getResult() instanceof Double) {
					result = (Double) resultMsg.getResult();
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
