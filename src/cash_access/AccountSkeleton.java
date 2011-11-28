package cash_access;

import java.io.IOException;

import mware_lib.exceptions.IllegalMessageException;
import mware_lib.messages.InvokeMessage;
import mware_lib.messages.ResultMessage;
import mware_lib.tcp_advanced.Connection;
import mware_lib.tcp_advanced.Server;

public class AccountSkeleton implements Runnable {

	private int port;
	private boolean isRunning;
	private Server server;
	private Thread skelThread;
	private Account account;
	private Connection connection;

	public AccountSkeleton(int port, Account account) {
		this.port = port;
		this.skelThread = new Thread(this);
		this.isRunning = true;
		this.account = account;
	}

	@Override
	public void run() {
		try {
			this.server = new Server(this.port);
			while (isRunning) {
				connection = new Connection(server.accept());
				try {
					Object rcvdObj = connection.receive();
					if (rcvdObj instanceof InvokeMessage) {
						InvokeMessage iMsg = (InvokeMessage) rcvdObj;
						AccountWorker accWorker = new AccountWorker(connection,
								iMsg, account);
						accWorker.start();
					} else {
						connection.send(new ResultMessage(
								new IllegalMessageException(rcvdObj.getClass()
										.toString())));
					}
				} catch (ClassNotFoundException e) {
					connection.send(new ClassNotFoundException(e.getMessage()));
				}
			}
			server.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		this.skelThread.start();
	}

	public void stop() {
		this.isRunning = false;
	}

}
