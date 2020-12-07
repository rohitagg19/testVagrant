package pageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pojoClass.TempratureObject;
import utilities.BrowserActions;
import utilities.Reporting;

public class NDTVWeatherPage {

	WebDriver driver;
	Reporting report;
	BrowserActions browserAction;
	String xpathForInputBox = "//input[contains(@id,'REPLACE')]";
	By weatherInfoHeader = By.className("infoHolder");
	By allVisibleTemparature = By.className("cityText");
	By conditionHeading = By.xpath("//span[@class='heading'][1]");
	By windHeading = By.xpath("//span[@class='heading'][2]");
	By tempInCelHeading = By.xpath("//span[@class='heading'][4]");
	By tempInFahrenheit = By.xpath("//span[@class='heading'][5]");

	public NDTVWeatherPage(WebDriver driver, Reporting report) {
		this.driver = driver;
		this.report = report;
		browserAction = new BrowserActions(driver, report);
	}

	public boolean validateOnWeatherPage() {
		if (browserAction.getElement(weatherInfoHeader) != null) {
			report.pass("Validate", "On Weather Informaiton Page");
			return true;
		} else {
			report.fail("Validate", "Not on Weather Information Page");
			return false;
		}
	}

	public void selectRequiredCity(String city) {
		city = city.substring(0, 1).toUpperCase() + city.substring(1, city.length());
		WebElement cityInput = browserAction.getElement(By.xpath(getXpathOfCheckBoxForCity(city)));
		if (cityInput == null) {
			report.fail("Selecting City", city + " Not found in Search list");
		} else {
			browserAction.scrollToElement(cityInput);
			if (!cityInput.isSelected())
				browserAction.clickAction(cityInput);
			report.pass("Selecting City", city + " Found and selected");
		}
	}

	public String getXpathOfCheckBoxForCity(String city) {
		return xpathForInputBox.replaceAll("REPLACE", city);
	}

	public boolean verifyTempratureVisible(String city) {
		List<WebElement> listOfCities = driver.findElements(allVisibleTemparature);
		for (WebElement cityName : listOfCities) {
			if (cityName.getText().toLowerCase().contains(city.toLowerCase())) {
				report.pass("Validate", "Temperature for the City is visible on map");
				cityName.click();
				return true;
			}

		}
		report.fail("Validate", "Temperature for the City is not visible on map");
		return false;
	}

	public TempratureObject getTempraturObject(String city) {

		TempratureObject cityTemp = new TempratureObject();
		cityTemp.setCityName(city);

		String tempText = browserAction.getElement(conditionHeading).getText();
		cityTemp.setCondition(tempText.split(":")[1].trim());

		tempText = browserAction.getElement(windHeading).getText();
		cityTemp.setWindSpeed(Float.parseFloat(tempText.split(":")[1].split("KPH")[0].trim()));

		tempText = browserAction.getElement(tempInCelHeading).getText();
		cityTemp.setTempInCell(Integer.parseInt(tempText.split(":")[1].trim()));

		tempText = browserAction.getElement(tempInFahrenheit).getText();
		cityTemp.setTempInFahren(Integer.parseInt(tempText.split(":")[1].trim()));

		return cityTemp;
	}

}
