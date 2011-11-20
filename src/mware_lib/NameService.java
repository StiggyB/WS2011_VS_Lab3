package mware_lib;

public abstract class NameService {
	
	public abstract void rebind(Object servant, String name);
	   // Meldet ein Objekt (servant) beim Namensdienst an.
	   // Eine eventuell schon vorhandene Objektreferenz gleichen Namens
	   // soll überschrieben werden.
	
	public abstract Object resolve(String name);
	// Liefert die Objektreferenz (Stellvertreterobjekt) zu einem Namen.
	
	
}
