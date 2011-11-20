package tester;

import java.lang.reflect.Proxy;

import mware_lib.NameService;
import cash_access.Account;
import cash_access.OverdraftException;

public class DynamicProxyAccount extends Account{

	private String remoteName;
	private NameService nameService;
	private Account proxy;
	
	
	public DynamicProxyAccount(NameService nameService, String remoteName) {
		this.remoteName = remoteName;
		Object remoteObj = this.nameService.resolve(this.remoteName);
		Handler handler = new Handler(remoteObj);
		
		ClassLoader loader = Account.class.getClassLoader();
		Class<?>[] interfaces = new Class[] { Account.class };
		proxy = (Account) Proxy.newProxyInstance(loader, interfaces, handler);
	}


	@Override
	public void deposit(double amount) {
		this.proxy.deposit(amount);
	}

	@Override
	public void withdraw(double amount) throws OverdraftException {
		this.proxy.withdraw(amount);
	}

	@Override
	public double getBalance() {
		return this.proxy.getBalance();
	}
	
}
