package net.thumbtack.school.hiring.mappers;

import net.thumbtack.school.hiring.model.Employee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Set;

public interface EmployeeMapper {

    @Insert("INSERT INTO employee(id, status) VALUES(#{employee.id}, true)")
    Integer insert(@Param("employee")Employee employee);


    @Update("UPDATE employee JOIN active_user ON employee.id = active_user.id_user " +
            "SET status = #{employee.status} WHERE active_user.token = #{token}")
    void update(@Param("employee") Employee employee,@Param("token") String token);


    @Delete("DELETE FROM user JOIN active_user ON user.id = active_user.id_user WHERE active_user.token = #{token}")
    void delete(@Param("token")String token);


    @Select("SELECT * FROM employee JOIN user ON employee.id = user.id")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "skills", column = "id", javaType = Set.class,
                    many = @Many(select = "net.thumbtack.school.hiring.mappers.SkillMapper.getSkillsByEmployee",
                            fetchType = FetchType.LAZY))})
    List<Employee> getAllEmployees();


    @Select("SELECT * FROM employee JOIN user ON employee.id = user.id WHERE employer.id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "skills", column = "id", javaType = Set.class,
                    many = @Many(select = "net.thumbtack.school.hiring.mappers.SkillMapper.getSkillsByEmployee",
                            fetchType = FetchType.LAZY))})
    Employee getEmployeeById(@Param("id") int id);


    @Select("SELECT * " +
            "FROM active_user JOIN user ON active_user.id_user = user.id JOIN employee ON user.id = employee.id " +
            "WHERE token = #{token}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "skills", column = "id", javaType = Set.class,
                    many = @Many(select = "net.thumbtack.school.hiring.mappers.SkillMapper.getSkillsByEmployee",
                            fetchType = FetchType.LAZY))})
    Employee getEmployeeByToken(@Param("token") String token);


    @Select("SELECT user.id FROM employee JOIN user ON employee.id = user.id WHERE user.login = #{login}")
    Integer getIdByEmployee(@Param("login") String login);
}
