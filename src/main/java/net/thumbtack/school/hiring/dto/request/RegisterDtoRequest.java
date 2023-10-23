package net.thumbtack.school.hiring.dto.request;

import java.util.Objects;

public class RegisterDtoRequest {
    private String email;
    private String surname;
    private String name;
    private String patronymic;
    private String login;
    private String password;

    public RegisterDtoRequest(String email, String surname, String name, String patronymic,String login, String password){
        setEmail(email);
        setSurname(surname);
        setName(name);
        setPatronymic(patronymic);
        setLogin(login);
        setPassword(password);
    }

    public String getEmail(){
        return email;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPatronymic(String patronymic){
        this.patronymic = patronymic;
    }

    protected void setLogin(String login){
        this.login = login;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterDtoRequest that = (RegisterDtoRequest) o;
        return Objects.equals(email, that.email) && Objects.equals(surname, that.surname) && Objects.equals(name, that.name) && Objects.equals(patronymic, that.patronymic) && Objects.equals(login, that.login) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, surname, name, patronymic, login, password);
    }
}
