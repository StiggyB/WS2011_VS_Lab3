package namensdienst;

import java.io.Serializable;

public class ObjectRef implements Serializable{

	private String host;
	private int port;
	private Object type;
	
	public ObjectRef(String host, int port, Object type) {
		super();
		this.host = host;
		this.port = port;
		this.type = type;
	}
	
}
