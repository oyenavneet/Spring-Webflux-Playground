package com.oyenavneet.playground.sec04.advice;


import com.oyenavneet.playground.sec04.exceptions.CustomerNotFoundException;
import com.oyenavneet.playground.sec04.exceptions.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail handleCustomerNotFoundException(CustomerNotFoundException ex){
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setType(URI.create("https://oyenavneet.com/problem/CustomerNotFound"));
        problem.setTitle("Customer not found");
        return problem;
    }


    @ExceptionHandler(InvalidInputException.class)
    public ProblemDetail handleInvalidInputException(InvalidInputException ex){
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setType(URI.create("https://oyenavneet.com/problem/InvalidInput"));
        problem.setTitle("Invalid input");
        return problem;
    }
}
