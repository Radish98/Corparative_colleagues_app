package operators;

import objects.Department;
import objects.Employee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentOperator {

    public static List<String> findSubstitutionOfCounterparts(Map<String, Department> mapOfDepartments){
        Collection<Department> allDepartments = mapOfDepartments.values();
        List<String> returnList = new ArrayList<>();
        for(Department dp : allDepartments){
            Map <String, Employee> mapOfEmployees = new HashMap<>();
            String stringOfEmployees = new String();
            for(Employee em:dp.getListOfObjectEmployees()){
                mapOfEmployees.put(em.getName(),em);
                stringOfEmployees = stringOfEmployees + em.getName() + "/";
            }
            for(String str: stringOfEmployees.split("/")){
                if(!str.equals("")) {
                    findMultiSubstitutions("/" + str, stringOfEmployees.replaceFirst(str, ""),
                            returnList,mapOfEmployees, dp.getAverageSalary(), allDepartments);
                }
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
            for( Employee employee : dp.getListOfObjectEmployees()){
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

    public static void findMultiSubstitutions(String prefix,String stringOfAllEmployees, List<String> returnList,
                                              Map<String,Employee> mapOfEmployees,
                                              BigDecimal averageSalary, Collection<Department> allDepartments){
        BigDecimal differenceAmount = new BigDecimal("0");
        BigDecimal sumOfPrefixSalaries = new BigDecimal("0");
        int countOfPrefixEmployees = 0;
        String nameOfDepartment = "";
        if(!prefix.equals("")){
            for(String str2 : prefix.split("/")){
                if(!str2.equals("")){
                    differenceAmount = differenceAmount.add(
                            mapOfEmployees.get(str2).getSalary()
                                    .subtract(averageSalary));
                    sumOfPrefixSalaries = sumOfPrefixSalaries.add(mapOfEmployees.get(str2).getSalary());
                    countOfPrefixEmployees++;
                    if(nameOfDepartment.equals("")){
                        nameOfDepartment = mapOfEmployees.get(str2).getDepartment();
                    }
                }
            }
        }
        if(differenceAmount.compareTo(new BigDecimal("0")) < 0)
            for(Department department: allDepartments){
                if(sumOfPrefixSalaries.divide(BigDecimal.valueOf(countOfPrefixEmployees)).compareTo(department.getAverageSalary())>0) {
                    returnList.add("Перевод: " + prefix + " из " + nameOfDepartment + " в "+ department.getName());
                }
            }
        for(String str: stringOfAllEmployees.split("/")){
            if(!str.equals("")) {
                findMultiSubstitutions(prefix + "/" + str,
                        stringOfAllEmployees.replaceFirst(str, ""), returnList, mapOfEmployees, averageSalary, allDepartments);
            }

        }

    }

    public static List<Employee> deleterElementFromCopy(List<Employee> list, Employee employee){
        List <Employee> copyList = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            Employee em = new Employee(list.get(i).getName(),
                    list.get(i).getDepartment(), list.get(i).getSalary());
            copyList.add(em);
        }

        copyList.remove(0);
        return copyList;
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
