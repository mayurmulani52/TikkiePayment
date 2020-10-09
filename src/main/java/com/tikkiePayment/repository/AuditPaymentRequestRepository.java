package com.tikkiepayment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tikkiepayment.entity.AuditPaymentRequest;


@Repository
public interface AuditPaymentRequestRepository extends MongoRepository<AuditPaymentRequest, String> {

	Page<AuditPaymentRequest> findAll(Pageable pageable);
	
	//@Query("{ $or : [ { $where: '?0.length == 0' } , { $where: '?1.length == 0' } , { $where: '?2.length == 0' } ,{ field : { $eq : ?0 } }, { field : { $eq : ?1 } }, { field : { $eq : ?2 } } ] }")
	//@Query("{ $or : [ {'paymentRequestToken':?0},{'referenceId':?1},{'email':?2}")
	@Query("{$and :[" 
	        + "?#{ [0] == null ? { $where : 'true'} : { 'paymentRequestToken' : [0] } },"
	        + "?#{ [1] == null ? { $where : 'true'} : { 'referenceId' : [1] } },"
	        + "?#{ [2] == null ? { $where : 'true'} : { 'email' : [2] } },"
	        + "]}")
	Page<AuditPaymentRequest> findByPaymentRequestTokenAndReferenceIdAndEmail(String paymentRequestToken, String referenceId, String email, Pageable pageable);

	
}

