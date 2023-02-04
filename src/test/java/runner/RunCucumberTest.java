package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features ={"feature/deneme.feature"},
        glue ="stepDefinition",
        monochrome = true,
        plugin = {
                "pretty",
                "json:target/Cucumber.json"
        }
)
public class RunCucumberTest {
    //plugin ={"pretty","html:target/cucumber-html-report","json:target/cucumber.json"}
}
