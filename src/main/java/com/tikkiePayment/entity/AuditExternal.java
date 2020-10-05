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
@Document(collection = "audit_external")
public class AuditExternal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String auditId = null;
	private String logTransactionId = null;
	private String externalService = null;
	private String request = null;
	private String response = null;
	private String status = null;
	private long timeTaken;
	private Date createdDate;

}
