import FileEditors.FileEditor;
import objects.Department;
import operators.DepartmentOperator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EnterPoint {

 //   private static String absolutePath = "src/main/resources/list.txt";
   // private static  File justFile = new File (absolutePath);

    public static void main(String[] args) {
        if(args[0] == null){
            System.out.println("Не введен адресс файла");
        }else {
            File justFile = new File(args[0]);
            if(justFile.isAbsolute()){
                System.out.println("Указана абсолютная ссылка");
            }
            FileEditor fileEditor = new FileEditor();
            Map<String, Department> mapOfDepartments = new HashMap<>();
            mapOfDepartments = fileEditor.readTheFile(justFile);
            DepartmentOperator departmentOperator = new DepartmentOperator();
            fileEditor.writeFile(departmentOperator.findSubstitutionOfCounterparts(mapOfDepartments));
        }
    }
}


