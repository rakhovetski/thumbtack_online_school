package net.thumbtack.school.hiring.daoimpl.collections;

import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.VacancyRequirement;

import java.util.List;

public class EmployerDaoImplCol extends UserDaoImplCol implements EmployerDao {
    @Override
    public int insert(Employer employer) throws ServerException {
        return Database.getInstance().register(employer);
    }

    @Override
    public void update(Employer employer, String token) throws ServerException {
        Database.getInstance().replaceEmployer(employer, token);
    }

    @Override
    public void delete(int id) throws ServerException{
        Database.getInstance().removeUser(id);
    }

    @Override
    public List<Employer> getAllEmployers(){
        return Database.getInstance().getAllEmployers();
    }

    @Override
    public Employer getEmployerById(int id) {
        return (Employer) Database.getInstance().getUserById(id);
    }

    @Override
    public Employer getEmployerByToken(String token) {
        return (Employer) Database.getInstance().getUserByToken(token);
    }

    @Override
    public Integer getIdByEmployer(String login) throws ServerException{
        return Database.getInstance().getIdByUser(login);
    }

    @Override
    public List<Employee> getEmployeesByVacancyRequirement(VacancyRequirement vacancyRequirement) {
        return Database.getInstance().getEmployeesByVacancyRequirement(vacancyRequirement);
    }
}
