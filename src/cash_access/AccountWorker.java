package cash_access;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import mware_lib.exceptions.RemoteException;
import mware_lib.messages.InvokeMessage;
import mware_lib.messages.ResultMessage;
import mware_lib.tcp_advanced.Connection;

public class AccountWorker implements Runnable {

	private Connection connection;
	private Object remoteResult;
	private InvokeMessage iMsg;
	private Thread workThread;
	private Account account;

	public AccountWorker(Connection connection, InvokeMessage iMsg,
			Account account) {
		this.connection = connection;
		this.iMsg = iMsg;
		this.account = account;
		this.workThread = new Thread(this);
		this.remoteResult = null;
	}

	@Override
	public void run() {
		ResultMessage rMsg = null;
		try {
			System.out.println("KEYNAME: " + iMsg.getClassName());
			System.out.println("REMOTEOBJECT: " + account);
			Method invokeMeth;
			try {
				Class<?>[] argArray = null;
				if (iMsg.getMethodArgs() != null) {
					List<Class<?>> methodArgs = new ArrayList<Class<?>>();
					for (Object type : iMsg.getMethodArgs()) {
						methodArgs.add(unboxType(type.getClass()));
					}
					argArray = new Class<?>[methodArgs.size()];
					for (int i = 0; i < argArray.length; i++) {
						argArray[i] = methodArgs.get(i);
					}
				}
				invokeMeth = account.getClass().getMethod(iMsg.getMethodName(),
						argArray);
				remoteResult = invokeMeth.invoke(account,
						(Object[]) iMsg.getMethodArgs());
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				remoteResult = new RemoteException(e.getClass().toString()
						+ ": " + e.getMessage(), e);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				remoteResult = new RemoteException(e.getCause().getClass()
						.toString()
						+ ": " + e.getCause().getMessage(), e);
			}
			System.out.println("REMOTERESULT: " + remoteResult);
			if (remoteResult == null || remoteResult instanceof Serializable) {
				Serializable serialResult = (Serializable) remoteResult;
				rMsg = new ResultMessage(serialResult);
				connection.send(rMsg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		this.workThread.start();
	}

	private Class<?> unboxType(Class<?> type) {
		Class<?> primType = type;
		if (type.equals(Byte.class)) {
			primType = byte.class;
		} else if (type.equals(Short.class)) {
			primType = short.class;
		} else if (type.equals(Integer.class)) {
			primType = int.class;
		} else if (type.equals(Float.class)) {
			primType = float.class;
		} else if (type.equals(Double.class)) {
			primType = double.class;
		} else if (type.equals(Character.class)) {
			primType = char.class;
		}
		return primType;
	}

}
