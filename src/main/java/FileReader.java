
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    private static String listOfColleagues = "list.txt";

    public static void main(String[] args) {
        InputStream dataStream = FileReader.class.getResourceAsStream(listOfColleagues);
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        String s;
        List<String> arrayListOfColleagues = new ArrayList<String>();
        try {
            br = new BufferedReader(new InputStreamReader(dataStream));
            int i = 0;
            while((s = br.readLine())!=null){
                arrayListOfColleagues.add(i,s);
                i++;
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br == null){
                try{ br.close();}
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        }
        System.out.println("+++" + arrayListOfColleagues);

    }
}
