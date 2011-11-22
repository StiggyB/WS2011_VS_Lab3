package application;

import java.util.ArrayList;
import java.util.List;

import mware_lib.NameService;
import branch_access.Manager;

public class ManagerImpl extends Manager {

	NameService nameService;
	List<AccountImpl> accList;

	public ManagerImpl(NameService nameService) {
		super();
		this.nameService = nameService;
		accList = new ArrayList<AccountImpl>();
	}

	@Override
	public String createAccount(String owner) {
		System.out.println("createAccount");
		AccountImpl acc = new AccountImpl(owner);
		accList.add(acc);
		nameService.rebind(acc, acc.getAccID());
		System.out.println("AccID: " + acc.getAccID());
		return acc.getAccID();
	}

	@Override
	public boolean removeAccount(String accountID) {
		System.out.println("removeAccount");
		boolean result = false;
		if (!(accList.isEmpty())) {
			int i;
			for (i = 0; i < accList.size(); i++) {
				if ((accList.get(i).getAccID().equals(accountID))) {
					accList.remove(i);
					result = true;
				}
			}
		}
		return result;
	}

}
