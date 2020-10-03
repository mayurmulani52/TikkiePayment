package com.tikkiePayment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tikkiePayment.entity.Audit;

@Repository
public interface AuditRepository extends MongoRepository<Audit, String> {


}
