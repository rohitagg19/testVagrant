package utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

public class BrowserActions {
	public WebDriver driver;

	public JavascriptExecutor jse;

	public Reporting report;

	public WebDriverWait wait;

	public Actions actions;

	public BrowserActions(WebDriver driver, Reporting report) {
		this.driver = driver;
		this.report = report;
		actions = new Actions(driver);
		jse = (JavascriptExecutor) driver;
	}

	/**
	 * USED TO LOAD A URL
	 * 
	 * @param url
	 */
	public void loadURL(String url) {
		if (driver != null) {
			driver.get(url);
			WaittoPageLoad();
			report.info("URL", "Successfully Navigated to " + url);
		}
	}

	/**
	 * USED TO WAIT UNTIL PAGE IS LOADED
	 */
	public void WaittoPageLoad() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * USED TO GET AN ELEMENT BASED ON LOCATOR PROVIDED
	 * 
	 * @param by
	 * @return
	 */
	public WebElement getElement(By by) {
		WebElement we = null;

		WebDriverWait waiter = new WebDriverWait(driver,
				Integer.parseInt(new XMLReader().readTagVal("MaxWaitForElement")));
		we = waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
		return we;
	}
	public WebElement getElement(By by,int timeSec) {
		WebElement we = null;

		WebDriverWait waiter = new WebDriverWait(driver,timeSec);
		we = waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
		return we;
	}

	// Click on Web Element

	public void click(WebElement element) {
		try {
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Click on Web Element using Action Class

	public void clickAction(WebElement element) {
		try {
			actions.moveToElement(element).click().build().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Click on Web Element using Action Class

	public void clickAction(WebElement element, String imagePath, String message) {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(element).click().build().perform();
			report.logStepToReport(LogStatus.INFO, "Click", message, imagePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Click on Web Element and log step in report

	public void click(WebElement element, String imagePath, String message) {
		try {
			scrollToElement(element);
			element.click();
			report.logStepToReport(LogStatus.INFO, "Click", message, imagePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Click on Web Element using JavaScriptExecuter and log step in report

	public void clickJS(WebElement element, String imagePath, String message) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			report.logStepToReport(LogStatus.INFO, "Click", message, imagePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Click on Web Element using JavaScriptExecuter

	public void clickJS(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Scroll to WebElement

	public void scrollToElement(WebElement element) {
		try {
			// ((JavascriptExecutor)
			// driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Actions actions = new Actions(driver);
			actions.moveToElement(element);
			actions.perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Get text of Web element

	public String getText(WebElement element) {
		String elementText = null;

		try {
			elementText = element.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return elementText;
	}

	// Set Text of Web element

	public void setText(WebElement element, String value) {
		try {
			element.sendKeys(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Set Text of Web element using java script executor

	public void setTextJS(WebElement element, String value) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Set Text of Web element using java script executor and log step in report

	public void setTextJS(WebElement element, String value, String imagePath, String message) {
		try {
			setTextJS(element, value);
			report.logStepToReport(LogStatus.INFO, "Input", message, imagePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Set Text of Web element and log step in report

	public void setText(WebElement element, String value, String imagePath, String message) {
		try {
			element.sendKeys(value);
			report.logStepToReport(LogStatus.INFO, "Input", message, imagePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Clear text from the field

	public void clearText(WebElement element) {
		try {
			element.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Wait for Web Element visibility and log step in report if element is not
	// visible

	public void waitForElementVisibility(WebElement element) {
		try {
			wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Wait for Web element Clickable

	public void waitForElement(WebElement element) {
		try {
			wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Scroll and set text on Web Element and log step in report

	public void ScrollAndSetText(WebElement element, String value, String imgPath, String message) {
		scrollToElement(element);
		setText(element, value, imgPath, message);
	}

	// Scroll and set text on Web Element and log step in report

	public void ScrollAndSetTextAction(WebElement element, String value, String imgPath, String message) {
		try {
			// ((JavascriptExecutor)
			// driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Actions actions = new Actions(driver);
			actions.moveToElement(element).sendKeys(value).build().perform();
			report.logStepToReport(LogStatus.INFO, "Input", message, report.imagePath);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	// Scroll and set text on Web Element using javascript executor and log step in
	// report

	public void ScrollAndSetTextJS(WebElement element, String value, String imgPath, String message) {
		scrollToElement(element);
		setTextJS(element, value, imgPath, message);
	}

	// Select value from Drop Down & log step in report

	public void selectFromDD(WebElement element, String value, String imagePath, String message) {
		try {

			Select select = new Select(element);

			select.selectByVisibleText(value);

			report.logStepToReport(LogStatus.INFO, "Select", message, imagePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Select value from Drop Down using Java script executor & log step in report

	public void selectFromDDJS(WebElement element, String value, String imagePath, String message) {
		try {
			((JavascriptExecutor) driver).executeScript(
					"var select = arguments[0]; for(var i = 0; i < select.options.length; i++){ if(select.options[i].text == arguments[1]){ select.options[i].selected = true; } }",
					element, value);

			report.logStepToReport(LogStatus.INFO, "Select", message, imagePath);
		} catch (Exception e) {
			report.logStepToReport(LogStatus.FAIL, "Select", message, imagePath);
			e.printStackTrace();
		}
	}

	// Select value from Drop Down using Java script executor, based on index & log
	// step in report

	public void selectByIndexFromDDJS(WebElement element, int index, String imagePath, String message) {
		try {

			((JavascriptExecutor) driver).executeScript(
					"var select = arguments[0]; select.options[arguments[1]].selected = true; ", element,
					String.valueOf(index));

			report.logStepToReport(LogStatus.INFO, "Select", message, imagePath);

		} catch (Exception e) {
			report.logStepToReport(LogStatus.FAIL, "Select", message, imagePath);
			e.printStackTrace();
		}
	}

	// Check for presence of alert on web page

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		} catch (Exception Ex) {
			return false;
		}
	}

	// Check for presence of web element

	public void isElementPresent(WebElement element, int timeout) {
		for (int i = 0; i < timeout; i++) {
			try {
				Thread.sleep(1000);
				boolean presence = element.isEnabled() || element.isDisplayed();
				if (presence) {
					return;
				}
			} catch (Exception Ex) {
			}
		}

		System.err.println("Timeout Exception for element -" + Thread.currentThread().getStackTrace());

	}

	// Select value from a list

	public void selectValueFromList(List<WebElement> elements, String valueToBeSelected, String filenamePath,
			String details) {

		boolean IsValueSelected = false;

		try {

			for (WebElement element : elements) {

				if (element.getText().contains(valueToBeSelected)) {

					IsValueSelected = true;

					try {
						clickJS(element);
						report.logStepToReport(LogStatus.INFO, "Select", valueToBeSelected + " selected", filenamePath);
					} catch (Exception e2) {
						e2.printStackTrace();
						break;
					}
				}
			}

			/*
			 * if (!IsValueSelected) { report.logStepToReport(LogStatus.FAIL, "Select",
			 * valueToBeSelected + " not found in list", filenamePath); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean selectValueFromDropdownUsingList(List<WebElement> elements, String valuetoselect, String imagePath,
			String message) {
		boolean isselected = false;

		try {
			for (WebElement e : elements) {
				if (e.getText().contains(valuetoselect)) {
					e.click();
					report.logStepToReport(LogStatus.INFO, "Click", message, imagePath);
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isselected;
	}

	public void selectFromList(List<WebElement> e, String textToselect, String imagePath, String message) {
		for (int i = 0; i < e.size(); i++) {
			if ((e.get(i).getText().contains(textToselect))) {
				click(e.get(i), imagePath, message);
				break;
			}
		}
	}

	public void mouseHoverOnElementUsingActions(WebElement element, String imagePath, String message) {
		try {
			actions = new Actions(driver);
			actions.moveToElement(element).perform();
			report.logStepToReport(LogStatus.INFO, "Click", message, imagePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
