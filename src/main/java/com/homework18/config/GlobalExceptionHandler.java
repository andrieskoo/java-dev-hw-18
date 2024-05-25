package com.homework18.config;

import com.homework18.exception.IllegalArgumentException;
import com.homework18.exception.UserNotFoundException;
import com.homework18.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, Map<String, List<String>>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, List<String>> result = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> {
                    if (result.containsKey(e.getField())) {
                        result.get(e.getField()).add(e.getDefaultMessage());
                    } else {
                        result.put(e.getField(), asList(e.getDefaultMessage()));
                    }
                });
        return new ResponseEntity<>(getErrorsMap(result), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
            UserNotFoundException.class
    })
    public ResponseEntity<Map<String, List<String>>> userNotFoundException(Exception ex) {
        return getErrorsMap(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class
    })
    public ResponseEntity<Map<String, List<String>>> illegalArgumentException(Exception ex) {
        return getErrorsMap(ex, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {
            Exception.class
    })
    public ResponseEntity<Map<String, List<String>>> handleOtherExceptions(Exception ex) {
        return getErrorsMap(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Map<String, List<String>>> getErrorsMap(Map<String, List<String>> errors) {
        Map<String, Map<String, List<String>>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    private ResponseEntity<Map<String, List<String>>> getErrorsMap(Throwable ex, HttpStatus status) {
        Map<String, List<String>> map = new HashMap<>();
        map.put("error", Collections.singletonList(status.getReasonPhrase()));
        return new ResponseEntity<>(map, new HttpHeaders(), status);
    }
}
