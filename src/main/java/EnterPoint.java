import fileeditors.FileEditor;
import objects.Department;
import operators.DepartmentOperator;

import java.io.File;
import java.util.Map;

public class EnterPoint {

    public static void main(String[] args) {
        try{
            if(args.length == 0)
                throw new ArrayIndexOutOfBoundsException("Не введены адреса входного и выходного файла");
            if(args.length == 1)
                throw new ArrayIndexOutOfBoundsException("Не введен адрес выходного файла");
            File enterFile = new File(args[0]);
            File outputFile = new File(args[1]);
            if(enterFile.isAbsolute()){
                System.out.println("Указана абсолютная ссылка входного файла");
            }
            FileEditor fileEditor = new FileEditor();
            Map<String, Department> mapOfDepartments = fileEditor.readTheFile(enterFile);
            fileEditor.writeFile(DepartmentOperator.findSubstitutionOfCounterparts(mapOfDepartments), outputFile);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
    }
}


