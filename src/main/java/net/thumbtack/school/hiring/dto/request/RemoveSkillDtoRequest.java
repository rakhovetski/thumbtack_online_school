package net.thumbtack.school.hiring.dto.request;

public class RemoveSkillDtoRequest {
    private String token;
    private int id;

    public RemoveSkillDtoRequest() {
    }

    public RemoveSkillDtoRequest(String token, int id) {
        this.token = token;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(int id) {
        this.id = id;
    }
}
