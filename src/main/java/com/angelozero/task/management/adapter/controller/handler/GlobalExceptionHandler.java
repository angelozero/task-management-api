package com.angelozero.task.management.adapter.controller.handler;

import com.angelozero.task.management.usecase.exception.FieldValidatorException;
import com.angelozero.task.management.usecase.exception.TaskException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TaskException.class)
    public ResponseEntity<Object> handleResourceTaskException(TaskException ex) {
        return new ResponseEntity<>(
                new ErrorData(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value()),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(FieldValidatorException.class)
    public ResponseEntity<Object> handleResourceFieldValidatorException(FieldValidatorException ex) {
        return new ResponseEntity<>(
                new ErrorData(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value()),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(
                new ErrorData(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
