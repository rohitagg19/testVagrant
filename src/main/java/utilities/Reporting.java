package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reporting {

	public String folderName, screenshotsFolder, folder, folderpath, uniqueID, PROJECT_PATH;

	boolean screenshotRequired;
	public WebDriver driver;

	public ExtentReports extentReports;

	public ExtentTest test;

	public ExtentTest Summarytest;

	Date date = new Date();

	public String imagePath = "";

	DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ss_SSaa");

	public Reporting(WebDriver driver) {
		uniqueID = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		PROJECT_PATH = new File("").getAbsolutePath();
		this.driver = driver;
		createFolder(uniqueID);
		screenshotRequired = new XMLReader().readTagVal("screenShots").equalsIgnoreCase("yes");
		extentReports = new ExtentReports(PROJECT_PATH + File.separator + "Reports" + File.separator + uniqueID
				+ File.separator + "WeatherAutomationReport.html");

		// extentReports.loadConfig(new File("extent-config.xml"));

		extentReports.flush();

	}

	/** Create Reporting and Screenshot folders */

	public String createFolder(String Uniqueid) {
		folderName = "";

		try {

			// Check Reports folder create if not created

			File file = new File(PROJECT_PATH + File.separator + "Reports");

			if (!file.exists()) {
				if (file.mkdir()) {
				}
			}

			// Check TimeStamp folder create if not created

			folderName = PROJECT_PATH + File.separator + "Reports" + File.separator + Uniqueid;
			file = new File(folderName);

			if (!file.exists()) {
				if (file.mkdir()) {
				}
			}

			screenshotsFolder = folderName;

		} catch (Exception e) {
			e.printStackTrace();
		}

		setFolderpath(folderName);
		setfolder(Uniqueid);
		return folderName;
	}

	/** Set Reporting Folder Path */

	public void setFolderpath(String folderpath) {
		this.folderpath = folderpath;
		imagePath = folderpath + File.separator + "screenshots" + File.separator;
	}

	/** Set Reporting Folder */

	public void setfolder(String foldername) {
		this.folder = foldername;
	}

	/** Get screenshot from Screenshots folder */

	public String getscreenshot(String fileName) {
		DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ss_SSaa");
		Date date = new Date();
		String screenshotfile = dateFormat.format(date) + ".png";

		try {
			org.apache.commons.io.FileUtils.copyFile(((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE),
					new File(fileName + screenshotfile));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return "screenshots/" + screenshotfile;
	}

	/** Log step into the extent report */

	public void logStepToReport(LogStatus status, String stepname, String Details, String filenamePath) {
		if (!filenamePath.equalsIgnoreCase("")) {

			try {
				test.log(status, "<font color=800080> <b> " + stepname,
						"<div align='left' style='float:left'><a " + NewWindowPopUpHTMLCode() + " target='_blank' href="
								+ getscreenshot(imagePath) + ">" + Details + "</a></div>");

			} catch (Exception e) {
				test.log(status, "<font color=800080> <b> " + stepname,
						Details + "<div align='right' style='float:right'>Unable to take screenshot</div>");
			}

		} else {
			test.log(status, stepname, Details);
		}

		extentReports.flush();
	}

	/** Assert Expected condition with the Actual Condition */

	public void assertThat(boolean status, String passMessage, String failMessage) {

		if (!status) {

			String line2 = " Expected - " + passMessage + "<br> <b> <font color='red'> Actual - " + failMessage;

			test.log(LogStatus.FAIL, "<font color=800080> <b> Verification Point",
					line2 + "<div align='right' style='float:right'><a " + NewWindowPopUpHTMLCode()
							+ " target='_blank' href=" + getscreenshot(imagePath) + ">Screenshot</a></div>");

		} else {

			String line2 = " Expected - " + passMessage + "<br> <b> <font color='green'> Actual - " + passMessage;

			test.log(LogStatus.PASS, "<font color=800080> <b> Verification Point",
					line2 + "<div align='right' style='float:right'><a " + NewWindowPopUpHTMLCode()
							+ " target='_blank' href=" + getscreenshot(imagePath) + ">Screenshot</a></div>");

		}

		extentReports.flush();

	}

	/** End Test Cases */

	public void endTestCase() {
		extentReports.endTest(test);
		extentReports.flush();
	}

	/*
	 * public static void copyFile(String fromLocation, String toLocation) throws
	 * Exception {
	 * 
	 * FileInputStream instream = null; FileOutputStream outstream = null;
	 * 
	 * try {
	 * 
	 * File infile = new File(fromLocation); File outfile = new File(toLocation);
	 * instream = new FileInputStream(infile); outstream = new
	 * FileOutputStream(outfile); byte[] buffer = new byte[1024];
	 * 
	 * int length;
	 * 
	 * // Copy contents from input stream to output stream using read and write
	 * methods
	 * 
	 * while ((length = instream.read(buffer)) > 0) { outstream.write(buffer, 0,
	 * length); }
	 * 
	 * // Close input / output file streams
	 * 
	 * instream.close(); outstream.close();
	 * 
	 * } catch (Exception ioe) { ioe.printStackTrace(); } }
	 */

	/** Generate attribute tag to open mentioned file path in new pop up window */

	public String NewWindowPopUpHTMLCode() {
		return "onclick = \"window.open(this.href,'newwindow', 'width=1000 ,height=500');return false;\"";
	}

	public void startTest(String testName) {
		try {
			ExtentTest newTest = extentReports
					.startTest("<font face='Verdana' color=#805500> <bold>" + testName + "</bold> </font>");
			extentReports.flush();
			test = newTest;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pass(String StepName, String Description) {

		StepName = "<font color='#43C6DB'>" + StepName;
		if (screenshotRequired)
			Description = "<div align='left' style='float:left'><a " + NewWindowPopUpHTMLCode()
					+ " target='_blank' href=" + getscreenshot(imagePath) + ">" + Description + "</a></div>";
		test.log(LogStatus.PASS, StepName, "<b><font color='Green'>" + Description);

		extentReports.flush();
	}

	public void fail(String StepName, String Description) {

		StepName = "<font color='#43C6DB'>" + StepName;

		if (screenshotRequired)
			Description = "<div align='left' style='float:left'><a " + NewWindowPopUpHTMLCode()
					+ " target='_blank' href=" + getscreenshot(imagePath) + ">" + Description + "</a></div>";
		test.log(LogStatus.FAIL, StepName, "<b><font color='red'>" + Description);
		extentReports.flush();
	}

	public void info(String StepName, String Description) {
		if (screenshotRequired)
			Description = "<div align='left' style='float:left'><a " + NewWindowPopUpHTMLCode()
					+ " target='_blank' href=" + getscreenshot(imagePath) + ">" + Description + "</a></div>";
		StepName = "<font color='#43C6DB'>" + StepName;

		test.log(LogStatus.INFO, StepName, Description);
		extentReports.flush();
	}

	public void pass(String StepName, String Description, boolean screenshotRequired) {

		StepName = "<font color='#43C6DB'>" + StepName;
		if (screenshotRequired)
			Description = "<div align='left' style='float:left'><a " + NewWindowPopUpHTMLCode()
					+ " target='_blank' href=" + getscreenshot(imagePath) + ">" + Description + "</a></div>";
		test.log(LogStatus.PASS, StepName, "<b><font color='Green'>" + Description);

		extentReports.flush();
	}

	public void fail(String StepName, String Description, boolean screenshotRequired) {

		StepName = "<font color='#43C6DB'>" + StepName;

		if (screenshotRequired)
			Description = "<div align='left' style='float:left'><a " + NewWindowPopUpHTMLCode()
					+ " target='_blank' href=" + getscreenshot(imagePath) + ">" + Description + "</a></div>";
		test.log(LogStatus.FAIL, StepName, "<b><font color='red'>" + Description);
		extentReports.flush();
	}

	public void info(String StepName, String Description, boolean screenshotRequired) {
		if (screenshotRequired)
			Description = "<div align='left' style='float:left'><a " + NewWindowPopUpHTMLCode()
					+ " target='_blank' href=" + getscreenshot(imagePath) + ">" + Description + "</a></div>";
		StepName = "<font color='#43C6DB'>" + StepName;

		test.log(LogStatus.INFO, StepName, Description);
		extentReports.flush();
	}

	public ExtentTest getCurrentExtentTest() {
		return this.test;
	}

	public void ReportSuccessResponse(String currentResponseFileRelativePath) {

		try {
			pass("Response File Path",
					"Response is verified successfully" + "<div align='right' style='float:right'><a "
							+ NewWindowPopUpHTMLCode() + " target='_blank' href=" + currentResponseFileRelativePath
							+ ">Response Json</a></div>",
					false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * FUNCTION - Report failed response message
	 * 
	 * @param currentResponseFileRelativePath - Path of response file in String
	 *                                        format
	 */

	public void ReportFailedResponse(String currentResponseFileRelativePath) {

		try {
			fail("Response File Path",
					"Response failed in verification" + "<div align='right' style='float:right'><a "
							+ NewWindowPopUpHTMLCode() + " target='_blank' href=" + currentResponseFileRelativePath
							+ ">Response Json</a></div>",
					false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
