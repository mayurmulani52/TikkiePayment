package com.tikkiepayment.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tikkiepayment.external.tikkie.model.PaymentRequestCreationRequest;
import com.tikkiepayment.external.tikkie.model.PaymentRequestCreationResponse;
import com.tikkiepayment.model.CreatePaymentRequest;
import com.tikkiepayment.model.CreatePaymentRequestSuccess;
import com.tikkiepayment.model.CreatePaymentRequestSuccess.StatusEnum;

public class TikkiePaymentTransformer {
	
	private static final Logger logger = LoggerFactory.getLogger(TikkiePaymentTransformer.class);
	
	public static PaymentRequestCreationRequest createPaymentRequestCreationRequest(CreatePaymentRequest request) {
		
		logger.info("createPaymentRequestCreationRequest transformer invoked!");
		PaymentRequestCreationRequest paymentRequestCreationRequest = new PaymentRequestCreationRequest();
		paymentRequestCreationRequest.setAmountInCents(request.getAmountInCents());
		paymentRequestCreationRequest.setDescription(request.getDescription());
		paymentRequestCreationRequest.setExpiryDate(request.getExpiryDate().toString());
		paymentRequestCreationRequest.setReferenceId(request.getReferenceId());
		
		return paymentRequestCreationRequest;
	}
	
	public static CreatePaymentRequestSuccess transformPaymentRequestSuccessResponse(PaymentRequestCreationResponse creationResponse) {
		
		logger.info("transformPaymentRequestSuccessResponse transformer invoked!");
		CreatePaymentRequestSuccess paymentRequestSuccess = new CreatePaymentRequestSuccess();
		paymentRequestSuccess.setAmountInCents(creationResponse.getAmountInCents());
		paymentRequestSuccess.setCreatedDateTime(creationResponse.getCreatedDateTime());
		paymentRequestSuccess.setDescription(creationResponse.getDescription());
		paymentRequestSuccess.setPaymentRequestToken(creationResponse.getPaymentRequestToken());
		paymentRequestSuccess.setReferenceId(creationResponse.getReferenceId());
		paymentRequestSuccess.setStatus(StatusEnum.fromValue(creationResponse.getStatus().getValue()));
		paymentRequestSuccess.setUrl(creationResponse.getUrl());
		
		return paymentRequestSuccess;
	}

}
