package com.tikkiePayment.exception;

public class TikkiePaymentRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public static final String PAGE_SIZE_INVALID = "Page size is not valid";

    public TikkiePaymentRuntimeException() {
    }

    public TikkiePaymentRuntimeException(String msg) {
        super(msg);
    }

    public TikkiePaymentRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public TikkiePaymentRuntimeException(Throwable cause) {
        super(cause);
    }

}
