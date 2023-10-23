package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.model.Vacancy;
import net.thumbtack.school.hiring.model.VacancyRequirement;

import java.util.Set;

public interface VacancyRequirementDao {

    int insert(VacancyRequirement vacancyRequirement, Vacancy vacancy);

    void update(int vacancyId, VacancyRequirement vacancyRequirement);

    void delete(int vacancyId, int vacancyRequirementId);

    void deleteAll();

    VacancyRequirement getVacancyRequirementById(int id);

    Set<VacancyRequirement> getVacancyRequirementsByVacancy(Vacancy vacancy);

    Set<VacancyRequirement> getAllVacancyRequirements();

}
