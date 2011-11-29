package bank;

public class IllegalParameterException extends RuntimeException {

	private static final long serialVersionUID = 5726575097967984645L;

	public IllegalParameterException(String msg) {
		super(msg);
	}
}
