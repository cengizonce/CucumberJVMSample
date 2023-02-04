package stepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import utils.Utils;

public class BaseTest extends Utils{

    @BeforeAll
    public static void beforeAlll(){
        System.out.println("********************** Test basliyor **********************");
        dbConnection();
        testId();
    }

    @Before
    public void before(){
        setupChromeDriver();
        getStartDate();
    }

    @After
    public void after(Scenario scenario){
        System.out.println("********************** Test sonlandi **********************");
        getFinishTime();
        ifScenarioFailTakeScreenShootAndAttachReport(scenario);
        printAndSaveScenarioDb(scenario);
        driverClose();
    }

}
