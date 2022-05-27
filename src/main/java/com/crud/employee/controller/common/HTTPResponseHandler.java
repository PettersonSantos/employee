package com.crud.employee.controller.common;

import com.crud.employee.domain.exception.BadRequestException;
import com.crud.employee.domain.exception.NotFoundException;
import com.crud.employee.infrastructure.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.logging.Logger;

public abstract class HTTPResponseHandler {
    private static final String STATUS_CODE = "Employee-Status-Code";

    private static final String STATUS_INTERNAL_MESSAGE = "Employee-Status-Internal";

    private static final String STATUS_END_USER_MESSAGE = "Employee-Status-Message";

    private static final Logger logger = Logger.getLogger(String.valueOf(HTTPResponseHandler.class));

    public HTTPResponseHandler() {
    }

    public void setStatusHeaders(HttpServletResponse response, int httpStatus, String code, String message) {
        response.setStatus(httpStatus);
        response.setHeader(STATUS_CODE, code);
        response.setHeader(STATUS_INTERNAL_MESSAGE, message);
    }

    public void setStatusHeaders(HttpServletResponse response, int httpStatus, String code, String message,
                                 String endUserMessage) {
        response.setStatus(httpStatus);
        response.setHeader(STATUS_CODE, code);
        response.setHeader(STATUS_INTERNAL_MESSAGE, message);
        response.setHeader(STATUS_END_USER_MESSAGE, endUserMessage);
    }

    public void setStatusHeaders(HttpServletResponse response, int httpStatus, String code, String message,
                                 String endUserMessage, String externalMessage) {
        response.setStatus(httpStatus);
        response.setHeader(STATUS_CODE, code);
        response.setHeader(STATUS_INTERNAL_MESSAGE, message);
        response.setHeader(STATUS_END_USER_MESSAGE, endUserMessage);

    }

    public void setStatusHeadersToSuccess(HttpServletResponse response) {
        int status = HttpStatus.OK.value();
        setStatusHeaders(response, status, "SUCCESS", "Success");
    }

    @ExceptionHandler(NotFoundException.class)
    public void handleNotFoundException(NotFoundException ex, HttpServletRequest request,
                                        HttpServletResponse response) {

        String code = CommonConstants.HTTP_STATUS_NOT_FOUND_STATUS;
        String message = ex.getStatusMessage();
        int httpStatus = HttpStatus.NOT_FOUND.value();
        String endUserMessage = ex.getEndUserMessage();
        String externalMessage = ex.getExternalMessage();

        logger.warning(message);

        constructHeaders(response, code, message, httpStatus, endUserMessage, externalMessage);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request,
                                                      HttpServletResponse response) {

        String code = CommonConstants.HTTP_STATUS_BAD_REQUEST_STATUS;

        String message = "Invalid character OR characters are specified in the request body.Please double check your request";
        String debugMessage = message + " More details >> " + ex.getMessage();
        int httpStatus = HttpStatus.BAD_REQUEST.value();

        logger.warning(message);

        constructHeaders(response, code, debugMessage, httpStatus, message, null);

    }

    @ExceptionHandler(BadRequestException.class)
    public void handleBadRequestException(BadRequestException ex, HttpServletRequest request,
                                          HttpServletResponse response) {

        String code = CommonConstants.HTTP_STATUS_BAD_REQUEST_STATUS;
        String message = ex.getStatusMessage();
        int httpStatus = HttpStatus.BAD_REQUEST.value();
        String endUserMessage = ex.getEndUserMessage();
        String externalMessage = ex.getExternalMessage();

        logger.warning(message);

        constructHeaders(response, code, message, httpStatus, endUserMessage, externalMessage);

    }

    private void constructHeaders(HttpServletResponse response, String code, String message, int httpStatus,
                                  String endUserMessage, String externalMessage) {

        if (endUserMessage != null && externalMessage != null) {
            setStatusHeaders(response, httpStatus, code, message, endUserMessage, externalMessage);
        } else if (endUserMessage != null) {
            setStatusHeaders(response, httpStatus, code, message, endUserMessage);
        } else {
            setStatusHeaders(response, httpStatus, code, message);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request,
                                               HttpServletResponse response) {

        constants(response, ex.getMessage());

    }

    private void constants(HttpServletResponse response, String message2) {
        String code = CommonConstants.HTTP_STATUS_BAD_REQUEST_STATUS;
        int httpStatus = HttpStatus.BAD_REQUEST.value();

        constructHeaders(response, code, message2, httpStatus, message2, null);

        logger.warning(message2);
    }

    @ExceptionHandler(IllegalStateException.class)
    public void handleIllegalStateException(IllegalStateException ex, HttpServletRequest request,
                                            HttpServletResponse response) {

        constants(response, ex.getMessage());

    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public void handleHttpStatusCodeException(HttpStatusCodeException ex, HttpServletRequest request,
                                              HttpServletResponse response) {

        String code = "";
        String message = "";
        int status = ex.getStatusCode().value();

        try {
            code = Objects.requireNonNull(Objects.requireNonNull(ex.getResponseHeaders()).get(STATUS_CODE)).get(0);
            message = Objects.requireNonNull(ex.getResponseHeaders().get(STATUS_INTERNAL_MESSAGE)).get(0);
        } catch (NullPointerException ignored) {

        }

        logger.warning(message);

        setStatusHeaders(response, status, code, message);

    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleMissingServletRequestParameterException(MissingServletRequestParameterException ex,
                                                              HttpServletRequest request, HttpServletResponse response) {

        String code = CommonConstants.HTTP_STATUS_BAD_REQUEST_STATUS;
        String message = ex.getMessage();
        int status = HttpStatus.BAD_REQUEST.value();

        logger.warning(message);

        setStatusHeaders(response, status, code, message);

    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public void handleServletRequestBindingException(ServletRequestBindingException ex, HttpServletRequest request,
                                                     HttpServletResponse response) {

        String code = CommonConstants.HTTP_STATUS_BAD_REQUEST_STATUS;
        String message = ex.getMessage();
        int status = HttpStatus.BAD_REQUEST.value();

        logger.warning(message);

        setStatusHeaders(response, status, code, message);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request,
                                                      HttpServletResponse response) {

        String code = HttpStatus.BAD_REQUEST.name();
        String message = HttpStatus.BAD_REQUEST.name();

        if (ex.getBindingResult().hasErrors()) {
            List<FieldError> unmodifiableErrorList = ex.getBindingResult().getFieldErrors();
            List<FieldError> errorList = new ArrayList<>(unmodifiableErrorList);
            errorList.sort(new Comparator<FieldError>() {

                @Override
                public int compare(FieldError arg0, FieldError arg1) {
                    return Objects.requireNonNull(arg0.getDefaultMessage()).compareTo(Objects.requireNonNull(arg1.getDefaultMessage()));
                }
            });

            message = errorList.get(0).getDefaultMessage();
        }

        int status = HttpStatus.BAD_REQUEST.value();

        setStatusHeaders(response, status, code, message);

        logger.warning(message);

    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {

        String code = HttpStatus.INTERNAL_SERVER_ERROR.name();
        String message = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

        setStatusHeaders(response, status, code, message);

        logger.warning(message);

    }
}
