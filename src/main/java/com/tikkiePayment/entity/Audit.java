package com.tikkiePayment.entity;

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
@Document(collection="audit")
public class Audit  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String auditId = null;
    private String logTransactionId = null;
    private String method = null;
    private String className = null;
    private String status = null;
    private String errorId = null;
    private String userId = null;
    private String request = null;
    private String response = null;
    private String clientIp = null;
    private String serverIp = null;
    private String device = null;
    private String os = null;
    private String osVersion = null;
    private String appVersion = null;
    private String sourceApp = null;
    private String location = null;
    private String awsTraceId = null;
    private String host = null;
    private String sourceDescription = null;
    private long timeTaken;
    private Date createdDate;
}
