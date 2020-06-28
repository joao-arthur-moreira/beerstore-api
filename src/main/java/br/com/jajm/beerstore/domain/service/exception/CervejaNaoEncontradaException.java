package br.com.jajm.beerstore.domain.service.exception;

import org.springframework.http.HttpStatus;

public class CervejaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CervejaNaoEncontradaException() {
		super("cervejas-6", HttpStatus.NOT_FOUND);
	}

}
