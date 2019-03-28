package exceptions;

/**
 * This is a custom exception. It is used when the column we want to 
 * add in excel, already exists.
 * @author sandeepchowdaryannabathuni
 *
 */
public class ColumnAlreadyExistsException extends Exception{
	
	public ColumnAlreadyExistsException() {
		super();
	}
	
	public ColumnAlreadyExistsException(String message) {
		super(message);
	}
}
