package hu.baxter.service.dto;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class EmployeeDepartmentMappingDto {

    private final String departmentName;
    private final String employeeName;

    public EmployeeDepartmentMappingDto(String departmentName, String employeeName) {
        this.departmentName = StringUtils.trim(departmentName);
        this.employeeName = StringUtils.normalizeSpace(employeeName);
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof EmployeeDepartmentMappingDto that)) return false;

        return new EqualsBuilder().append(departmentName, that.departmentName).append(employeeName, that.employeeName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(departmentName).append(employeeName).toHashCode();
    }
}
