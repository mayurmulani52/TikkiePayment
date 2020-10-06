package com.tikkiepayment.service;

import com.tikkiepayment.exception.TikkiePaymentRuntimeException;
import com.tikkiepayment.external.tikkie.model.PaymentListResponse;
import com.tikkiepayment.model.CreatePaymentRequest;
import com.tikkiepayment.model.CreatePaymentRequestSuccess;
import com.tikkiepayment.model.GetPaymentRequestListSuccess;

public interface TikkiePaymentService {
	
	CreatePaymentRequestSuccess createTikkiePaymentService(CreatePaymentRequest request) throws TikkiePaymentRuntimeException;
	
	GetPaymentRequestListSuccess getAuditOfpaymentRequests(String email, String paymentRequestToken, String referenceId, int page, int size, String fromDateTime, String toDateTime) throws TikkiePaymentRuntimeException;
	
	PaymentListResponse paymentsOfPaymentRequest(String paymentRequestToken, int page, int size, String fromDateTime, String toDateTime) throws TikkiePaymentRuntimeException;

}
