package cucumber.Options;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "./src/test/java/features/catImage.feature",
        glue = {"stepDefinitions"},
        plugin = "json:target/jsonReports/cucumber-report.json",
        dryRun = false
)
public class TestRunner {
}
