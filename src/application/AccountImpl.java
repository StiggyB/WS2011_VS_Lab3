package application;

import java.math.BigInteger;
import java.security.SecureRandom;

import cash_access.Account;
import cash_access.OverdraftException;

/**
 * Konto
 * @author Benny
 *
 */
public class AccountImpl extends Account {

	private String owner;
	private String accID;
	private double balance;
	
	public AccountImpl(String owner) {
		this.balance = 0;
		this.owner = owner;
		this.accID = new BigInteger(6, new SecureRandom()).toString();
	}

	public String getOwner() {
		return owner;
	}

	public String getAccID() {
		return accID;
	}

	@Override
	public void deposit(double amount) {
		System.out.println("deposit");
		balance += amount;
	}

	@Override
	public void withdraw(double amount) throws OverdraftException {
		System.out.println("withdraw");
		if(amount > balance) {
			throw new OverdraftException("The owner" + owner + " have not enough balance on his account" + accID + ".");
		} else {
			balance -= amount;
		}
	}

	@Override
	public double getBalance() {
		System.out.println("getBalance");
		return this.balance;
	}

}
