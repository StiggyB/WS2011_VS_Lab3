package namensdienst;

import java.io.Serializable;
import java.math.BigInteger;

public class RemoteObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5426211725643507517L;
	private String remoteName;
	private BigInteger objID;
	private Class<?> type;

	public Class<?> getType() {
		return type;
	}

	public String getRemoteName() {
		return remoteName;
	}

	public BigInteger getObjID() {
		return objID;
	}

	public RemoteObject(String remoteName, BigInteger objID, Class<?> type) {
		this.remoteName = remoteName;
		this.objID = objID;
		this.type = type;
	}

	@Override
	public String toString() {
		return "RemoteObject [remoteName=" + remoteName + ", objID=" + objID
				+ ", type=" + type + "]";
	}
}
