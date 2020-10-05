package com.tikkiepayment.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Document(collection = "audit_payment_request")
public class AuditPaymentRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String auditId = null;
	private String email = null;
	private String paymentRequestToken = null;
	private Integer amountInCents=null;
	private String referenceId = null;
	private String description = null;
	private String paymentURL = null;
	private String expiryDate = null;
	private String status=null;
	private String paymentType=null;
	private Date createdDateTime;

}
