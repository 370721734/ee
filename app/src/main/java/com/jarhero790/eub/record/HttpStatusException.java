package com.jarhero790.eub.record;

/**
 * Http状态异常
 */
public class HttpStatusException extends Exception {
    public HttpStatusException(String detailMessage) {
        super(detailMessage);
    }
}