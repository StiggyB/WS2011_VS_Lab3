package namensdienst;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.SecureRandom;

import tcp_advanced.Connection;

public class NameServiceWorker implements Runnable {

	private Connection connection;
	private LocalNameService localNS;
	private Object remoteResult;
	private boolean isRunning;

	public NameServiceWorker(Connection connection, LocalNameService localNS) {
		this.connection = connection;
		this.localNS = localNS;
		this.remoteResult = null;
		this.isRunning = true;
	}

	@Override
	public void run() {

		while (isRunning) {
			Object message = null;
			try {
				message = connection.receive();
				if (message instanceof InvokeMessage) {
					InvokeMessage iMsg = (InvokeMessage) message;
					Object remoteObj = localNS.get(iMsg.getClassName());
					Method invokeMeth = iMsg.getInvMethod();
					try {
						remoteResult = invokeMeth.invoke(remoteObj,
								iMsg.getMethodArgs());
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					} catch (InvocationTargetException e1) {
						e1.printStackTrace();
					}
					ResultMessage rMsg = new ResultMessage(remoteResult);
					connection.send(rMsg);
				} else if (message instanceof String) {
					String name = (String) message;
					if (localNS.getRemoteEntries().containsKey(name)) {
						RemoteObject remoteObj = new RemoteObject(name,
								new BigInteger(6, new SecureRandom()),
								localNS.get(name));
						connection.send(remoteObj);
					}
				} else if (message instanceof RemoteObject) {
					RemoteObject remoteObj = (RemoteObject) message;
					localNS.put(remoteObj.getRemoteName(),
							remoteObj.getObjRef());
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
