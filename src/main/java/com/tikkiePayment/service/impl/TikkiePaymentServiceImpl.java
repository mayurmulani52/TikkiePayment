package com.tikkiepayment.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tikkiepayment.entity.AuditPaymentRequest;
import com.tikkiepayment.exception.TikkiePaymentRuntimeException;
import com.tikkiepayment.external.tikkie.model.PaymentListResponse;
import com.tikkiepayment.external.tikkie.model.PaymentRequestCreationRequest;
import com.tikkiepayment.external.tikkie.model.PaymentRequestCreationResponse;
import com.tikkiepayment.external.tikkie.service.TikkiePaymentExternalService;
import com.tikkiepayment.model.CreatePaymentRequest;
import com.tikkiepayment.model.CreatePaymentRequestSuccess;
import com.tikkiepayment.model.GetPaymentRequestListSuccess;
import com.tikkiepayment.model.PaymentRequestResponse;
import com.tikkiepayment.repository.AuditPaymentRequestRepository;
import com.tikkiepayment.service.TikkiePaymentService;
import com.tikkiepayment.transformer.TikkiePaymentTransformer;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TikkiePaymentServiceImpl implements TikkiePaymentService {

	private static final Logger logger = LoggerFactory.getLogger(TikkiePaymentServiceImpl.class);

	@Autowired
	private TikkiePaymentExternalService externalService;

	@Autowired
	private AuditPaymentRequestRepository auditPaymentRepository;

	@Override
	public CreatePaymentRequestSuccess createTikkiePaymentService(CreatePaymentRequest request)
			throws TikkiePaymentRuntimeException {

		logger.info("createTikkiePaymentService service layer called!");
		CreatePaymentRequestSuccess paymentRequestSuccess = null;
		PaymentRequestCreationRequest createPaymentRequest = TikkiePaymentTransformer
				.createPaymentRequestCreationRequest(request);
		AuditPaymentRequest auditPaymentRequest = createNewAuditPaymentRequest(request);
		try {
			PaymentRequestCreationResponse responseSuccess = externalService
					.createPaymentRequestCreation(createPaymentRequest);

			if (responseSuccess != null) {
				auditPaymentRequest.setPaymentType("SUCCESSFUL");
				auditPaymentRequest.setStatus(responseSuccess.getStatus().getValue());
				auditPaymentRequest.setPaymentURL(responseSuccess.getUrl());
			}
			paymentRequestSuccess = TikkiePaymentTransformer.transformPaymentRequestSuccessResponse(responseSuccess);
		} catch (Exception e) {
			auditPaymentRequest.setPaymentType("ERROR");
			throw e;
		} finally {
			auditPaymentRepository.save(auditPaymentRequest);
		}

		return paymentRequestSuccess;

	}

	@Override
	public GetPaymentRequestListSuccess getAuditOfpaymentRequests(String email, String paymentRequestToken,
			String referenceId, int page, int size, String fromDateTime, String toDateTime)
			throws TikkiePaymentRuntimeException {

		logger.info("getAuditOfpaymentRequests service layer called!");

		page = page - 1;
		if (page < 0)
			throw new TikkiePaymentRuntimeException("Page index must be greater than zero!");
		else if (size <= 0)
			throw new TikkiePaymentRuntimeException("Size index must be greater than zero!");

		Page<AuditPaymentRequest> listOfAuditPaymentRequest = auditPaymentRepository
				.findAll(PageRequest.of(page, size, Sort.by("createdDateTime").descending()));
		
		
		// As mongo atlas free doesnt support where clause 
		/*Page<AuditPaymentRequest> listOfAuditPaymentRequest = auditPaymentRepository
				.findByPaymentRequestTokenAndReferenceIdAndEmail(paymentRequestToken, referenceId, email, PageRequest.of(page, size, Sort.by("createdDateTime").descending()));*/
		
		Long count = auditPaymentRepository.count();
		
		
		GetPaymentRequestListSuccess responseSuccess = new GetPaymentRequestListSuccess();
		
		List<PaymentRequestResponse> paymentRequestResponse = TikkiePaymentTransformer.transformAuditPaymentRequestResponse(listOfAuditPaymentRequest.getContent());
		
		responseSuccess.setTotalElementCount(count!=null?count.intValue():0);
		responseSuccess.setPaymentRequests(paymentRequestResponse);

		return responseSuccess;
	}

	@Override
	public PaymentListResponse paymentsOfPaymentRequest(String paymentRequestToken, int page, int size,
			String fromDateTime, String toDateTime) throws TikkiePaymentRuntimeException {
		logger.info("paymentsOfPaymentRequest service layer called!");
		return externalService.getAllPaymentList(paymentRequestToken, page, size, fromDateTime, toDateTime,
				Boolean.TRUE);
	}

	private AuditPaymentRequest createNewAuditPaymentRequest(CreatePaymentRequest request) {

		AuditPaymentRequest auditPaymentRequest = new AuditPaymentRequest();

		auditPaymentRequest.setAmountInCents(request.getAmountInCents());
		auditPaymentRequest.setDescription(request.getDescription());
		auditPaymentRequest.setEmail(request.getEmail());
		auditPaymentRequest.setExpiryDate(request.getExpiryDate().toString());
		auditPaymentRequest.setReferenceId(request.getReferenceId());
		auditPaymentRequest.setCreatedDateTime(new Date());
		auditPaymentRequest.setPaymentType("ERROR"); // set default error if payment creation successful then it will be
														// changed

		return auditPaymentRequest;

	}

}
