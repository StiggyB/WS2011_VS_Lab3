package tester;

import java.io.IOException;

import mware_lib.NameService;
import mware_lib.ObjectBroker;
import application.ManagerImpl;
import branch_access.Manager;
import cash_access.Account;

public class TestNameService {

	public static final int PORT = 14001;

	public static void main(String[] args) throws IOException {

		System.out.println("Test started");
		ObjectBroker obRemote = ObjectBroker.getBroker("localhost", PORT);
		NameService remoteNS = obRemote.getNameService();

		System.out.println("Testing...");
		ObjectBroker obLocal = ObjectBroker.getBroker("localhost", PORT);
		NameService localNS = obLocal.getNameService();

		System.out.println("Nameservices implemented");
		Manager remoteManager = new ManagerImpl(remoteNS);
		remoteNS.rebind(remoteManager, "Manager");

		System.out.println("RemoteManager implemented: " + remoteManager);
		Manager localManager = (Manager) localNS.resolve("Manager");

		System.out.println("localManager: " + localManager);
		String localAcc = localManager.createAccount("1234");

		System.out.println("LocalManager implemented");
		Account account = (Account) localNS.resolve(localAcc);
		account.deposit(10);
	}
}
