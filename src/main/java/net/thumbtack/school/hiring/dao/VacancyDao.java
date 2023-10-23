package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.Vacancy;

import java.util.Set;

public interface VacancyDao {

    int insert(Vacancy vacancy, Employer employer);

    void update(String token, Vacancy vacancy);

    void delete(String token, int id);

    void deleteAll();

    Vacancy getVacancyById(int id);

    Set<Vacancy> getAllVacancies();

    Set<Vacancy> getVacanciesByEmployer(Employer employer);

    int getIdVacancyByName(String name) throws ServerException;
}
