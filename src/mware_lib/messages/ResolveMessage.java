package mware_lib.messages;

import java.io.Serializable;

public class ResolveMessage implements Serializable {

	private static final long serialVersionUID = -7726419185052169495L;
	private String remoteName;

	public ResolveMessage(String remoteName) {
		super();
		this.remoteName = remoteName;
	}

	public String getRemoteName() {
		return remoteName;
	}
}
