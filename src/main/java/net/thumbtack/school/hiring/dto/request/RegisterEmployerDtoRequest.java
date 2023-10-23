package net.thumbtack.school.hiring.dto.request;

import java.util.Set;

public class RegisterEmployerDtoRequest extends RegisterDtoRequest{
    private String title;
    private String address;
    private Set<AppendVacancyDtoRequest> vacancies;

    public RegisterEmployerDtoRequest(String email, String surname, String name, String patronymic,String login,
                                      String password,
                                      String title, String address) {
        super(email, surname, name, patronymic, login, password);
        setTitle(title);
        setAddress(address);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<AppendVacancyDtoRequest> getVacancies() {
        return vacancies;
    }

    public void setVacancies(Set<AppendVacancyDtoRequest> vacancies) {
        this.vacancies = vacancies;
    }
}
