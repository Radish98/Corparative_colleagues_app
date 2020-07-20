package fileEditors;

import objects.Department;
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
            System.out.println("Полученный файл:");
            while (line != null) {
                System.out.println(line);
//                if(line.isEmpty() ||){
//
//                }
                DepartmentOperator departmentOperator = new DepartmentOperator();
                departmentOperator.createMapOfDepartment(mapOfDepartments,line);
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
