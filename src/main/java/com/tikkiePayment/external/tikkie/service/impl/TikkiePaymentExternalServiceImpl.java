package com.tikkiepayment.external.tikkie.service.impl;

import org.springframework.stereotype.Service;

import com.tikkiepayment.external.tikkie.model.PaymentListResponse;
import com.tikkiepayment.external.tikkie.model.PaymentRequestCreationRequest;
import com.tikkiepayment.external.tikkie.model.PaymentRequestCreationResponse;
import com.tikkiepayment.external.tikkie.model.PaymentRequestsListResponse;
import com.tikkiepayment.external.tikkie.service.TikkiePaymentExternalService;

@Service
public class TikkiePaymentExternalServiceImpl implements TikkiePaymentExternalService{

	@Override
	public PaymentRequestCreationResponse createPaymentRequestCreation(
			PaymentRequestCreationRequest paymentCreationRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentRequestsListResponse getAllPaymentRequestList(int pageNumber, int pageSize, String fromDateTime,
			String toDateTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentListResponse getAllPaymentList(String paymentRequestToken, int pageNumber, int pageSize,
			String fromDateTime, String toDateTime, boolean includeRefunds) {
		// TODO Auto-generated method stub
		return null;
	}

}
