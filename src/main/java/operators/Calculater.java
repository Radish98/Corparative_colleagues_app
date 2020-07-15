package operators;

import objects.Department;
import objects.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Calculater {

    public List<String> findSubstitutionOfCounterparts(Map<String, Department> mapOfDepartments, List<Employee> employees){
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
}
