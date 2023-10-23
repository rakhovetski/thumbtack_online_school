package net.thumbtack.school.hiring.daoimpl.mybatis;

import net.thumbtack.school.hiring.dao.UserDao;
import net.thumbtack.school.hiring.model.User;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDaoImpl extends DaoImplBase implements UserDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);

    public int insert(User user) {
        LOGGER.debug("DAO insert user {}", user);
        int id;
        try (SqlSession sqlSession = getSession()) {
            try {
                id = getUserMapper(sqlSession).insert(user);
            }  catch (RuntimeException ex) {
                LOGGER.info("DAO can't insert user {}, {}", ex, user);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return id;
    }


    @Override
    public void update(User user, String token) {
        LOGGER.debug("DAO update user {} by token {}", user, token);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).update(user, token);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't update user by token {}, {}, {}", ex, user, token);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }


    @Override
    public void delete(int id) {
        LOGGER.debug("DAO delete user by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).delete(id);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't delete user by id {}, {}", ex, id);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }


    @Override
    public void deleteAll() {
        LOGGER.debug("DAO delete all users");
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't delete all users {}", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }


    @Override
    public List<User> getAllUsers() {
        LOGGER.debug("DAO get all users");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getUserMapper(sqlSession).getAllUsers();
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't get all user {}", ex);
                throw ex;
            }
        }
    }


    @Override
    public User getById(int id) {
        LOGGER.debug("DAO get user by id {}", id);
        try (SqlSession sqlSession = getSession()){
            try {
                return getUserMapper(sqlSession).getById(id);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't get User by id {}, {}", id, ex);
                throw ex;
            }
        }
    }

    @Override
    public User getByLogin(String login) {
        LOGGER.debug("DAO get user by login {}", login);
        try (SqlSession sqlSession = getSession()){
            try {
                return getUserMapper(sqlSession).getByLogin(login);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't get User by login {}, {}", login, ex);
                throw ex;
            }
        }
    }

}
