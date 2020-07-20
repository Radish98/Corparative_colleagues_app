package objects;

import java.math.BigDecimal;

public class Employee {

    private String name;
    private String department;
    private BigDecimal salary;

    public Employee(String name, String department, BigDecimal salary){
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public BigDecimal getSalary() {
        return salary;
    }
    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

//    public void setDepartment(String department) {
//        this.department = department;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setSalary(BigDecimal salary) {
//        this.salary = salary;
//    }
}
