package net.thumbtack.school.hiring.daoimpl.mybatis;

import net.thumbtack.school.hiring.mappers.*;
import net.thumbtack.school.hiring.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class DaoImplBase {

    protected SqlSession getSession() {
        return MyBatisUtils.getSqlSessionFactory().openSession();
    }

    protected UserMapper getUserMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(UserMapper.class);
    }

    protected EmployerMapper getEmployerMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(EmployerMapper.class);
    }

    protected EmployeeMapper getEmployeeMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(EmployeeMapper.class);
    }

    protected ActiveUserMapper getActiveUserMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(ActiveUserMapper.class);
    }

    protected SkillMapper getSkillMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SkillMapper.class);
    }

    protected VacancyMapper getVacancyMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(VacancyMapper.class);
    }

    protected VacancyRequirementMapper getVacancyRequirementMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(VacancyRequirementMapper.class);
    }
}
