package stepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import io.cucumber.java.Scenario;
import io.cucumber.java.StepDefinitionAnnotation;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.masterthought.cucumber.json.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {

    public WebDriver driver;

    @Before
    public void before(){


        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.google.com");
        System.out.println("Test Basariyla Basladi");

    }

    @After
    public void after(Scenario scenario){
        if (scenario.isFailed()){
            System.out.println("Test Basarisiz");
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png",scenario.getName());
        }
        else {
            System.out.println("Test Basarili");
        }
        driver.close();
        driver.quit();
    }
}
