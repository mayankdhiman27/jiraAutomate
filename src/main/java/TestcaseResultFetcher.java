import DTO.Response;
import DTO.TestCaseResult;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class TestcaseResultFetcher {

    static Map<String, String> keyToId = new HashMap<>();

    public void getTestResult(String testCycleKey) throws IOException {

        StringBuffer url = new StringBuffer("http://localhost:8080/jira/rest/atm/1.0/");
        url.append("testrun/").append(testCycleKey).append("/testresults");

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


        Response response1 = new Response();

        if(response.statusCode() == 200){
            Object object = response.body();
            response1 = (Response) object;
        }
        else{
            System.out.println("\n\nError in fetching testId, please check testKey===>\n\n");
            return;
        }

        for(TestCaseResult testCaseResult : response1.getResponse2()){
            String testcaseKey = testCaseResult.getKey();
            String testId = testCaseResult.getId();
            keyToId.put(testcaseKey, testId);
        }


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
