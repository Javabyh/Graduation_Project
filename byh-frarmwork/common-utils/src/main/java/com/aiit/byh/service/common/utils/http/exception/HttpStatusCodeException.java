package com.aiit.byh.service.common.utils.http.exception;

/**
 * http状态非200异常
 * @author dsqin
 * @datetime 2017/10/12
 */
public class HttpStatusCodeException extends Exception {

    private int statusCode;

    private byte[] body;

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public HttpStatusCodeException() {
    }

    public HttpStatusCodeException(String message) {
        super(message);
    }

    public HttpStatusCodeException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatusCodeException(String message, int statusCode, byte[] body){
        this(message, statusCode);
        this.body = body;
    }
}
