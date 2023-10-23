package net.thumbtack.school.hiring.dto.response;

public class RegisterDtoResponse {
    private int id;

    public RegisterDtoResponse(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
