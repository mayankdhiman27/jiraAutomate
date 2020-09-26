import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class ExcelReader {

    private static TestcaseResultFetcher resultFetcher = new TestcaseResultFetcher();

    public void readFromExcel(Map<String, Integer> testCaseMap){
        try (CSVReader reader = new CSVReader(new FileReader("CSVDemo.csv"))){
            String [] tokens;

            while ((tokens = reader.readNext()) != null) {

                System.out.println(tokens[0] + "       " + tokens[1] + "    " + tokens[2] + "    " + tokens[3]);

                if(!tokens[0].equalsIgnoreCase("TestCase Key"))
                    resultFetcher.uploadAttachment(tokens[0], tokens[1], tokens[2] +  tokens[3], testCaseMap.get(tokens[0]));
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
