package net.thumbtack.school.hiring.dto.response;
import lombok.*;

@Data
@NoArgsConstructor
public class EmployeeDtoResponse {
    private int id;
    private String email;
    private String surname;
    private String name;
    private String patronymic;
    private String login;
    private String password;

    public EmployeeDtoResponse(int id, String email, String surname, String name, String patronymic, String login, String password) {
        this.id = id;
        this.email = email;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "EmployeeDtoResponse{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}