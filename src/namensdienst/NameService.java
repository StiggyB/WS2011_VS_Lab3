package namensdienst;

import java.util.*;

import tcp_advanced.Server;

public abstract class NameService {
	
	private Map<String, ObjectRef> entries = new HashMap<String, ObjectRef>();
	private Server socket;
	private List<Thread> workerList = new ArrayList<Thread>();
	
	
	public abstract void rebind(Object servant, String name);
	   // Meldet ein Objekt (servant) beim Namensdienst an.
	   // Eine eventuell schon vorhandene Objektreferenz gleichen Namens
	   // soll überschrieben werden.
	
	public abstract Object resolve(String name);
	// Liefert die Objektreferenz (Stellvertreterobjekt) zu einem Namen.
	
	public static void main(String[] args) {
		
	}
	
}
