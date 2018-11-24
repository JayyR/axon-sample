package io.jay.config;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class ExceptionHandlerAdvice {

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Resource NotFound")
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleResourceNotFound(IllegalArgumentException e){

    }
}
