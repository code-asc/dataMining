package exceptions;


/**
 * @author sandeepchowdaryannabathuni
 * 
 * This is custom exception. It is used when data
 * extracted from the GIT logs is empty or null.
 */
public class NoStringDataException extends Exception{
	
	public NoStringDataException() {
		super();
	}
	
	public NoStringDataException(String message) {
		super(message);
	}
}
