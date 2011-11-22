package testApplication;

import test_user_access.OnlineUser;

public class OnlineUserImpl extends OnlineUser {

	@Override
	public boolean doTransfer(String accOwner, String accTarget, String accID, int BSC) {
		System.out.println("doTransfer");
		return false;
	}

	@Override
	public String showTransactionVolume() {
		System.out.println("showTransactionVolume");
		return null;
	}

	@Override
	public String editOwnerData(String ownerName, String ownerAddr, String fon) {
		System.out.println("editOwnerData");
		return null;
	}

}
