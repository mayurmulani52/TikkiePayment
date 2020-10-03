package com.tikkiepayment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tikkiepayment.entity.Audit;

@Repository
public interface AuditRepository extends MongoRepository<Audit, String> {


}
