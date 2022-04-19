package com.mflyyou.common.exception;

import lombok.Data;

import java.util.Map;

@Data
public class ErrorResponse {
    private final String code;
    private final String description;
    private final Map<String, ?> details;

    public static ErrorResponse fromAppException(AppException appException) {
        return new ErrorResponse(appException.getErrorCode().code(),
                appException.getErrorCode().description(), appException.getDetails());
    }

    public static ErrorResponse fromException(Exception exception) {
        return new ErrorResponse(CommonErrors.INTERNAL_SERVER_ERROR.code(), exception.getMessage(), Map.of());
    }

    public static ErrorResponse fromErrorCode(ErrorCode errorCode, Map<String, ?> details) {
        return new ErrorResponse(errorCode.code(), errorCode.description(), details);
    }
}
