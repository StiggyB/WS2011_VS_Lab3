package test_user_access;

import java.io.IOException;
import java.io.Serializable;
import java.net.UnknownHostException;

import namensdienst.InvokeMessage;
import namensdienst.ResultMessage;

import tcp_advanced.Client;

public class OnlineUserProxy extends OnlineUser {

	private String host;
	private int port;
	private Client client;
	private String remoteName;

	public OnlineUserProxy(String host, int port, String remoteName) {
		super();
		this.host = host;
		this.port = port;
		this.remoteName = remoteName;
	}

	@Override
	public boolean doTransfer(String accOwner, String accTarget, String accID, int BSC) {
		System.out.println("remote doTransfer");
		Boolean result = false;
		try {
			this.client = new Client(this.host, this.port);
			InvokeMessage iMsg = new InvokeMessage(remoteName, "doTransfer",
					new Serializable[] { accOwner, accTarget, accID, BSC });
			client.send(iMsg);
			Object Msg = client.receive();
			if (Msg instanceof ResultMessage) {
				Object resMsg = ((ResultMessage) Msg).getResult();
				if (resMsg instanceof Boolean) {
					result = (Boolean) resMsg;
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public String showTransactionVolume() {
		System.out.println("remote showTransactionVolume");
		return null;
	}

	@Override
	public String editOwnerData(String ownerName, String ownerAddr, String fon) {
		System.out.println("remote editOwnerData");
		return null;
	}

}
