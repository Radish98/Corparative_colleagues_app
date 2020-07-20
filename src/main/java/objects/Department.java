package objects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Department {

    private String name;
    private BigDecimal averageSalary = new BigDecimal(0);
    private BigDecimal sumOfSalary = new BigDecimal(0);
    private List<Employee> listOfObjectEmployees = new ArrayList<>();
    private int countOfEmployees;

    public String getName() {
        return name;
    }

    public BigDecimal getAverageSalary() { return averageSalary;}

    public List<Employee> getListOfObjectEmployees(){
        return listOfObjectEmployees;
    }

    public BigDecimal getSumOfSalary() {
        return sumOfSalary;
    }

    public int getCountOfObjectEmployees() {
        countOfEmployees = listOfObjectEmployees.size();
        return countOfEmployees;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployeeObjectList(List<Employee> employee){
        listOfObjectEmployees.addAll(employee);
        getCountOfObjectEmployees();
        for(Employee newEmployee:listOfObjectEmployees){
            sumOfSalary = sumOfSalary.add(newEmployee.getSalary());
        }
        calculateAverageSalary();
    }

    public void addEmployeeObject(Employee employee){
        listOfObjectEmployees.add(employee);
        getCountOfObjectEmployees();
        sumOfSalary = sumOfSalary.add(employee.getSalary());
        calculateAverageSalary();
    }

    public void deleteObjectEmployee(Employee employee){
        listOfObjectEmployees.remove(employee);
        getCountOfObjectEmployees();
        sumOfSalary = sumOfSalary.subtract(employee.getSalary());
        averageSalary = sumOfSalary.divide(BigDecimal.valueOf(countOfEmployees), 4, RoundingMode.CEILING);
    }

    private BigDecimal calculateAverageSalary(){
        averageSalary = sumOfSalary.divide(BigDecimal.valueOf(getCountOfObjectEmployees()),8, RoundingMode.CEILING);
        return averageSalary;
    }
}
