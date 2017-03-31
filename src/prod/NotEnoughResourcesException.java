package prod;

public class NotEnoughResourcesException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotEnoughResourcesException(String message){
	     super(message);
	  }
}
