package com.tikkiepayment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditPaymentRequest extends MongoRepository<AuditPaymentRequest, String> {


}
