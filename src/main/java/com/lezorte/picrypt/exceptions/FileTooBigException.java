package com.lezorte.picrypt.exceptions;

public class FileTooBigException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1831519497275101534L;

	public FileTooBigException() {
	}

	public FileTooBigException(String arg0) {
		super(arg0);
	}

	public FileTooBigException(Throwable arg0) {
		super(arg0);
	}

	public FileTooBigException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FileTooBigException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
