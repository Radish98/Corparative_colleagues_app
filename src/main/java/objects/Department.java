package objects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Department {

    private String name;
    private BigDecimal averageSalary;
    private BigDecimal sumOfSalary;
    private List<String> listOfEmployees = new ArrayList<>();
    private List<Employee> listOfObjectEmployees = new ArrayList<>();
    private int countOfEmployees;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAverageSalary() {
        averageSalary = sumOfSalary.divide(BigDecimal.valueOf(getCountOfObjectEmployees()),8, RoundingMode.CEILING);
        return averageSalary;
    }

    public void addEmployeeObject(Employee employee){
        listOfObjectEmployees.add(employee);
    }

    public void setEmployeeObject(List<Employee> employee){
        listOfObjectEmployees.addAll(employee);
    }

    public List<Employee> getListOfObjectEmployees(){
        return listOfObjectEmployees;
    }

    public BigDecimal getSumOfSalary() {
        return sumOfSalary;
    }

    public void setSumOfSalary(BigDecimal sumOfSalary) {
        this.sumOfSalary = sumOfSalary;
    }

    public void addNewEmployee(String fio){
        listOfEmployees.add(fio);
    }

    public void deleteObjectEmployee(Employee employee, BigDecimal bgdc){

        listOfObjectEmployees.remove(employee);
        System.out.println(listOfObjectEmployees.size());
        sumOfSalary = sumOfSalary.subtract(bgdc);
        System.out.println(sumOfSalary);

    }

    public int getCountOfObjectEmployees() {
        countOfEmployees = listOfObjectEmployees.size();
        return countOfEmployees;
    }

}
