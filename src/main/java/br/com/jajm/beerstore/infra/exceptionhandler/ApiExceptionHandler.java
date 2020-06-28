package br.com.jajm.beerstore.infra.exceptionhandler;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.jajm.beerstore.domain.service.exception.EntidadeNaoEncontradaException;
import br.com.jajm.beerstore.domain.service.exception.NegocioException;
import br.com.jajm.beerstore.infra.exceptionhandler.ErrorResponse.ApiError;

@RestControllerAdvice
public class ApiExceptionHandler {
	
	@Autowired
	private MessageSource apiErrorMessageSource;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, 
			Locale locale) {
		Stream<ObjectError> errors = exception.getBindingResult().getAllErrors().stream();
		
		List<ApiError> apiErrors = errors
				.map(ObjectError::getDefaultMessage)
				.map(code -> apiError(code, locale))
				.collect(Collectors.toList());
		
		ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErrors);
		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, Locale locale) {
		final String codigoErro = "generico-1";
		final HttpStatus status = HttpStatus.BAD_REQUEST;
		final ErrorResponse errorResponse = ErrorResponse.of(status, apiError(codigoErro, locale));
		return ResponseEntity.badRequest().body(errorResponse);		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception exception, Locale locale) {
		final String codigoErro = "generico-2";
		final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		final ErrorResponse errorResponse = ErrorResponse.of(status, apiError(codigoErro, locale));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);		
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<ErrorResponse> handleNegocioException(NegocioException exception, Locale locale) {
		final String codigoErro = exception.getCodigoMensagem();
		final HttpStatus status = exception.getStatus();
		final ErrorResponse errorResponse = ErrorResponse.of(status, apiError(codigoErro, locale));
		return ResponseEntity.badRequest().body(errorResponse);		
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<ErrorResponse> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException exception, Locale locale) {
		final String codigoErro = exception.getCodigoMensagem();
		final HttpStatus status = exception.getStatus();
		final ErrorResponse errorResponse = ErrorResponse.of(status, apiError(codigoErro, locale));
		return ResponseEntity.status(status).body(errorResponse);		
	}
	
	private ApiError apiError(String codigo, Locale locale, Object... args) {
		String mensagem;
		try {			
			mensagem = apiErrorMessageSource.getMessage(codigo, args, locale);
		} catch (NoSuchMessageException e) {
			mensagem = "Nenhuma mensagem disponível!";
		}
		return new ErrorResponse.ApiError(codigo, mensagem);
	}

}
