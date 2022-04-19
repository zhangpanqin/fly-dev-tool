package com.mflyyou.common.config;


import com.mflyyou.common.exception.AppException;
import com.mflyyou.common.exception.CommonErrors;
import com.mflyyou.common.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.mflyyou.common.Constants.COMMON_EXAMPLE_WITH_500;
import static com.mflyyou.common.exception.ErrorResponse.fromAppException;
import static com.mflyyou.common.exception.ErrorResponse.fromErrorCode;
import static com.mflyyou.common.exception.ErrorResponse.fromException;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


public class GlobalExceptionHandlerConfig {
    @Slf4j
    @RestControllerAdvice
    static class GlobalExceptionHandler {

        @ExceptionHandler(AppException.class)
        public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {
            log.error("App error: ", ex);
            return new ResponseEntity<>(fromAppException(ex), null, ex.getErrorCode().status());
        }

        /**
         * public Long createByJson(@Valid @RequestBody CreateOrgRequest request)
         * public OrgResponse create2ByParam(@Valid CreateOrgRequest request)
         */
        @ApiResponse(responseCode = "400",
                description = "request parameters may have errors",
                content = @Content(mediaType = APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class)))
        @ExceptionHandler(BindException.class)
        public ResponseEntity<Object> handleBindException(BindException ex) {
            log.error("Invalid request error: ", ex);
            var errorMessageMap = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .collect(groupingBy(FieldError::getField, mapping(FieldError::getDefaultMessage, toList())));

            return new ResponseEntity<>(fromErrorCode(CommonErrors.BAD_REQUEST, errorMessageMap),
                    null, HttpStatus.BAD_REQUEST);
        }


        /**
         * public OrgResponse getById(@NotNull(message = "id 不能为空") Long id)
         */
        @ApiResponse(responseCode = "400",
                description = "request parameters may have errors",
                content = @Content(mediaType = APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class)))
        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
            log.error("Invalid request error: ", ex);
            var errorMessageMap = ex.getConstraintViolations()
                    .stream()
                    .collect(toMap(it -> it.getPropertyPath().toString(), it -> List.of(it.getMessage())));

            return new ResponseEntity<>(fromErrorCode(CommonErrors.BAD_REQUEST, errorMessageMap),
                    null, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(Exception.class)
        @ApiResponse(responseCode = "500",
                description = "internal server error",
                content = @Content(mediaType = APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                name = COMMON_EXAMPLE_WITH_500,
                                ref = COMMON_EXAMPLE_WITH_500
                        )
                )
        )
        public ResponseEntity<ErrorResponse> handleException(Exception ex) {
            log.error("Unexpected error: ", ex);
            return new ResponseEntity<>(fromException(ex), null, INTERNAL_SERVER_ERROR);
        }
    }

    @Controller
    @RequestMapping("${server.error.path:${error.path:/error}}")
    static class CommonErrorController extends AbstractErrorController {

        private final ErrorAttributes errorAttributes;

        private final ErrorProperties errorProperties;

        public CommonErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
            super(errorAttributes, Collections.emptyList());
            this.errorAttributes = errorAttributes;
            this.errorProperties = serverProperties.getError();
        }

        @RequestMapping
        public ResponseEntity<ErrorResponse> error(HttpServletRequest request) {
            HttpStatus status = getStatus(request);
            if (status==HttpStatus.NO_CONTENT) {
                return new ResponseEntity<>(status);
            }

            return new ResponseEntity<>(fromErrorCode(CommonErrors.INTERNAL_SERVER_ERROR,
                    getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL))), status);
        }

        protected ErrorProperties getErrorProperties() {
            return this.errorProperties;
        }

        protected Map<String, Object> getErrorAttributes(HttpServletRequest request, ErrorAttributeOptions options) {
            WebRequest webRequest = new ServletWebRequest(request);
            return this.errorAttributes.getErrorAttributes(webRequest, options);
        }

        protected ErrorAttributeOptions getErrorAttributeOptions(HttpServletRequest request, MediaType mediaType) {
            ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
            if (this.errorProperties.isIncludeException()) {
                options = options.including(Include.EXCEPTION);
            }
            if (isIncludeStackTrace(request, mediaType)) {
                options = options.including(Include.STACK_TRACE);
            }
            if (isIncludeMessage(request, mediaType)) {
                options = options.including(Include.MESSAGE);
            }
            if (isIncludeBindingErrors(request, mediaType)) {
                options = options.including(Include.BINDING_ERRORS);
            }
            return options;
        }

        protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
            switch (getErrorProperties().getIncludeStacktrace()) {
                case ALWAYS:
                    return true;
                case ON_PARAM:
                    return getTraceParameter(request);
                default:
                    return false;
            }
        }

        protected boolean isIncludeMessage(HttpServletRequest request, MediaType produces) {
            switch (getErrorProperties().getIncludeMessage()) {
                case ALWAYS:
                    return true;
                case ON_PARAM:
                    return getMessageParameter(request);
                default:
                    return false;
            }
        }

        protected boolean isIncludeBindingErrors(HttpServletRequest request, MediaType produces) {
            switch (getErrorProperties().getIncludeBindingErrors()) {
                case ALWAYS:
                    return true;
                case ON_PARAM:
                    return getErrorsParameter(request);
                default:
                    return false;
            }
        }
    }
}
