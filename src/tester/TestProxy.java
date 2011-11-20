package tester;

import java.lang.reflect.Proxy;


public class TestProxy {

	public static void main(String[] args) {
		TestClass tester = new TestClass("TESTER");
		
		Handler handler = new Handler(tester);
		
		ClassLoader loader = TestInterface.class.getClassLoader();
		Class<?>[] interfaces = new Class[] { TestInterface.class};
		TestInterface proxy = (TestInterface) Proxy.newProxyInstance(loader, interfaces, handler);
		
		proxy.setName("TESTERS");
		proxy.getName();
		proxy.doIt();
		
	}
	
}
