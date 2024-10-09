package com.xiang.home.common;

/**
 * Custom Error Codes
 *
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2024/10/19 16:17
 */
public enum ErrorCode {

    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "Invalid Request Parameters"),
    NOT_LOGIN_ERROR(40100, "Not Login"),
    NO_AUTH_ERROR(40101, "Not Authenticated"),
    NOT_FOUND_ERROR(40400, "Requested Data Not Found"),
    FORBIDDEN_ERROR(40300, "Access Forbidden"),
    SYSTEM_ERROR(50000, "Internal System Exception"),
    OPERATION_ERROR(50001, "Operation Failed");

    /**
     * Status Code
     */
    private final int code;

    /**
     * Message
     */
    private final String message;


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
