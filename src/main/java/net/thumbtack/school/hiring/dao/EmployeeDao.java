package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.model.Employee;

import java.util.List;

public interface EmployeeDao extends UserDao{

    Integer insert(Employee employee) throws ServerException;

    void update(Employee employee, String token) throws ServerException;

    void delete(int id) throws ServerException;

    List<Employee> getAllEmployees() throws ServerException;

    Employee getEmployeeById(int id);

    Employee getEmployeeByToken(String token);

    Integer getIdByEmployee(String login) throws ServerException;
}
