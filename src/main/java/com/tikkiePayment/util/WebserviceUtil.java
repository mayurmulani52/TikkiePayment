package com.tikkiePayment.util;

import java.text.Normalizer;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.tikkiePayment.exception.TikkiePaymentRuntimeException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebserviceUtil {

	public <R> ResponseEntity<?> triggerPostRestAPI(String webserviceURL, R requestObj, Class<?> responseClass,
			HttpHeaders headers) {
		return (ResponseEntity<?>) triggerRestAPI(webserviceURL, requestObj, responseClass, headers, HttpMethod.POST);
	}

	public <R> ResponseEntity<?> triggerGetRestAPI(String webserviceURL, R requestObj, Class<?> responseClass, HttpHeaders headers) {
		return (ResponseEntity<?>) triggerRestAPI(webserviceURL, requestObj, responseClass, headers, HttpMethod.GET);
	}

	private <R> ResponseEntity<?> triggerRestAPI(String webserviceURL, R requestObj, Class<?> responseClass, HttpHeaders headers,
			HttpMethod method) {

		HttpEntity<R> entity = null;
		RestTemplate restTemplate = new RestTemplate();

		if (requestObj != null) {
			entity = new HttpEntity<>(requestObj, headers);
		} else {
			entity = new HttpEntity<>(headers);
		}

		ResponseEntity<?> result = null;

		log.debug("triggerRestAPI request url: " + webserviceURL);
		log.debug("triggerRestAPI sending headers: " + headers.toString());
		log.debug("triggerRestAPI request entity: " + entity);
		webserviceURL = Normalizer.normalize(webserviceURL, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		log.debug("triggerMicroserviceRestAPI request Normalizer uri: " + webserviceURL);

		try {
			result = restTemplate.exchange(webserviceURL, method, entity, responseClass);
			log.debug("triggerRestAPI response: " + result);
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				log.error(
						"triggerRestAPI: 404 — The client specified by the connectionId property is not connected to the session");
				log.error("Client Exception", ex);
				throw new TikkiePaymentRuntimeException(ex.getMessage(), ex);
			} else {
				log.error("Client Exception", ex);
				throw new TikkiePaymentRuntimeException(ex.getMessage(), ex);
			}
		} catch (HttpStatusCodeException e) {
			throw new TikkiePaymentRuntimeException("Pass Valid Consultation Id! No Patient found against consultation Id!");
		} catch (Exception ex) {
			log.error("Exception", ex);
			throw new TikkiePaymentRuntimeException(ex.getMessage(), ex);
		}
		return result;
	}
}
