package resources;

import broswerstackApis.BrowserStackApis;
import org.junit.Test;

public class RunnerTest {
    BrowserStackApis browserStackApis = new BrowserStackApis();

    @Test
    public void uploadAndTriggerXCUITest() throws Exception{
        browserStackApis.getBrowserStackCredentials();
        System.out.println("/********* STEP TO UPLOAD XCUITEST SUITE TO BROWSERSTACK *************/");

        browserStackApis.startlocal();
        System.out.println("Starting local");
        browserStackApis.uploadXCUITests();
        System.out.println("/********* STEP TO RUN XCUITEST SUITE ON BROWSERSTACK *************/");
        browserStackApis.triggerXCUITestsOnParallelMode();

        browserStackApis.stoplocal();
        System.out.println("stop local");
    }

}
