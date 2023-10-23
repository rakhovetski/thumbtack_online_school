package net.thumbtack.school.hiring.mappers;

import net.thumbtack.school.hiring.model.Vacancy;
import net.thumbtack.school.hiring.model.VacancyRequirement;
import org.apache.ibatis.annotations.*;

import java.util.Set;

public interface VacancyRequirementMapper {

    @Insert("INSERT INTO vacancy_requirement(description, level, obligatory, vacancy_id) " +
            "VALUES(#{vacancyRequirement.description}, #{vacancyRequirement.level}," +
            " #{vacancyRequirement.obligatory}, #{vacancy.id})")
    @Options(useGeneratedKeys = true, keyProperty = "vacancyRequirement.id")
    Integer insert(@Param("vacancyRequirement")VacancyRequirement vacancyRequirement, @Param("vacancy") Vacancy vacancy);


    @Update("UPDATE vacancy_requirement " +
            "SET description = #{vacancyRequirement.description}, level = #{vacancyRequirement.level}," +
            " obligatory = #{vacancyRequirement.obligatory}" +
            " WHERE id = #{vacancyRequirement.id}")
    void update(@Param("vacancyRequirement") VacancyRequirement vacancyRequirement);


    @Delete("DELETE FROM vacancy_requirement WHERE id = #{id}")
    void delete(@Param("id") int id);


    @Delete("DELETE FROM vacancy_requirement")
    void deleteAll();


    @Select("SELECT * FROM vacancy_requirement WHERE id = #{id}")
    VacancyRequirement getVacancyRequirementById(@Param("id") int id);


    @Select("SELECT id, description, level, obligatory FROM vacancy_requirement" +
            " WHERE (vacancy_id = #{vacancy.id})")
    Set<VacancyRequirement> getVacancyRequirementsByVacancy(@Param("Vacancy") Vacancy vacancy);


    @Select("SELECT * FROM vacancy_requirement")
    Set<VacancyRequirement> getAllVacancyRequirements();


    @Select("SELECT id FROM vacancy_requirement WHERE description = #{name} LIMIT 1")
    int getVacancyRequirementIdByName(String name);

}
