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


        for(Department department:allDepartments ){
            List<Employee> listOfEmployees = department.getListOfObjectEmployees();
            for(Employee employee:listOfEmployees){
                if(department.getAverageSalary().compareTo(employee.getSalary()) >= 0){
                    for(Department departmentTo:allDepartments){
                        if(departmentTo.getAverageSalary().compareTo(employee.getSalary())<= 0){
                            BigDecimal averageSalaryAfterTransfer = (department.getSumOfSalary().subtract(employee.getSalary()))
                                    .divide(BigDecimal.valueOf(listOfEmployees.size() - 1), 2, RoundingMode.CEILING);
                            returnList.add("Средняя ЗП в " + department.getName() + " до перевода сотрудника "
                                    + employee.getName()
                                    + " в " + departmentTo.getName() + " составляет " + department.getAverageSalary()
                                    + ", а после перевода составит " + averageSalaryAfterTransfer);
                            averageSalaryAfterTransfer = ((departmentTo.getSumOfSalary().add(employee.getSalary())))
                                    .divide(BigDecimal.valueOf(listOfEmployees.size() + 1), 2, RoundingMode.CEILING);
                            returnList.add("Средняя ЗП в " + departmentTo.getName() + " до перевода сотрудника " + employee.getName() + " из "
                                    + department.getName() + " составляет " + departmentTo.getAverageSalary()
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
            if(line.equals("")) {
                System.out.println("bl");
                throw new Exception("Предупреждение: Не оставляйте строки пустыми!!!\n");
            }else {
                Department newDepartment;
                String [] splitArray = line.split("/");

                if (mapOfDepartments.get(splitArray[1]) == null) {
                    newDepartment = new Department();
                    newDepartment.setName(splitArray[1]);
                    newDepartment.addEmployeeObject(new Employee(splitArray[0], splitArray[1],
                            new BigDecimal(splitArray[2])));
                    mapOfDepartments.put(splitArray[1], newDepartment);
                } else {
                    mapOfDepartments.get(splitArray[1]).addEmployeeObject(new Employee(splitArray[0], splitArray[1],
                            new BigDecimal(splitArray[2])));
                }
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Предупреждение: Неправильный формат записи.\nВычисления произведены без учета данной строки." +
                    "\nДля корректности расчетов приведите строку в формат ФИО/Отдел/ЗП \n");
        } catch (Exception ex){
            System.out.println( ex+ "ala");
        }

    }

    public static List<Employee> getAllEmployee(Map<String, Department> mapOfDepartments){
        List<Employee> employees = new ArrayList<>();
        for(String str : mapOfDepartments.keySet()) {
            employees.addAll(mapOfDepartments.get(str).getListOfObjectEmployees());
        }
        return employees;
    }

    public String checkerOfEmployees(String line){

        return line;
    }
}
