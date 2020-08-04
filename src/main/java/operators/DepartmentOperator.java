package operators;

import objects.Department;
import objects.Employee;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

public class DepartmentOperator {
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


}
