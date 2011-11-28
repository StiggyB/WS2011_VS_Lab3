package mware_lib;

import java.io.IOException;
import java.net.UnknownHostException;

public class ObjectBroker {

	private String host;
	private int port;
	private LocalNameService nameService;
	private static ObjectBroker objectBroker = null;

	public ObjectBroker(String host, int port) throws UnknownHostException,
			IOException {
		this.host = host;
		this.port = port;
	}

	public static ObjectBroker getBroker(String serviceHost, int listenPort)
			throws UnknownHostException, IOException {
		if (objectBroker == null) {
			objectBroker = new ObjectBroker(serviceHost, listenPort);
		}
		return objectBroker;
	}

	// Das hier zurückgelieferte Objekt soll der zentrale Einstiegspunkt
	// der Middleware aus Anwendersicht sein.
	// Parameter: Host und Port, bei dem die Dienste (Namensdienst)
	// kontaktiert werden sollen.

	public NameService getNameService() {
		this.nameService = new LocalNameService(this.host, this.port);
		return nameService;
	}
	// Liefert den Namensdienst (Stellvetreterobjekt).
}
