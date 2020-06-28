package br.com.jajm.beerstore.infra.exceptionhandler;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
// @JsonAutoDetect(fieldVisibility = Visibility.ANY)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
	
	private final int status;
	private final List<ApiError> erros;
	
	static ErrorResponse of(HttpStatus status, List<ApiError> erros) {
		return new ErrorResponse(status.value(), erros);
	}
	
	static ErrorResponse of(HttpStatus status, ApiError erro) {
		return of(status, Collections.singletonList(erro));
	}
	
	// @JsonAutoDetect(fieldVisibility = Visibility.ANY)
	@Getter
	@Setter
	@RequiredArgsConstructor
	static class ApiError {
		private final String codigo;
		private final String mensagem;
	}

}
