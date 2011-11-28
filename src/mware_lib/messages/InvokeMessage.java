package mware_lib.messages;

import java.io.Serializable;
import java.util.Arrays;

public class InvokeMessage implements Serializable {

	private static final long serialVersionUID = 2128014230850201840L;
	private String className;
	private String invMethod;
	private Serializable[] methodArgs;

	public InvokeMessage(String className, String invMethod,
			Serializable... methodArgs) {
		this.className = className;
		this.invMethod = invMethod;
		this.methodArgs = methodArgs;
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return invMethod;
	}

	public Serializable[] getMethodArgs() {
		return methodArgs;
	}

	@Override
	public String toString() {
		return "InvokeMessage [className=" + className + ", invMethod="
				+ invMethod + ", methodArgs=" + Arrays.toString(methodArgs)
				+ "]";
	}
}
