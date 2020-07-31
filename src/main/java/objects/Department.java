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
    /**These lists keep employees whose salary
     lower or higher than average salary of department
     */
    private List<Employee> listOfEmployeesLowerThanAverage = new ArrayList<>();
    private List<Employee> listOfEmployeesHigherThanAverage = new ArrayList<>();


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
        averageSalary = sumOfSalary.divide(BigDecimal.valueOf(getCountOfObjectEmployees()),2, RoundingMode.DOWN);
    }

    public List<Employee> getListOfEmployeesHigherThanAverage() {
        List<Employee> newList= new ArrayList<>();
        for(int i = 0; i < listOfEmployeesHigherThanAverage.size(); i++){
            Employee em = new Employee(listOfEmployeesHigherThanAverage.get(i).getName(),
                    listOfEmployeesHigherThanAverage.get(i).getDepartment(), listOfEmployeesHigherThanAverage.get(i).getSalary());
            newList.add(em);
        }
        return newList;
    }

    public void addEmployeeHigherThanAverage(Employee employee){
        listOfEmployeesHigherThanAverage.add(employee);
    }

    public void setListOfEmployeesHigherThanAverage(List<Employee> listOfEmployeesHigherThanAverage) {
        this.listOfEmployeesHigherThanAverage = listOfEmployeesHigherThanAverage;
    }

    public List<Employee> getListOfEmployeesLowerThanAverage() {
        List<Employee> newList= new ArrayList<>();
        for(int i = 0; i < listOfEmployeesLowerThanAverage.size(); i++){
            Employee em = new Employee(listOfEmployeesLowerThanAverage.get(i).getName(),
                    listOfEmployeesLowerThanAverage.get(i).getDepartment(), listOfEmployeesLowerThanAverage.get(i).getSalary());
            newList.add(em);
        }
        return newList;
    }

    public void addEmployeeLowerThanAverage(Employee employee){
        listOfEmployeesLowerThanAverage.add(employee);
    }

    public void setListOfEmployeesLowerThanAverage(List<Employee> listOfEmployeesLowerThanAverage) {
        this.listOfEmployeesLowerThanAverage = listOfEmployeesLowerThanAverage;
    }
}
