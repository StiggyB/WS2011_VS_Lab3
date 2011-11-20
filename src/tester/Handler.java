package tester;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Handler implements InvocationHandler {

	private Object accObject;

	public Handler(Object accObject) {
		super();
		this.accObject = accObject;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("[Proxy] Methode: " + method.getName());
		if (args != null) {
			System.out.println("[Proxy] Args: " + args.length);
		}
		return method.invoke(accObject, args);
	}

}
