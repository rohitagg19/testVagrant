package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utilities.BrowserActions;
import utilities.Reporting;
import utilities.XMLReader;

public class NDTVHomePage {

	WebDriver driver;
	Reporting report;
	By ndtvLogo = By.className("ndtvlogo");
	By extraMenu = By.id("h_sub_menu");
	By weatherLink = By.linkText("WEATHER");
	By noThanksForNotificatins = By.linkText("No Thanks");
	BrowserActions browserAction;

	public NDTVHomePage(WebDriver driver, Reporting report) {
		this.driver = driver;
		this.report = report;
		browserAction = new BrowserActions(driver, report);

	}

	public void navigateToNDTVWebsite() {
		browserAction.loadURL(new XMLReader().readTagVal("UIURL"));
		WebElement notification = browserAction.getElement(noThanksForNotificatins, 10);
		if (notification != null)
			notification.click();
	}

	public void clickWeatherLink() {
		browserAction.getElement(extraMenu).click();
		WebElement weather = browserAction.getElement(weatherLink);
		browserAction.clickAction(weather);
		report.info("Click", "Clicked on Weather Link");
	}

}
