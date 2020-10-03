package com.tikkiePayment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tikkiePayment.entity.Audit;
import com.tikkiePayment.repository.AuditRepository;
import com.tikkiePayment.service.AuditService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public Audit createAudit(Audit audit) {
        Audit a = auditRepository.save(audit);
        log.info("audit created with id: {}", a.getAuditId());
        return a;
    }
}
