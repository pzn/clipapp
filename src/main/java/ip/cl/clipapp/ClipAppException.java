package ip.cl.clipapp;

public class ClipAppException extends Exception {

	public ClipAppException() {
		super();
	}

	public ClipAppException(String message) {
		super(message);
	}

	public ClipAppException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClipAppException(Throwable cause) {
		super(cause);
	}

	protected ClipAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
