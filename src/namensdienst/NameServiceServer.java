package namensdienst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tcp_advanced.Connection;
import tcp_advanced.Server;

/**
 * This NameServiceServer delegates requests from the Clients to the
 * NameServerWorker
 * 
 * @author Administrator
 * 
 */
public class NameServiceServer implements Runnable {

	// TODO implement invoking side - resultMsg etc.
	// TODO implement only one nsServerThread permitted

	// private String host;
	private int port;
	private boolean isRunning;
	private Server server;
	private Connection connection;
	private LocalNameService nameService;
	private List<Thread> workerList = new ArrayList<Thread>();

	public NameServiceServer(String host, int port, LocalNameService nameService) {
		// this.host = host;
		this.port = port;
		this.isRunning = true;
		this.nameService = nameService;
	}

	public void delegateRequest(Connection connection) {
		NameServiceWorker nsWorker = new NameServiceWorker(connection,
				nameService);
		Thread worker = new Thread(nsWorker);
		workerList.add(worker);
		worker.start();
	}

	@Override
	public void run() {
		try {
			this.server = new Server(this.port);
			while (isRunning) {
				connection = server.getConnection();
				delegateRequest(connection);

			}
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
