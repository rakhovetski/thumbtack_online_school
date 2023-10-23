package net.thumbtack.school.hiring.dto.request;

public class LoginUserDtoRequest {
    String login;

    public LoginUserDtoRequest(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
