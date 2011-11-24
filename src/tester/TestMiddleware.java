package tester;

import java.io.IOException;
import java.net.UnknownHostException;

import testApplication.OnlineUserImpl;
import test_user_access.OnlineUser;

import mware_lib.NameService;
import mware_lib.ObjectBroker;
import bank.Bank;
import branch_access.Manager;

public class TestMiddleware {
	public static final int PORT = 14001;

	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
	System.out.println("Test started");
	ObjectBroker obRemote = ObjectBroker.getBroker("localhost", PORT);
	NameService remoteNS = obRemote.getNameService();

	System.out.println("Testing...");
	ObjectBroker obLocal = ObjectBroker.getBroker("localhost", PORT);
	NameService localNS = obLocal.getNameService();

	System.out.println("Nameservices implemented");
	Manager remoteManager = new Bank(remoteNS.toString());
	remoteNS.rebind(remoteManager, "Manager");
	
	Manager localManager = (Manager) localNS.resolve("Manager");
	
	String testerID = localManager.createAccount("Tester");
	String targetTesterID = localManager.createAccount("targetTester");
	
	System.out.println("New Accounts: " + testerID + ", " + targetTesterID);
	OnlineUser remoteUser = new OnlineUserImpl();
	remoteNS.rebind(remoteUser, "User");
	
	OnlineUser localUser = (OnlineUser) localNS.resolve("User");
	
	System.out.println("Do trasfer from: " + localUser + " - to: " + targetTesterID);
	localUser.doTransfer("Tester", "targetTester", targetTesterID, 1234);
	
	
	}
	
}
