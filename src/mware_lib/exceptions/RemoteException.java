package mware_lib.exceptions;

public class RemoteException extends RuntimeException {

	private static final long serialVersionUID = -8020006298105767262L;
	
	Exception exception;
   
    public RemoteException(String message){
            super(message);
            this.exception = null;
    }

    public RemoteException(String message, Exception exception){
            super(message);
            this.exception = exception;
    }
   
    public Exception getException(){
            return this.exception;
    }
}

