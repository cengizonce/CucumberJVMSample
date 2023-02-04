package utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public WebDriver driver;
    public String date;
    public String startTime;
    public String finishTime;
    public String featureName;
    public static String testID;
    public static MongoCollection<Document> collection;

    public static void dbConnection(){
        MongoClient db = new MongoClient("localhost",27017);
        System.out.println("*-*-*-*-*-*-*-* DB baglantisi basarili *-*-*-*-*-*-*-*");
        MongoDatabase database= db.getDatabase("Irmak");
        collection =database.getCollection("Irmak1");
        System.out.println("*-*-*-*-*-*-*-* Collection baglantisi basarili *-*-*-*-*-*-*-*");
    }

    public static void testId(){
        System.out.println("*-*-*-*-*-*-*-* TestID degeri bulunacak *-*-*-*-*-*-*-*");

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

    public void setupChromeDriver(){
        System.out.println("********************** Driver Setup Ediliyor **********************");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.google.com");
        System.out.println("********************** Test basariyla basladi **********************");
    }

    public void ifScenarioFailTakeScreenShootAndAttachReport(Scenario scenario){
        if (scenario.isFailed()){
            System.out.println("********************** Test basarisiz sonlandi **********************");
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png",scenario.getName());

        }
        else {
            System.out.println("********************** Test basariyla sonlandi **********************");
        }
    }

    public void getStartDate(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        date = myDateObj.format(dateFormat);
        startTime = myDateObj.format(timeFormat);
        System.out.println("Scenario running date:"+date);
        System.out.println("Scenario running startTime:"+startTime);
    }

    public void getFinishTime(){
        LocalDateTime myDateObj2 = LocalDateTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        finishTime = myDateObj2.format(timeFormat);
        System.out.println("Scenario running finishTime:"+finishTime);
    }

    public void getFeatureName(Scenario scenario){
        String featureUri =scenario.getUri().toString();
        String[] parts = featureUri.split("/");

        for (String part:parts){
            if (part.contains("feature")){
                featureName = part;
            }
        }
    }

    public void printAndSaveScenarioDb(Scenario scenario){

        System.out.println("*-*-*-*-*-*-*-* Test sonuclari db ye yazilacak *-*-*-*-*-*-*-*");
        getFeatureName(scenario);
        System.out.println("id"+testID);
        System.out.println("ScenarioName"+scenario.getName());
        System.out.println("ScenarioStatus"+scenario.getStatus().toString());
        System.out.println("ScenarioFeature"+featureName);
        System.out.println("ScenarioRunningDate"+date);

        Document doc =new Document();
        doc.append("id",testID);
        doc.append("ScenarioName",scenario.getName());
        doc.append("ScenarioStatus",scenario.getStatus().toString());
        doc.append("ScenarioFeature",featureName);
        doc.append("ScenarioRunningDate",date);
        collection.insertOne(doc);
        System.out.println("*-*-*-*-*-*-*-* Test sonuclari db ye yazildi *-*-*-*-*-*-*-*");
    }

    public void driverClose(){
        System.out.println("********************** Driver kapatilacak **********************");
        driver.close();
        driver.quit();
        System.out.println("********************** Driver sonlandi **********************");
    }
}
