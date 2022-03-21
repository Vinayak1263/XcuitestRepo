package broswerstackApis;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.File;

public class BrowserStackApis {

    String testUrl;
    String buildId;
    String browserStackUserName;
    String browserStackPassword;

    public void uploadXCUITests(){
        RestAssured.baseURI = "https://api-cloud.browserstack.com/app-automate/";
        String basePath = "xcuitest/test-suite";
        RequestSpecification request = RestAssured.given().log().all();
        request.contentType("multipart/form-data");
        request.auth().preemptive().basic(browserStackUserName, browserStackPassword);
        request.multiPart("file", new File(System.getProperty("user.dir")+"/src/test/java/resources/WooliesGOUITests-Runner.zip"), "multipart/form-data");
        Response response = request.post(basePath);
        System.out.println("Sent request to upload xcuitest suite: "+response.getStatusCode());
        System.out.println("Response body is "+response.asString());
        final int statusCode = response.getStatusCode();
        if (statusCode >= 400) {
            System.err.println("Failed to upload wooliesGO app. Response status code: " + statusCode);
        }
        else{
            JsonPath js = response.jsonPath();
            testUrl = js.getString("test_url");
            System.out.println("Test url is :"+testUrl);
        }
    }

    public void triggerXCUITestsOnParallelMode(){

        // HashMap<String, String> bsLocalArgs = new HashMap<String, String>();
        // bsLocalArgs.put("key",browserStackPassword);
      
        // // Starts the Local instance with the required arguments
        // bsLocal.start(bsLocalArgs);
      
        // // Check if BrowserStack local instance is running
        // System.out.println("Local is running ?"+bsLocal.isRunning());

        RestAssured.baseURI = "https://api-cloud.browserstack.com/app-automate/";
        String basePath = "xcuitest/v2/build";
        RequestSpecification request = RestAssured.given().log().all();
        request.contentType(ContentType.JSON);
        request.auth().preemptive().basic(browserStackUserName, browserStackPassword);
        //request.body(new File(System.getProperty("user.dir")+"/src/test/java/resources/testbody.json"));
        request.body("{\"shards\": {\"numberOfShards\": 10, \"deviceSelection\": \"any\", \"mapping\": [{\"name\": \"Shard 1\", \"strategy\": \"only-testing\", \"values\": [\"WooliesGOUITests/End2EndNormalRouteTest2\"]}, {\"name\": \"Shard 2\", \"strategy\": \"only-testing\", \"values\": [\"WooliesGOUITests/End2EndNormalRouteTest4\"]}, {\"name\": \"Shard 3\", \"strategy\": \"only-testing\", \"values\": [\"WooliesGOUITests/End2EndNormalRouteTest5\"]}, {\"name\": \"Shard 4\", \"strategy\": \"only-testing\", \"values\": [\"WooliesGOUITests/End2EndNormalRouteTest6\"]}, {\"name\": \"Shard 5\", \"strategy\": \"only-testing\", \"values\": [\"WooliesGOUITests/EndToEndManualRouteTest\"]}, {\"name\": \"Shard 6\", \"strategy\": \"only-testing\", \"values\": [\"WooliesGOUITests/EndToEndMultiLoadRouteTest\"]}, {\"name\": \"Shard 7\", \"strategy\": \"only-testing\", \"values\": [\"WooliesGOUITests/End2EndNominatedIdNormalRouteTest\"]}, {\"name\": \"Shard 8\", \"strategy\": \"only-testing\", \"values\": [\"WooliesGOUITests/EndToEndNominatedIdManualRouteTest\"]}, {\"name\": \"Shard 9\", \"strategy\": \"only-testing\", \"values\": [\"WooliesGOUITests/EndToEndSecurityOnlyRouteTest\"]}, {\"name\": \"Shard 10\", \"strategy\": \"only-testing\", \"values\": [\"WooliesGOUITests/BottomBarTest\"]}]}, \"deviceLogs\" : \"true\", \"networkLogs\": \"true\", \"local\": \"true\", \"devices\": [\"iPhone 11-15\", \"iPhone XS-14\", \"iPhone 12 Pro-14\", \"iPhone 12-14\", \"iPhone 13 Mini-15\", \"iPhone 12 Mini-14\", \"iPhone XS-15\", \"iPhone 13-15\"], \"app\":\""+"bs://b98db739fce165c436b1c3c3993b54d1c4accfd3"+"\", \"testSuite\":\""+testUrl+"\"}");
        Response response = request.post(basePath);
        System.out.println("Sent request to trigger automation tests : "+response.getStatusCode());
        System.out.println("Response body is "+response.asString());
        final int statusCode = response.getStatusCode();
        if (statusCode >= 400) {
            System.err.println("Test execution Build failed. Response status code: " + statusCode);
        }
        else{
            JsonPath js = response.jsonPath();
            buildId = js.getString("build_id");
            System.out.println("Build id is :"+buildId);
        }

        // bsLocal.stop();    
    }

    public void getBrowserStackCredentials(){
        browserStackUserName = System.getenv("BROWSERSTACK_USERNAME");
        // if (browserStackUserName.contains("azure")){
        //     System.out.println("Usernmae before"+browserStackUserName);
        //     System.out.println("I'm inside this");

        //     browserStackUserName=browserStackUserName.replace("-azure","");
        //     System.out.println("Usernmae after"+browserStackUserName);
        // }
        browserStackPassword = System.getenv("BROWSERSTACK_ACCESS_KEY");
    }

 
}
