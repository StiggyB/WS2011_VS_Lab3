package mware_lib.messages;

import java.io.Serializable;
import java.util.Arrays;

public class ResultMessage implements Serializable {

	private static final long serialVersionUID = -2917288197125287853L;
	private Serializable result;
	private Exception[] remoteExceptions;

	public ResultMessage(Serializable result) {
		super();
		this.result = result;
	}

	public Serializable getResult() {
		return result;
	}

	public Exception[] getRemoteExceptions() {
		return remoteExceptions;
	}

	@Override
	public String toString() {
		return "ResultMessage [result=" + result + ", remoteExceptions="
				+ Arrays.toString(remoteExceptions) + "]";
	}
}
