package net.thumbtack.school.hiring.dto.response;

import java.util.Set;

public class SetVacanciesByEmployerDtoResponse {
    private Set<VacancyDtoResponse> vacancyDtoResponses;

    public SetVacanciesByEmployerDtoResponse(Set<VacancyDtoResponse> vacancyDtoResponses) {
        this.vacancyDtoResponses = vacancyDtoResponses;
    }

    public Set<VacancyDtoResponse> getVacancyDtoResponses() {
        return vacancyDtoResponses;
    }

    public void setVacancyDtoResponses(Set<VacancyDtoResponse> vacancyDtoResponses) {
        this.vacancyDtoResponses = vacancyDtoResponses;
    }
}
