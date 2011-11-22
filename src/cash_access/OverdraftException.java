package cash_access;

public class OverdraftException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8345179486930252564L;
	private String message;

	public OverdraftException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
