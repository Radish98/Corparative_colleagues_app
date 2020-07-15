package FileEditors;

import objects.Department;
import objects.Employee;
import operators.DepartmentOperator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileEditor {
    public Map<String, Department> readTheFile(File justFile) {
        Map<String, Department> mapOfDepartments = new HashMap<>();

        try {
            FileReader fr = new FileReader(justFile);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            int i  = 0;
            while (line != null) {
                System.out.println(line);
                Department newDepartment = new Department();
                Employee newEmployee = new Employee();
                DepartmentOperator departmentOperator = new DepartmentOperator();
                mapOfDepartments = departmentOperator.createMapOfDepartment(newEmployee,
                        newDepartment,mapOfDepartments,line);
                line = reader.readLine();
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        return mapOfDepartments;
    }

    public void writeFile(List<String> list){
        try(FileWriter fwr = new FileWriter("src/main/resources/readylist.txt", false)) {
            for(String str : list) {
                fwr.write(str);
                fwr.append('\n');
            }
            fwr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}