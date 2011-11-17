package cash_access;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Konto extends Account {
	
	private BigInteger id = null;
	private double balance = 0;
	
	public Konto() {
		SecureRandom sc = new SecureRandom();
		BigInteger bi = new BigInteger(32, sc);
		this.id = bi;
	}

	@Override
	public void deposit(double amount) {
		this.balance = balance + amount;
	}

	@Override
	public void withdraw(double amount) throws OverdraftException {
		if (balance - amount < 0) {
			throw new OverdraftException("Fehlende Deckung des Kontos, Abhebung abgelehnt.");
		} else {
			this.balance = balance - amount;
		}
	}

	@Override
	public double getBalance() {
		return balance;
	}

	public BigInteger getId() {
		return id;
	}

}
