package net.thumbtack.school.hiring.dto.request;

public class RemoveEmployeeDtoRequest {
    private String token;

    public RemoveEmployeeDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
