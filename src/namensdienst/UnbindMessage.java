package namensdienst;

import java.io.Serializable;

public class UnbindMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8417428426993878483L;
	private String remoteName;

	public UnbindMessage(String remoteName) {
		this.remoteName = remoteName;
	}
	
	public String getRemoteName() {
		return remoteName;
	}
}
