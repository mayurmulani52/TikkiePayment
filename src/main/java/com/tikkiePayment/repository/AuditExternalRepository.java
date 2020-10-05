package com.tikkiepayment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tikkiepayment.entity.AuditExternal;

@Repository
public interface AuditExternalRepository extends MongoRepository<AuditExternal, String> {


}
