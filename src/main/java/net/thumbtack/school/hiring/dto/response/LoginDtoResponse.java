package net.thumbtack.school.hiring.dto.response;

public class LoginDtoResponse {
    private String token;

    public LoginDtoResponse(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }
}
