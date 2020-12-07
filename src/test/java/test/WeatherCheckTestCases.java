package test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObject.NDTVHomePage;
import pageObject.NDTVWeatherPage;
import pageObject.WeatherClass;
import pojoClass.TempratureComparator;
import pojoClass.TempratureObject;
import utilities.BrowserActions;
import utilities.Driver;
import utilities.Reporting;
import utilities.RestFunction;
import utilities.XMLReader;

public class WeatherCheckTestCases {

	XMLReader xmlReader = new XMLReader("configFile.xml");
	Driver driver = new Driver();
	Reporting report;
	BrowserActions browserActions;
	NDTVWeatherPage weatherPage;
	NDTVHomePage homePage;
	TempratureObject UiObject;
	TempratureObject APIObject;

	@BeforeClass
	public void getWeatherInformation() {
		driver.openBrowser();
		report = new Reporting(driver.getDriver());
		browserActions = new BrowserActions(driver.getDriver(), report);
		homePage = new NDTVHomePage(driver.getDriver(), report);
		weatherPage = new NDTVWeatherPage(driver.getDriver(), report);

	}

	@BeforeMethod
	public void startTest() {
		report.startTest("Weather Information");

	}

	@AfterMethod
	public void endTest() {
		report.endTestCase();
		driver.closeBrowser();
	}

	@Test
	public void exampleOfTestNgMaven() {

		APIObject = WeatherClass.getWeatherInformation(xmlReader.readTagVal("CityName"), xmlReader.readTagVal("Degree"),
				report);

		homePage.navigateToNDTVWebsite();
		homePage.clickWeatherLink();
		System.out.println("This is TestNG-Maven Example");

		weatherPage.validateOnWeatherPage();
		weatherPage.selectRequiredCity(xmlReader.readTagVal("CityName"));
		weatherPage.verifyTempratureVisible(xmlReader.readTagVal("CityName"));
		UiObject = weatherPage.getTempraturObject(xmlReader.readTagVal("CityName"));

		TempratureComparator obj = new TempratureComparator(report);

		if (obj.compare(APIObject, UiObject) == 0)
			report.pass("Object Comparision",
					"Temperature Comparision SuccessFull. Temprature difference with in Range");
		else
			report.fail("Object Comparision",
					"Temperature Comparision Failed. Temprature difference is not with in Range");

	}

}
