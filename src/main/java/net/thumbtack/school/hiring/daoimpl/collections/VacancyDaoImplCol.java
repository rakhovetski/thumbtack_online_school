package net.thumbtack.school.hiring.daoimpl.collections;

import net.thumbtack.school.hiring.dao.VacancyDao;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.Vacancy;

import java.util.Set;

public class VacancyDaoImplCol implements VacancyDao {
    @Override
    public int insert(Vacancy vacancy, Employer employer) {
        return Database.getInstance().addVacancy(employer, vacancy);
    }

    @Override
    public void update(String token, Vacancy vacancy) {
        Database.getInstance().replaceVacancy(vacancy, token);
    }

    @Override
    public void delete(String token, int id) {
        Database.getInstance().removeVacancy(token, id);
    }

    @Override
    public void deleteAll() {
        Database.getInstance().deleteAllVacancies();
    }

    @Override
    public Vacancy getVacancyById(int id) {
        return Database.getInstance().getVacancyById(id);
    }

    @Override
    public Set<Vacancy> getAllVacancies() {
        return Database.getInstance().getAllVacancies();
    }

    @Override
    public Set<Vacancy> getVacanciesByEmployer(Employer employer) {
        return Database.getInstance().getVacanciesByEmployer(employer);
    }

    @Override
    public int getIdVacancyByName(String name) throws ServerException {
        return Database.getInstance().getIdVacancyByName(name);
    }
}
