package br.com.estevam.listademateriais.services.exception;

public class OperationException extends RuntimeException{

	private static final long serialVersionUID = 1l;
	
	public OperationException(String msg) {
		super(msg);
	}
}
