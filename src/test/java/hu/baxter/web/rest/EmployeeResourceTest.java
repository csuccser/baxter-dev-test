package hu.baxter.web.rest;

import hu.baxter.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeResourceTest {

    @Autowired
    private MockMvc employeeResource;


    /**
     * Testing /rest/employees endpoint without any filtering
     * @throws Exception
     */
    @Test
    public void getAllEmployees() throws Exception{
        employeeResource
            .perform(get("/rest/employees"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(7)))
            .andExpect(jsonPath("$[0].name").value("Dale Miller"))
            .andExpect(jsonPath("$[1].name").value("George Smith"))
            .andExpect(jsonPath("$[2].name").value("James Doyle"))
            .andExpect(jsonPath("$[3].name").value("Joanne Olsen"))
            .andExpect(jsonPath("$[4].name").value("Michael Smith"))
            .andExpect(jsonPath("$[5].name").value("Peter Goeking"))
            .andExpect(jsonPath("$[6].name").value("Samuel Palmisano"))
            ;
    }

    /**
     * Testing /rest/employees endpoint with filtering department 'it'
     * @throws Exception
     */
    @Test
    public void getAllEmployeesFilteredIt() throws Exception{
        employeeResource
            .perform(get("/rest/employees?department=it"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].name").value("Michael Smith"))
            .andExpect(jsonPath("$[1].name").value("Peter Goeking"))
            .andExpect(jsonPath("$[2].name").value("Samuel Palmisano"))
        ;
    }


    /**
     * Testing /rest/employees endpoint with filtering department 'packaging'
     * @throws Exception
     */
    @Test
    public void getAllEmployeesFilteredPackaging() throws Exception{
        employeeResource
            .perform(get("/rest/employees?department=packaging"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].name").value("Dale Miller"))
            .andExpect(jsonPath("$[1].name").value("James Doyle"))
        ;
    }


    /**
     * Testing /rest/employees endpoint with filtering department 'finance'
     * @throws Exception
     */
    @Test
    public void getAllEmployeesFilteredFinance() throws Exception{
        employeeResource
            .perform(get("/rest/employees?department=finance"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(5)))
            .andExpect(jsonPath("$[0].name").value("Dale Miller"))
            .andExpect(jsonPath("$[1].name").value("George Smith"))
            .andExpect(jsonPath("$[2].name").value("Joanne Olsen"))
            .andExpect(jsonPath("$[3].name").value("Michael Smith"))
            .andExpect(jsonPath("$[4].name").value("Peter Goeking"))
        ;
    }


    /**
     * Testing /rest/employees/groupby/department endpoint
     * @throws Exception
     */
    @Test
    public void getEmployeesGroupedByDepartment() throws Exception{
        employeeResource
            .perform(get("/rest/employees/groupby/department"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$", hasSize(3)))
            // checking departments
            .andExpect(jsonPath("$[0].departmentName").value("finance"))
            .andExpect(jsonPath("$[1].departmentName").value("it"))
            .andExpect(jsonPath("$[2].departmentName").value("packaging"))
            // checking employees
            .andExpect(jsonPath("$[0].employees", hasSize(5)))
            .andExpect(jsonPath("$[0].employees[0].name").value("Dale Miller"))
            .andExpect(jsonPath("$[0].employees[1].name").value("George Smith"))
            .andExpect(jsonPath("$[0].employees[2].name").value("Joanne Olsen"))
            .andExpect(jsonPath("$[0].employees[3].name").value("Michael Smith"))
            .andExpect(jsonPath("$[0].employees[4].name").value("Peter Goeking"))
            .andExpect(jsonPath("$[1].employees", hasSize(3)))
            .andExpect(jsonPath("$[1].employees[0].name").value("Michael Smith"))
            .andExpect(jsonPath("$[1].employees[1].name").value("Peter Goeking"))
            .andExpect(jsonPath("$[1].employees[2].name").value("Samuel Palmisano"))
            .andExpect(jsonPath("$[2].employees", hasSize(2)))
            .andExpect(jsonPath("$[2].employees[0].name").value("Dale Miller"))
            .andExpect(jsonPath("$[2].employees[1].name").value("James Doyle"))
        ;
    }


}
