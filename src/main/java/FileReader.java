
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {
//    private static String listOfColleagues = "list.txt";
    private static String absolutePath = "src/main/resources/list.txt";
    private static File justFile = new File (absolutePath);
    private static List<String> listOfColleagues;
//    private static List<String> justList;




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
            e.printStackTrace();
        }
        System.out.println("All list:" + listOfColleagues);
        
        for(int i = 0; i < listOfColleagues.size(); i++){
            String fio = listOfColleagues.get(i).split("/")[0];
            String department = listOfColleagues.get(i).split("/")[1];
            String salary = listOfColleagues.get(i).split("/")[2];

        }


    }
}
