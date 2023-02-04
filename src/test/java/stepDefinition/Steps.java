package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;


public class Steps {


    @Given("Ilk senaryo adimi")
    public void a() {
        System.out.println("Senaryo birinci adimm");
    }
    @When("Ikinci senaryo adimi")
    public void b() {
        System.out.println("Senaryo ikinci adimm");

    }
    @When("Ucuncu senaryo adimi")
    public void c() {
        System.out.println("Senaryo ucuncu adimm");
        Assertions.fail();

    }
    @Given("Dorduncu senaryo adimi")
    public void d() {
        System.out.println("Senaryo dorduncu adimm");
    }

}
