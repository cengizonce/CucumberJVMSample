package stepDefinition;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.ReportParser;
import org.apache.maven.model.ReportSet;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {

    public WebDriver driver;
    public static MongoCollection<Document> collection;
    public static String testID;


    @BeforeAll
    public static void beforeAlll(){
        System.out.println("********************** Test basliyor **********************");
        MongoClient db = new MongoClient("localhost",27017);
        System.out.println("*-*-*-*-*-*-*-* DB baglantisi basarili *-*-*-*-*-*-*-*");
        MongoDatabase database= db.getDatabase("Irmak");
        collection =database.getCollection("Irmak1");
        System.out.println("*-*-*-*-*-*-*-* Collection baglantisi basarili *-*-*-*-*-*-*-*");

        testID = System.getProperty("testID");

        if (testID.isEmpty()){
            System.out.println("Test ID Degeri bulunamadi !");
            Assertions.fail();
        }
        else{
            System.out.println("*-*-*-*-*-*-*-*-*-* TEST ID BULUNDU :["+testID+"] *-*-*-*-*-*-*-*-*-*");
            collection.deleteMany(Filters.eq("id",testID));
        }

    }

    @Before
    public void before(){
        System.out.println("********************** Driver Setup Ediliyor **********************");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.google.com");
        System.out.println("********************** Test basariyla basladi **********************");
    }

    @After
    public void after(Scenario scenario){
        System.out.println("********************** Test sonlandi **********************");

        if (scenario.isFailed()){
            System.out.println("********************** Test basarisiz sonlandi **********************");
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png",scenario.getName());

        }
        else {
            System.out.println("********************** Test basariyla sonlandi **********************");
        }

        System.out.println("*-*-*-*-*-*-*-* Test sonuclari db ye yazilacak *-*-*-*-*-*-*-*");
        Document doc =new Document();
        doc.append("id",testID);
        doc.append("ScenarioName",scenario.getName());
        doc.append("ScenarioStatus",scenario.getStatus().toString());
        doc.append("ScenarioDeneme","Cengo123");
        collection.insertOne(doc);
        System.out.println(doc);
        System.out.println("*-*-*-*-*-*-*-* Test sonuclari db ye yazildi *-*-*-*-*-*-*-*");

        driver.close();
        driver.quit();
        System.out.println("********************** Driver sonlandi **********************");
    }
}
