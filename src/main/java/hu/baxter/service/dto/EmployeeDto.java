package hu.baxter.service.dto;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class EmployeeDto implements Serializable {

    private String name;

    public EmployeeDto() {}

    public EmployeeDto(String name) {
        this.name = StringUtils.normalizeSpace(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof EmployeeDto that)) return false;

        return new EqualsBuilder().append(name, that.name).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(name).toHashCode();
    }
}
