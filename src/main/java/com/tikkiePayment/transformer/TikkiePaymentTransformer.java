package com.tikkiepayment.transformer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tikkiepayment.entity.AuditPaymentRequest;
import com.tikkiepayment.external.tikkie.model.PaymentRequestCreationRequest;
import com.tikkiepayment.external.tikkie.model.PaymentRequestCreationResponse;
import com.tikkiepayment.model.CreatePaymentRequest;
import com.tikkiepayment.model.CreatePaymentRequestSuccess;
import com.tikkiepayment.model.CreatePaymentRequestSuccess.StatusEnum;
import com.tikkiepayment.model.PaymentRequestResponse;
import com.tikkiepayment.model.PaymentRequestResponse.PaymentTypeEnum;

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

	public static CreatePaymentRequestSuccess transformPaymentRequestSuccessResponse(
			PaymentRequestCreationResponse creationResponse) {

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

	public static List<PaymentRequestResponse> transformAuditPaymentRequestResponse(
			List<AuditPaymentRequest> listOfAuditPaymentRequest) {

		List<PaymentRequestResponse> paymentRequestResponselst = new ArrayList<PaymentRequestResponse>();

		if (listOfAuditPaymentRequest != null && listOfAuditPaymentRequest.size() > 0) {

			listOfAuditPaymentRequest.stream().forEach(auditPaymentRequest -> {

				PaymentRequestResponse paymentRequestResp = new PaymentRequestResponse();

				paymentRequestResp.setAmountInCents(auditPaymentRequest.getAmountInCents());
				paymentRequestResp.setDescription(auditPaymentRequest.getDescription());
				paymentRequestResp.setCreatedDateTime(auditPaymentRequest.getCreatedDateTime());
				paymentRequestResp.setEmail(auditPaymentRequest.getEmail());
				paymentRequestResp.setExpiryDate(auditPaymentRequest.getExpiryDate());
				paymentRequestResp.setPaymentReferenceToken(auditPaymentRequest.getPaymentRequestToken());
				paymentRequestResp.setPaymentType(PaymentTypeEnum.fromValue(auditPaymentRequest.getPaymentType()));
				paymentRequestResp.setPaymentURL(auditPaymentRequest.getPaymentURL());
				paymentRequestResp.setReferenceId(auditPaymentRequest.getReferenceId());
				paymentRequestResp
						.setStatus(PaymentRequestResponse.StatusEnum.fromValue(auditPaymentRequest.getStatus()));
				paymentRequestResponselst.add(paymentRequestResp);

			});
		}
		return paymentRequestResponselst;

	}

}
