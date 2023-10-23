package net.thumbtack.school.hiring.dto.request;

public class RemoveEmployerDtoRequest {
    private String token;

    public RemoveEmployerDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
