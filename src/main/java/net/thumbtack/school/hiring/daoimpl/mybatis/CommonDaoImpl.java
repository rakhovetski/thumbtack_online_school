package net.thumbtack.school.hiring.daoimpl.mybatis;

import net.thumbtack.school.hiring.dao.CommonDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonDaoImpl extends DaoImplBase implements CommonDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonDaoImpl.class);

    @Override
    public void clear(){
        try(SqlSession sqlSession = getSession()){
            try{
                getUserMapper(sqlSession).deleteAll();
                getVacancyMapper(sqlSession).deleteAll();
                getSkillMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex){
                LOGGER.info("Can't clear database");
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}
