import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class UploadAttachment {

    public static TestcaseResultFetcher testcaseResultFetcher;

    public void uploadAttachmets() throws IOException {

        System.out.println("starting attachment upload into steps...");

        System.out.println("Enter testcycle Key===>\n ");

        Scanner sc = new Scanner(System.in);
        String testCycleKey = sc.next();

        testcaseResultFetcher.getTestResult(testCycleKey);

        ExcelReader excelReader = new ExcelReader();
        excelReader.readFromExcel();

    }

}
