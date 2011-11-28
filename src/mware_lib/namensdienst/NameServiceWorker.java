package mware_lib.namensdienst;

import java.io.IOException;

import mware_lib.messages.RebindMessage;
import mware_lib.messages.ResolveMessage;
import mware_lib.messages.UnbindMessage;
import mware_lib.tcp_advanced.Connection;

public class NameServiceWorker implements Runnable {

	private Connection connection;
	private NameServiceServer NSServer;

	public NameServiceWorker(Connection connection, NameServiceServer NSServer) {
		this.connection = connection;
		this.NSServer = NSServer;
	}

	@Override
	public void run() {
		Object message = null;
		try {
			message = connection.receive();
			if (message instanceof RebindMessage) {
				RebindMessage rbMsg = (RebindMessage) message;
				System.out.println("REBINDMSG: " + rbMsg.getRemoteName() + ", "
						+ rbMsg.getRemoteInfo().getType());
				NSServer.put(rbMsg.getRemoteName(), rbMsg.getRemoteInfo());
			} else if (message instanceof ResolveMessage) {
				ResolveMessage resMsg = (ResolveMessage) message;
				System.out.println("RESOLVEMSG: " + resMsg.getRemoteName());
				Object remoteInfo = NSServer.get(resMsg.getRemoteName());
				connection.send(remoteInfo);
			} else if (message instanceof UnbindMessage) {
				NSServer.remove(((UnbindMessage) message).getRemoteName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
