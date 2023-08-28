package com.blogapi11.exception;

import com.blogapi11.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFound(ResourceNotFoundException e, WebRequest request){
        ErrorDetails ed=new ErrorDetails(e.getMessage(), request.getDescription(false),new Date() );
        return new ResponseEntity<>(ed, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception e,WebRequest request){
        ErrorDetails ed=new ErrorDetails(e.getMessage(),request.getDescription(false),new Date());
        return new ResponseEntity<>(ed,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
