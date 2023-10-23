package net.thumbtack.school.hiring.model;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.thumbtack.school.hiring.error.ErrorCode;
import net.thumbtack.school.hiring.error.ServerException;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employer extends User{
    private String title;
    private String address;
    private Set<Vacancy> vacancies = new HashSet<>();

    public Employer(String title, String address, String email, String surname, String name, String patronymic, String login, String password){
        super(email, surname, name, patronymic, login, password);
        this.title = title;
        this.address = address;
    }

    public void addVacancy(Vacancy vacancy){
        vacancies.add(vacancy);
    }

    public void removeVacancy(Vacancy vacancy){
        vacancies.remove(vacancy);
    }

    public Vacancy getVacancyById(int id) throws ServerException{
        for(Vacancy vac : vacancies){
            if(vac.getId() == id){
                return vac;
            }
        }
        throw new ServerException(ErrorCode.WRONG_VACANCY_ID);
    }

    public void addVacancyRequirement(VacancyRequirement vacancyRequirement, Vacancy vacancy){
        vacancies.remove(vacancy);
        vacancy.addVacancyRequirement(vacancyRequirement);
        vacancies.add(vacancy);
    }


    public void deleteVacancy(Vacancy vacancy){
        vacancies.remove(vacancy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employer)) return false;
        if (!super.equals(o)) return false;
        Employer employer = (Employer) o;
        return Objects.equal(getTitle(), employer.getTitle()) && Objects.equal(getAddress(), employer.getAddress()) && Objects.equal(getVacancies(), employer.getVacancies());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getTitle(), getAddress(), getVacancies());
    }
}





























