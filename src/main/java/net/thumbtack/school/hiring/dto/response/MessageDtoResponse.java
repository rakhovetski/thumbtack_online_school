package net.thumbtack.school.hiring.dto.response;

public class MessageDtoResponse {
    private String message;

    public MessageDtoResponse(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
