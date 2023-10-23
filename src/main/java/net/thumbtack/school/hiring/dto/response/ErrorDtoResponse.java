package net.thumbtack.school.hiring.dto.response;

import net.thumbtack.school.hiring.error.ErrorCode;

public class ErrorDtoResponse {
    private ErrorCode errorCode;
    private String message;

    public ErrorDtoResponse(ErrorCode errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorDtoResponse(ErrorCode errorCode) {
        this(errorCode, "");
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
