package support;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber/report.html", "json:target/cucumber/report.json"},
        features = "src/test/resources/features",
        glue = {"definitions", "support"},
        tags = "@api" // was "@predefined", which matched no scenario, so nothing ever ran
)
public class TestRunner {
}
