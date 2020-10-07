package com.tikkiepayment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tikkiepayment.entity.AuditPaymentRequest;

@Repository
public interface AuditPaymentRequestRepository extends MongoRepository<AuditPaymentRequest, String> {

	Page<AuditPaymentRequest> findAll(Pageable pageable);
}
