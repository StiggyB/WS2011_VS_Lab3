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

	private int port;
	private boolean isRunning;
	private Server server;
	private Connection connection;
	private LocalNameService nameService;
	private List<Thread> workerList = new ArrayList<Thread>();

	public NameServiceServer(String host, int port, LocalNameService nameService) {
		this.port = port;
		this.isRunning = true;
		this.nameService = nameService;
	}

	public void delegateRequest(Connection connection) {
		System.out.println("Delegate!");
		NameServiceWorker nsWorker = new NameServiceWorker(connection,
				nameService);
		Thread worker = new Thread(nsWorker);
		workerList.add(worker);
		worker.start();
	}

	@Override
	public void run() {
		try {
			int i = 0;
			this.server = new Server(this.port);
			while (isRunning) {
				System.out.println("Waiting for client" + i + "...on" + port);
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
