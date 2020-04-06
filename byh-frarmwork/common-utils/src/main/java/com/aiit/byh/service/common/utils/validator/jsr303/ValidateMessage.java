package com.aiit.byh.service.common.utils.validator.jsr303;

/**
 * @author dsqin
 * @datetime 2017/8/30
 */
public class ValidateMessage {
    private String property;
    private String message;
    private Integer code;

    public ValidateMessage() {
        super();
    }

    public ValidateMessage(String property, String message, Integer code) {
        this.property = property;
        this.message = message;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"property\":\"")
                .append(property).append('\"');
        sb.append(",\"message\":\"")
                .append(message).append('\"');
        sb.append(",\"code\":")
                .append(code);
        sb.append('}');
        return sb.toString();
    }
}

