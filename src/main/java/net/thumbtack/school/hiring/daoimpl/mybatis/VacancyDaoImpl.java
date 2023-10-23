package net.thumbtack.school.hiring.daoimpl.mybatis;

import net.thumbtack.school.hiring.dao.VacancyDao;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.Vacancy;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class VacancyDaoImpl extends DaoImplBase implements VacancyDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);

    @Override
    public int insert(Vacancy vacancy, Employer employer) {
        LOGGER.debug("DAO insert vacancy to employer {}, {}", vacancy, employer);
        int id;
        try (SqlSession sqlSession = getSession()) {
            try {
                getVacancyMapper(sqlSession).insert(vacancy, employer);
                id = getVacancyMapper(sqlSession).getIdVacancyByName(vacancy.getJobTitle());
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't insert vacancy to employer {}, {}, {}", ex, vacancy, employer);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return id;
    }


    @Override
    public void update(String token, Vacancy vacancy) {
        LOGGER.debug("DAO update vacancy {}", vacancy);
        try (SqlSession sqlSession = getSession()) {
            try {
                getVacancyMapper(sqlSession).update(vacancy);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't update vacancy {}, {}", ex, vacancy);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }


    @Override
    public void delete(String token, int id) {
        LOGGER.debug("DAO delete vacancy by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                getVacancyMapper(sqlSession).delete(id);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't delete vacancy by id {}, {}", ex, id);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("DAO delete all vacancies");
        try (SqlSession sqlSession = getSession()) {
            try {
                getVacancyMapper(sqlSession).deleteAll();
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't delete all vacancies {}", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }


    @Override
    public Vacancy getVacancyById(int id) {
        LOGGER.debug("DAO get vacancy by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getVacancyMapper(sqlSession).getVacancyById(id);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't get vacancy by id {}, {}", ex, id);
                throw ex;
            }
        }
    }


    @Override
    public Set<Vacancy> getAllVacancies() {
        LOGGER.debug("DAO get all vacancies ");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getVacancyMapper(sqlSession).getAllVacancies();
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't get all vacancies {}", ex);
                throw ex;
            }
        }
    }


    @Override
    public Set<Vacancy> getVacanciesByEmployer(Employer employer) {
        LOGGER.debug("DAO get all vacancies by employer {}", employer);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getVacancyMapper(sqlSession).getVacanciesByEmployer(employer);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't get all vacancies by employer {}, {}", ex, employer);
                throw ex;
            }
        }
    }


    @Override
    public int getIdVacancyByName(String name) {
        LOGGER.debug("DAO get vacancy id by name {}", name);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getVacancyMapper(sqlSession).getIdVacancyByName(name);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't get vacancy id by name {}, {}", ex, name);
                throw ex;
            }
        }
    }
}
