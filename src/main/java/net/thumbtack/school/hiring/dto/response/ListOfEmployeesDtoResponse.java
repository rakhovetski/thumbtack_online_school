package net.thumbtack.school.hiring.dto.response;

import java.util.List;

public class ListOfEmployeesDtoResponse {
    private List<EmployeeDtoResponse> employeeList;

    public ListOfEmployeesDtoResponse(List<EmployeeDtoResponse> employeeList) {
        this.employeeList = employeeList;
    }

    public List<EmployeeDtoResponse> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeDtoResponse> employeeList) {
        this.employeeList = employeeList;
    }
}
