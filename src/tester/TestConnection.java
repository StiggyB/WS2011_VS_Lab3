package tester;

import java.io.IOException;
import java.math.BigInteger;
import java.net.UnknownHostException;

import mware_lib.RemoteObject;
import mware_lib.messages.ResultMessage;
import mware_lib.tcp_advanced.Client;

public class TestConnection {

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, SecurityException, NoSuchMethodException {
		Client c = new Client("localhost", 14001);
		
		System.out.println("RUNNING");
//		InvokeMessage message = new InvokeMessage("IT IS!", "receive", (Object[])null);
		ResultMessage message = new ResultMessage(new RemoteObject("Manager", BigInteger.ONE, null));
		c.send(message);
		
		System.out.println("RUNNING");
		
	}
}
