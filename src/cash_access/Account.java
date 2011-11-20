package cash_access;

public abstract class Account {
	

	public abstract void deposit(double amount);
	
	public abstract void withdraw(double amount) throws OverdraftException; 
	
	public abstract double getBalance();
	
}
