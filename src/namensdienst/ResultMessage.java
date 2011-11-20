package namensdienst;

import java.io.Serializable;

public class ResultMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2917288197125287853L;
	private Object result;
	private Exception[] remoteExceptions;

	public ResultMessage(Object result) {
		super();
		this.result = result;
	}
	
	public Object getResult() {
		return result;
	}
	
	public Exception[] getRemoteExceptions() {
		return remoteExceptions;
	}
}
