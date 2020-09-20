import DTO.Response;
import DTO.TestCaseResult;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class TestcaseResultFetcher {

    static Map<String, String> keyToId = new HashMap<>();

    public void getTestResult(String testCycleKey) throws IOException {

//        StringBuffer url = new StringBuffer("http://localhost:8080/jira/rest/atm/1.0/");
//        url.append("testrun/").append(testCycleKey).append("/testresults");
//
//        CredentialsProvider provider = new BasicCredentialsProvider();
//
//        // todo: provide correct username and password for auth
//        UsernamePasswordCredentials credentials
//                = new UsernamePasswordCredentials("user1", "user1Pass");
//        provider.setCredentials(AuthScope.ANY, credentials);
//
//        HttpClient client = HttpClientBuilder.create()
//                    .setDefaultCredentialsProvider(provider)
//                    .build();
//
//        org.apache.http.HttpResponse response = client.execute(
//                new HttpGet(url.toString()));



        try {

            StringBuffer url1 = new StringBuffer("http://localhost:8080/jira/rest/atm/1.0/");
            url1.append("testrun/").append(testCycleKey).append("/testresults");

            URL url = new URL (url1.toString());
            String encoding = Base64.getEncoder().encodeToString(("test1:test1").getBytes("UTF-8"));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in   =
                    new BufferedReader (new InputStreamReader(content));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }


//        for(TestCaseResult testCaseResult : response1.getResponse2()){
//            String testcaseKey = testCaseResult.getKey();
//            String testId = testCaseResult.getId();
//            keyToId.put(testcaseKey, testId);
//        }


    }

    public Integer getTestResultId(String testcaseKey, String stepNumber, String attachmentPath) throws IOException {

        StringBuffer url = new StringBuffer("http://localhost:8080/jira/rest/atm/1.0/");
        url.append("testrun/").append(testcaseKey).append("/testresults");


        CredentialsProvider provider = new BasicCredentialsProvider();

        // todo: provide correct username and password for auth
        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials("user1", "user1Pass");
        provider.setCredentials(AuthScope.ANY, credentials);

        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();

        HttpResponse response = (HttpResponse) client.execute(
                new HttpGet(url.toString()));


        if(response.statusCode() == 200){
            Object object = response.body();
            Response response1 = (Response) object;
        }
        else{
            System.out.println("\n\nError in fetching testId, please check testKey===>\n\n");
        }

        return 0;

    }

}
