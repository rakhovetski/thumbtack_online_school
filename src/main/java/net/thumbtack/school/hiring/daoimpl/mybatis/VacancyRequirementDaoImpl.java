package net.thumbtack.school.hiring.daoimpl.mybatis;

import net.thumbtack.school.hiring.dao.VacancyRequirementDao;
import net.thumbtack.school.hiring.model.Vacancy;
import net.thumbtack.school.hiring.model.VacancyRequirement;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class VacancyRequirementDaoImpl extends DaoImplBase implements VacancyRequirementDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);

    @Override
    public int insert(VacancyRequirement vacancyRequirement, Vacancy vacancy) {
        LOGGER.debug("DAO insert vacancyRequirement in vacancy by id {}, {}", vacancyRequirement, vacancy);
        int id;
        try (SqlSession sqlSession = getSession()) {
            try {
                getVacancyRequirementMapper(sqlSession).insert(vacancyRequirement, vacancy);
                id = getVacancyRequirementMapper(sqlSession).getVacancyRequirementIdByName(vacancyRequirement.getDescription());
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't insert vacancyRequirement in vacancy by id {}, {}, {}", ex, vacancyRequirement, vacancy);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return id;
    }


    @Override
    public void update(int vacancyId, VacancyRequirement vacancyRequirement) {
        LOGGER.debug("DAO update vacancyRequirement {}", vacancyRequirement);
        try (SqlSession sqlSession = getSession()) {
            try {
                getVacancyRequirementMapper(sqlSession).update(vacancyRequirement);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't update vacancyRequirement {}, {}", ex, vacancyRequirement);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }


    @Override
    public void delete(int vacancyId, int vacancyRequirementId) {
        LOGGER.debug("DAO delete vacancyRequirement by id {}", vacancyRequirementId);
        try (SqlSession sqlSession = getSession()) {
            try {
                getVacancyRequirementMapper(sqlSession).delete(vacancyRequirementId);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't delete vacancyRequirement {}, {}", ex, vacancyRequirementId);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }


    @Override
    public VacancyRequirement getVacancyRequirementById(int id) {
        LOGGER.debug("DAO get vacancyRequirement by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getVacancyRequirementMapper(sqlSession).getVacancyRequirementById(id);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't get vacancyRequirement by id {}, {}", ex, id);
                throw ex;
            }
        }
    }

    @Override
    public Set<VacancyRequirement> getVacancyRequirementsByVacancy(Vacancy vacancy) {
        LOGGER.debug("DAO get vacancyRequirements by vacancy {}", vacancy);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getVacancyRequirementMapper(sqlSession).getVacancyRequirementsByVacancy(vacancy);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't get vacancyRequirements by vacancy {}, {}", ex, vacancy);
                throw ex;
            }
        }
    }

    @Override
    public Set<VacancyRequirement> getAllVacancyRequirements() {
        LOGGER.debug("DAO get all vacancyRequirements ");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getVacancyRequirementMapper(sqlSession).getAllVacancyRequirements();
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't get all vacancies {}", ex);
                throw ex;
            }
        }
    }


    @Override
    public void deleteAll() {
        LOGGER.debug("DAO delete all vacancyRequirements");
        try (SqlSession sqlSession = getSession()) {
            try {
                getVacancyRequirementMapper(sqlSession).deleteAll();
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't delete all vacancyRequirements {}", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}
