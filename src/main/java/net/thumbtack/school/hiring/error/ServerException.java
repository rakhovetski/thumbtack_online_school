package net.thumbtack.school.hiring.error;

public class ServerException extends Exception{
    private ErrorCode errorCode;
    private String string;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public ServerException(ErrorCode errorCode){
        super(errorCode.getErrorString());
        this.errorCode = errorCode;
    }

    public ServerException(ErrorCode errorCode, String string) {
        this.errorCode = errorCode;
        this.string = string;
    }


}
