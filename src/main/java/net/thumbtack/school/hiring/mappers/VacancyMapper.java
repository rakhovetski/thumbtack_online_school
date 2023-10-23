package net.thumbtack.school.hiring.mappers;

import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.Vacancy;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.Set;

public interface VacancyMapper {

    @Insert("INSERT INTO vacancy(jobTitle, salary, status, employer_id) " +
            "VALUES(#{vacancy.jobTitle}, #{vacancy.salary}, true, #{employer.id})")
    @Options(useGeneratedKeys = true, keyProperty = "vacancy.id")
    Integer insert(@Param("vacancy") Vacancy vacancy, @Param("employer") Employer employer);


    @Update("UPDATE vacancy SET jobTitle = #{vacancy.jobTitle}, salary = #{vacancy.salary}, status = #{vacancy.status}" +
            " WHERE id = #{vacancy.id}")
    void update(@Param("vacancy") Vacancy vacancy);


    @Delete("DELETE FROM vacancy WHERE id = #{id}")
    void delete(@Param("id") int id);


    @Delete("DELETE FROM vacancy")
    void deleteAll();


    @Select("SELECT * FROM vacancy WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "vacancyRequirements", column = "id", javaType = Set.class,
                    many = @Many(select = "net.thumbtack.school.hiring.mappers.VacancyRequirementMapper.getVacancyRequirementsByVacancy",
                            fetchType = FetchType.LAZY))})
    Vacancy getVacancyById(@Param("id") int id);


    @Select("SELECT * FROM vacancy")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "vacancy_requirements", column = "id", javaType = Set.class,
                    many = @Many(select = "net.thumbtack.school.hiring.mappers.VacancyRequirementMapper.getVacancyRequirementsByVacancy",
                            fetchType = FetchType.LAZY))})
    Set<Vacancy> getAllVacancies();


    @Select("SELECT * FROM vacancy" +
            " WHERE (employer_id = #{employer.id})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "vacancyRequirements", column = "id", javaType = Set.class,
                    many = @Many(select = "net.thumbtack.school.hiring.mappers.VacancyRequirementMapper.getVacancyRequirementsByVacancy",
                            fetchType = FetchType.LAZY))})
    Set<Vacancy> getVacanciesByEmployer(Employer Employer);


    @Select("SELECT id FROM vacancy WHERE jobTitle = #{name}  LIMIT 1")
    int getIdVacancyByName(String name);

}
