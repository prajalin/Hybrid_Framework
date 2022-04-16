package CommonFunLibrary;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.PropertyFileUtil;

public class FunctionLibrary 
{
	public static WebDriver driver;
	//method for launching browser
	public static WebDriver startBrowser()throws Throwable
	{
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver","./CommonDrivers/chromedriver.exe");
			driver=new ChromeDriver();
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
		}
		else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver","./CommonDrivers/geckodriver.exe");
			driver= new FirefoxDriver();
			driver.manage().deleteAllCookies();
		}
		else
		{
			System.out.println("Browser value is not matching");
		}
		return driver;
	}
	//method for launching url
	public static void openApplication(WebDriver driver)throws Throwable
	{
		driver.get(PropertyFileUtil.getValueForKey("url"));
	}
	//method for text boxes
	public static void typeAction(WebDriver driver,String locatortype,String locatorvalue,String testdata)
	{
		if(locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(locatorvalue)).clear();
			driver.findElement(By.name(locatorvalue)).sendKeys(testdata);
			
		}
		else if(locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorvalue)).clear();
			driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
		}
		else if(locatortype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(locatorvalue)).clear();
			driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);
		}
		else
		{
			System.out.println("unable to execute typeAction method");
		}
	}
	//method for waitForElement 
	public static void waitForElement(WebDriver driver,String locatortype,String locatorvalue,String waittime)
	{
		WebDriverWait myWait= new WebDriverWait(driver,Integer.parseInt(waittime));
		if(locatortype.equalsIgnoreCase("name"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
		}
		else if(locatortype.equalsIgnoreCase("id"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
		}
		else if(locatortype.equalsIgnoreCase("xpath"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
		}
		else
		{
			System.out.println("unable to execute waitForElement method");
		}
	}
	//method for buttons(clickAction)
	public static void clickAction(WebDriver driver,String locatortype,String locatorvalue)
	{
		if(locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(locatorvalue)).click();
		}
		else if(locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorvalue)).sendKeys(Keys.ENTER);
		}
		else if(locatortype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(locatorvalue)).click();
		}
		else
		{
			System.out.println("unable to execute clickAction method");
		}
	}
	//method for validate title
	public static void validateTitle(WebDriver driver,String expectedtitle)
	{
		String actualtitle=driver.getTitle();
		try
		{
			Assert.assertEquals(actualtitle, expectedtitle,"Title is not matching");
			
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}
	//method for closeBrowser
	public static void closeBrowser(WebDriver driver)
	{
		driver.close();
	}
	//method for writing suppier number into notepad
	public static void captureData(WebDriver driver,String locatortype,String locatorvalue)throws Throwable
	{
		String suppliernumber="";
		if(locatortype.equalsIgnoreCase("name"))
		{
			suppliernumber=driver.findElement(By.name(locatorvalue)).getAttribute("value");
		}
		else if(locatortype.equalsIgnoreCase("xpath"))
		{
			suppliernumber=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
			
		}
		FileWriter fw= new FileWriter("./CaptureData/supplier.txt");
		BufferedWriter bw= new BufferedWriter(fw);
		bw.write(suppliernumber);
		bw.flush();
		bw.close();
	}
	
}
