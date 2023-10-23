package net.thumbtack.school.hiring.model;
import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User{
    private int id;
    private String email;
    private String surname;
    private String name;
    private String patronymic;
    private String login;
    private String password;
    private String token;

    public User(String email, String surname, String name, String patronymic, String login, String password) {
        this.email = email;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
    }

    public User(int id, String email, String surname, String name, String patronymic, String login, String password) {
        this(email, surname, name, patronymic, login, password);
        this.id = id;

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() && Objects.equal(getEmail(), user.getEmail()) && Objects.equal(getSurname(), user.getSurname()) && Objects.equal(getName(), user.getName()) && Objects.equal(getPatronymic(), user.getPatronymic()) && Objects.equal(getLogin(), user.getLogin()) && Objects.equal(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getEmail(), getSurname(), getName(), getPatronymic(), getLogin(), getPassword());
    }
}