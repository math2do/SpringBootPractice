package in.math2do.practice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.math2do.practice.entity.EmployeeEntity;
import in.math2do.practice.repository.EmployeeRepository;

@Service
public class EmployeeService {
  private final EmployeeRepository repository;

  @Autowired
  public EmployeeService(EmployeeRepository repository) {
    this.repository = repository;
  }

  public List<EmployeeEntity> getAllEmployees() {
    return repository.findAll();
  }

  public EmployeeEntity getEmployeeById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public EmployeeEntity saveEmployee(EmployeeEntity employee) {
    return repository.save(employee);
  }

  public EmployeeEntity updateEmployee(Long id, EmployeeEntity employee) {
    return repository.findById(id).map(e -> {
      e.setName(employee.getName());
      e.setRole(employee.getRole());
      e.setRole(employee.getEmail());
      e.setSalary(employee.getSalary());
      return repository.save(e);
    }).orElse(null);
  }

  public void deleteEmployee(Long id) {
    repository.deleteById(id);
  }
}
