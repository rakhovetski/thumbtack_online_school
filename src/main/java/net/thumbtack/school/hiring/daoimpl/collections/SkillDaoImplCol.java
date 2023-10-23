package net.thumbtack.school.hiring.daoimpl.collections;

import net.thumbtack.school.hiring.dao.SkillDao;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Skill;

import java.util.Set;

public class SkillDaoImplCol implements SkillDao {
    @Override
    public int insert(Skill skill, Employee employee) {
        return Database.getInstance().addSkill(employee, skill);
    }

    @Override
    public void delete(String token, int id) {
        Database.getInstance().removeSkill(token, id);
    }

    @Override
    public void update(String token, Skill skill) {
        Database.getInstance().replaceSkill(skill, token);
    }

    @Override
    public Skill getSkillById(int id) {
        return Database.getInstance().getSkillById(id);
    }

    @Override
    public Set<Skill> getAllSkills() {
        return Database.getInstance().getAllSkills();
    }

    @Override
    public Set<Skill> getSkillsByEmployee(Employee employee) {
        return Database.getInstance().getSkillsByEmployee(employee);
    }

    @Override
    public void deleteAll() {
        Database.getInstance().deleteAllSkills();
    }
}
