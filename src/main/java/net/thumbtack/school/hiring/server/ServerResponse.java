package net.thumbtack.school.hiring.server;

import net.thumbtack.school.hiring.error.ServerException;

public class ServerResponse {
    private int responseCode;
    private String responseMessage;

    public ServerResponse(int code, String message){
        responseCode = code;
        responseMessage = message;
    }

    public ServerResponse(ServerException ex){
        responseCode = 400;
        responseMessage = ex.getErrorCode().getErrorString();
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
