package exceptions;


/**
 * @author sandeepchowdaryannabathuni
 * 
 * This is custom exception. It is used when no data is 
 * extracted from the GIT logs.
 */
public class NoDataException extends Exception{
	
	public NoDataException() {
		super();
	}
	
	public NoDataException(String message) {
		super(message);
	}
}
