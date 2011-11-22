package namensdienst;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mware_lib.MWCommunication;
import mware_lib.NameService;
import testApplication.OnlineUserImpl;
import test_user_access.OnlineUser;
import test_user_access.OnlineUserProxy;
import application.AccountImpl;
import application.ManagerImpl;
import branch_access.ManagerProxy;
import cash_access.AccountProxy;

public class LocalNameService extends NameService {

	// TODO implement specific exceptions...

	private String host;
	private int port;
	private List<Class<?>> typeList;
	private NameServiceServer nameServiceServer;
	private MWCommunication mwCom;
	private Thread nsServerThread;
	private Map<String, Object> remoteEntries;

	public LocalNameService(String host, int port) {
		this.host = host;
		this.port = port;
		this.typeList = new ArrayList<Class<?>>();
		this.nameServiceServer = new NameServiceServer(this.host, this.port,
				this);
		this.nsServerThread = new Thread(this.nameServiceServer);
		this.nsServerThread.setDaemon(true);
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

	@Override
	public void rebind(Object servant, String name) {
		if (remoteEntries == null && !(nsServerThread.isAlive())) {
			this.nsServerThread.start();
			this.remoteEntries = new HashMap<String, Object>();
			this.mwCom = new MWCommunication(host, port);
		}
		typeList.add(servant.getClass());
		System.out.println("REBIND Servant: " + servant + "; Name: " + name);
		if (servant != null) {
			remoteEntries.put(name, servant);
		}
		System.out.println("REBIND MapID: " + remoteEntries);
	}

	@Override
	public Object resolve(String name) {
		System.out.println("RESOLVE Servant: " + name);
		if (remoteEntries == null) {
			this.remoteEntries = new HashMap<String, Object>();
			this.mwCom = new MWCommunication(host, port);
		}
		Object remoteObjType = mwCom.sendRequest(name);
		if (remoteObjType != null) {
			System.out.println("NEW REMOTEOBJ: " + remoteObjType);
			Object remoteObj = generateObjectRef(remoteObjType, name);
			this.remoteEntries.put(name, remoteObj);
		}
		System.out.println("RESOLVE MapID: " + remoteEntries);
		return remoteEntries.get(name);
	}

	public Object generateObjectRef(Object remoteObj, String name) {
		Class<?> remoteClass = null;
		Object resultObj = null;
		if (remoteObj instanceof RemoteObject) {
			remoteClass = ((RemoteObject) remoteObj).getType();
		}
		//TODO impl dynamic generation String & Type is not dynamic
//		try {
//			resultObj = remoteObj.getClass().newInstance();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//		for (Class<?> type : typeList) {
//			Type newType = OnlineUser.class;
//			if(type.equals(remoteClass)) {
//			}
//		}
		if (AccountImpl.class.equals(remoteClass)) {
			resultObj = new AccountProxy(host, port, name);
		} else if (ManagerImpl.class.equals(remoteClass)) {
			resultObj = new ManagerProxy(host, port, name);
		} else if (OnlineUserImpl.class.equals(remoteClass)) {
			resultObj = new OnlineUserProxy(host, port, name);
		} 
		return resultObj;
	}
}
