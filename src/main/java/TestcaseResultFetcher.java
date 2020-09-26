import DTO.TestCycle;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class TestcaseResultFetcher {

    static Map<String, String> keyToId = new HashMap<>();

    private OutputStream outputStream;

    public Map<String, Integer> getTestResult(String testCycleKey) throws IOException {

        Object response1 = null;
        Map<String, Integer> testCaseResponseMap = new HashMap<>();
        try {

            StringBuffer url1 = new StringBuffer("http://localhost:8080/rest/atm/1.0/");
            url1.append("testrun/").append(testCycleKey).append("/testresults");

            URL url = new URL (url1.toString());
            String encoding = Base64.getEncoder().encodeToString(("mayankdhiman27:Dhiman@1005").getBytes("UTF-8"));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            int status = connection.getResponseCode();
            if(status == 200){
                String response = getResponseFromInputStream(connection.getInputStream()).toString();
                response1 = new Gson().fromJson(response, Object.class);
                String jsonString = new Gson().toJson(response1);
                TestCycle[] testCycle = new Gson().fromJson(jsonString, TestCycle[].class);
                testCaseResponseMap = converToMap(testCycle);
                System.out.println("test");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return testCaseResponseMap;

    }


    private Map<String, Integer> converToMap(TestCycle[] testCycles){

        Map<String, Integer> testCaseMap = new HashMap<>();

        for(int i = 0; i < testCycles.length; i++){
            testCaseMap.put(testCycles[i].getTestCaseKey(), testCycles[i].getId());
        }

        return testCaseMap;

    }



    private StringBuilder getResponseFromInputStream(InputStream is) throws IOException {
        String line = "";
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        // Read response until the end
        while ((line = rd.readLine()) != null) {
            total.append(line);
        }

        // Return full string
        return total;
    }


    public void uploadAttachment(String testcaseKey, String stepNumber, String attachmentPath, Integer testId) throws IOException {

        try {

            File file = new File(attachmentPath);
            // /testresult/{testResultId}/step/{stepIndex}/attachments
            StringBuffer url1 = new StringBuffer("http://localhost:8080/rest/atm/1.0/");
            url1.append("testresult/").append(testId).append("/step/").append(stepNumber).append("/attachments");

            URL url = new URL (url1.toString());
            String encoding = Base64.getEncoder().encodeToString(("mayankdhiman27:Dhiman@1005").getBytes("UTF-8"));

            HttpPost post = new HttpPost(String.valueOf(url));
            post.setHeader("Authorization", "Basic " + encoding);

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.addPart("file", new FileBody(file));

            post.setEntity(entityBuilder.build());

            HttpResponse response = HttpClients.createDefault().execute(post);

            int status = response.getStatusLine().getStatusCode();


            if(status == 201){
                System.out.println("Attached");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }


}
