import FileEditors.FileReader;

import java.io.File;

public class EnterPoint {

    private static String absolutePath = "src/main/resources/list.txt";
    private static File justFile = new File (absolutePath);
//    private static List<String> justList;
//    private static String listOfColleagues = "list.txt";




    public static void main(String[] args) {
        FileReader fileReader = new FileReader();
        fileReader.readTheFile(justFile);

    }
}


