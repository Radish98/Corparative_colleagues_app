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

    public static List<String> findSubstitutionOfCounterparts(Map<String, Department> mapOfDepartments){
//        List<Employee> employees  = getAllEmployee(mapOfDepartments);
//        Set<String> namesOfDepartments = mapOfDepartments.keySet();
        Collection<Department> allDepartments = mapOfDepartments.values();
        List<String> returnList = new ArrayList<>();


        for(Department departmentTransferFrom:allDepartments ){
            List<Employee> listOfEmployees = departmentTransferFrom.getListOfObjectEmployees();
            for(Employee employee:listOfEmployees){
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
            }
        }

//        for(int i = 0; i < employees.size(); i++){
//            if(mapOfDepartments.get(employees.get(i).getDepartment()).getAverageSalary()
//                    .compareTo(
//                            employees.get(i).getSalary()) >= 0){
//                for(String department : namesOfDepartments){
//                    if(mapOfDepartments.get(department).getAverageSalary()
//                            .compareTo(
//                                    employees.get(i).getSalary())<=0){
//                        BigDecimal salaryBefore = mapOfDepartments.get(employees.get(i).getDepartment()).getAverageSalary();
//                        Department departmentTransferFrom = new Department();
//                        departmentTransferFrom.setName(mapOfDepartments.get(employees.get(i).getDepartment()).getName());
//                        departmentTransferFrom.setEmployeeObjectList(mapOfDepartments.get(employees.get(i).getDepartment()).getListOfObjectEmployees());
//                        departmentTransferFrom.deleteObjectEmployee(employees.get(i));
//                        BigDecimal salaryAfter = departmentTransferFrom.getAverageSalary();
//
//                        returnList.add("Средняя ЗП в " + employees.get(i).getDepartment() + " до перевода сотрудника "
//                                + employees.get(i).getName()
//                                + " в " + department + " составляет " + salaryBefore + ", а после перевода составит " + salaryAfter);
//
//                        salaryBefore = mapOfDepartments.get(department).getAverageSalary();
//                        Department departmentTransferTo = new Department();
//                        departmentTransferTo.setName(mapOfDepartments.get(department).getName());
//                        departmentTransferTo.setEmployeeObjectList(mapOfDepartments.get(department).getListOfObjectEmployees());
//                        departmentTransferTo.addEmployeeObject(employees.get(i));
//                        salaryAfter = departmentTransferTo.getAverageSalary();
//
//                        returnList.add("Средняя ЗП в " + department + " до перевода сотрудника " + employees.get(i).getName() + " из "
//                                + employees.get(i).getDepartment() + " составляет " + salaryBefore + ", a после перевода составит " + salaryAfter + "\n");
//                    }
//                }
//            }
//        }
        return returnList;
    }

    public static void fillMapOfDepartments(Map<String, Department> mapOfDepartments, String line){
        try {
            checkerOfEmployees(line);
            Department newDepartment;
            String [] splitArray = line.split("/");

            if (mapOfDepartments.get(splitArray[1].trim()) == null) {
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

    public static List<Employee> getAllEmployee(Map<String, Department> mapOfDepartments){
        List<Employee> employees = new ArrayList<>();
        for(String str : mapOfDepartments.keySet()) {
            employees.addAll(mapOfDepartments.get(str).getListOfObjectEmployees());
        }
        return employees;
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
}
