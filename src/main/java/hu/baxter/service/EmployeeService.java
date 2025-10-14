package hu.baxter.service;

import hu.baxter.service.dto.DepartmentDto;
import hu.baxter.service.dto.EmployeeDepartmentMappingDto;
import hu.baxter.service.dto.EmployeeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    private static final String EMPLOYEES_DISTINCT_XPATH = "//employee/name[not(. = preceding::employee/name)]/text()";
    private static final String EMPLOYEES_DISTINCT_BY_DEPARTMENT_XPATH = "//employee[department = '%s']/name[not(. = preceding::employee[department = '%s']/name)]/text()";
    private static final String EMPLOYEES_ALL_XPATH = "//employee";

    private final Document xmlDocument;
    private final XPath xpath;

    public EmployeeService(Document xmlDocument) {
        this.xmlDocument = xmlDocument;
        this.xpath = XPathFactory.newInstance().newXPath();
    }

    public List<EmployeeDto> getAllEmployees() {
        try {
            final List<EmployeeDto> res = new LinkedList<>();

            final NodeList nodes = (NodeList) xpath.evaluate(EMPLOYEES_DISTINCT_XPATH, xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                final Node node = nodes.item(i);
                res.add(new EmployeeDto(node.getTextContent()));
            }

            return res.stream().sorted(Comparator.comparing(EmployeeDto::getName)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<EmployeeDto> getEmployeesByDepartment(String department) {
        try {
            final List<EmployeeDto> res = new LinkedList<>();
            final String xpathExpression = String.format(EMPLOYEES_DISTINCT_BY_DEPARTMENT_XPATH, department, department);

            final NodeList nodes = (NodeList) xpath.evaluate(xpathExpression, xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                final Node node = nodes.item(i);
                res.add(new EmployeeDto(node.getTextContent()));
            }

            return res.stream().sorted(Comparator.comparing(EmployeeDto::getName)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<DepartmentDto> getEmployeesGroupedByDepartment() {
        try {
            final List<EmployeeDepartmentMappingDto> allEmployeeAndDepartment = new LinkedList<>();

            // collecting all employee nodes
            final NodeList employeeNodes = (NodeList) xpath.evaluate(EMPLOYEES_ALL_XPATH, xmlDocument, XPathConstants.NODESET);

            // iterating it and collecting data into a temporary mapping list
            for (int i = 0; i < employeeNodes.getLength(); i++) {
                final Node employeeNode = employeeNodes.item(i);
                final String employeeName = XPathFactory.newInstance().newXPath().evaluate("name/text()", employeeNode);

                final NodeList departmentNodes = (NodeList) XPathFactory.newInstance().newXPath().evaluate("department/text()", employeeNode, XPathConstants.NODESET);
                for (int j = 0; j < departmentNodes.getLength(); j++) {
                    final Node departmentNode = departmentNodes.item(j);
                    final String departmentName = departmentNode.getTextContent();
                    LOG.info("{} {}", departmentName, employeeName);
                    allEmployeeAndDepartment.add(new EmployeeDepartmentMappingDto(departmentName, employeeName));
                }
            }

            // distinct filtering
            final Collection<EmployeeDepartmentMappingDto> distinctMappings = allEmployeeAndDepartment
                .stream()
                .collect(Collectors.toMap(
                    mapping -> mapping.getDepartmentName() + "_" + mapping.getEmployeeName(),
                    mapping -> mapping,
                    (existing, replacement) -> existing // keep existing if duplicated
                ))
                .values();

            // grouping into a map where department name is key and the corresponding employee list is the value
            final Map<String, List<EmployeeDto>> groupedByDepartment = distinctMappings
                .stream()
                .collect(Collectors.groupingBy(
                    EmployeeDepartmentMappingDto::getDepartmentName,
                    Collectors.collectingAndThen(
                        Collectors.toList(),
                        list ->
                            list
                                .stream()
                                .map(m -> new EmployeeDto(m.getEmployeeName()))
                                .sorted(Comparator.comparing(EmployeeDto::getName)) // sorting by employee name
                                .collect(Collectors.toList())
                    )
                ));

            // converting to return structure and ordering by department name
            return groupedByDepartment
                .entrySet()
                .stream()
                .map(entry -> new DepartmentDto(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(DepartmentDto::getDepartmentName))
                .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
