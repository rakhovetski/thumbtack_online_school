package net.thumbtack.school.hiring.daoimpl.mybatis;

import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.VacancyRequirement;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmployerDaoImpl extends UserDaoImpl implements EmployerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);

    @Override
    public int insert(Employer employer) throws ServerException {
        LOGGER.debug("DAO insert employer {}", employer);
        int id;
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insert(employer);
                getEmployerMapper(sqlSession).insert(employer);
                id = getUserMapper(sqlSession).getUserIdByLogin(employer.getLogin());
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't insert Employee {}, {}", ex, employer);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return id;
    }


    @Override
    public void update(Employer employer, String token) throws ServerException {
        LOGGER.debug("DAO update employee {} by token {}", employer, token);
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployerMapper(sqlSession).update(employer, token);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't update Employer by token {}, {}, {}", ex, employer, token);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }


    @Override
    public void delete(int id) {
        LOGGER.debug("DAO delete employer by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).delete(id);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't delete Employer by id {}, {}", ex, id);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }


    @Override
    public List<Employer> getAllEmployers() {
        LOGGER.debug("DAO get all employers");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getEmployerMapper(sqlSession).getAllEmployers();
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't get all Employees {}", ex);
                throw ex;
            }
        }
    }


    @Override
    public Employer getEmployerById(int id) {
        LOGGER.debug("DAO get employee by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getEmployerMapper(sqlSession).getEmployerById(id);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't get employer by id {}, {}", ex, id);
                throw ex;
            }
        }
    }


    @Override
    public Employer getEmployerByToken(String token) {
        LOGGER.debug("DAO get employer by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getEmployerMapper(sqlSession).getEmployerByToken(token);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't get employer by token {}, {}", ex, token);
                throw ex;
            }
        }
    }

    @Override
    public Integer getIdByEmployer(String login) {
        LOGGER.debug("DAO get id employer by login {}", login);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getEmployerMapper(sqlSession).getIdByEmployer(login);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't get id by employer by login {}, {}", ex, login);
                throw ex;
            }
        }
    }


    @Override
    public List<Employee> getEmployeesByVacancyRequirement(VacancyRequirement vacancyRequirement) {
        LOGGER.debug("DAO get employees by vacancyRequirement {}", vacancyRequirement);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getEmployerMapper(sqlSession).getEmployeesByVacancyRequirement(vacancyRequirement);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't get employees by vacancyRequirement {}, {}", ex, vacancyRequirement);
                throw ex;
            }
        }
    }
}
