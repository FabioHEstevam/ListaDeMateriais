package br.com.estevam.listademateriais.services.exception;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1l;
	
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
}
