package io.github.hoongjian_0w0.cmsback.common.result;

/**
 * Pre-Defined Result Code
 */
public interface ResultCode {

    // 2xx Success
    int SUCCESS = 200;
    int CREATED = 201;
    int ACCEPTED = 202;
    int NO_CONTENT = 204;

    // 4xx Client Errors
    int BAD_REQUEST = 400;
    int UNAUTHORIZED = 401;
    int FORBIDDEN = 403;
    int NOT_FOUND = 404;
    int METHOD_NOT_ALLOWED = 405;
    int CONFLICT = 409;
    int UNSUPPORTED_MEDIA_TYPE = 415;
    int TOO_MANY_REQUESTS = 429;

    // 5xx Server Errors
    int INTERNAL_SERVER_ERROR = 500;
    int NOT_IMPLEMENTED = 501;
    int BAD_GATEWAY = 502;
    int SERVICE_UNAVAILABLE = 503;
    int GATEWAY_TIMEOUT = 504;

    // 6xx Custom Business Errors
    int CUSTOM_FAIL = 600;

}
