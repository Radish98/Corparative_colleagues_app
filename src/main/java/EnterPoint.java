import fileEditors.FileEditor;
import objects.Department;
import operators.DepartmentOperator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EnterPoint {

    public static void main(String[] args) {
        try{
            File justFile = new File(args[0]);
            if(justFile.isAbsolute()){
                System.out.println("Указана абсолютная ссылка");
            }
            FileEditor fileEditor = new FileEditor();
            Map<String, Department> mapOfDepartments = new HashMap<>();
            mapOfDepartments = fileEditor.readTheFile(justFile);
            DepartmentOperator departmentOperator = new DepartmentOperator();
            fileEditor.writeFile(departmentOperator.findSubstitutionOfCounterparts(mapOfDepartments));
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Не введен адресс файла");
        }

    }
}


