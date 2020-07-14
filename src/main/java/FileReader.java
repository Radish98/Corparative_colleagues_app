import Objects.Department;
import Objects.Employee;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileReader {
    private static String absolutePath = "src/main/resources/list.txt";
    private static File justFile = new File (absolutePath);
    private static List<String> listOfColleagues;
//    private static List<String> justList;
//    private static String listOfColleagues = "list.txt";




    public static void main(String[] args) {
//        InputStream dataStream = FileReader.class.getResourceAsStream(listOfColleagues);
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
            newEmployee.setSalary(Float.parseFloat(listOfColleagues.get(i).split("/")[2]));

            employees.add(newEmployee);
            if(mapOfDepartments.get(department)== null){
                newDepartment.setName(department);
                newDepartment.setSumOfSalary(BigDecimal.valueOf(Float.parseFloat(listOfColleagues.get(i).split("/")[2])));
                newDepartment.addNewEmployee(listOfColleagues.get(i).split("/")[0]);
                mapOfDepartments.put(department, newDepartment);
            }else{
                mapOfDepartments.get(department).addNewEmployee(listOfColleagues.get(i).split("/")[0]);
                mapOfDepartments.get(department).setSumOfSalary(BigDecimal.valueOf(Float.
                        parseFloat(
                                listOfColleagues
                                        .get(i)
                                        .split("/")[2])).add(mapOfDepartments.get(department).getSumOfSalary()));
            }
        }

        Set<String> keySet = mapOfDepartments.keySet();
        for(int i = 0; i < employees.size(); i++){

            if(mapOfDepartments.get(employees.get(i).getDepartment()).getAverageSalary()
                    .compareTo(
                            BigDecimal.valueOf(
                                    employees.get(i).getSalary())) >= 0){
                for(String key : keySet){
                    if(mapOfDepartments.get(key).getAverageSalary()
                            .compareTo(
                                    BigDecimal.valueOf(employees.get(i).getSalary()))<=0)
                        System.out.println("Сотрудника " + employees.get(i).getName() + " можно перевести в " + key);
                }
            }
        }

    }
}
