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

    public String getName() {
        return name;
    }

    public BigDecimal getAverageSalary() { return averageSalary;}

    public List<Employee> getListOfObjectEmployees(){
        List<Employee> newList= new ArrayList<>();
        for(int i = 0; i < listOfObjectEmployees.size(); i++){
            Employee em = new Employee(listOfObjectEmployees.get(i).getName(),
                    listOfObjectEmployees.get(i).getDepartment(), listOfObjectEmployees.get(i).getSalary());
            newList.add(em);
        }
        return newList;
    }


    public BigDecimal getSumOfSalary() {
        return sumOfSalary;
    }

    public int getCountOfObjectEmployees() {
       return listOfObjectEmployees.size();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployeeObjectList(List<Employee> employee){
        listOfObjectEmployees.clear();
        sumOfSalary = new BigDecimal(0);
        averageSalary = new BigDecimal(0);
        listOfObjectEmployees.addAll(employee);
        for(Employee newEmployee:listOfObjectEmployees){
            sumOfSalary = sumOfSalary.add(newEmployee.getSalary());
        }
        calculateAverageSalary();
    }

    public void addEmployeeObject(Employee employee){
        listOfObjectEmployees.add(employee);
        sumOfSalary = sumOfSalary.add(employee.getSalary());
        calculateAverageSalary();
    }

    public void deleteObjectEmployee(Employee employee){
        listOfObjectEmployees.remove(employee);
        sumOfSalary = sumOfSalary.subtract(employee.getSalary());
        averageSalary = sumOfSalary.divide(BigDecimal.valueOf(getCountOfObjectEmployees()), 2, RoundingMode.CEILING);
    }

    protected void calculateAverageSalary(){
        averageSalary = sumOfSalary.divide(BigDecimal.valueOf(getCountOfObjectEmployees()),2, RoundingMode.CEILING);
    }
}
