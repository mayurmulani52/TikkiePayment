/*package com.tikkiepayment.sercurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tikkiepayment.model.ApiError;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
        String errorIds = MDC.get("errorID");
        logger.error("error", ex);
        logger.debug("errorIds: {}", errorIds);
        logger.error("RestAuthenticationEntryPoint commence authenticationException: ", ex);
        processException(request, response, errorIds, ex.getLocalizedMessage());

    }

    private void processException(HttpServletRequest request, HttpServletResponse response, String errorId, String errMessage) throws IOException {
        logger.debug("RestAuthenticationEntryPoint processException getContextPath: {}", request.getContextPath());
        logger.debug("RestAuthenticationEntryPoint processException getSession: {}", request.getSession().getId());

        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ApiError apiError = new ApiError(String.valueOf(HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED.name(), errMessage, errorId);
        ObjectMapper objectMapper = new ObjectMapper();
        response.getOutputStream().println(objectMapper.writeValueAsString(apiError));
    }

}*/