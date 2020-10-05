package com.tikkiepayment.external.tikkie.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.tikkiepayment.exception.TikkiePaymentRuntimeException;
import com.tikkiepayment.external.tikkie.model.PaymentListResponse;
import com.tikkiepayment.external.tikkie.model.PaymentRequestCreationRequest;
import com.tikkiepayment.external.tikkie.model.PaymentRequestCreationResponse;
import com.tikkiepayment.external.tikkie.model.PaymentRequestsListResponse;
import com.tikkiepayment.external.tikkie.model.SandboxApp;
import com.tikkiepayment.external.tikkie.service.TikkiePaymentExternalService;
import com.tikkiepayment.util.WebserviceUtil;

@Service
public class TikkiePaymentExternalServiceImpl implements TikkiePaymentExternalService{
	
	private static final Logger log = LoggerFactory.getLogger(TikkiePaymentExternalServiceImpl.class);

	@Autowired
	WebserviceUtil webserviceUtil;
	
	@Value("${tikkie.payment.base.sandbox.url}")
	private String tikkiePaymentSandboxURL;
	
	@Value("${tikkie.payment.api.key}")
	private String tikkiePaymentAPIKEY;
	
	private final String CREATE_PAYMENT_REQUEST="/paymentrequests";
	
	private final String GET_PAYMENT_REQUESTS="/paymentrequests";
	
	private final String GET_PAYMENTS="/payments";
	
	private final String AUTH_TOKEN="/sandboxapps";

	@Override
	public PaymentRequestCreationResponse createPaymentRequestCreation(
			PaymentRequestCreationRequest paymentCreationRequest) {
		
		String webserviceURL = tikkiePaymentSandboxURL + CREATE_PAYMENT_REQUEST;
		PaymentRequestCreationResponse resp = webserviceUtil.triggerPostRestAPIWithAudit(webserviceURL,
				paymentCreationRequest, PaymentRequestCreationResponse.class, getAuthHeader());
		log.info("createPaymentRequestCreation webservice called");
		return resp;
	}

	@Override
	public PaymentRequestsListResponse getAllPaymentRequestList(int pageNumber, int pageSize, String fromDateTime,
			String toDateTime) {

		String webserviceURL = tikkiePaymentSandboxURL + GET_PAYMENT_REQUESTS;
		
		String urlParameters ="pageNumber="+pageNumber+"&pageSize="+pageSize;
		if(fromDateTime!=null && toDateTime!=null && !fromDateTime.isEmpty() && !toDateTime.isEmpty()) {
			urlParameters = urlParameters+"&fromDateTime="+fromDateTime+"&toDateTime="+toDateTime;
		}
		
		webserviceURL = webserviceURL+urlParameters;
		PaymentRequestsListResponse resp = webserviceUtil.triggerPostRestAPIWithAudit(webserviceURL,
				null, PaymentRequestsListResponse.class, getAuthHeader());
		log.info("getAllPaymentRequestList webservice called");
		return resp;
	}

	@Override
	public PaymentListResponse getAllPaymentList(String paymentRequestToken, int pageNumber, int pageSize,
			String fromDateTime, String toDateTime, boolean includeRefunds) {

		String webserviceURL = tikkiePaymentSandboxURL +"/"+ paymentRequestToken+ GET_PAYMENTS;
		String urlParameters ="pageNumber="+pageNumber+"&pageSize="+pageSize+"&includeRefunds="+includeRefunds;
		if(fromDateTime!=null && toDateTime!=null && !fromDateTime.isEmpty() && !toDateTime.isEmpty()) {
			urlParameters = urlParameters+"&fromDateTime="+fromDateTime+"&toDateTime="+toDateTime;
		}
		webserviceURL = webserviceURL+urlParameters;
		PaymentListResponse resp = webserviceUtil.triggerPostRestAPIWithAudit(webserviceURL,
				null, PaymentListResponse.class, getAuthHeader());
		log.info("getAllPaymentList webservice called");
		return resp;
	}
	
	private HttpHeaders getAuthHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-App-Token", getAccessToken());
		headers.set("API-Key", tikkiePaymentAPIKEY);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	@Cacheable(value = "getAccessToken")
	private String getAccessToken() {
		log.info("==========triggering getAccessToken===========");
		String accessToken = null;
		String webserviceURL = tikkiePaymentSandboxURL+AUTH_TOKEN;
		HttpHeaders headers = new HttpHeaders();
		headers.set("API-Key", tikkiePaymentAPIKEY);
		headers.setContentType(MediaType.APPLICATION_JSON);

		try {
			SandboxApp resp = webserviceUtil.triggerPostRestAPI(webserviceURL, null,
					SandboxApp.class, headers);

			if (resp != null && resp.getAppToken() != null) {
				log.info("got access token");
				accessToken = resp.getAppToken().toString();
			}else {
				throw new TikkiePaymentRuntimeException("Something went wrong! Pleaser try again later!");
			}
		} catch (Exception e) {
			throw new TikkiePaymentRuntimeException("Something went wrong! Pleaser try again later!"+e.getMessage());
		}
		log.info("Access token =" + accessToken);
		return accessToken;
	}

}
