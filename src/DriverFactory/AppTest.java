package DriverFactory;

import org.testng.annotations.Test;

public class AppTest 
{
	@Test
	public void kicStart()
	{
		try {
			
		DriverScript ds= new DriverScript();
		ds.startTest();
		}
		catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}

}
