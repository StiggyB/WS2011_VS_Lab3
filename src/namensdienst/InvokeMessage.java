package namensdienst;

import java.io.Serializable;
import java.lang.reflect.Method;

public class InvokeMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2128014230850201840L;
	private String className;
	private Method invMethod;
	private Object[] methodArgs;
	
	public InvokeMessage(String className, Method invMethod, Object... methodArgs) {
		this.className = className;
		this.invMethod = invMethod;
		this.methodArgs = methodArgs;
	}

	public String getClassName() {
		return className;
	}

	public Method getInvMethod() {
		return invMethod;
	}

	public Object[] getMethodArgs() {
		return methodArgs;
	}
	
}
