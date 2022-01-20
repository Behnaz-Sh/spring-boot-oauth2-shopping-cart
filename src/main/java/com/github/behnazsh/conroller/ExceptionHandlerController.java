package com.github.behnazsh.conroller;

import com.github.behnazsh.common.ExceptionResponse;
import com.github.behnazsh.common.UnauthorizedUserException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.*;

/**
 * @author Behnaz Sh
 */

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    private static final Logger LOG = getLogger(ExceptionHandlerController.class);
    private static final String EXCEPTION_MESSAGE = "Exception in: {} , message: {}";
    private static final String INCORRECT_REQUEST = "INCORRECT_REQUEST";
    @Autowired 
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(ex.getClass().getSimpleName());
        response.setMessage(ex.getBindingResult()
                .getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList()));
        response.setDetails(request.getDescription(false));
        return new ResponseEntity<>(response, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        LOG.error("Exception in: {} , message: {} , status: {}", request.getDescription(false),ex.getMessage(), status.name());
        ExceptionResponse response = new ExceptionResponse();
        response.setException(ex.getClass().getSimpleName());
        response.setMessage(Collections.singletonList(status.name() + ":" + ex.getMessage()));
        response.setDetails(request.getDescription(false));
        return new ResponseEntity<>(response, headers, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFoundHandler(ResourceNotFoundException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(HttpStatus.NOT_FOUND.name());
        response.setMessage(Collections.singletonList(ex.getMessage()));
        response.setDetails(request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public final ResponseEntity<ExceptionResponse> handleUserNotFoundException(UnauthorizedUserException ex,
                                                                               WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(INCORRECT_REQUEST);
        response.setMessage(Collections.singletonList(ex.getMessage()));
        response.setDetails(request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request){
        LOG.error(EXCEPTION_MESSAGE, request.getDescription(false) , ex.getMessage());
        Set<ConstraintViolation<?>> s = ex.getConstraintViolations();
        List<String> message= new ArrayList<>();
        for (ConstraintViolation<?> violation : s) {
            message.add(violation.getMessage());
        }
        ExceptionResponse response = new ExceptionResponse();
        response.setException(ex.getClass().getSimpleName());
        response.setMessage(message);
        response.setDetails(request.getDescription(false));
        return new ResponseEntity<>(response, new HttpHeaders(), BAD_REQUEST);
     }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<Object> handleOptimisticLockingException(ObjectOptimisticLockingFailureException ex, WebRequest request){
        LOG.error(EXCEPTION_MESSAGE , request.getDescription(false) , ex.getMessage());
        ExceptionResponse response = new ExceptionResponse();
        response.setException(ex.getClass().getSimpleName());
        response.setMessage(Collections.singletonList(messageSource.getMessage("product.optimisticLocking", null, Locale.ENGLISH)));
        response.setDetails(request.getDescription(false));
        return new ResponseEntity<>(response, new HttpHeaders(), CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request){
        LOG.error(EXCEPTION_MESSAGE , request.getDescription(false) , ex.getMessage());
        ExceptionResponse response = new ExceptionResponse();
        response.setException(ex.getClass().getSimpleName());
        response.setMessage(Collections.singletonList(ex.getMessage()));
        response.setDetails(request.getDescription(false));
        return new ResponseEntity<>(response, new HttpHeaders(), INTERNAL_SERVER_ERROR);
    }
}