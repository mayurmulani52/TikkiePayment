package com.tikkiepayment.external.tikkie.service;

import com.tikkiepayment.exception.TikkiePaymentRuntimeException;
import com.tikkiepayment.external.tikkie.model.PaymentListResponse;
import com.tikkiepayment.external.tikkie.model.PaymentRequestCreationRequest;
import com.tikkiepayment.external.tikkie.model.PaymentRequestCreationResponse;
import com.tikkiepayment.external.tikkie.model.PaymentRequestsListResponse;

public interface TikkiePaymentExternalService {
	
	PaymentRequestCreationResponse createPaymentRequestCreation(PaymentRequestCreationRequest paymentCreationRequest) throws TikkiePaymentRuntimeException;
	
	PaymentRequestsListResponse getAllPaymentRequestList(int pageNumber, int pageSize, String fromDateTime, String toDateTime) throws TikkiePaymentRuntimeException;
	
	PaymentListResponse getAllPaymentList(String paymentRequestToken, int pageNumber, int pageSize, String fromDateTime, String toDateTime, boolean includeRefunds) throws TikkiePaymentRuntimeException;
	
}
