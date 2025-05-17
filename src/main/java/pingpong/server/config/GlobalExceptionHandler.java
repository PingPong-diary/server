package pingpong.server.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import pingpong.server.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = { IllegalStateException.class, IllegalArgumentException.class })
	public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException ex) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

	    return new ResponseEntity<>(
	        new ApiResponse<>(false, ex.getMessage(), null),
	        headers,
	        HttpStatus.UNAUTHORIZED
	    );
	}

}
