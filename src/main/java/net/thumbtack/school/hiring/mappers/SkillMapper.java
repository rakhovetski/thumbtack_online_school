package net.thumbtack.school.hiring.mappers;

import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Skill;
import org.apache.ibatis.annotations.*;

import java.util.Set;

public interface SkillMapper {

    @Insert("INSERT INTO skill(description, level, employee_id) VALUES(#{skill.description}, #{skill.level}, #{employee.id})")
    @Options(useGeneratedKeys = true, keyProperty = "skill.id")
    Integer insert(@Param("skill")Skill skill, @Param("employee") Employee employee);


    @Delete("DELETE FROM skill WHERE id = #{id}")
    void delete(@Param("id") int id);


    @Update("UPDATE skill SET description = #{skill.description}, level = #{skill.level} WHERE id = #{skill.id}")
    void update(@Param("skill") Skill skill);


    @Select("SELECT * FROM skill WHERE id = #{id}")
    Skill getSkillById(@Param("id") int id);


    @Select("SELECT * FROM skill")
    Set<Skill> getAllSkills();


    @Select("SELECT * FROM skill WHERE employee_id = #{employee.id}")
    Set<Skill> getSkillsByEmployee(Employee employee);


    @Select("DELETE FROM skill")
    void deleteAll();


    @Select("SELECT id FROM skill WHERE description = #{name} LIMIT 1")
    int getIdSkillByName(String name);
}
