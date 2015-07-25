package ip.cl.clipapp;

public class ClipAppRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ClipAppRuntimeException() {
		super();
	}

	public ClipAppRuntimeException(String arg0) {
		super(arg0);
	}

	public ClipAppRuntimeException(Throwable arg0) {
		super(arg0);
	}

	public ClipAppRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ClipAppRuntimeException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
