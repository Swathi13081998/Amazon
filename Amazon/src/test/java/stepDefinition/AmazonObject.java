package stepDefinition;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AmazonObject {

	WebDriver driver;

    public AmazonObject(WebDriver driver) {
        this.driver = driver;
    }

    protected WebElement signinButton() {
        return driver.findElement(By.xpath("//span[contains(text(),'Hello, sign in')]"));
    }

	protected WebElement Email() {
        return driver.findElement(By.xpath("//input[@type='email']"));
    }
	
	protected WebElement Continue() {
        return driver.findElement(By.xpath("//input[@id='continue']"));
    }
	
	protected WebElement Password() {
        return driver.findElement(By.xpath("//input[@type='password']"));
    }
	
	protected WebElement SignIn() {
        return driver.findElement(By.xpath("//input[@id='signInSubmit']"));
    }
	
	protected WebElement VerifyHello() {
        return driver.findElement(By.xpath("//span[contains(text(),'Hello, Swathi')]"));
    }
	
	protected WebElement SearchBox() {
        return driver.findElement(By.xpath("//input[@type='text']"));
    }
	
	protected WebElement Select() {
        return driver.findElement(By.xpath("//div[@id='sac-suggestion-row-1']"));
    }
	
	protected WebElement AddToCart() {
        return driver.findElement(By.xpath("(//button[contains(text(),'Add to cart')])[1]"));
    }
	
	protected WebElement CartValue() {
        return driver.findElement(By.xpath("//span[@id='nav-cart-count']"));
    }

	protected WebElement ProductList() {
		return driver.findElement(By.xpath("//div[@id='sac-autocomplete-results-container']"));
	}

	

}
