package FileEditors;

import objects.Department;
import objects.Employee;
import operators.Calculater;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileReader {
    private List<String> listOfColleagues;
//    private static List<String> justList;
//    private static String listOfColleagues = "list.txt";




    public void readTheFile(File justFile) {
//        InputStream dataStream = FileEditors.FileReader.class.getResourceAsStream(listOfColleagues);
//        BufferedReader br = null;
//        StringBuffer sb = new StringBuffer();
//        String s;
//        List<String> arrayListOfColleagues = new ArrayList<>();
//        try {
//            br = new BufferedReader(new InputStreamReader(dataStream));
//            int i = 0;
//            while((s = br.readLine())!=null){
//                arrayListOfColleagues.add(i,s);
//                i++;
//            }
//
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if(br == null){
//                try{ br.close();}
//                catch (IOException ex){
//                    ex.printStackTrace();
//                }
//            }
//        }
//        System.out.println("+++" + arrayListOfColleagues);

        try {
            System.out.println("++++++++++++++");
            listOfColleagues = Files.readAllLines(Paths.get(justFile.toURI()));
        } catch (IOException e) {
            System.out.println("point of exeption");
            e.printStackTrace();
        }

        List<Employee> employees = new ArrayList<>();

        Map<String, Department> mapOfDepartments = new HashMap<>();

        for(int i = 0; i < listOfColleagues.size(); i++){
            Department newDepartment = new Department();
            Employee newEmployee = new Employee();
            String department = listOfColleagues.get(i).split("/")[1];
            newEmployee.setDepartment(department);
            newEmployee.setName(listOfColleagues.get(i).split("/")[0]);
            newEmployee.setSalary(BigDecimal.valueOf(Float.parseFloat(listOfColleagues.get(i).split("/")[2])));

            employees.add(newEmployee);
            if(mapOfDepartments.get(department)== null){
                newDepartment.setName(department);
                newDepartment.setSumOfSalary(BigDecimal.valueOf(Float.parseFloat(listOfColleagues.get(i).split("/")[2])));
                newDepartment.addNewEmployee(listOfColleagues.get(i).split("/")[0]);
                newDepartment.addEmployeeObject(newEmployee);
                mapOfDepartments.put(department, newDepartment);
            }else{
                mapOfDepartments.get(department).addEmployeeObject(newEmployee);
                mapOfDepartments.get(department).addNewEmployee(listOfColleagues.get(i).split("/")[0]);
                mapOfDepartments.get(department).setSumOfSalary(BigDecimal.valueOf(Float.
                        parseFloat(
                                listOfColleagues
                                        .get(i)
                                        .split("/")[2]))
                        .add(mapOfDepartments.get(department).getSumOfSalary()));

            }
        }
        System.out.println(""+ mapOfDepartments.get("LR").getListOfObjectEmployees().get(2).getName());
        Calculater calculater = new Calculater();
        System.out.println(calculater.findSubstitutionOfCounterparts(mapOfDepartments,employees));


    }
}
