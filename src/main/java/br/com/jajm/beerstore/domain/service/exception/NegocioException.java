package br.com.jajm.beerstore.domain.service.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final String codigoMensagem;
	private final HttpStatus status;

}
