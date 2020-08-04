package operators;

import objects.Department;
import objects.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Transfer {
    public static List<String> findSubstitution(Map<String, Department> mapOfDepartments) {
        List<String> returnList = new ArrayList<>();
        Collection<Department> departmentsCollection = mapOfDepartments.values();
        for( Department department : departmentsCollection){
            List<Employee> allEmployeesList = department.getListOfObjectEmployees();
            List<Employee> lowSalaryEmployees= department.getListOfEmployeesLowerThanAverage();
            List<Employee> checkList = new ArrayList<>();
            int i = 0;
            while (i < allEmployeesList.size()){
                checkList.add(allEmployeesList.get(i));
//                System.out.println(allEmployeesList.get(i).getName());
                i++;
                checkingTransferOfEmployee(checkList,department, departmentsCollection,returnList);
                recursionOfFindSubstitutions(i, allEmployeesList, returnList, checkList, department, departmentsCollection);
                checkList.clear();
            }
        }
        return returnList;
    }

    public static void recursionOfFindSubstitutions(int index, List<Employee> allEmployeeList,
                                                    List<String> returnList, List<Employee> checkList,
                                                    Department department,
                                                    Collection<Department> departments){
        int i = index;
        List<Employee> myChecklist = new ArrayList<>();
        if(allEmployeeList.size()>i) {
            while (allEmployeeList.size() > i) {
                myChecklist.addAll(checkList);
                myChecklist.add(allEmployeeList.get(i));
//                for(Employee employee:myChecklist){
//                    System.out.print(employee.getName()+ "/");
//                }
//                System.out.println("\n");
                checkingTransferOfEmployee(myChecklist, department,departments, returnList);
                i++;
                recursionOfFindSubstitutions(i, allEmployeeList, returnList, myChecklist, department, departments);
                myChecklist.clear();
            }
        }
    }

    public static void checkingTransferOfEmployee(List<Employee> checkList, Department department,
                                                  Collection<Department> departments, List<String> returnList){
        BigDecimal sumOfCheckListSalary = new BigDecimal(0);
        BigDecimal averageSalary = department.getAverageSalary();
        String names = "";
        for(Employee employee:checkList){
            sumOfCheckListSalary = sumOfCheckListSalary.add(employee.getSalary());
            names= names +  employee.getName() + ", ";
        }
        if(averageSalary.compareTo(sumOfCheckListSalary.divide(BigDecimal.valueOf(checkList.size()), RoundingMode.DOWN)) > 0
                & BigDecimal.valueOf(department.getCountOfObjectEmployees()).compareTo(BigDecimal.valueOf(checkList.size()))!=0) {
            for (Department dep : departments) {
                if(dep.getAverageSalary().compareTo(sumOfCheckListSalary.divide(BigDecimal.valueOf(checkList.size()), RoundingMode.DOWN))< 0){
                    returnList.add("Перевод сотрудников: " + names + " из "
                            + department.getName()+ "(ЗП до/после["+ department.getAverageSalary()
                            + "/" + (department.getSumOfSalary()
                            .subtract(sumOfCheckListSalary))
                            .divide(BigDecimal.valueOf(department.getCountOfObjectEmployees())
                                    .subtract(BigDecimal.valueOf(checkList.size())), RoundingMode.DOWN) + "]"
                            + " в "
                            + dep.getName()
                            + "(ЗП до/после["+ dep.getAverageSalary()
                            + "/" + (dep.getSumOfSalary()
                            .add(sumOfCheckListSalary))
                            .divide(BigDecimal.valueOf(dep.getCountOfObjectEmployees())
                                    .add(BigDecimal.valueOf(checkList.size())), RoundingMode.DOWN) + "]" + "\n");
                }
            }
        }
    }
}
