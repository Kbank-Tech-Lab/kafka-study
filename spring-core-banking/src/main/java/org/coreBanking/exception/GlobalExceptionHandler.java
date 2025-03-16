package org.coreBanking.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // CustomException 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> handleCustomException(CustomException ex) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("errorCode", ex.getErrorCode());
        errorDetails.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    // Exception 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("errorCode", ErrorCode.SYSTEM_ERROR.getCode());
        errorDetails.put("message", ErrorCode.SYSTEM_ERROR.getMessage());
        errorDetails.put("details", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }

    // MethodArgumentNotValidException 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errorDetails = new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();

        // 첫 번째 에러 가져오기
        if (bindingResult.hasFieldErrors()) {
            var firstError = bindingResult.getFieldErrors().get(0);
            errorDetails.put("errorCode", ErrorCode.INVALID_PARAM.getCode());
            errorDetails.put("message", firstError.getDefaultMessage());
            errorDetails.put("field", firstError.getField());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }
}
