package vn.hoidanit.jobhunter.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import vn.hoidanit.jobhunter.domain.RestResponse;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(IdInValidException.class)
    public ResponseEntity<RestResponse<Object>> handleIdInValidException(IdInValidException ex) {

        RestResponse<Object> rs = new RestResponse<Object>();
        rs.setStatus(HttpStatus.BAD_REQUEST.value());
        rs.setMessage("Error Id is invalid (<1500)");
        rs.setError(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rs);
    }

}