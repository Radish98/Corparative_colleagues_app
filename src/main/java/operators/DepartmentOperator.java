package operators;

import objects.Department;
import objects.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DepartmentOperator {

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
                System.out.println(allEmployeesList.get(i).getName());
                i++;
                checkingTransferOfEmployee(checkList,department, departmentsCollection,returnList);
                recursionOfFindSubstitutions(i, allEmployeesList, returnList, checkList, department, departmentsCollection);
                checkList.clear();
            }
        }

        return returnList;
    }

    public static void fillMapOfDepartments(Map<String, Department> mapOfDepartments, String line){
        try {
            checkerOfEmployees(line);
            Department newDepartment;
            String [] splitArray = line.split("/");
            if (!mapOfDepartments.containsKey(splitArray[1].trim())) {
                newDepartment = new Department();
                newDepartment.setName(splitArray[1].trim());
                newDepartment.addEmployeeObject(new Employee(splitArray[0].trim(), splitArray[1].trim(),
                        new BigDecimal(splitArray[2].trim())));
                mapOfDepartments.put(splitArray[1].trim(), newDepartment);
            } else {
                mapOfDepartments.get(splitArray[1].trim()).addEmployeeObject(new Employee(splitArray[0].trim(), splitArray[1].trim(),
                        new BigDecimal(splitArray[2].trim())));
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Предупреждение: Неправильный формат записи.\nВычисления произведены без учета данной строки." +
                    "\nДля корректности расчетов приведите строку в формат ФИО/Отдел/ЗП \n");
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }


    public static void fillDepartmentsList(Map<String, Department> mapOfDepartments){
        Collection<Department> departmentsCollection = mapOfDepartments.values();
        for(Department dp : departmentsCollection){
            for(Employee employee : dp.getListOfObjectEmployees()){
                if(employee.getSalary().compareTo(dp.getAverageSalary()) >= 0)
                    mapOfDepartments.get(dp.getName()).addEmployeeHigherThanAverage(employee);
                if(employee.getSalary().compareTo(dp.getAverageSalary())< 0)
                    mapOfDepartments.get(dp.getName()).addEmployeeLowerThanAverage(employee);
            }
        }
    }
    public static String checkerOfEmployees (String line) throws Exception {

        if(line.equals(""))
            throw new Exception("\nПредупреждение: Не оставляйте строки пустыми!!!\n");
        String [] splitLine = line.split("/");
        if(splitLine[0].trim().length() == 0)
            throw new Exception("\nОшибка: Отсутствует ФИО." +
                    "\nДанный сотрудник не учитывается при расчете\n");
        if(splitLine[1].trim().length() == 0) {
            throw new Exception("\nОшибка: Отсутствует отдел." +
                    "\nДанный сотрудник не учитывается при расчете\n");
        }
        try {
            BigDecimal testBgdcm = new BigDecimal(splitLine[2].trim());

            if(testBgdcm.compareTo(BigDecimal.valueOf(0)) < 0)
                throw  new Exception("\nОшибка: Отрицательная заработная плата." +
                        "\nДанный сотрудник не учитывается при расчете\n");
            if(testBgdcm.scale() > 2)
                throw new Exception("\nОшибка: Неверная точность в заработной плате." +
                        "\nДанный сотрудник не учитывается при расчете" +
                        "\nУкажите точность до сотых\n");
        }catch (NumberFormatException ex){
            throw new Exception("\nОшибка: Неверный формат заработной платы." +
                    "\nДанный сотрудник не учитывается при расчете" +
                    "\nУкажите число\n");
        }
        return line;
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
                for(Employee employee:myChecklist){
                    System.out.print(employee.getName()+ "/");
                }
                System.out.println("\n");

                checkingTransferOfEmployee(myChecklist, department,departments, returnList);
                i++;
                recursionOfFindSubstitutions(i, allEmployeeList, returnList, myChecklist, department, departments);
                myChecklist.clear();
            }
        }

    }

    public static void checkingTransferOfEmployee(List<Employee> checkList, Department department,
                                                  Collection<Department> departments, List<String> returnList){
        try {
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

        }catch (ArrayIndexOutOfBoundsException ex){
            System.out.println("Ошибка программиста:\n" + ex.getMessage());
        }
    }
    /*
                    if(departmentTransferFrom.getAverageSalary().compareTo(employee.getSalary()) > 0){
                    for(Department departmentTransferTo:allDepartments){
                        if(departmentTransferTo.getAverageSalary().compareTo(employee.getSalary())< 0){
                            BigDecimal averageSalaryAfterTransfer = (departmentTransferFrom.getSumOfSalary().subtract(employee.getSalary()))
                                    .divide(BigDecimal.valueOf(listOfEmployees.size() - 1), 2, RoundingMode.CEILING);
                            returnList.add("Средняя ЗП в " + departmentTransferFrom.getName() + " до перевода сотрудника "
                                    + employee.getName()
                                    + " в " + departmentTransferTo.getName() + " составляет " + departmentTransferFrom.getAverageSalary()
                                    + ", а после перевода составит " + averageSalaryAfterTransfer);
                            averageSalaryAfterTransfer = ((departmentTransferTo.getSumOfSalary().add(employee.getSalary())))
                                    .divide(BigDecimal.valueOf(listOfEmployees.size() + 1), 2, RoundingMode.CEILING);
                            returnList.add("Средняя ЗП в " + departmentTransferTo.getName() + " до перевода сотрудника " + employee.getName() + " из "
                                    + departmentTransferFrom.getName() + " составляет " + departmentTransferTo.getAverageSalary()
                                    + ", a после перевода составит " + averageSalaryAfterTransfer +  "\n");
                        }
                    }
                }
     */
}
