package namensdienst;

import java.util.HashMap;
import java.util.Map;

import mware_lib.MWCommunication;
import mware_lib.NameService;
import branch_access.Manager;
import branch_access.ManagerProxy;
import cash_access.Account;
import cash_access.AccountProxy;

public class LocalNameService extends NameService {

	private String host;
	private int port;
	private NameServiceServer nameServiceServer;
	private MWCommunication mwCom;
	private Thread nsServerThread;
	private Map<String, Object> remoteEntries;

	public LocalNameService(String host, int port) {
		this.host = host;
		this.port = port;
		this.nameServiceServer = new NameServiceServer(this.host, this.port,
				this);
		this.mwCom = new MWCommunication(host, port);
	}

	synchronized public Map<String, Object> getRemoteEntries() {
		return remoteEntries;
	}

	synchronized public void put(String key, Object value) {
		remoteEntries.put(key, value);
	}

	synchronized public Object get(Object key) {
		return remoteEntries.get(key);
	}

	@Override
	public void rebind(Object servant, String name) {
		if (remoteEntries == null) {
			System.out.println("create it!");
			this.remoteEntries = new HashMap<String, Object>();
			this.nsServerThread = new Thread(this.nameServiceServer);
			this.nsServerThread.setDaemon(true);
			this.nsServerThread.start();
		}
		if (servant != null) {
			if (servant instanceof Manager) {
				remoteEntries.put(name, new ManagerProxy(this, this.host,
						this.port));
			} else if (servant instanceof Account) {
				remoteEntries.put(name, new AccountProxy(this, this.host,
						this.port));
			}
		}
	}

	@Override
	public Object resolve(String name) {
		System.out.println("Servant: " + name);

		mwCom.sendRequest(name);
		for (String key : remoteEntries.keySet()) {
			System.out.println("Entries: " + remoteEntries.get(key));
		}
		return remoteEntries.get(name);
	}

}
