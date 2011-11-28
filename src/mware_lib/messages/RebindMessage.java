package mware_lib.messages;

import java.io.Serializable;

import mware_lib.RemoteInfo;

public class RebindMessage implements Serializable{

	private static final long serialVersionUID = -5025999473771344271L;
	private String remoteName;
	private RemoteInfo remoteInfo;

	public RemoteInfo getRemoteInfo() {
		return this.remoteInfo;
	}

	public String getRemoteName() {
		return this.remoteName;
	}

	public RebindMessage(RemoteInfo remoteInfo, String remoteName) {
		this.remoteName = remoteName;
		this.remoteInfo = remoteInfo;
	}

	@Override
	public String toString() {
		return "RebindMessage [remoteName=" + remoteName + ", type=" + remoteInfo + "]";
	}
}
