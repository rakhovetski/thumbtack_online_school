package net.thumbtack.school.hiring.daoimpl.mybatis;

import net.thumbtack.school.hiring.dao.SkillDao;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Skill;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class SkillDaoImpl extends DaoImplBase implements SkillDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);

    @Override
    public int insert(Skill skill, Employee employee) {
        LOGGER.debug("DAO insert skill to employee {}, {}",skill, employee);
        int id;
        try (SqlSession sqlSession = getSession()) {
            try {
                getSkillMapper(sqlSession).insert(skill, employee);
                id = getSkillMapper(sqlSession).getIdSkillByName(skill.getDescription());
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't insert skill to employee {}, {}, {}", ex, employee, skill);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return id;
    }

    @Override
    public void delete(String token, int id) {
        LOGGER.debug("DAO delete skill by {}",id);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSkillMapper(sqlSession).delete(id);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't delete skill by id {}, {}", ex, id);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }


    @Override
    public void update(String token, Skill skill) {
        LOGGER.debug("DAO update skill {}",skill);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSkillMapper(sqlSession).update(skill);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't update skill {}, {}", ex, skill);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }


    @Override
    public Skill getSkillById(int id) {
        LOGGER.debug("DAO get skill by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getSkillMapper(sqlSession).getSkillById(id);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't get get skill by id {}, {}", ex, id);
                throw ex;
            }
        }
    }


    @Override
    public Set<Skill> getAllSkills() {
        LOGGER.debug("DAO get all skills");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getSkillMapper(sqlSession).getAllSkills();
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't delete skills {}", ex);
                throw ex;
            }
        }
    }


    @Override
    public Set<Skill> getSkillsByEmployee(Employee employee) {
        LOGGER.debug("DAO get skills by employee {}", employee);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getSkillMapper(sqlSession).getSkillsByEmployee(employee);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't get all skills by employee {}, {}", ex, employee);
                throw ex;
            }
        }
    }



    @Override
    public void deleteAll() {
        LOGGER.debug("DAO delete all skills");
        try (SqlSession sqlSession = getSession()) {
            try {
                getSkillMapper(sqlSession).deleteAll();
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't delete all skills {}", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}
