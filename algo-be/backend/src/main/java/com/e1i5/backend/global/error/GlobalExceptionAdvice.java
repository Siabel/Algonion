package com.e1i5.backend.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    /**
     * 우리가 만든 커스텀 예외 처리
     * @param e
     * @return
     */
    @ExceptionHandler(GlobalBaseException.class)
    protected ResponseEntity<ExceptionResponse> handleGlobalBaseException(final GlobalBaseException e) {
        log.error("{} Exception {}: {}", e.getErrorCode(), e.getErrorCode().getCode(), e.getErrorCode().getMessage());
        return makeErrorResponse(e.getErrorCode());
    }

    /**
     * Valid 검증 실패시 오류 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    //TODO @Valid 예외 처리 코드 추가

    public ResponseEntity<ExceptionResponse> makeErrorResponse(GlobalErrorCode e){
        return ResponseEntity.status(e.getStatus())
                .body(ExceptionResponse.builder()
                        .code(e.getCode())
                        .message(e.getMessage())
                        .status(e.getStatus())
                        .build());
    }
}
