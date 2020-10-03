package com.tikkiepayment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tikkiepayment.entity.Audit;
import com.tikkiepayment.repository.AuditRepository;
import com.tikkiepayment.service.AuditService;

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
