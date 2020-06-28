package br.com.jajm.beerstore.domain.service.exception;

import org.springframework.http.HttpStatus;

public class CervejaExistenteException extends NegocioException {

	private static final long serialVersionUID = 1L;
	
	public CervejaExistenteException() {
		super("cervejas-5", HttpStatus.BAD_REQUEST);
	}


}
