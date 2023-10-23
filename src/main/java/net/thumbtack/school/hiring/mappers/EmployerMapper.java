package net.thumbtack.school.hiring.mappers;

import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.VacancyRequirement;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Set;

public interface EmployerMapper {

    @Insert("INSERT INTO employer(id, title, address) VALUES(#{employer.id}, #{employer.title}, #{employer.address})")
    Integer insert(@Param("employer") Employer employer);


    @Update("UPDATE employer JOIN active_user ON employer.id = active_user.id_user " +
            "SET title = #{employer.title}, address = #{employer.address} WHERE active_user.token = #{token}")
    void update(@Param("employer") Employer employer, @Param("token") String token);


    @Delete("DELETE FROM user JOIN active_user ON user.id = active_user.id_user WHERE active_user.token = #{token}")
    void delete(@Param("token") String token);


    @Select("SELECT * FROM employer JOIN user ON employer.id = user.id WHERE employer.id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "vacancies", column = "id", javaType = Set.class,
                    many = @Many(select = "net.thumbtack.school.hiring.mappers.VacancyMapper.getByEmployer",
                            fetchType = FetchType.LAZY))})
    Employer getEmployerById(@Param("id") int id);


    @Select("SELECT * FROM employer JOIN user ON employer.id = user.id")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "vacancies", column = "id", javaType = Set.class,
                    many = @Many(select = "net.thumbtack.school.hiring.mappers.VacancyMapper.getByEmployer",
                            fetchType = FetchType.LAZY))})
    List<Employer> getAllEmployers();


    @Select("SELECT id_user AS id, email, surname, name, patronymic, email, login, password, token, title, address" +
            " FROM active_user JOIN user ON active_user.id_user = user.id JOIN employer ON user.id = employer.id" +
            " WHERE token = #{token}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "vacancies", column = "id", javaType = Set.class,
                    many = @Many(select = "net.thumbtack.school.hiring.mappers.VacancyMapper.getVacanciesByEmployer",
                            fetchType = FetchType.LAZY))})
    Employer getEmployerByToken(@Param("token") String token);


    @Select("SELECT user.id FROM employer JOIN user ON employer.id = user.id WHERE user.login = #{login}")
    Integer getIdByEmployer(@Param("login") String login);


    @Select("SELECT * " +
            "FROM employee JOIN user ON employee.id = user.id JOIN skill ON employee.id = skill.employee_id " +
            "WHERE (skill.description = (SELECT description FROM vacancy_requirement " +
            "WHERE vacancy_requirement.description = #{vacancyRequirement.description})) " +
            "AND (skill.level >= " +
            "(SELECT level FROM vacancy_requirement WHERE vacancy_requirement.level = #{vacancyRequirement.level} LIMIT 1))")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "skills", column = "id", javaType = Set.class,
                    many = @Many(select = "net.thumbtack.school.hiring.mappers.SkillMapper.getSkillsByEmployee",
                            fetchType = FetchType.LAZY))})
    List<Employee> getEmployeesByVacancyRequirement(@Param("vacancyRequirement")VacancyRequirement vacancyRequirement);
}
