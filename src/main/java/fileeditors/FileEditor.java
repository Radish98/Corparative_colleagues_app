package fileeditors;

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
    public Map<String, Department> readTheFile(File enterFile) {
        Map<String, Department> mapOfDepartments = new HashMap<>();

        try(FileReader fr = new FileReader(enterFile)) {
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            int i  = 0;
            System.out.println("Полученный файл:");
            while (line != null) {
                System.out.println(line);
                DepartmentOperator.fillMapOfDepartments(mapOfDepartments,line);
                line = reader.readLine();
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Входной файл не найден:\n" + enterFile.getPath());
        } catch (IOException e) {
            System.out.println("бла-бла проблемы с файлом бла-бла");
        }
        return mapOfDepartments;
    }

    public void writeFile(List<String> list, File outputFile){
        try(FileWriter fwr = new FileWriter(outputFile, false)) {
                for(String str : list) {
                fwr.write(str);
                fwr.append('\n');
            }
            fwr.flush();
        } catch (IOException e) {
            System.out.println("Неверный путь к выходному файлу:\n" + outputFile.getPath());
        }
    }
}
