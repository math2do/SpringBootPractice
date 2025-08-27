package in.math2do.practice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import in.math2do.practice.entity.EmployeeEntity;
import in.math2do.practice.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
  private final EmployeeService service;

  @Autowired
  public EmployeeController(EmployeeService service) {
    this.service = service;
  }

  @GetMapping
  public List<EmployeeEntity> getAllEmployees() {
    return service.getAllEmployees();
  }

  @GetMapping("/{id}")
  public EmployeeEntity getEmployee(@PathVariable Long id) {
    return service.getEmployeeById(id);
  }

  @PostMapping
  public EmployeeEntity createEmployee(@RequestBody EmployeeEntity employee) {
    return service.saveEmployee(employee);
  }

  @PutMapping("/{id}")
  public EmployeeEntity updateEmployee(@PathVariable Long id,
      @RequestBody EmployeeEntity employee) {
    return service.updateEmployee(id, employee);
  }

  @DeleteMapping("/{id}")
  public void deleteEmployee(@PathVariable Long id) {
    service.deleteEmployee(id);
  }
}
