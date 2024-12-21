package vn.hoidanit.jobhunter.service.error;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import vn.hoidanit.jobhunter.domain.RestResponse;

@ControllerAdvice
public class GlobalException {

    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    // Tạo đối tượng RestResponse dùng chung
    private RestResponse<Object> createErrorResponse(HttpStatus status, String message, String error) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatus(status.value());
        res.setMessage(message);
        res.setError(error);
        return res;
    }

    // Xử lý ngoại lệ chung
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<Object>> handleAllExceptions(Exception ex) {
        logger.error("Unhandled exception: ", ex);
        RestResponse<Object> res = createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }

    // Xử lý IdInValidException
    @ExceptionHandler(IdInValidException.class)
    public ResponseEntity<RestResponse<Object>> handleIdInValidException(IdInValidException ex) {
        logger.warn("Invalid ID exception: {}", ex.getMessage());
        RestResponse<Object> res = createErrorResponse(HttpStatus.BAD_REQUEST,
                "Error: ID is invalid (<1500)",
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    // Xử lý UsernameNotFoundException và BadCredentialsException
    @ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class })
    public ResponseEntity<RestResponse<Object>> handleAuthenticationExceptions(Exception ex) {
        logger.warn("Authentication exception: {}", ex.getMessage());
        RestResponse<Object> res = createErrorResponse(HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                "Authentication error");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    // Xử lý MethodArgumentNotValidException (lỗi validate dữ liệu)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        // Thu thập lỗi
        List<String> errors = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        RestResponse<Object> res = createErrorResponse(HttpStatus.BAD_REQUEST,
                errors.size() > 1 ? errors.toString() : errors.get(0),
                "Validation error");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    
}
