package net.thumbtack.school.hiring.daoimpl.collections;

import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.model.Employee;

import java.util.List;

public class EmployeeDaoImplCol extends UserDaoImplCol implements EmployeeDao{

    @Override
    public Integer insert(Employee employee) throws ServerException{
        return Database.getInstance().register(employee);
    }

    @Override
    public void update(Employee employee, String token) throws ServerException {
        Database.getInstance().replaceEmployee(employee, token);
    }

    @Override
    public void delete(int id) throws ServerException{
        Database.getInstance().removeUser(id);
    }

    @Override
    public List<Employee> getAllEmployees() throws ServerException {
        return Database.getInstance().getAllEmployees();
    }

    @Override
    public Employee getEmployeeById(int id) {
        return (Employee) Database.getInstance().getUserById(id);
    }

    @Override
    public Employee getEmployeeByToken(String token) {
        return (Employee) Database.getInstance().getUserByToken(token);
    }

    @Override
    public Integer getIdByEmployee(String login) throws ServerException{
        return Database.getInstance().getIdByUser(login);
    }
}
