package cash_access;

public abstract class Account {
	

	public abstract void deposit(double amount);
	
	public abstract void withdraw(double amount) throws OverdraftException; 
	
	public abstract double getBalance();
	
	public class OverdraftException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		//- message muss über die getMessage()-Methode abrufbar sein! -
		public OverdraftException(String message) {
			return;
		}
	}
}
