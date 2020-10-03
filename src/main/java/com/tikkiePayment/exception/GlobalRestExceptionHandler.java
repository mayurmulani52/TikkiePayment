package com.tikkiePayment.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tikkiePayment.model.ApiError;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestController
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {TikkiePaymentRuntimeException.class })
	protected ResponseEntity<Object> handleTikkiePaymentRuntimeException(TikkiePaymentRuntimeException ex, WebRequest request) {
		ApiError apiError = new ApiError(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getLocalizedMessage() , MDC.get("errorID"));
		log.error("inside TikkiePaymentRuntimeException:", ex);
		log.error("inside TikkiePaymentRuntimeException: errorID" + MDC.get("errorID"));
		return new ResponseEntity<Object>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("inside handleMethodArgumentNotValid: ",ex);
		log.error("inside handleMethodArgumentNotValid: errorID" + MDC.get("errorID"));
		ApiError apiError = new ApiError(String.valueOf(status.value()), status.getReasonPhrase(), ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.joining(",")) , MDC.get("errorID"));
		return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
	}
    
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
            log.error("CVE" + violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }
        log.error("inside handleConstraintViolation: errorID" + MDC.get("errorID"));
        ApiError apiError = new ApiError(String.valueOf(HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST.getReasonPhrase(),ex.getConstraintViolations().toString()  , MDC.get("errorID"));
        return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
    }
	
}