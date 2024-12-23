package testRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "Feature/AmazonFeatureFile.feature",
				glue = "stepDefinition",
				stepNotifications = true,	
				monochrome = true,
				dryRun = false,
				plugin = {"pretty", 
						  "json:target/cucumber-json/reports.json",
						  "junit:target/cucumber-xml/Cucumber.xml",
						  "html:target/cucumber-html/reports.html"}
				)

public class AmazonTestRunner {

}