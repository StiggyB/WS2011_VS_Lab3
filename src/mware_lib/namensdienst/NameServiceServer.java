package mware_lib.namensdienst;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mware_lib.tcp_advanced.Connection;
import mware_lib.tcp_advanced.Server;

/**
 * This NameServiceServer delegates requests from the Clients to the
 * NameServerWorker
 * 
 * @author Administrator
 * 
 */
public class NameServiceServer implements Runnable {

	private int port;
	private boolean isRunning;
	private Server server;
	private Connection connection;
	private Thread serverThread;
	private Map<String, Object> remoteEntries;
	
	public NameServiceServer(int port) {
		this.port = port;
		this.isRunning = true;
		this.serverThread = new Thread(this);
		this.remoteEntries = new HashMap<String, Object>();
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

	synchronized public void remove(String key) {
		if(!(remoteEntries.isEmpty())) {
			remoteEntries.remove(key);
		}
	}

	public void delegateRequest(Connection connection) {
		NameServiceWorker nsWorker = new NameServiceWorker(connection,
				this);
		Thread worker = new Thread(nsWorker);
		worker.start();
	}
	
	public void start() {
		this.serverThread.start();
	}
	
	public void stop() {
		this.isRunning = false;
	}
	
	public void join() throws InterruptedException {
		this.serverThread.join();
	}
	
	@Override
	public void run() {
		try {
			int i = 0;
			this.server = new Server(this.port);
			while (isRunning) {
				System.out.println("Waiting for client" + i + " ...on " + port);
				connection = new Connection(server.accept());
				delegateRequest(connection);
				i++;
			}
			server.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
