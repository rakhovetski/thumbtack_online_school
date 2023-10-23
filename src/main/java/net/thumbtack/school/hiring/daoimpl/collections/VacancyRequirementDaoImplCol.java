package net.thumbtack.school.hiring.daoimpl.collections;

import net.thumbtack.school.hiring.dao.VacancyRequirementDao;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.model.Vacancy;
import net.thumbtack.school.hiring.model.VacancyRequirement;

import java.util.Set;

public class VacancyRequirementDaoImplCol implements VacancyRequirementDao {

    @Override
    public int insert(VacancyRequirement vacancyRequirement, Vacancy vacancy) {
        return Database.getInstance().addVacancyRequirement(vacancy, vacancyRequirement);
    }

    @Override
    public void update(int vacancyId, VacancyRequirement vacancyRequirement) {
        Database.getInstance().replaceVacancyRequirement(vacancyId, vacancyRequirement);
    }

    @Override
    public void delete(int vacancyId, int vacancyRequirementId) {
        Database.getInstance().removeVacancyRequirement(vacancyId, vacancyRequirementId);
    }

    @Override
    public void deleteAll() {
        Database.getInstance().deleteAllVacancyRequirements();
    }

    @Override
    public VacancyRequirement getVacancyRequirementById(int id) {
        return Database.getInstance().getVacancyRequirementById(id);
    }

    @Override
    public Set<VacancyRequirement> getVacancyRequirementsByVacancy(Vacancy vacancy) {
        return Database.getInstance().getVacancyRequirementByVacancy(vacancy);
    }

    @Override
    public Set<VacancyRequirement> getAllVacancyRequirements() {
        return Database.getInstance().getAllVacancyRequirements();
    }
}
