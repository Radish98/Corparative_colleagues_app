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
                                    employees.get(i).getSalary())<=0)
                        returnList.add("Сотрудника " + employees.get(i).getName() + " можно перевести в " + key);
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
