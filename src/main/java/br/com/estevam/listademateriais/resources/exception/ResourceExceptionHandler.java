package br.com.estevam.listademateriais.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.estevam.listademateriais.services.exception.ObjectNotFoundException;
import br.com.estevam.listademateriais.services.exception.OperationException;
@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(OperationException.class)
	public ResponseEntity<StandardError> objectNotfound(OperationException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_MODIFIED;
		StandardError err = new StandardError(System.currentTimeMillis(),status.value(),"Conflito",e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotfound(ObjectNotFoundException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(System.currentTimeMillis(),status.value(),"Não encontrado",e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	
}
