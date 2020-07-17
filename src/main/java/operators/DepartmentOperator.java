package operators;

import objects.Department;
import objects.Employee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DepartmentOperator {

    public List<String> findSubstitutionOfCounterparts(Map<String, Department> mapOfDepartments){
        List<Employee> employees  = getAllEmployee(mapOfDepartments);
        Set<String> setOfDepartments = mapOfDepartments.keySet();
        List<String> returnList = new ArrayList<>();

        for(int i = 0; i < employees.size(); i++){
            if(mapOfDepartments.get(employees.get(i).getDepartment()).getAverageSalary()
                    .compareTo(
                            employees.get(i).getSalary()) >= 0){
                for(String department : setOfDepartments){
                    if(mapOfDepartments.get(department).getAverageSalary()
                            .compareTo(
                                    employees.get(i).getSalary())<=0){
                        BigDecimal salaryBefore = mapOfDepartments.get(employees.get(i).getDepartment()).getAverageSalary();
                        Department departmentTransferFrom = new Department();
                        departmentTransferFrom.setSumOfSalary(mapOfDepartments.get(employees.get(i).getDepartment()).getSumOfSalary());
                        departmentTransferFrom.setName(mapOfDepartments.get(employees.get(i).getDepartment()).getName());
                        departmentTransferFrom.setEmployeeObject(mapOfDepartments.get(employees.get(i).getDepartment()).getListOfObjectEmployees());
                        departmentTransferFrom.deleteObjectEmployee(employees.get(i));
                        BigDecimal salaryAfter = departmentTransferFrom.getAverageSalary();

                        returnList.add("Средняя ЗП в " + employees.get(i).getDepartment() + " до перевода сотрудника "
                                + employees.get(i).getName()
                                + " в " + department + " составляет " + salaryBefore + ", а после перевода составит " + salaryAfter);

                        salaryBefore = mapOfDepartments.get(department).getAverageSalary();
                        Department departmentTransferTo = new Department();
                        departmentTransferTo.setSumOfSalary(mapOfDepartments.get(department).getSumOfSalary());
                        departmentTransferTo.setName(mapOfDepartments.get(department).getName());
                        departmentTransferTo.setEmployeeObject(mapOfDepartments.get(department).getListOfObjectEmployees());
                        departmentTransferTo.addEmployeeObject(employees.get(i));
                        salaryAfter = departmentTransferTo.getAverageSalary();

                        returnList.add("Средняя ЗП в " + department + " до перевода сотрудника " + employees.get(i).getName() + " из "
                                + employees.get(i).getDepartment() + " составляет " + salaryBefore + ", a после перевода составит " + salaryAfter + "\n");
                    }
                }
            }
        }
        return returnList;
    }

    public Map<String, Department> createMapOfDepartment(Employee newEmployee, Department newDepartment, Map<String, Department> mapOfDepartments, String line){
        String department = line.split("/")[1];
        newEmployee.setDepartment(department);
        newEmployee.setName(line.split("/")[0]);
        newEmployee.setSalary(BigDecimal.valueOf(Float.parseFloat(line.split("/")[2])));

        if(mapOfDepartments.get(department)== null){
            newDepartment.setName(department);
            newDepartment.addEmployeeObject(newEmployee);
            mapOfDepartments.put(department, newDepartment);
        }else{
            mapOfDepartments.get(department).addEmployeeObject(newEmployee);
        }
        return mapOfDepartments;
    }

    public List<Employee> getAllEmployee(Map<String, Department> mapOfDepartments){
        List<Employee> employees = new ArrayList<>();
        for(String str : mapOfDepartments.keySet()) {
            employees.addAll(mapOfDepartments.get(str).getListOfObjectEmployees());
        }
        return employees;
    }
}
