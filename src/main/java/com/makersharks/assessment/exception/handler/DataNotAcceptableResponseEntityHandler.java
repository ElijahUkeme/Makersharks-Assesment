package com.makersharks.assessment.exception.handler;

import com.makersharks.assessment.exception.exeception.DataNotAcceptableException;
import com.makersharks.assessment.exception.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ResponseStatus
@ControllerAdvice
public class DataNotAcceptableResponseEntityHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataNotAcceptableException.class)
    public ResponseEntity<ErrorMessage> dataNotAcceptableException(DataNotAcceptableException dataNotAcceptableException, WebRequest webRequest){
        ErrorMessage errorMessage = new ErrorMessage(dataNotAcceptableException.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(errorMessage);
    }
}
