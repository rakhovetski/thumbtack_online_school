package net.thumbtack.school.hiring.daoimpl.mybatis;

import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmployeeDaoImpl extends UserDaoImpl implements EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);

    @Override
    public Integer insert(Employee employee){
        int id;
        LOGGER.debug("DAO insert employee {}", employee);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insert(employee);
                getEmployeeMapper(sqlSession).insert(employee);
                id = getUserMapper(sqlSession).getUserIdByLogin(employee.getLogin());
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't insert Employee {}, {}", ex, employee);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return id;
    }


    @Override
    public void update(Employee employee, String token) throws ServerException {
        LOGGER.debug("DAO update employee {} by token {}", employee, token);
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployeeMapper(sqlSession).update(employee, token);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't update Employee {}, {}", ex, employee);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }


    @Override
    public void delete(int id) {
        LOGGER.debug("DAO delete employee by id {}", id);
        try (SqlSession sqlSession = getSession()){
            try {
                getUserMapper(sqlSession).delete(id);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't delete Employee by id {}, {}", ex, id);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }


    @Override
    public List<Employee> getAllEmployees() throws ServerException {
        LOGGER.debug("DAO get all employees");
        try (SqlSession sqlSession = getSession()){
            try {
                return getEmployeeMapper(sqlSession).getAllEmployees();
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't get all Employees {}", ex);
                throw ex;
            }
        }
    }


    @Override
    public Employee getEmployeeById(int id) {
        LOGGER.debug("DAO get employee by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getEmployeeMapper(sqlSession).getEmployeeById(id);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't get employee by id {}, {}", ex, id);
                throw ex;
            }
        }
    }


    @Override
    public Employee getEmployeeByToken(String token) {
        LOGGER.debug("DAO get employee by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getEmployeeMapper(sqlSession).getEmployeeByToken(token);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't get employee by token {}, {}", ex, token);
                throw ex;
            }
        }
    }

    @Override
    public Integer getIdByEmployee(String login) {
        LOGGER.debug("DAO get id employee by login {}", login);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getEmployeeMapper(sqlSession).getIdByEmployee(login);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't get id by employee by login {}, {}", ex, login);
                throw ex;
            }
        }
    }
}
