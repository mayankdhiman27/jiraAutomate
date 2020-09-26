import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UploadAttachment {

    public static TestcaseResultFetcher testcaseResultFetcher = new TestcaseResultFetcher();

    public void uploadAttachmets() throws IOException {

        System.out.println("starting attachment upload into steps...");

        System.out.println("Enter testcycle Key===>\n ");

        Scanner sc = new Scanner(System.in);
        String testCycleKey = sc.next();

        Map<String, Integer> testCaseMap = testcaseResultFetcher.getTestResult(testCycleKey);

        if(testCaseMap.size() > 0){
            ExcelReader excelReader = new ExcelReader();
            excelReader.readFromExcel(testCaseMap);
        }
        else {
            System.out.println("No testcase exists for the given test cycle.");
        }

    }

//    public Map<String, Integer> objectToMapOfTestCaseKey(Object response){
//
//        Map<String, Integer> testCaseMap = new HashMap<>();
//
////        for(int i = 0; i < ){
////
////        }
//
//    }

}
