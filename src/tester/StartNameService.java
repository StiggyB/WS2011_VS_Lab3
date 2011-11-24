package tester;

import java.io.IOException;

import mware_lib.ObjectBroker;

public class StartNameService {

	public static void main(String[] args) throws IOException, InterruptedException {

		System.out.println("Test started");
		ObjectBroker obRemote = ObjectBroker.getBroker(args[0], Integer.parseInt(args[1]));
		obRemote.getNameService();
		System.out.println("Testing...");
		Thread.sleep(1000000);
		System.out.println("ENDE");
	}
}