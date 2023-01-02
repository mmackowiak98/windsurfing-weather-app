package com.weather.windsurfingweather.handler;

import com.weather.windsurfingweather.exceptions.GlobalException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ProblemDetail handleGlobalException(GlobalException e){
        return ProblemDetail.forStatusAndDetail(e.getStatus(),e.getMessage());
    }
    @ExceptionHandler(Throwable.class)
    public ProblemDetail handleUnknownException(Throwable e){
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500),e.getMessage());
    }
}
