package com.mflyyou.common.exception;


import lombok.Getter;

import java.util.Map;


@Getter
public abstract class AppException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final transient ErrorCode errorCode;
    private final transient Map<String, ?> details;

    protected AppException(ErrorCode errorCode) {
        this(errorCode, Map.of());
    }

    protected AppException(ErrorCode errorCode, Map<String, ?> details) {
        super(String.format("[%s]%s:%s.", errorCode.code(), errorCode.description(), details.toString()));
        this.errorCode = errorCode;
        this.details = details;
    }
}
