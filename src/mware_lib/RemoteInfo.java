package mware_lib;

import java.io.Serializable;

public class RemoteInfo implements Serializable {

	private static final long serialVersionUID = 7238872978717217828L;
	private String host;
	private int port;
	private String type;

	public RemoteInfo(String host, int port, String type) {
		this.host = host;
		this.port = port;
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getType() {
		return type;
	}
}
