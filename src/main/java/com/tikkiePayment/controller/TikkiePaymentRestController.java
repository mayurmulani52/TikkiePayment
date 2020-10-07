package com.tikkiepayment.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tikkiepayment.exception.TikkiePaymentRuntimeException;
import com.tikkiepayment.external.tikkie.model.PaymentListResponse;
import com.tikkiepayment.model.CreatePaymentRequest;
import com.tikkiepayment.model.CreatePaymentRequestSuccess;
import com.tikkiepayment.model.GetPaymentRequestListSuccess;
import com.tikkiepayment.service.TikkiePaymentService;


@RestController
@RequestMapping(value = "/tikkie-payment")
public class TikkiePaymentRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TikkiePaymentRestController.class);
	
	private static final int DEFAULT_CURRENT_PAGE = 0;
	private static final int DEFAULT_CURRENT_PAGESIZE = 10;
	
	@Autowired
	private TikkiePaymentService tikkiePaymentService;
	
	@PostMapping(value = "/createPaymentRequest")
	public ResponseEntity<CreatePaymentRequestSuccess> createPaymentRequest( @Valid @RequestBody CreatePaymentRequest body) throws TikkiePaymentRuntimeException{
		logger.info("calling createPaymentRequest!");
		
		return new ResponseEntity<>(tikkiePaymentService.createTikkiePaymentService(body)
				, HttpStatus.OK);
	}

	@GetMapping(value = "/getAuditOfpaymentRequests")
	public ResponseEntity<GetPaymentRequestListSuccess> getAuditOfpaymentRequests(@Valid @RequestParam(value = "email", required = false) String email, @Valid @RequestParam(value = "paymentRequestToken", required = false) String paymentRequestToken, 
			@Valid @RequestParam(value = "referenceId", required = false) String referenceId,
			@Valid @RequestParam(value = "page") Optional<Integer> page,
			@Valid @RequestParam(value = "size") Optional<Integer> size,@Valid @RequestParam(value = "fromDateTime", required = false) String fromDateTime,
			@Valid @RequestParam(value = "toDateTime", required = false) String toDateTime, HttpServletRequest request) throws TikkiePaymentRuntimeException{
		logger.info("calling getAuditOfpaymentRequests!");
		
		int currentPage = DEFAULT_CURRENT_PAGE;
		if (page.isPresent()) {
			currentPage = page.get();
		}

		int pageSize = DEFAULT_CURRENT_PAGESIZE;
		if (size.isPresent()) {
			pageSize = size.get();
		}
		
		return new ResponseEntity<>(tikkiePaymentService.getAuditOfpaymentRequests(email, paymentRequestToken, referenceId, currentPage, pageSize, fromDateTime, toDateTime)
				, HttpStatus.OK);
	}
	
	@GetMapping(value = "/paymentsOfPaymentRequest")
	public ResponseEntity<PaymentListResponse> paymentsOfPaymentRequest(
			@Valid @RequestParam(value = "paymentRequestToken", required = true) String paymentRequestToken ,
			@Valid @RequestParam(value = "page") Optional<Integer> page,
			@Valid @RequestParam(value = "size") Optional<Integer> size,@Valid @RequestParam(value = "fromDateTime") Optional<String> fromDateTime,
			@Valid @RequestParam(value = "toDateTime") Optional<String> toDateTime, HttpServletRequest request) throws TikkiePaymentRuntimeException{
		logger.info("calling paymentsOfPaymentRequest!");
		
		int currentPage = DEFAULT_CURRENT_PAGE;
		if (page.isPresent()) {
			currentPage = page.get();
		}

		int pageSize = DEFAULT_CURRENT_PAGESIZE;
		if (size.isPresent()) {
			pageSize = size.get();
		}
		
		return new ResponseEntity<>(tikkiePaymentService.paymentsOfPaymentRequest(paymentRequestToken, currentPage, pageSize, fromDateTime.get(), toDateTime.get())
				, HttpStatus.OK);
	}
}
