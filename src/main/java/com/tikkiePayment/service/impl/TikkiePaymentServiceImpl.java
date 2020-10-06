package com.tikkiepayment.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tikkiepayment.service.TikkiePaymentService;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TikkiePaymentServiceImpl implements TikkiePaymentService {

}
