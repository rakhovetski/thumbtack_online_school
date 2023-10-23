package net.thumbtack.school.hiring.daoimpl.mybatis;

import net.thumbtack.school.hiring.dao.ActiveUserDao;
import net.thumbtack.school.hiring.model.User;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveUserDaoImpl extends DaoImplBase implements ActiveUserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisUtils.class);

    @Override
    public void insert(User user, String token) {
        LOGGER.debug("DAO insert session user {} ", user);
        try (SqlSession sqlSession = getSession()) {
            try {
                getActiveUserMapper(sqlSession).insert(user, token);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't insert user by id {}, {}", ex, user);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void delete(String token) {
        LOGGER.debug("DAO delete session by token {} ", token);
        try (SqlSession sqlSession = getSession()) {
            try {
                getActiveUserMapper(sqlSession).delete(token);
            } catch (RuntimeException ex) {
                LOGGER.info("DAO can't delete session by token {}, {}", ex, token);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}
