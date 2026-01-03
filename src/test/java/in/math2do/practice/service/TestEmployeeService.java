package in.math2do.practice.service;

import in.math2do.practice.entity.EmployeeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TestEmployeeService {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void testGetAllEmployees() {
        List<EmployeeEntity> employees = employeeService.getAllEmployees();
        assertNotNull(employees);
    }
}
