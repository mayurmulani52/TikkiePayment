package com.tikkiepayment.aspect;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tikkiepayment.entity.Audit;
import com.tikkiepayment.service.AuditService;
import com.tikkiepayment.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditService auditService;
    
    
    @Value("${spring.application.name}")
    private String moduleName;

    @Pointcut("execution(public * *(..))")
    public void methodPointcut() {
        // leave empty for pointcut implimentation
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {
        // leave empty for pointcut implimentation
    }

    @Around("controller() && methodPointcut()")
    public Object aroundAllAdviceController(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("inside around advice for controller");
        Object returnObj;
        String className;
        String methodName;
        long lStartTime = 0;
        className = joinPoint.getTarget().getClass().getName();
        methodName = joinPoint.getSignature().getName();
        log.debug("componentName=[{}]", className);
        log.debug("operationName=[{}]", methodName);
        
        Object[] obj = null;
        lStartTime = System.currentTimeMillis();
        try {
            obj = joinPoint.getArgs();
            if (obj != null) {
                returnObj = joinPoint.proceed(obj);
            } else {
                returnObj = joinPoint.proceed();
            }
            try {
                doAudit(methodName, className, CommonUtil.SUCCESS, null, obj, returnObj, null, lStartTime);
            } catch (Exception e) {
                log.error("Failed to audit request ", e);
            }
        } catch (Throwable t) {
            String errorId = UUID.randomUUID().toString();
            log.debug("errorId: {}", errorId);
            MDC.put("errorID", errorId);
            doAudit(methodName, className, CommonUtil.ERROR, errorId, obj, null, t.getMessage(), lStartTime);
            long lEndTime = System.currentTimeMillis();
            long difference = lEndTime - lStartTime;
            log.debug("PERF_LOG ComponentName=[{}] operationName=[{}] timeTaken=[ {} ]", className, methodName, difference);
            throw t;
        }
        return returnObj;
    }
    
    @AfterThrowing(pointcut = "controller() && methodPointcut()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
            log.error("AuditAspect logAfterThrowing exception:{}",joinPoint.getSignature().getName());
            log.error("AuditAspect logAfterThrowing cause:{}", exception.getCause());
    }

    private void doAudit(String method, String className, String status, String errorId, Object[] args, Object returnObj, String errorMessage, long lStartTime)
            throws JsonProcessingException, UnknownHostException {
        log.debug("inside do audit");
        String clientIp = null;
        String serverIp = null;
        try {
            serverIp = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            // do nothing
        }
        String device = null;
        String os = null;
        String osVersion = null;
        String appVersion = null;
        String requestPayload = null;
        String responsePayload = null;
        String sourceApp = null;
        String location = null;
        String awsTraceId = null;
        String host = null;
        String userId=null;
        boolean isPost = true;
        Enumeration enumeration = null;
        HttpServletRequest req = null;
        log.debug("className: {}", className);
        for (Object obj : args) {
            if (obj instanceof HttpServletRequest) {
                req = (HttpServletRequest) obj;
                clientIp = getClientIp(req);
                isPost = "POST".equalsIgnoreCase(req.getMethod());
                enumeration = req.getHeaderNames();
                device = req.getHeader("device");
                os = req.getHeader("os");
                osVersion = req.getHeader("osVersion");
                appVersion = req.getHeader("appVersion");
                sourceApp = req.getHeader("sourceApp");
                location = req.getHeader("location");
                awsTraceId = req.getHeader("x-amzn-trace-id");
                host = req.getHeader("host");
                userId= req.getHeader("userId");
            }
            
            if (!(obj instanceof HttpServletRequest) && (obj instanceof JsonNode)) {
                JsonNode json = (JsonNode) obj;
                requestPayload = json.toString();
                log.debug("1requestPayload: {}", requestPayload);
            } else if (!(obj instanceof HttpServletRequest) && !(obj instanceof HttpServletResponse)) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    requestPayload = mapper.writeValueAsString(obj);
                    /*if (requestPayload.length() > 60000) {
                        requestPayload = requestPayload.substring(0, 60000);
                    }*/
                    log.debug("2requestPayload: {}", requestPayload);
                } catch (JsonProcessingException e) {
                    log.error("Not able to parse request for audit ", e);
                }
            }
        }
        if(!isPost && enumeration!=null && req!=null){
        	JSONObject reqJSON = new JSONObject();
        	while (enumeration.hasMoreElements())             {	 
                 String header = (String) enumeration.nextElement();
                 try {
					reqJSON.put(header, req.getHeader(header));
				} catch (JSONException e) {
					log.error(e.getMessage());
				}
                 log.debug(header + ": " + req.getHeader(header) + " ");
            }
        	requestPayload = reqJSON.toString();
        }
        log.debug("requestPayload: {}", requestPayload);

        if (returnObj !=null) {            
        	ObjectMapper mapper = new ObjectMapper();
            responsePayload = mapper.writeValueAsString(returnObj);
       	  log.debug("responsePayload: {}"+ returnObj.toString());
        }
        if (errorMessage != null) {
            responsePayload = errorMessage;
        }

        createAuditLog(method, className, status, errorId, requestPayload, responsePayload, clientIp, serverIp, device, os, 
                osVersion, appVersion, sourceApp, location, awsTraceId, host, lStartTime,userId);

    }

    private void createAuditLog(String method, String className, String status, String errorId, String request, String response, String clientIp,
            String serverIp, String device, String os, String osVersion, String appVersion, String sourceApp, String location, String awsTraceId, 
            String host, long startTime,String userId) {
        Audit audit = new Audit();

        audit.setMethod(trimBylength(method));
        audit.setClassName(trimBylength(className));
        audit.setStatus(trimBylength(status));
        audit.setErrorId(trimBylength(errorId));
        audit.setUserId(userId);
        audit.setRequest(request);
        audit.setResponse(response);
        audit.setClientIp(trimBylength(clientIp));
        audit.setServerIp(trimBylength(serverIp));
        audit.setDevice(trimBylength(device));
        audit.setOs(trimBylength(os));
        audit.setOsVersion(trimBylength(osVersion));
        audit.setAppVersion(trimBylength(appVersion));
        audit.setSourceApp(trimBylength(sourceApp));
        audit.setLocation(trimBylength(location));
        audit.setLogTransactionId(trimBylength(MDC.get("transactionID")));
        audit.setAwsTraceId(trimBylength(awsTraceId));
        audit.setHost(trimBylength(host));
        
        long lEndTime = System.currentTimeMillis();
        long difference = lEndTime - startTime;
        audit.setTimeTaken(difference);
        Date createdDate = new Date();
        audit.setCreatedDate(createdDate);
        
        audit = auditService.createAudit(audit);
    }

    private String trimBylength(String method) {
        if (method == null) {
            return method;
        }
        if (method.length() > 255) {
            return method.substring(0, 255);
        }
        return method;
    }

    private static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
    
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
    
}
