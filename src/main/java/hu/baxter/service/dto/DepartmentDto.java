package hu.baxter.service.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.thymeleaf.util.StringUtils;

import java.io.Serializable;
import java.util.List;

public class DepartmentDto implements Serializable {

    private final String departmentName;
    private final List<EmployeeDto> employees;

    public DepartmentDto(String departmentName, List<EmployeeDto> employees) {
        this.departmentName = StringUtils.trim(departmentName);
        this.employees = employees;
    }


    public String getDepartmentName() {
        return departmentName;
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof DepartmentDto that)) return false;

        return new EqualsBuilder().append(departmentName, that.departmentName).append(employees, that.employees).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(departmentName).append(employees).toHashCode();
    }
}
