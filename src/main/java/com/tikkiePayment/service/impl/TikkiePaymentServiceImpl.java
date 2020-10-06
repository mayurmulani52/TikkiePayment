package com.tikkiepayment.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tikkiepayment.exception.TikkiePaymentRuntimeException;
import com.tikkiepayment.external.tikkie.model.PaymentListResponse;
import com.tikkiepayment.external.tikkie.service.TikkiePaymentExternalService;
import com.tikkiepayment.model.CreatePaymentRequest;
import com.tikkiepayment.model.CreatePaymentRequestSuccess;
import com.tikkiepayment.model.GetPaymentRequestListSuccess;
import com.tikkiepayment.service.TikkiePaymentService;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TikkiePaymentServiceImpl implements TikkiePaymentService {
	
	private static final Logger logger = LoggerFactory.getLogger(TikkiePaymentServiceImpl.class);
	
	@Autowired
	private TikkiePaymentExternalService externalService;

	@Override
	public CreatePaymentRequestSuccess createTikkiePaymentService(CreatePaymentRequest request) throws TikkiePaymentRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetPaymentRequestListSuccess getAuditOfpaymentRequests(String email, String paymentRequestToken,
			String referenceId, int page, int size, String fromDateTime, String toDateTime) throws TikkiePaymentRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentListResponse paymentsOfPaymentRequest(String paymentRequestToken, int page, int size,
			String fromDateTime, String toDateTime) throws TikkiePaymentRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
