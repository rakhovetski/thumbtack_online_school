package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.VacancyRequirement;

import java.util.List;

public interface EmployerDao extends UserDao{
    int insert(Employer employer) throws ServerException;

    void update(Employer employer, String token) throws ServerException;

    List<Employer> getAllEmployers();

    void delete(int id) throws ServerException;

    Employer getEmployerById(int id);

    Employer getEmployerByToken(String token);

    Integer getIdByEmployer(String login) throws ServerException;

    List<Employee> getEmployeesByVacancyRequirement(VacancyRequirement vacancyRequirement);

}
