package ip.cl.clipapp;

public class ClipAppRuntimeException extends RuntimeException {

	public ClipAppRuntimeException() {
		super();
	}

	public ClipAppRuntimeException(String message) {
		super(message);
	}

	public ClipAppRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClipAppRuntimeException(Throwable cause) {
		super(cause);
	}

	protected ClipAppRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
