package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Skill;

import java.util.Set;

public interface SkillDao {

    int insert(Skill skill, Employee employee);

    void update(String token, Skill skill);

    void delete(String token, int id);

    Skill getSkillById(int id);

    Set<Skill> getAllSkills();

    Set<Skill> getSkillsByEmployee(Employee employee);

    void deleteAll();
}
