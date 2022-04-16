package DriverFactory;

import org.openqa.selenium.WebDriver;

import CommonFunLibrary.FunctionLibrary;
import Utilities.ExcelFileUtil;

public class DriverScript 
{
	public static WebDriver driver;
	String inputpath="./Hybrid_Framework/TestInput/HybridTest1.xlsx";
	String outputpath="./Hybrid_Framework/TestOutput/HybridResults1.xlsx";
	public void startTest()throws Throwable
	{
		//access Exelfileutil methods
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		//iterate all rows in MasterTestCases sheet
		for(int i=1;i<=xl.rowCount("MasteTestCases");i++)
		{
			String moduleStatus="";
			if(xl.getCellData("MasterTestCases", i,2).equalsIgnoreCase("y"))
			{
				//store corresponding sheet into TCModule
				String TCModule = xl.getCellData("MasterTestCases", i, 1);
				//iterate all in TCModule sheet
				for(int j=1;j<=xl.rowCount(TCModule);j++)
				{
					//read all cells from TCModule
					String Description=xl.getCellData(TCModule, j, 0);
					String ObjectType=xl.getCellData(TCModule, j, 1);
					String LocatorType=xl.getCellData(TCModule, j, 2);
					String LocatorValue=xl.getCellData(TCModule, j, 3);
					String TestData=xl.getCellData(TCModule, j, 4);
					try
					{
						if(ObjectType.equalsIgnoreCase("startBrowser"))
						{
							driver=FunctionLibrary.startBrowser();
						}
						else if(ObjectType.equalsIgnoreCase("openApplication"))
						{
							FunctionLibrary.openApplication(driver);
						}
						else if(ObjectType.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(driver, LocatorType, LocatorValue, TestData);
						}
						else if(ObjectType.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(driver, LocatorType, LocatorValue, TestData);
						}
						else if(ObjectType.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(driver, LocatorType, LocatorValue);
						}
						else if(ObjectType.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(driver, TestData);
						}
						else if(ObjectType.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser(driver);
						}
						//write as pass into status cell in TCModule
						xl.setCellData(TCModule, j, 5,"pass", outputpath);
						moduleStatus="True";
						
					}catch(Exception e)
					{
						System.out.println(e.getMessage());
						//write as fail into status cell in TCModule
						xl.setCellData(TCModule, j, 5, "fail", outputpath);
						moduleStatus="False";
					}
					if(moduleStatus.equalsIgnoreCase("True"))
					{
						xl.setCellData("MasterTestCases", i, 3, "pass", outputpath);
					}
					if(moduleStatus.equalsIgnoreCase("False"))
					{
						xl.setCellData("MasterTestCases", i, 3, "fail", outputpath);
					}
				}
			}
			else
			{
				//write as blocked which are flaged to N
				xl.setCellData("MasterTestCases", i, 3, "Blocked", outputpath);
			}
		}
		
	}

}
