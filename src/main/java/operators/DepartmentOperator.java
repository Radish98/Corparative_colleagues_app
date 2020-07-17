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
        Set<String> keySet = mapOfDepartments.keySet();
        List<String> returnList = new ArrayList<>();

        for(int i = 0; i < employees.size(); i++){
            if(mapOfDepartments.get(employees.get(i).getDepartment()).getAverageSalary()
                    .compareTo(
                            employees.get(i).getSalary()) >= 0){
                for(String key : keySet){
                    if(mapOfDepartments.get(key).getAverageSalary()
                            .compareTo(
                                    employees.get(i).getSalary())<=0){
                        BigDecimal dec1 = mapOfDepartments.get(employees.get(i).getDepartment()).getAverageSalary();
                        Department dep = new Department();
                        dep.setSumOfSalary(mapOfDepartments.get(employees.get(i).getDepartment()).getSumOfSalary());
                        dep.setName(mapOfDepartments.get(employees.get(i).getDepartment()).getName());
                        dep.setEmployeeObject(mapOfDepartments.get(employees.get(i).getDepartment()).getListOfObjectEmployees());
                        dep.deleteObjectEmployee(employees.get(i), employees.get(i).getSalary());
                        BigDecimal dec2 = dep.getAverageSalary();

                        returnList.add("Средняя ЗП в " + employees.get(i).getDepartment() + " до перевод сотрудника "
                                + employees.get(i).getName()
                                + " в " + key + " составляет " + dec1 + ", а после перевода составит " + dec2);
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
            newDepartment.setSumOfSalary(BigDecimal.valueOf(Float.parseFloat(line.split("/")[2])));
            newDepartment.addNewEmployee(line.split("/")[0]);
            newDepartment.addEmployeeObject(newEmployee);
            mapOfDepartments.put(department, newDepartment);
        }else{
            mapOfDepartments.get(department).addEmployeeObject(newEmployee);
            mapOfDepartments.get(department).addNewEmployee(line.split("/")[0]);
            mapOfDepartments.get(department).setSumOfSalary(BigDecimal.valueOf(Float.
                    parseFloat(line.split("/")[2]))
                    .add(mapOfDepartments.get(department).getSumOfSalary()));

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
