import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class ExcelReader {

    private static TestcaseResultFetcher resultFetcher = new TestcaseResultFetcher();

    public void readFromExcel(){
        try (CSVReader reader = new CSVReader(new FileReader("CSVDemo.csv"))){
            String [] tokens;

            while ((tokens = reader.readNext()) != null) {

                System.out.println(tokens[0] + "       " + tokens[1] + "    " + tokens[2] + "    " + tokens[3]);

                if(!tokens[0].equalsIgnoreCase("TestCase Key"))
                    resultFetcher.getTestResultId(tokens[0], tokens[1], tokens[2] +  tokens[3]);
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
