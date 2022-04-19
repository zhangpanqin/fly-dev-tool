package com.mflyyou.common.exception;

public enum CommonErrors implements ErrorCode {

    BAD_REQUEST(400, "BAD_REQUEST", "bad request"),
    UNAUTHORIZED(401, "UNAUTHORIZED", "unauthorized"),
    FORBIDDEN(403, "FORBIDDEN", "forbidden"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "internal server error");

    private final int status;
    private final String code;
    private final String description;

    CommonErrors(int status, String code, String description) {
        this.status = status;
        this.code = code;
        this.description = description;
    }

    @Override
    public int status() {
        return status;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String description() {
        return description;
    }
}
