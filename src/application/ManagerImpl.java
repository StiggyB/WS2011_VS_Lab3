package application;

import java.util.ArrayList;
import java.util.List;

import mware_lib.NameService;
import branch_access.Manager;
import cash_access.Account;

public class ManagerImpl extends Manager {

	NameService nameService;
	List<Account> accList;
	
	public ManagerImpl(NameService nameService) {
		super();
		this.nameService = nameService;
		accList = new ArrayList<Account>();
	}
	
	@Override
	public String createAccount(String owner) {
		System.out.println("createAccount");
		AccountImpl acc = new AccountImpl(owner);
		accList.add(acc);
		nameService.rebind(acc, acc.getAccID());
		return acc.getAccID();
	}

	@Override
	public boolean removeAccount(String accountID) {
		// TODO Auto-generated method stub
		System.out.println("removeAccount");
		return false;
	}

}
