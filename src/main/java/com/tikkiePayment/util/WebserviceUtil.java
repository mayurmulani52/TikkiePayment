package com.tikkiepayment.util;

import java.text.Normalizer;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.tikkiepayment.entity.AuditExternal;
import com.tikkiepayment.exception.TikkiePaymentRuntimeException;
import com.tikkiepayment.repository.AuditExternalRepository;


@Component
public class WebserviceUtil {

	private static final Logger log = LoggerFactory.getLogger(WebserviceUtil.class);
	
	@Autowired
	AuditExternalRepository auditExternalRepository;
    
    public <T, R> T triggerPostRestAPI(String webserviceURL,
			R requestObj, Class<T> responseClass, HttpHeaders headers) {
    	return triggerRestAPI(webserviceURL, requestObj, responseClass, headers, HttpMethod.POST, false);
    }
    
    public <T, R> T triggerPostRestAPIWithAudit(String webserviceURL,
			R requestObj, Class<T> responseClass, HttpHeaders headers) {
    	return triggerRestAPI(webserviceURL, requestObj, responseClass, headers, HttpMethod.POST, true);
    	
    }
    
    public <T, R> T triggerGetRestAPI(String webserviceURL,
			R requestObj, Class<T> responseClass, HttpHeaders headers) {
    	return triggerRestAPI(webserviceURL, requestObj, responseClass, headers, HttpMethod.GET, false);
    }
    
   
    
	
	private <T, R> T triggerRestAPI(String webserviceURL,
			R requestObj, Class<T> responseClass, HttpHeaders headers, HttpMethod method, boolean isAudit) {

		HttpEntity<R> entity = null;
		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

		long lStartTime = 0;
		long lEndTime = 0;
		
		if(requestObj!=null) {
			entity = new HttpEntity<>(requestObj, headers);
		}else {
			entity = new HttpEntity<>(headers);
		}
		
		ResponseEntity<T> result = null;

		log.debug("triggerRestAPI request url: " + webserviceURL);
		log.debug("triggerRestAPI sending headers: " + headers.toString());
		log.debug("triggerRestAPI request entity: " + entity);
		webserviceURL = Normalizer.normalize(webserviceURL, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		log.info("triggerMicroserviceRestAPI request Normalizer uri: " + webserviceURL);

		try {
			lStartTime = System.currentTimeMillis();
			result = restTemplate.exchange(webserviceURL, method, entity, responseClass);
			lEndTime = System.currentTimeMillis();
			log.debug("triggerRestAPI response: " + result);
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				log.warn("service returned error code 401 validation failed");
				
				log.error("Error Message", ex);
				lEndTime = System.currentTimeMillis();
				createAudit(webserviceURL, entity, result, "ERROR", (lEndTime - lStartTime));
		        throw new TikkiePaymentRuntimeException(ex.getMessage(), ex);
		    }
			if(isAudit) {
				lEndTime = System.currentTimeMillis();
				createAudit(webserviceURL, entity, result, "ERROR", (lEndTime - lStartTime));
			}
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				log.error(
						"triggerRestAPI: 404 — The client specified by the connectionId property is not connected to the session");
				log.error("Client Exception", ex);
				throw new TikkiePaymentRuntimeException(ex.getMessage(), ex);
			} else {
				log.error("Error body", ex.getResponseBodyAsString());
				log.error("Client Exception", ex);
				throw new TikkiePaymentRuntimeException(ex.getMessage(), ex);
			}
		} catch (HttpServerErrorException e) {
			if(isAudit) {
				lEndTime = System.currentTimeMillis();
				createAudit(webserviceURL, entity, result, "ERROR", (lEndTime - lStartTime));
			}
			log.error("Server Exception", e);
			throw new TikkiePaymentRuntimeException(e.getResponseBodyAsString());
		}catch (HttpStatusCodeException e) {
			if (e.getStatusCode() == HttpStatus.I_AM_A_TEAPOT) {
				log.warn("service returned error code 418 validation failed");
				lEndTime = System.currentTimeMillis();
				createAudit(webserviceURL, entity, result, "SUCCESS", (lEndTime - lStartTime));
		        throw new TikkiePaymentRuntimeException("");
		    }
			if(isAudit) {
				lEndTime = System.currentTimeMillis();
				createAudit(webserviceURL, entity, result, "ERROR", (lEndTime - lStartTime));
			}
			log.error("Client Exception", e);
			throw new TikkiePaymentRuntimeException(e.getResponseBodyAsString());
		} catch (Exception ex) {
			if(isAudit) {
				lEndTime = System.currentTimeMillis();
				createAudit(webserviceURL, entity, result, "ERROR", (lEndTime - lStartTime));
			}
			log.error("Exception", ex);
			throw new TikkiePaymentRuntimeException(ex.getMessage(), ex);
		}
		if(isAudit) {
			createAudit(webserviceURL, entity, result, "SUCCESS", (lEndTime - lStartTime));
		}
		return readValue(result, responseClass);
	}
	
	
	private <T> T readValue(ResponseEntity<?> response, Class<T> responseClass) {
        T result = null;
        if (response.getStatusCode() == HttpStatus.OK ||
        		response.getStatusCode() == HttpStatus.CREATED) {
			result = responseClass.cast(response.getBody());
        } else {
        	log.info("No data found {}", response.getStatusCode());
        }
        return result;
    }
	
	private ClientHttpRequestFactory getClientHttpRequestFactory() {
	    int timeout = 10000;
	    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
	      = new HttpComponentsClientHttpRequestFactory();
	    clientHttpRequestFactory.setConnectTimeout(timeout);

	    return clientHttpRequestFactory;
	}
	
	private <T, R> void createAudit(String webserviceURL, HttpEntity<R> request, ResponseEntity<T> response, 
			String status, long timeTaken) {
		AuditExternal auditExternal = new AuditExternal();
		
		auditExternal.setExternalService(webserviceURL);
		auditExternal.setCreatedDate(new Date());
		auditExternal.setLogTransactionId(MDC.get("transactionID"));
		
		if(request!=null && request.getBody()!=null) {
			String req = request.getBody().toString();
			if (req.length() > 60000) {
				req = req.substring(0, 60000);
            }
			auditExternal.setRequest(req);
		}
		
		if(response!=null && response.getBody()!=null) {
			String resp = response.getBody().toString();
			if (resp.length() > 60000) {
				resp = resp.substring(0, 60000);
            }
			auditExternal.setResponse(resp);
		}
		
		auditExternal.setStatus(status);
		auditExternal.setTimeTaken(timeTaken);
		
		auditExternalRepository.save(auditExternal);
	}
}
