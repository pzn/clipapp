package ip.cl.clipapp;

public class ClipAppException extends Exception {
	private static final long serialVersionUID = 1L;

	public ClipAppException() {
		super();
	}

	public ClipAppException(String arg0) {
		super(arg0);
	}

	public ClipAppException(Throwable arg0) {
		super(arg0);
	}

	public ClipAppException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ClipAppException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
