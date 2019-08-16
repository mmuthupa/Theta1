package operations;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
//import com.google.common.base.Function;   //  Commented for using Selenium 2.53
import driverScript.ExecuteTest;
import excelReader.ReadExcel;
import excelReader.UpdateResult;
import objectRepository.LoadPropertySingleton;

public class UIOperations extends ExecuteTest implements Runnable {
	public static WebDriver driver = null;
	public static String fileName_E;
	public static WebElement element;
	public static List<WebElement> elements;
	public int elementval = 0;
	public static JavascriptExecutor js;
	public static String imagedir = "TestEvidenceImage";
	public static int imagecounter = 1;
	public static String screenshotfilepath;
	static DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
	ExecuteTest objExecuteTest = new ExecuteTest();
	UpdateResult objUpdateResult = new UpdateResult();
	public static boolean alertFlag = false;
	public static boolean frameFlag = false;
	public static boolean loginAlertCheck = false;
	public static boolean CASaveAlertFlag = false;
	public HSSFSheet resultsheet = null;
	public static String alertMessage = "";
	public static Alert alert;
	public static String Pass_SnapShot = LoadPropertySingleton.configResourceBundle.getProperty("Pass_ScreenShot");
	public static String Fail_SnapShot = LoadPropertySingleton.configResourceBundle.getProperty("Fail_ScreenShot");
	static final Logger loggerUI = LogManager.getLogger(UIOperations.class.getName());
	String tcStatus = LoadPropertySingleton.configResourceBundle.getProperty("TESTCASE_PASS");
	public static WebDriverWait wait = null;
	public String tabvalues[];
	public Cell cell = null;
	public FileInputStream fis = null;
	public HSSFWorkbook workbook = null;
	public HSSFSheet sheet = null;
	public HSSFRow row = null;
	public HSSFRow rowval;
	public String selectedheaderval;
	public HSSFRow srcsheetval = null;
	public HSSFRow fieldval;
	public String cellval;
	public int counterval1;
	public int col_Num;
	public int rowNum = 3;
	public int size;
	public static int alertCounter = 0;
	int counter = 1;
	int destcolnum;
	int srcrow;
	String programId;
	String programName;
	String corporateClientId;
	String generatedAccountNumber;
	String startingChequeLeafNumber;
	private long i;
	private static String generatedID;
	private static String scrollNumber;
	private static String generatedSerial;
	private static boolean searchFlag = false; // This flag is to check the presence of value in the web table
	private String tcName;
	public static String textvalue = null;
	public static String Verifytextvalue = null;
	public String gettextvalue;
	public String testdatavalue;
	private static String businessDate;
	public static String valueAttribute = null;
	public static String randomNumber = null;
	public ArrayList<String> windowCount = new ArrayList<String>();
	int j = 0;
	ArrayList<String> serialnoarryval = new ArrayList<String>();
	ArrayList<String> accountNumber = new ArrayList<String>();
	int serialcounterval = 0;
	int accountcounterval = 0;
	public static Actions act = null;
	private Thread t = null; // Added to handle Utkarsh ThirdWindow
	WebElement tableElement = null;
	Map<String, String> generatedNumberCollection = new HashMap<String, String>();
	Map<String, String> testDataStorage = new HashMap<String, String>();
	String winHandle = null;
	String LMSAccountNumber = null;
	String keyword = null;
	UIOperationsExtended uie = null;

	public void run() {

		if (keyword.equalsIgnoreCase("SearchSourceKeyAndClickUtkarsh")) {
			act.moveToElement(tableElement).doubleClick().build().perform();
		} else

		if (keyword.equalsIgnoreCase("PressEnterUtkarsh")) {
			element.sendKeys(Keys.ENTER);
		}

		else if (keyword.equalsIgnoreCase("ClickButtonUtkarsh")) {
			element.click();
		}

		// driver.switchTo().window(winHandle);
	}

	public UIOperations(WebDriver driver) {
		UIOperations.driver = driver;
	}

	static {

		System.setProperty("java.awt.headless", "false");
	}

	/*
	 * public void perform(Properties objProperties,String testCase, String
	 * operation,String objectName,String objectType, String frame, String value,
	 * String delay, int slNo, int row, int tcNameRow, ArrayList<String> t_Status,
	 * String webDriverLocation,XSSFWorkbook xssfWorkbook,Sheet actSheet,String
	 * taskCode) throws Exception
	 */
	@SuppressWarnings({ "unused" })
	public void perform(String testCase, String operation, String objectName, String frame, String delay, String value,
			int row, int tcNameRow, ArrayList<String> t_Status, String webDriverLocation, Sheet actSheet,
			String taskCode, String browser) throws Exception {
		uie = new UIOperationsExtended(driver);
		keyword = operation;
		if (value != "") {
			testDataStorage.put(objectName, value);
		}
		Objectlablel = objectName;
		// keyWordAction = operation;
		// testDataFromExcel = value;
		CASaveAlertFlag = false;
		alertCounter = 0;
		Robot robot = new Robot();
		System.out.println("Row: " + row);
		if (delay != "") {
			i = (long) Double.parseDouble(delay);
			loginAlertCheck = false; // The LoginAlertCheck is changed to FALSE so that the status of the next CLICK
										// BUTTON will be updated properly
			System.out.println("Wait Time: " + i);
		} else {
			i = 0;
			System.out.println("Wait Time is Empty " + i);
		}
		// System.out.println("Driver: " + driver);
		// driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);

		try {
			wait = new WebDriverWait(driver, i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Perform_Value: " + value);
		if (row == 360) {
			System.out.println("Here");
		}
		if (!frame.equals("")) {
			if (frame.equalsIgnoreCase("NONE")) {
				try {
					driver.switchTo().defaultContent();
				} catch (org.openqa.selenium.UnhandledAlertException e) {
					Alertcaught();
					t_Status.add("False");
					System.out.println(objectName + " --> FAILS ---> Updated in the report");
					loggerUI.error(objectName + " --> FAILS ---> Updated in the report");
					StringWriter strWriter = new StringWriter();
					e.printStackTrace(new PrintWriter(strWriter));
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
				}
			} else if (!operation.equalsIgnoreCase("SwitchMultipleFrameByXpath")) {
				if (frame.contains("mainFrame")) {
					programId = value.toLowerCase();
				} else if (frame.contains(".faces")) {
					String[] pgmName = frame.split("\'");
					String[] pgName = pgmName[1].split("//.");
					programName = pgName[0];
				}
				waitForFrame(frame, browser);
			}

			/*
			 * if(!waitForFrame(frame,objectType)) {
			 * objUpdateResult.writeOutputWorkBook(xssfWorkbook, actSheet,taskCode, row,
			 * "F","Frame Doesnot Exist"); if(Fail_SnapShot.equalsIgnoreCase("TRUE") &&
			 * !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
			 * getScreenShot(objExecuteTest.evidencePath, fileName_E); } return; }
			 */
		}
		act = new Actions(driver);
		if (objectName.contains("Mobile") || objectName.contains("PAN")) {
			Thread.sleep(1000);
		} else {
			try {
				if (i != 0) {
					WebDriverWait wait1 = new WebDriverWait(driver, i);
					if (operation.toUpperCase().contains("ALERT") || operation.toUpperCase().contains("ROBOT")
							|| operation.toUpperCase().contains("ENTERBY") || operation.toUpperCase().contains("TAB")) {
						wait1.until(ExpectedConditions.alertIsPresent());
					} else if (operation.equalsIgnoreCase("SwitchMultipleFrameByXpath")
							|| operation.equalsIgnoreCase("GetCurrentWindowHandle")
							|| operation.equalsIgnoreCase("SwitchWindowByCount")) {
						System.out.println("SwitchMultipleFrameByXpath so no need to wait here");
					} else {
						if (objectName != "") {
							wait1.until(
									ExpectedConditions.visibilityOfElementLocated(UIOperations.getObject(objectName)));
						} else {
							Thread.sleep(3000);
						}
					}
					// wait.until(ExpectedConditions.presenceOfElementLocated(UIOperations.getObject(objProperties,objectName,objectType)));
				}
				System.out.println(objectName + " - WebElement Exists ");
			} catch (Exception e) {

				try {
					WebElement searchElement = null;
					js.executeScript("return document.getElementById('" + objectName + "');", searchElement);
					System.out.println(objectName + " - Element Identified by JavaScriptExecutor");
				} catch (Exception e1) {
					System.out.println(objectName + " - Element Does Not Exist");
					e.printStackTrace();
				}

				// e.printStackTrace();
			}
		}

		switch (operation.toUpperCase()) {
		// Need to vaidate this keyword
		case "CLICKSAVEFILE":

			try {
				Robot robot1 = new Robot();
				Thread.sleep(2000);
				// robot1.mouseMove(coordinates.getX()+100,coordinates.getY()-400);
				Thread.sleep(2000);
				robot1.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				robot1.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			} catch (AWTException e) {
				e.printStackTrace();
			}
			break;

		// Added to handle the Switch window by Title in Ithala-LOS
		case "SWITCHTOTITLE":
			// Switching to New Window By Title
			try {
				String switchstatus = null;
				boolean switched = false;
				do {
					Thread.sleep(5000);
					Set<String> handles = driver.getWindowHandles();
					for (String windowHandle : handles) {
						driver.switchTo().window(windowHandle);
						loggerUI.info("Switched to " + driver.getTitle());
						if (driver.getTitle().contains(objectName)) {
							loggerUI.info("\"CIF: Finally Switched to " + driver.getTitle());
							System.out.println("Finally Switched to" + ":" + driver.getTitle());
							switchstatus = "True";
							switched = true;
							break;
						}
					}
				} while (!switched);

				if (switchstatus != "True") {
					do {
						Thread.sleep(5000);
						Set<String> handles = driver.getWindowHandles();
						for (String windowHandle : handles) {
							driver.switchTo().window(windowHandle);
							loggerUI.info("Switched to " + driver.getTitle());
							if (driver.getTitle().contains(objectName)) {
								loggerUI.info("\"CIF: Finally Switched to " + driver.getTitle());
								System.out.println("Second Try - Finally Switched to" + ":" + driver.getTitle());
								switchstatus = null;
								switched = true;
								break;
							}
						}
					} while (!switched);

				}

				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				System.out.println("Switched to : " + driver.getTitle());
				loggerUI.info(objectName + " --> Switch Window By Title - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Switch Window By Title - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			break;

		// This keyword is used to relaunch the browser using keyword - Sample Inputs -
		// IE,Chrome,Firefox
		case "SETBROWSER":
			try {
				driver.quit();

				switch (value.toUpperCase()) {
				case "IE":

					try {
						Set<String> allWindows = driver.getWindowHandles();
						for (String win : allWindows) {
							driver.switchTo().window(win);
						}
						element = driver.findElement(UIOperations.getObject(objectName));
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
					} catch (Exception e) {

					}
					initializeRunTimeTask(value.toUpperCase(), driverFlag, webDriverLocation, chromeDriverPath);
					UIOperations.driver = ExecuteTest.driver;
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					loggerUI.info("Opened" + value.toUpperCase() + "Browser");
					t_Status.add("True");
					break;
				case "CHROME":
					initializeRunTimeTask(value.toUpperCase(), driverFlag, webDriverLocation, chromeDriverPath);
					UIOperations.driver = ExecuteTest.driver;
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					loggerUI.info("Opened" + value.toUpperCase() + "Browser");
					t_Status.add("True");
					break;
				case "FIREFOX":
					initializeRunTimeTask(value.toUpperCase(), driverFlag, webDriverLocation, chromeDriverPath);
					UIOperations.driver = ExecuteTest.driver;
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					loggerUI.info("Opened" + value.toUpperCase() + "Browser");
					t_Status.add("True");
					break;
				}

			} catch (Exception e) {
				loggerUI.error(objectName + " --> Set Browser - FAIL ---> Updated in the report");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				t_Status.add("False");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "CLICKBUTTON":
			// Perform click on Button
			loggerUI.info("CLICK BUTTON_SLEEP: " + i);
			if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);

			}
			try {
				try {
					WebDriverWait wait = new WebDriverWait(driver, 10);
					wait.until(ExpectedConditions.elementToBeClickable(UIOperations.getObject(objectName)));
					element = driver.findElement(UIOperations.getObject(objectName));
					element.click();
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					loggerUI.info(objectName + " --> Alert Click - PASS ---> Updated in the report");
					System.out.println("Excel updated - loginAlertCheck - P and TC_Status Array - True");
					t_Status.add("True");
				} catch (Exception e) {
					try {
						JavascriptExecutor executor = (JavascriptExecutor) driver;
						executor.executeScript("arguments[0].click();", element);
						objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
						if (t_Status.contains("False")) {
							objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", "");
						} else {
							objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
						}
						loggerUI.info(objectName + " --> Alert Click - PASS ---> Updated in the report");
						System.out.println("Excel updated - loginAlertCheck - P and TC_Status Array - True");
						t_Status.add("True");
					} catch (Exception e1) {
						loggerUI.info("Unable to Find Click Button ");
						loggerUI.info("Exception In Finding CLICK BUTTON");
						e.printStackTrace();
						t_Status.add("False");
						System.out.println(objectName + " --> Click Button - FAIL ---> Updated in the report");
						loggerUI.error(objectName + " --> Click Button - FAIL ---> Updated in the report");
						StringWriter strWriter = new StringWriter();
						e.printStackTrace(new PrintWriter(strWriter));
						objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
						if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
							getScreenShot(objExecuteTest.evidencePath, fileName_E);
						}
					}
					// ((JavascriptExecutor)
					// driver).executeScript("arguments[0].scrollIntoView(true);", element);
					// ((JavascriptExecutor) driver).executeScript("arguments[0].click();",element);
					break;
				}
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", ""); // Alert_Msg: Emptied the alert
																							// message value
				loggerUI.info(objectName + " --> Button Click - PASS ---> Updated in the report");
				System.out.println("Excel updated - loginAlertCheck - P and TC_Status Array - True");
				t_Status.add("True");
			} catch (Exception ex) {
				loggerUI.info("CLICK BUTTON Failed");
				loggerUI.error(objectName + " --> Click Button Failed");
				ex.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				loggerUI.error(objectName + " --> Button Click - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				ex.printStackTrace(new PrintWriter(strWriter));
				if (alertMessage.contains("Invalid user")) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", alertMessage);

				} else {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				}
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "CLICKLOGINBUTTON": // Added for login check
			loggerUI.info("CLICK BUTTON_SLEEP: " + i);
			if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);

			}
			try {
				try {
					WebDriverWait wait = new WebDriverWait(driver, 10);
					wait.until(ExpectedConditions.elementToBeClickable(UIOperations.getObject(objectName)));
					element = driver.findElement(UIOperations.getObject(objectName));
					element.click();
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					loggerUI.info(objectName + " --> Alert Click - PASS ---> Updated in the report");
					System.out.println("Excel updated - loginAlertCheck - P and TC_Status Array - True");
					t_Status.add("True");
				} catch (Exception e) {
					try {
						JavascriptExecutor executor = (JavascriptExecutor) driver;
						executor.executeScript("arguments[0].click();", element);
						objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
						loggerUI.info(objectName + " --> Alert Click - PASS ---> Updated in the report");
						System.out.println("Excel updated - loginAlertCheck - P and TC_Status Array - True");
						t_Status.add("True");
					} catch (Exception e1) {
						loggerUI.info("Unable to Find Click Button ");
						loggerUI.info("Exception In Finding CLICK BUTTON");
						e.printStackTrace();
						t_Status.add("False");
						System.out.println(objectName + " --> Click Button - FAIL ---> Updated in the report");
						loggerUI.error(objectName + " --> Click Button - FAIL ---> Updated in the report");
						StringWriter strWriter = new StringWriter();
						e.printStackTrace(new PrintWriter(strWriter));
						objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
						if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
							getScreenShot(objExecuteTest.evidencePath, fileName_E);
						}
						ExecuteTest.loginCheckFlag = false;
					}
					// ((JavascriptExecutor)
					// driver).executeScript("arguments[0].scrollIntoView(true);", element);
					// ((JavascriptExecutor) driver).executeScript("arguments[0].click();",element);
					break;
				}
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", ""); // Alert_Msg: Emptied the alert
																							// message value
				loggerUI.info(objectName + " --> Button Click - PASS ---> Updated in the report");
				System.out.println("Excel updated - loginAlertCheck - P and TC_Status Array - True");
				t_Status.add("True");
			} catch (Exception ex) {
				loggerUI.info("CLICK BUTTON Failed");
				loggerUI.error(objectName + " --> Click Button Failed");
				ex.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				loggerUI.error(objectName + " --> Button Click - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				ex.printStackTrace(new PrintWriter(strWriter));
				if (alertMessage.contains("Invalid user")) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", alertMessage);
					ExecuteTest.loginCheckFlag = false;

				} else {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				}
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			Thread.sleep(3000);
			if (driver.findElements(getObject(objectName)).size() != 0) {
				ExecuteTest.loginCheckFlag = false;
			}
			break;
		case "ALERTACCEPT":
			try {
				getAcceptAlert();
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", ""); // Alert_Msg: Emptied the alert
																							// message value
				loggerUI.info(objectName + " --> Alert Accept - PASS ---> Updated in the report");
				System.out.println("Excel updated - loginAlertCheck - P and TC_Status Array - True");
				t_Status.add("True");
			} catch (Exception e) {
				loggerUI.error(objectName + " --> Alert Accept - FAIL ---> Updated in the report");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				t_Status.add("False");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			break;
		case "ALERTCANCEL":
			try {
				getCancelAlert();
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", ""); // Alert_Msg: Emptied the alert
																							// message value
				loggerUI.info(objectName + " --> Alert Cancel - PASS ---> Updated in the report");
				System.out.println("Excel updated - loginAlertCheck - P and TC_Status Array - True");
				t_Status.add("True");
			} catch (Exception e) {
				loggerUI.error(objectName + " --> Alert Cancel - FAIL ---> Updated in the report");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				t_Status.add("False");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			break;

		case "GETREFNUMBERFROMALERTANDACCEPT":
			try {
				Alert alert = driver.switchTo().alert();
				String alertMsg = alert.getText();
				alert.accept();
				loggerUI.info("Alert Message: " + alertMsg);
				System.out.println("Alert Message: " + alertMsg);
				if (value != null && value != "") {
					if (value.contains("_")) {
						String splitValues[] = value.split("_");
						Pattern pattern = Pattern.compile(splitValues[0] + ".+" + splitValues[1] + "\\d+");
						Matcher matcher = pattern.matcher(alertMsg);
						while (matcher.find()) {
							System.out.println("Found match at: " + matcher.group());
							String requiredRef = matcher.group();
							Pattern pattern1 = Pattern.compile("\\w+\\d+");
							Matcher matcher1 = pattern1.matcher(requiredRef);
							while (matcher1.find()) {
								System.out.println("Found match at: " + matcher1.group());
								textvalue = matcher1.group();
							}
						}
					} else {
						Pattern pattern = Pattern.compile(value + ".+" + "\\d+");
						Matcher matcher = pattern.matcher(alertMsg);
						while (matcher.find()) {
							System.out.println("Found match at: " + matcher.group());
							String requiredRef = matcher.group();
							Pattern pattern1 = Pattern.compile("\\w+\\d+");
							Matcher matcher1 = pattern1.matcher(requiredRef);
							while (matcher1.find()) {
								System.out.println("Found match at: " + matcher1.group());
								textvalue = matcher1.group();
							}
						}
					}
				} else {
					Pattern pattern = Pattern.compile("\\d+");
					Matcher matcher = pattern.matcher(alertMsg);
					while (matcher.find()) {
						System.out.println("Found match at: " + matcher.group());
						textvalue = matcher.group();
					}
				}

				generatedNumberCollection.put("SBSCustomerNumber", textvalue);
				System.out.println("Customer Number Ganerated: " + textvalue);
				loggerUI.info("Customer Number Ganerated: " + textvalue);
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P",
						"Customer Number Ganerated: " + textvalue); // Alert_Msg: Emptied the alert message value
				loggerUI.info(objectName + " --> GetRefNumber From alert - PASS ---> Updated in the report");
				System.out.println("Excel updated - loginAlertCheck - P and TC_Status Array - True");
				t_Status.add("True");
			} catch (Exception e) {
				loggerUI.error(objectName + " --> GetRefNumber - FAIL ---> Updated in the report");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				t_Status.add("False");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// #NAB
		case "SETTEXTSERIALNUMBERPRESSENTER":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				element.clear();
				System.out.println("Set Text Value : " + value);
				System.out.println("Set Text Generated ID : " + generatedSerial);
				if (value == "") {
					if (generatedSerial == "") {
						System.out.println("generated Serial is not generated so please check that scenario");
						objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
								"Serial Number is not generated so please check that scenario");
						if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
							getScreenShot(objExecuteTest.evidencePath, fileName_E);
						}
					} else {
						element.sendKeys(generatedSerial, Keys.ENTER);
					}
				} else {
					element.sendKeys(value, Keys.ENTER);
				}
				t_Status.add("True");
				loggerUI.info(objectName + " --> Set Text Serial Number Press Enter - PASS ---> Updated in the report");

			} catch (Exception e) {
				StringWriter strWriter = new StringWriter();
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				t_Status.add("False");
				e.printStackTrace();
				loggerUI.error(
						objectName + " --> Set Text Serial Number Press Enter - FAIL ---> Updated in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// # NAB
		case "PRESSF5":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				element.click();
				Actions aa = new Actions(driver);
				aa.moveToElement(element);
				aa.sendKeys(Keys.F5);
				aa.build().perform();
				/*
				 * driver.switchTo().frame("helpframe"); wait = new WebDriverWait(driver, 10);
				 * wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id(
				 * "txtname")))); WebElement f5txtbox = driver.findElement(By.id("txtname"));
				 * f5txtbox.click(); Thread.sleep(1000); f5txtbox.sendKeys(Keys.ENTER);
				 * WebElement txtboxvalue =
				 * driver.findElement(By.xpath("//span[(@class='hlp-td-span') and (text()="+"'"+
				 * value+"'"+")]")); aa.moveToElement(txtboxvalue); aa.doubleClick();
				 * aa.build().perform();
				 * waitForFrameXpath("//iframe[contains(@src,'"+programId+".faces')]", browser);
				 */
				element.sendKeys(Keys.ENTER);
				loggerUI.info(objectName + " --> Press F5 - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				System.out.println(objectName + " --> Press F5 - FAIL ---> Updated in the report");
				loggerUI.error(objectName + " --> Press F5 - FAIL ---> Updated in the report");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			break;

		case "SCROLLELEMENT":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Scroll Element - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e1) {
				e1.printStackTrace();
				t_Status.add("False");
				System.out.println(objectName + " --> Scroll Element - FAIL ---> Updated in the report");
				loggerUI.error(objectName + " --> Scroll Element - FAIL ---> Updated in the report");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e1.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "SCROLLDOWN":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Scroll Down - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e1) {
				e1.printStackTrace();
				t_Status.add("False");
				System.out.println(objectName + " --> Scroll Down - FAIL ---> Updated in the report");
				loggerUI.error(objectName + " --> Scroll Down - FAIL ---> Updated in the report");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e1.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Created for SBS-IDC To enter the values one by one in text area
		case "SETTEXTAREA":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				String[] inputArr = value.split("_");
				for (String inp : inputArr) {
					element.sendKeys(inp, Keys.ENTER);
				}

				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Set Text Area - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e1) {
				e1.printStackTrace();
				t_Status.add("False");
				System.out.println(objectName + " --> Set Text Area - FAIL ---> Updated in the report");
				loggerUI.error(objectName + " --> Set Text Area - FAIL ---> Updated in the report");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e1.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// To identify the values in the grid and select the values by performing
		// doubleclick
		case "SELECTGRIDVALUES":

			String gridvalues = null;
			int counter = 0;
			try {
				Thread.sleep(1000);
				while (gridvalues == null) {
					counter++;
					elements = driver.findElements(UIOperations.getObject(objectName));
					for (WebElement gridval : elements) {
						if (gridval.getText().equalsIgnoreCase(value) || gridval.getText().equalsIgnoreCase(textvalue)
								|| gridval.getText().equalsIgnoreCase(generatedNumberCollection.get(value))) {
							Thread.sleep(1000);
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.elementToBeClickable(gridval));
							act.moveToElement(gridval);
							act.click(gridval).build().perform();
							act.doubleClick(gridval).build().perform();
							try {
								if (gridval.isDisplayed()) {
									Thread.sleep(2000);
									JavascriptExecutor executor = (JavascriptExecutor) driver;
									executor.executeScript("arguments[0].click();", element);
									Thread.sleep(1000);
									act.doubleClick(gridval).build().perform();
								}
							} catch (Exception e) {
								System.out.println("Clicked on the Grid");
							}
							gridvalues = "Found";
							break;
						} else {
							Thread.sleep(1000);
							act.click(gridval).build().perform();
							act.moveToElement(gridval).sendKeys(Keys.DOWN).build().perform();
							// gridval.sendKeys(Keys.DOWN);
						}
					}
					if (counter >= 50 || elements.size() == 0) {
						t_Status.add("False");
						if (elements.size() == 0) {
							objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
									"No records are found in the grid.Please verify the grid");
						} else {
							objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
									"Given Test data value is not found in the Grid");
						}
						System.out.println("The values are not found in the Grid");
						gridvalues = "Found";
						break;
					}
				}
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Grid Value clicked - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e1) {
				loggerUI.info("Unable to select the element in the grid");
				e1.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				System.out.println(objectName + " --> Select the values in Grid - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e1.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// Added for SBS-LMS --> It will search the input value from the grid and click
		// the record
		case "SELECTGRIDVALUESANDCLICK":

			String gridvalues1 = null;
			int counter1 = 0;
			String txtValue = null;
			try {
				Thread.sleep(1000);
				while (gridvalues1 == null) {
					elements = driver.findElements(UIOperations.getObject(objectName));
					for (WebElement gridval : elements) {
						if (textvalue != null) {
							txtValue = textvalue.trim();
						}
						if (gridval.getText().trim().equals(value.trim()) || gridval.getText().trim().equals(txtValue)
								|| gridval.getText().trim().equals(generatedNumberCollection.get("AccountNumber"))) {
							Thread.sleep(1000);
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.elementToBeClickable(gridval));
							act.moveToElement(gridval);
							act.click(gridval).build().perform();
							try {
								if (gridval.isDisplayed()) {
									Thread.sleep(2000);
									JavascriptExecutor executor = (JavascriptExecutor) driver;
									executor.executeScript("arguments[0].click();", element);
									Thread.sleep(1000);
									act.doubleClick(gridval).build().perform();
								}
							} catch (Exception e) {
								System.out.println("Clicked on the Grid");
							}
							gridvalues1 = "Found";
							break;
						} else if (elements.size() == counter1) {
							t_Status.add("False");
							objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
									"Given Test data value is not found in the Grid");
							System.out.println("The values are not found in the Grid");
							gridvalues = "Found";
							break;

						}

					}
				}
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Grid Value clicked - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e1) {
				loggerUI.info("Unable to select the element in the grid");
				e1.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				System.out.println(objectName + " --> Select the values in Grid - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e1.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "SELECTGRIDVALUESANDCLICKIRTM":

			String gridvalues2 = null;
			int counter2 = 0;
			String txtValue1 = null;
			System.out.println("Text Value is :  " + textvalue);
			try {
				Thread.sleep(1000);
				while (gridvalues2 == null) {
					elements = driver.findElements(UIOperations.getObject(objectName));
					for (WebElement gridval : elements) {
						if (textvalue != null) {
							txtValue1 = textvalue.trim();
						}
						if (gridval.getText().trim().equals(value.trim()) || gridval.getText().trim().equals(txtValue1)
								|| gridval.getText().trim().equals(generatedNumberCollection.get("AccountNumber"))) {
							Thread.sleep(1000);
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.elementToBeClickable(gridval));
							act.moveToElement(gridval);
							act.click(gridval).build().perform();
							/*
							 * try { if(gridval.isDisplayed()) { Thread.sleep(2000); JavascriptExecutor
							 * executor = (JavascriptExecutor)driver;
							 * executor.executeScript("arguments[0].click();", element); Thread.sleep(1000);
							 * act.doubleClick(gridval).build().perform(); } } catch (Exception e) {
							 * System.out.println("Clicked on the Grid"); }
							 */
							gridvalues2 = "Found";
							break;
						} else if (elements.size() == counter2) {
							t_Status.add("False");
							objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
									"Given Test data value is not found in the Grid");
							System.out.println("The values are not found in the Grid");
							gridvalues = "Found";
							break;

						}

					}
				}
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Grid Value clicked - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e1) {
				loggerUI.info("Unable to select the element in the grid");
				e1.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				System.out.println(objectName + " --> Select the values in Grid - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e1.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "GRIDVALUESELECTIRTM":

			String gridvalueIRTM = null;
			int counteriRTM = 0;
			String txtValueiRTM = null;
			System.out.println("Text Value is :  " + textvalue);
			System.out.println("Account No: " + generatedNumberCollection.get("AccountNumber"));
			try {
				Thread.sleep(1000);
				while (gridvalueIRTM == null) {
					elements = driver.findElements(UIOperations.getObject(objectName));
					for (WebElement gridval : elements) {
						if (textvalue != null) {
							txtValueiRTM = textvalue.trim();
						}
						if (gridval.getText().trim().equals(value.trim())
								|| gridval.getText().trim().equals(txtValueiRTM)
								|| gridval.getText().trim().equals(generatedNumberCollection.get("AccountNumber"))) {
							Thread.sleep(1000);
							WebDriverWait wait = new WebDriverWait(driver, 10);
							wait.until(ExpectedConditions.elementToBeClickable(gridval));
							act.moveToElement(gridval);
							act.click(gridval).build().perform();

							gridvalueIRTM = "Found";
							break;
						} else if (elements.size() == counteriRTM) {
							t_Status.add("False");
							objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
									"Given Test data value is not found in the Grid");
							System.out.println("The values are not found in the Grid");
							gridvalues = "Found";
							break;

						}

					}
				}
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Grid Value clicked - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e1) {
				loggerUI.info("Unable to select the element in the grid");
				e1.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				System.out.println(objectName + " --> Select the values in Grid - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e1.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			break;

		case "MOUSEHOVER":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				Actions act1 = new Actions(driver);
				act1.moveToElement(element);
				act1.build().perform();
				loggerUI.info(objectName + " --> Mouse Hover - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				t_Status.add("True");
			} catch (Exception e) {
				loggerUI.info("Unable to mouse hover the element");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				t_Status.add("False");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}

			}
			break;
		// Added to click the grid values based on two inputs(Ref Number and Stages) -
		// Added for YBLLS
		// //div[contains(text(),'Valuation
		// Initiated')]/ancestor::div[@role='row']/div[@col-id='ProposalNo']/ng-component/a[contains(text(),'1901180001')]
		// //div[contains(text(),'~')]/ancestor::div[@role='row']/div[@col-id='ProposalNo']/ng-component/a[contains(text(),'~')]
		// input: Valuation Initiated_1901180001
		case "CLICKGRIDVALUEWITHTWOINPUT":
			try {
				String objectValue = UIOperations.getObjectValue(objectName);
				String[] objctVal = objectValue.split("~");
				String[] valueArr = value.split("_");
				String xpathStr = objctVal[0] + valueArr[0] + objctVal[1] + valueArr[1] + objctVal[2];
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStr)));
				element = driver.findElement(By.xpath(xpathStr));
				element.click();
				loggerUI.info(objectName + " --> Click Grid Value with two input - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				t_Status.add("True");
			} catch (Exception e) {
				loggerUI.info("Getting while clicking on the element from the grid");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				t_Status.add("False");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}

			}
			break;

		// # NAB - to get the business date from the home page
		case "GETBUSINESSDATE":
			try {
				businessDate = driver.findElement(UIOperations.getObject(objectName)).getText();
				System.out.println("Business Date: " + businessDate);
				// getScreenShot(objExecuteTest.evidencePath, fileName_E);
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Get Business Date - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Get Business Date - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// #-NAB - to enter the business date
		case "SETDATEPRESSENTER":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				element.clear();
				System.out.println("Date Value From Excel: " + value);
				System.out.println("BusinessDate : " + businessDate);
				if (value == "") {
					SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");
					Date date = format.parse(businessDate);
					Format formatter = new SimpleDateFormat("dd-MM-yyyy");
					String s = formatter.format(date);
					System.out.println("Business Date in Conversion Format: " + s);
					element.sendKeys(s, Keys.ENTER);

				} else {
					element.sendKeys(value, Keys.ENTER);
				}

				t_Status.add("True");
				loggerUI.info(objectName + " -->  For Set Date Press Enter Identified");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");

			} catch (Exception e) {
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				tcStatus = "FAIL";
				t_Status.add("False");
				e.printStackTrace();
				loggerUI.error(objectName + " --> Set Date - FAIL ---> Updated in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "SETFUTUREDATEPRESSENTER":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				element.clear();
				if (businessDate != null) {
					System.out.println("Date Value From Excel: " + value);
					System.out.println("BusinessDate : " + businessDate);
					if (value.contains("_")) {
						String dateValue[] = value.split("_");
						SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");
						Date date = format.parse(businessDate);
						Format formatter = new SimpleDateFormat(dateValue[0]);
						String s = formatter.format(date);
						String sptDate[] = s.split("-");
						String day = Integer.toString(Integer.parseInt(sptDate[0]) + Integer.parseInt(dateValue[1]));
						if (Integer.parseInt(day) <= 9) {
							day = "0" + day;
						}
						String fDate = day + "-" + sptDate[1] + "-" + sptDate[2];
						System.out.println("Business Date in Conversion Format: " + fDate);
						element.sendKeys(fDate, Keys.ENTER);

					} else {
						element.sendKeys(value, Keys.ENTER);
					}
				} else {
					if (textvalue != null) {
						String sptDate[] = textvalue.split("-");
						String day = Integer.toString(Integer.parseInt(sptDate[0]) + Integer.parseInt(value));
						if (Integer.parseInt(day) <= 9) {
							day = "0" + day;
						}
						String fDate = day + "-" + sptDate[1] + "-" + sptDate[2];
						System.out.println("Business Date in Conversion Format: " + fDate);
						element.sendKeys(fDate, Keys.ENTER);
					} else {
						element.sendKeys(value, Keys.ENTER);
					}
				}

				loggerUI.info(objectName + " --> Set Future Date Press Enter - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", alertMessage);
				loggerUI.info(objectName + " --> Set Future Date Press Enter - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				t_Status.add("False");
				e.printStackTrace();
				loggerUI.error(objectName + " --> Set Future Date Press Enter - FAIL ---> Updated in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// #NAB - To click the save button and verify the batch numbers from the Alert
		// popup
		case "CLICKSAVEBUTTONGENERIC":
			if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}

			try {

				// wait.until(ExpectedConditions.visibilityOfElementLocated(UIOperations.getObject(objProperties,objectName,objectType)));
				// // Added_WebDriverWait

				try {
					element = driver.findElement(UIOperations.getObject(objectName));
				} catch (Exception e) {
					loggerUI.info("Unable to Find Click Button ");
					e.printStackTrace();
					tcStatus = "FAIL";
					t_Status.add("False");
					System.out.println(objectName + " --> Click Button - FAIL ---> Updated in the report");
					loggerUI.error(objectName + " --> Click Button - FAIL ---> Updated in the report");
					StringWriter strWriter = new StringWriter();
					e.printStackTrace(new PrintWriter(strWriter));
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
					break;
				}

				try {
					act.moveToElement(element).click().build().perform();
					// element.click();
					// Thread.sleep(5000);

				} catch (Exception e) {
					loggerUI.info("Unable to Click - Button ");
					e.printStackTrace();
				}
				System.out.println("Waiting To Catch Alert");
				wait = new WebDriverWait(driver, 20);
				if (wait.until(ExpectedConditions.alertIsPresent()) != null) {
					System.out.println("Alert Caught");
				} else {
					System.out.println("No Alert Present");
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
				}
				// wait.until(ExpectedConditions.presenceOfElementLocated(UIOperations.getObject(objProperties,objectName,objectType)));
				System.out.println("CHECK 1");
				System.out.println("TC Name: " + tcName);
				if (objectName.contains("photoInventoryCreation")) {
					if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
						System.out.println("Newly Added Screen Shot");
						robot.keyPress(KeyEvent.VK_ENTER);
						robot.keyRelease(KeyEvent.VK_ENTER);
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
						robot.keyPress(KeyEvent.VK_ENTER);
						robot.keyRelease(KeyEvent.VK_ENTER);
						CASaveAlertFlag = true;
					}
				}

				while (isAlert() == true) {
					System.out.println("CHECK 2");
					if (alertFlag) {
						alertMessage = getCancelAlert();
					} else {
						alertMessage = getAcceptAlert();
						System.out.println("CHECK 3");
						CASaveAlertFlag = true;
						if (loginAlertCheck) // If "Invalid User" Alert is thrown, loginCheckFlag is changed to FALSE
						{
							ExecuteTest.loginCheckFlag = false;
							loggerUI.info("Invalid User - 2");
							System.out.println("CHECK 4");
							break;
						}
						/*
						 * if(alertMessage.contains("Generated")) { System.out.println("AlertMessage: "
						 * + alertMessage); // SerialID = alertMessage.substring(alertMessage.length() -
						 * 8); // System.out.println("SerialID: " + SerialID); serialID =
						 * alertMessage.split(":"); System.out.println("SerialID: " + serialID[1]);
						 * 
						 * }
						 */
						if (alertMessage.contains("Generated") && alertMessage.contains("Corr Ref Num")
								&& alertMessage.contains("Batch")) {
							String text = alertMessage;
							String updatedText = text.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "");
							String input = "Generated Serial :";
							String arry1[] = input.split("_");
							Pattern pattern = Pattern.compile(input + "\\d+");
							Matcher matcher = pattern.matcher(updatedText);
							while (matcher.find()) {
								System.out.println("Found match at: " + matcher.start() + " to " + matcher.end()
										+ matcher.group());
								String requiredRef = matcher.group();
								Pattern pattern1 = Pattern.compile("\\d+");
								Matcher matcher1 = pattern1.matcher(requiredRef);
								while (matcher1.find()) {
									System.out.println("Found match at: " + matcher1.start() + " to " + matcher1.end()
											+ matcher1.group());
									generatedSerial = matcher1.group();
								}
							}

							String input1 = "Batch Number = ";
							Pattern pattern2 = Pattern.compile(input1 + "\\d+");
							Matcher matcher2 = pattern2.matcher(updatedText);
							while (matcher2.find()) {
								System.out.println("Found match at: " + matcher2.start() + " to " + matcher2.end()
										+ matcher2.group());
								String requiredRef = matcher2.group();
								Pattern pattern3 = Pattern.compile("\\d+");
								Matcher matcher3 = pattern3.matcher(requiredRef);
								while (matcher3.find()) {
									System.out.println("Found match at: " + matcher3.start() + " to " + matcher3.end()
											+ matcher3.group());
									generatedID = matcher3.group();
								}
							}
							generatedNumberCollection.put("SerialBatchNumber", generatedSerial);
							generatedNumberCollection.put("BatchNumber", generatedID);
							System.out.println("Corr Ref Number_SerialBatchNumber: " + generatedSerial);
							System.out.println("Corr Ref Number_BatchNumber: " + generatedID);
						} else if (alertMessage.contains("Generated") && alertMessage.contains("Scroll")
								&& alertMessage.contains("Batch")) {
							generatedSerial = "";
							scrollNumber = "";
							generatedID = "";
							String dSerial = "";
							String sNumber = "";
							System.out.println("AlertMessage: " + alertMessage + alertMessage.length());

							for (int i = 0; i < alertMessage.length(); i++) {
								Character character = alertMessage.charAt(i);
								if (Character.isDigit(character)) {
									int tillDigit = i;
									for (int j = i; j <= tillDigit; j++) {
										Character charac = alertMessage.charAt(j);
										if (Character.isDigit(charac)) {
											if (generatedSerial == "") {
												dSerial += charac.toString();
												tillDigit++;
												System.out.println("Serial Number Generated in loop" + dSerial);
											}

											else if (generatedSerial != "" && scrollNumber == "") {
												sNumber += charac.toString();
												tillDigit++;
												System.out.println("Scroll Number Generated in loop" + sNumber);
											}

											else {
												generatedID += charac.toString();
												tillDigit++;
												System.out.println("Batch Number Generated in loop" + generatedID);
											}
											i++;
										}

										else {
											if ((dSerial != "")) {
												generatedSerial = dSerial;

											}
											if ((sNumber != "")) {
												scrollNumber = sNumber;
											}
										}

										if (i > alertMessage.length() - 1) {
											break;
										}
									}
								}
							}

							System.out.println("Serial Number Generated Final" + generatedSerial);
							System.out.println("Scroll Number Generated Final" + scrollNumber);
							System.out.println("Batch Number Generated Final" + generatedID);

							generatedNumberCollection.put("BatchNumber", generatedID);
							System.out.println("Batch Number Updated in the Map");
						} else if (alertMessage.contains("Generated") && alertMessage.contains("Batch")) {
							generatedSerial = "";
							generatedID = "";
							String dSerial = "";
							System.out.println("AlertMessage: " + alertMessage + alertMessage.length());

							for (int i = 0; i < alertMessage.length(); i++) {
								Character character = alertMessage.charAt(i);
								if (Character.isDigit(character)) {
									int tillDigit = i;
									for (int j = i; j <= tillDigit; j++) {
										Character charac = alertMessage.charAt(j);
										if (Character.isDigit(charac)) {
											if (generatedSerial == "") {
												dSerial += charac.toString();
												tillDigit++;
												System.out.println("Serial Number Generated in loop" + dSerial);
											}

											else {
												generatedID += charac.toString();
												tillDigit++;
												System.out.println("Batch Number Generated in loop" + generatedID);
											}
											i++;
										}

										else {
											if (dSerial != "") {
												generatedSerial = dSerial;
											}
										}

										if (i > alertMessage.length() - 1) {
											break;
										}
									}
								}
							}

							System.out.println("Serial Number Generated Final" + generatedSerial);
							System.out.println("Batch Number Generated Final" + generatedID);
							generatedNumberCollection.put("SerialBatchNumber", generatedSerial);
							generatedNumberCollection.put("BatchNumber", generatedID);
							generatedNumberCollection.put("SerialNumber", generatedSerial);
							System.out.println("Batch Number Updated in the Map");
						} else {
							System.out.println("AlertMessage: " + alertMessage);
							if (alertMessage.contains("Generated") || alertMessage.contains("Batch")) {
								generatedSerial = "";
								generatedID = "";
								for (int i = 0; i < alertMessage.length(); i++) {
									Character character = alertMessage.charAt(i);
									if (Character.isDigit(character)) {

										generatedSerial += character.toString();
									}
								}

								if (alertMessage.contains("Generated")) {
									System.out.println("Serial Number Generated" + generatedSerial);
									generatedID = generatedSerial;

									if (alertMessage.contains("Loan File")) {
										generatedNumberCollection.put("LoanFileNumber", generatedID);
									} else if (alertMessage.contains("LAF")) {
										generatedNumberCollection.put("LAFNumber", generatedID);
									} else if (alertMessage.contains("Generated Account")) {
										generatedNumberCollection.put("AccountNumber", generatedID);
									} else if (alertMessage.contains("Generated Center Code")) {
										generatedNumberCollection.put("CenterNumber", generatedID);
									}

									if (programName != "" && programName != null) {
										if (programName.equalsIgnoreCase("mindclients.faces")) {
											generatedNumberCollection.put("IndividualClientNumber", generatedID);
										} else if (programName.equalsIgnoreCase("mcorpclients.faces")) {
											generatedNumberCollection.put("CorporateClientNumber", generatedID);
										} else if (programName.equalsIgnoreCase("einvspimage.faces")) {
											generatedNumberCollection.put("InventorySerialNumber", generatedID);
											System.out.println(
													"***********" + programId + "Serialnumber stored is" + generatedID);
										} else if (programId.contains("esigtag")) {
											generatedNumberCollection.put("SerialNumbereinvspimg", generatedID);
											System.out.println(
													"***********" + programId + "Serialnumber stored is" + generatedID);
										} else if (programId.contains("enomreg")) {
											generatedNumberCollection.put("SerialNumberenomreg", generatedID);
											System.out.println(
													"***********" + programId + "Serialnumber stored is" + generatedID);
										} else if (programId.contains("mindclients")) {
											if (value != null) {
												generatedNumberCollection.put(value, generatedID);
											} else {
												generatedNumberCollection.put("IndividualClientNumber", generatedID);
											}
										} else {

											generatedNumberCollection.put("SerialNumber", generatedID);
											System.out.println(
													"***********" + programId + "Serialnumber stored is" + generatedID);
										}
									}
								} else if (alertMessage.contains("Batch")) {
									System.out.println("Batch Number Generated" + generatedSerial);
									generatedID = generatedSerial;
								}
								if (alertMessage.contains("Batch Number = ")) {
									System.out.println("Batch Number = ");
									generatedNumberCollection.put("BatchNumber", generatedID);
								}
							}
						}

						// -------------------------------------------
						if (objectName.contains("photoInventoryCreation") || programId.equalsIgnoreCase("esigtag")
								|| programId.equalsIgnoreCase("einvspimage")) {
							if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
								getScreenShot(objExecuteTest.evidencePath, fileName_E);
								System.out.println("Newly Added Screen Shot");
								robot.keyPress(KeyEvent.VK_ENTER);
								robot.keyRelease(KeyEvent.VK_ENTER);
								getScreenShot(objExecuteTest.evidencePath, fileName_E);
								CASaveAlertFlag = true;

							}
						}

					}

				}
				if (loginAlertCheck) // If "Invalid User" Alert is thrown, update the report as "F"
				{
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", alertMessage); // If
																											// "Invalid
																											// User"
																											// Alert is
																											// thrown,
																											// update
																											// the
																											// respective
																											// test
																											// steps to
																											// "F"
					loggerUI.info("Excel updated - loginAlertCheck - F and TC_Status Array - False");
					loggerUI.info(objectName + " --> Button Click - PASS ---> Updated in the report");
					t_Status.add("False");
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
					System.out.println("CHECK 5");
				} else if (!alertMessage.equalsIgnoreCase("")) // If the alert msg contains the word "Invalid" or "Not",
																// update respective test steps to "F"
				{
					System.out.println("CHECK 6");
					System.out.println("CHECK 6_ Alert Message: " + alertMessage);
					if (alertMessage.contains("Invalid") || alertMessage.contains("Not")
							|| alertMessage.contains("invalid") || alertMessage.contains("not")
							|| alertMessage.contains("Rejected") || alertMessage.contains("rejected")) {
						loggerUI.info(objectName + " --> Set Text - FAIL");
						objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", alertMessage); // Alert_Msg:
																												// Changed
																												// to
																												// "P"
																												// from
																												// "F"
																												// and
																												// emptied
																												// the
																												// alert
																												// message
																												// value
						loggerUI.info("Click Button - Fail");
						loggerUI.info(objectName + " --> Click Button - FAIL ---> Updated in the report");
						t_Status.add("False");
						if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
							getScreenShot(objExecuteTest.evidencePath, fileName_E);
						}
						System.out.println("CHECK 7");
					} else if (alertMessage.contains("Number") || alertMessage.contains("number")
							|| alertMessage.contains("Serial") || alertMessage.contains("serial")) {
						if (CASaveAlertFlag) // This CASaveAlertFlag is introduced for CaterAllen to update the status
												// as PASS when there is a PopUp on saving the transaction
						{
							objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", generatedID);
							loggerUI.info(objectName + " --> SaveButton - PASS ---> Updated in the report");
							System.out.println("Excel updated - Number or Serial - P ");
							t_Status.add("True");
							System.out.println("CHECK 9");
						} else // This CASaveAlertFlag is introduced for CaterAllen to update the status as
								// FAIL when there is no PopUp on saving the transaction
						{
							objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", "");
							loggerUI.info(objectName + " --> SaveButton - FAIL ---> Updated in the report");
							System.out.println("Excel updated - Number or Serial - F");
							t_Status.add("False");
							System.out.println("CHECK 10");
						}
					} else {
						loggerUI.info(objectName + " --> Click Button - PASS");
						// if(!alertMessage.contains("Updated") || alertMessage.contains("updated"))
						// if(!alertMessage.contains("Record Updated")) // Commented for NOVA
						// 20_InwardClearingMastersSetp to update the status pass for the alert message
						// "Record Updated"
						if (alertMessage.contains("Record Updated")) // Added for NOVA 20_InwardClearingMastersSetp to
																		// update the status pass for the alert message
																		// "Record Updated"
						{
							objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P",
									"GeneratedSerial/Batch Number: " + generatedID + "_" + alertMessage);
							System.out.println("CHECK 12");
						}

						System.out.println("Alert Message contains no Number, id, invalid :  " + alertMessage);
						loggerUI.info(objectName + " --> Click Button - PASS ---> Updated in the report");
						t_Status.add("True");
						System.out.println("CHECK 8");
					}

				} else {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					loggerUI.info(objectName + " --> Button Click - PASS ---> Updated in the report");
					System.out.println("Excel updated - loginAlertCheck - P and TC_Status Array - True");
					t_Status.add("True");

				}
				/******************
				 * Added to capture Success Message - Ends
				 ************************/
				System.out.println("CHECK 11");
				System.out.println("Alert Message: " + alertMessage);

			} catch (Exception ex) {
				loggerUI.info("CLICK BUTTON Failed");
				loggerUI.error(objectName + " --> Click Button Failed");
				ex.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				loggerUI.error(objectName + " --> Button Click - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				ex.printStackTrace(new PrintWriter(strWriter));
				if (alertMessage.contains("Invalid user")) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Erro", ex.toString());

				} else {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", ex.toString());
				}
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "CLICKSAVEBUTTONTF": // Added for CBI_TF application to handle 4 Batch and Serial numbers
			if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}

			try {

				try {
					element = driver.findElement(UIOperations.getObject(objectName));
				} catch (Exception e) {
					loggerUI.info("Unable to Find Click Button ");
					e.printStackTrace();
					tcStatus = "FAIL";
					t_Status.add("False");
					System.out.println(objectName + " --> Click Button - FAIL ---> Updated in the report");
					loggerUI.error(objectName + " --> Click Button - FAIL ---> Updated in the report");
					StringWriter strWriter = new StringWriter();
					e.printStackTrace(new PrintWriter(strWriter));
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
					break;
				}

				try {
					act.moveToElement(element).click().build().perform();
				} catch (Exception e) {
					loggerUI.info("Unable to Click - Button ");
					e.printStackTrace();
				}
				System.out.println("Waiting To Catch Alert");
				wait = new WebDriverWait(driver, 20);
				if (wait.until(ExpectedConditions.alertIsPresent()) != null) {
					System.out.println("Alert Caught");
				} else {
					System.out.println("No Alert Present");
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
				}
				System.out.println("TC Name: " + tcName);

				while (isAlert() == true) {
					System.out.println("CHECK 2");
					if (alertFlag) {
						alertMessage = getCancelAlert();
					} else {
						alertMessage = getAcceptAlert();
						System.out.println("CHECK 3");
						CASaveAlertFlag = true;

						// if(alertMessage.contains("Generated")&&alertMessage.contains("Corr Ref
						// Num")&&alertMessage.contains("Batch"))
						if (alertMessage.startsWith("Generated Serial :") && (alertMessage.contains("Corr Ref Num"))) {
							String text = alertMessage;
							String updatedText = text.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "");
							String input = "Generated Serial :";
							String arry1[] = input.split("_");
							Pattern pattern = Pattern.compile(input + "\\d+");
							Matcher matcher = pattern.matcher(updatedText);
							while (matcher.find()) {
								System.out.println("Found match at: " + matcher.start() + " to " + matcher.end()
										+ matcher.group());
								String requiredRef = matcher.group();
								Pattern pattern1 = Pattern.compile("\\d+");
								Matcher matcher1 = pattern1.matcher(requiredRef);
								while (matcher1.find()) {
									System.out.println("Found match at: " + matcher1.start() + " to " + matcher1.end()
											+ matcher1.group());
									generatedSerial = matcher1.group();
								}
							}

							String input1 = "Batch Number = ";
							Pattern pattern2 = Pattern.compile(input1 + "\\d+");
							Matcher matcher2 = pattern2.matcher(updatedText);
							while (matcher2.find()) {
								System.out.println("Found match at: " + matcher2.start() + " to " + matcher2.end()
										+ matcher2.group());
								String requiredRef = matcher2.group();
								Pattern pattern3 = Pattern.compile("\\d+");
								Matcher matcher3 = pattern3.matcher(requiredRef);
								while (matcher3.find()) {
									System.out.println("Found match at: " + matcher3.start() + " to " + matcher3.end()
											+ matcher3.group());
									generatedID = matcher3.group();
								}
							}
							if (alertMessage.contains("Corr Ref Num")) {
								generatedNumberCollection.put("SerialNumber1", generatedSerial);
								generatedNumberCollection.put("BatchNumber1", generatedID);
								System.out.println(
										"Corr Ref Number_SerialBatchNumber:   SerialNumber1:  " + generatedSerial);
								System.out.println("Corr Ref Number_BatchNumber:   BatchNumber1:  " + generatedID);
							} else {
								generatedNumberCollection.put("SerialNumber2", generatedSerial);
								generatedNumberCollection.put("BatchNumber2", generatedID);
								System.out.println(
										"Corr Ref Number_SerialBatchNumber:   SerialNumber2:  " + generatedSerial);
								System.out.println("Corr Ref Number_BatchNumber:   BatchNumber2:  " + generatedID);
							}

						} else if (alertMessage.startsWith("Correspondence")) {
							String text = alertMessage;
							String updatedText = text.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "");
							String input = "Guarantee Serial: ";
							String arry1[] = input.split("_");
							Pattern pattern = Pattern.compile(input + "\\d+");
							Matcher matcher = pattern.matcher(updatedText);
							while (matcher.find()) {
								System.out.println("Found match at: " + matcher.start() + " to " + matcher.end()
										+ matcher.group());
								String requiredRef = matcher.group();
								Pattern pattern1 = Pattern.compile("\\d+");
								Matcher matcher1 = pattern1.matcher(requiredRef);
								while (matcher1.find()) {
									System.out.println("Found match at: " + matcher1.start() + " to " + matcher1.end()
											+ matcher1.group());
									generatedSerial = matcher1.group();
								}
							}

							String input1 = "Batch Number = ";
							Pattern pattern2 = Pattern.compile(input1 + "\\d+");
							Matcher matcher2 = pattern2.matcher(updatedText);
							while (matcher2.find()) {
								System.out.println("Found match at: " + matcher2.start() + " to " + matcher2.end()
										+ matcher2.group());
								String requiredRef = matcher2.group();
								Pattern pattern3 = Pattern.compile("\\d+");
								Matcher matcher3 = pattern3.matcher(requiredRef);
								while (matcher3.find()) {
									System.out.println("Found match at: " + matcher3.start() + " to " + matcher3.end()
											+ matcher3.group());
									generatedID = matcher3.group();
								}
							}
							generatedNumberCollection.put("SerialNumber3", generatedSerial);
							generatedNumberCollection.put("BatchNumber3", generatedID);
							System.out
									.println("Corr Ref Number_SerialBatchNumber:  SerialNumber3:  " + generatedSerial);
							System.out.println("Corr Ref Number_BatchNumber:  BatchNumber3:  " + generatedID);
						} else if (alertMessage.contains("Amendment ")) {
							String text = alertMessage;
							String updatedText = text.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "");
							String input = "Amendment Serial: ";
							String arry1[] = input.split("_");
							Pattern pattern = Pattern.compile(input + "\\d+");
							Matcher matcher = pattern.matcher(updatedText);
							while (matcher.find()) {
								System.out.println("Found match at: " + matcher.start() + " to " + matcher.end()
										+ matcher.group());
								String requiredRef = matcher.group();
								Pattern pattern1 = Pattern.compile("\\d+");
								Matcher matcher1 = pattern1.matcher(requiredRef);
								while (matcher1.find()) {
									System.out.println("Found match at: " + matcher1.start() + " to " + matcher1.end()
											+ matcher1.group());
									generatedSerial = matcher1.group();
								}
							}

							String input1 = "Batch Number = ";
							Pattern pattern2 = Pattern.compile(input1 + "\\d+");
							Matcher matcher2 = pattern2.matcher(updatedText);
							while (matcher2.find()) {
								System.out.println("Found match at: " + matcher2.start() + " to " + matcher2.end()
										+ matcher2.group());
								String requiredRef = matcher2.group();
								Pattern pattern3 = Pattern.compile("\\d+");
								Matcher matcher3 = pattern3.matcher(requiredRef);
								while (matcher3.find()) {
									System.out.println("Found match at: " + matcher3.start() + " to " + matcher3.end()
											+ matcher3.group());
									generatedID = matcher3.group();
								}
							}
							generatedNumberCollection.put("SerialNumber4", generatedSerial);
							generatedNumberCollection.put("BatchNumber4", generatedID);
							System.out
									.println("Corr Ref Number_SerialBatchNumber:   SerialNumber4:  " + generatedSerial);
							System.out.println("Corr Ref Number_BatchNumber:   BatchNumber4:  " + generatedID);
						} else if (alertMessage.contains("Generated Serial :")
								&& alertMessage.startsWith("Generated Serial :")) {
							if (alertMessage.contains("Batch Number")) {

								String text = alertMessage;
								String updatedText = text.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t",
										"");
								String input = "Generated Serial :";
								String arry1[] = input.split("_");
								Pattern pattern = Pattern.compile(input + "\\d+");
								Matcher matcher = pattern.matcher(updatedText);
								while (matcher.find()) {
									System.out.println("Found match at: " + matcher.start() + " to " + matcher.end()
											+ matcher.group());
									String requiredRef = matcher.group();
									Pattern pattern1 = Pattern.compile("\\d+");
									Matcher matcher1 = pattern1.matcher(requiredRef);
									while (matcher1.find()) {
										System.out.println("Found match at: " + matcher1.start() + " to "
												+ matcher1.end() + matcher1.group());
										generatedSerial = matcher1.group();
									}
								}

								String input1 = "Batch Number = ";
								Pattern pattern2 = Pattern.compile(input1 + "\\d+");
								Matcher matcher2 = pattern2.matcher(updatedText);
								while (matcher2.find()) {
									System.out.println("Found match at: " + matcher2.start() + " to " + matcher2.end()
											+ matcher2.group());
									String requiredRef = matcher2.group();
									Pattern pattern3 = Pattern.compile("\\d+");
									Matcher matcher3 = pattern3.matcher(requiredRef);
									while (matcher3.find()) {
										System.out.println("Found match at: " + matcher3.start() + " to "
												+ matcher3.end() + matcher3.group());
										generatedID = matcher3.group();
									}
								}
								generatedNumberCollection.put("SerialNumber2", generatedSerial);
								generatedNumberCollection.put("BatchNumber2", generatedID);
								System.out.println(
										"Corr Ref Number_SerialBatchNumber:   SerialNumber2:  " + generatedSerial);
								System.out.println("Corr Ref Number_BatchNumber:   BatchNumber2:  " + generatedID);
							} else {
								generatedNumberCollection.put("SerialNumber5", generatedSerial);
								System.out.println("Serial Number 5 : " + generatedSerial);
							}

						} else {
							System.out.println("AlertMessage: " + alertMessage);

							/*
							 * if(alertMessage.contains("Generated")||alertMessage.contains("Batch")) {
							 * generatedSerial=""; generatedID=""; for(int i=0;i<alertMessage.length();i++)
							 * { Character character = alertMessage.charAt(i);
							 * if(Character.isDigit(character)) {
							 * 
							 * generatedSerial += character.toString(); } }
							 * 
							 * if(alertMessage.contains("Generated")) {
							 * System.out.println("Serial Number Generated" + generatedSerial );
							 * generatedID=generatedSerial;
							 * 
							 * if(alertMessage.contains("Loan File")) {
							 * generatedNumberCollection.put("LoanFileNumber", generatedID); } else
							 * if(alertMessage.contains("LAF")) { generatedNumberCollection.put("LAFNumber",
							 * generatedID); } else if(alertMessage.contains("Generated Account")) {
							 * generatedNumberCollection.put("AccountNumber", generatedID); } else
							 * if(alertMessage.contains("Generated Center Code")) {
							 * generatedNumberCollection.put("CenterNumber", generatedID); } else
							 * if(alertMessage.contains("Generated Serial")) {
							 * if(programName!=""&&programName!=null) {
							 * if(programName.equalsIgnoreCase("mindclients.faces")) {
							 * generatedNumberCollection.put("IndividualClientNumber", generatedID); } else
							 * if(programName.equalsIgnoreCase("mcorpclients.faces")) {
							 * generatedNumberCollection.put("CorporateClientNumber", generatedID); } else
							 * if(programName.equalsIgnoreCase("einvspimage.faces")) {
							 * generatedNumberCollection.put("InventorySerialNumber", generatedID); } }
							 * generatedNumberCollection.put("SerialNumber", generatedID); } } else
							 * if(alertMessage.contains("Batch")) {
							 * System.out.println("Batch Number Generated" + generatedSerial );
							 * generatedID=generatedSerial; } if(alertMessage.contains("Batch Number = ")) {
							 * System.out.println("Batch Number = ");
							 * generatedNumberCollection.put("BatchNumber", generatedID); } }
							 */
						}

					}

				}
				if (loginAlertCheck) // If "Invalid User" Alert is thrown, update the report as "F"
				{
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", alertMessage); // If
																											// "Invalid
																											// User"
																											// Alert is
																											// thrown,
																											// update
																											// the
																											// respective
																											// test
																											// steps to
																											// "F"
					loggerUI.info("Excel updated - loginAlertCheck - F and TC_Status Array - False");
					loggerUI.info(objectName + " --> Button Click - PASS ---> Updated in the report");
					t_Status.add("False");
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
					System.out.println("CHECK 5");
				} else if (!alertMessage.equalsIgnoreCase("")) // If the alert msg contains the word "Invalid" or "Not",
																// update respective test steps to "F"
				{
					System.out.println("CHECK 6");
					System.out.println("CHECK 6_ Alert Message: " + alertMessage);
					if (alertMessage.contains("Number") || alertMessage.contains("number")
							|| alertMessage.contains("Serial") || alertMessage.contains("serial")) {
						if (alertMessage.contains("Record Updated")) // Added for NOVA 20_InwardClearingMastersSetp to
																		// update the status pass for the alert message
																		// "Record Updated"
						{
							objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P",
									"GeneratedSerial/Batch Number: " + generatedID + "_" + alertMessage);
							System.out.println("CHECK 12");
						}

						System.out.println("Alert Message contains no Number, id, invalid :  " + alertMessage);
						loggerUI.info(objectName + " --> Click Button - PASS ---> Updated in the report");
						t_Status.add("True");
						System.out.println("CHECK 8");
					}

				} else {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					loggerUI.info(objectName + " --> Button Click - PASS ---> Updated in the report");
					System.out.println("Excel updated - loginAlertCheck - P and TC_Status Array - True");
					t_Status.add("True");

				}
				/******************
				 * Added to capture Success Message - Ends
				 ************************/
				System.out.println("CHECK 11");
				System.out.println("Alert Message: " + alertMessage);

			} catch (Exception ex) {
				loggerUI.info("CLICK BUTTON Failed");
				loggerUI.error(objectName + " --> Click Button Failed");
				System.out.println("Click Save Button For CBI Failed");
				ex.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				loggerUI.error(objectName + " -->Click Save Button CBI - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				ex.printStackTrace(new PrintWriter(strWriter));
				if (alertMessage.contains("Invalid user")) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", ex.toString());

				} else {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", ex.toString());
				}
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "SETTEXTSERIALID":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				element.clear();
				element.sendKeys(generatedID);
				Thread.sleep(1000);
				element.sendKeys(Keys.ENTER);
				t_Status.add("True");
				System.out.println("Set Text Value : " + value);
				loggerUI.info(objectName + " -->  for Set Text Identified");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", alertMessage);
				break;
			} catch (Exception e3) {
				e3.printStackTrace();
				loggerUI.error(objectName + " --> Set Text - FAIL ---> Updated in the report");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e3.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// #NAB
		case "SETTEXTPRESSENTER":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				try {
					if (element.getText() != "") {
						element.clear();
					}
					element.sendKeys(value);
					Thread.sleep(1000);
					element.sendKeys(Keys.ENTER);
					t_Status.add("True");
					System.out.println("Set Text Value : " + value);
					loggerUI.info(objectName + " -->  for Set Text Identified");
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", alertMessage);
					break;
				} catch (InvalidElementStateException e4) {
					e4.printStackTrace();
					System.out.println("Invalid Element Exception Caught ");
					element.sendKeys(value);
					// element.sendKeys(Integer.parseInt(value));
					// js.executeScript("arguments[0].value=" + value + ";", element);
					Thread.sleep(1000);
					element.sendKeys(Keys.ENTER);
					t_Status.add("True");
					System.out.println("Set Text Value : " + value);
					loggerUI.info(objectName + " -->  for Set Text Identified");
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", alertMessage);
					break;
				} catch (Exception e3) {
					e3.printStackTrace();
					loggerUI.error(objectName + " --> Set Text - FAIL ---> Updated in the report");
					t_Status.add("False");
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e3.toString());
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Set Text Press Enter - FAIL ---> Updated in the report");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e1.toString());
				t_Status.add("False");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added for Utkarsh CBS
		case "PRESSF12":
			try {
				System.out.println("CHECK: " + UIOperations.getObject(objectName));
				WebElement ddlval = driver.findElement((UIOperations.getObject(objectName)));
				ddlval.sendKeys(Keys.F12);
				System.out.println("F12 Key Pressed");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Press F12 - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Press F12 - FAIL ---> Updated in the report");
				tcStatus = "FAIL";
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// selecting the value from the dropdown using visible text
		case "DROPDOWNSELECTTEXT":
			try {
				wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.elementToBeClickable(UIOperations.getObject(objectName)));
				element = driver.findElement(UIOperations.getObject(objectName));
				Select ddl = new Select(element);
				ddl.selectByVisibleText(value);
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> DropDown Select Text - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> DropDown Select Text - FAIL ---> Updated in the report");
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Selecting dropdown by its value
		case "DROPDOWNSELECTVALUE":

			try {
				Thread.sleep(2000);
				element = driver.findElement(UIOperations.getObject(objectName));
				Select ddl = new Select(element);
				ddl.selectByValue(value);
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> DropDown Select Value - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> DropDown Select Value - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Sending value to the drop down
		case "DROPDOWNSENDVALUE":
			try {
				System.out.println("CHECK: " + UIOperations.getObject(objectName));
				wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.elementToBeClickable(UIOperations.getObject(objectName)));
				WebElement ddlval = driver.findElement((UIOperations.getObject(objectName)));
				try {
					ddlval.click();
					Thread.sleep(1000);
				} catch (Exception e) {

				}
				// ddlval.sendKeys(value);
				Actions actions = new Actions(driver);
				actions.moveToElement(ddlval);
				actions.click();
				actions.sendKeys(value);
				actions.build().perform();
				Thread.sleep(2000);
				actions.sendKeys(Keys.DOWN);
				actions.build().perform();
				Thread.sleep(2000);
				actions.sendKeys(Keys.ENTER);
				actions.build().perform();
				Thread.sleep(2000);
				System.out.println("Drop Down Value Selected");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Drop Down Send Value - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> DropDown Send Value - FAIL ---> Updated in the report");
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added for Ithala - LMS
		case "DROPDOWNTYPEVALUE":
			try {
				System.out.println("CHECK: " + UIOperations.getObject(objectName));
				wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.elementToBeClickable(UIOperations.getObject(objectName)));
				WebElement ddlval = driver.findElement((UIOperations.getObject(objectName)));
				ddlval.sendKeys(value);
				Thread.sleep(1000);
				System.out.println("Drop Down Value Selected");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Drop Down Type Value - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Drop Down Type Value - FAIL ---> Updated in the report");
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// Added for JTrust-Payments to select the drop down value from the previous
		// given input - The previous object name should be provided in the test data
		// column
		case "DROPDOWNTYPEVALUEFROMGIVENINPUT":
			try {
				System.out.println("CHECK: " + UIOperations.getObject(objectName));
				wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.elementToBeClickable(UIOperations.getObject(objectName)));
				WebElement ddlval = driver.findElement((UIOperations.getObject(objectName)));
				ddlval.sendKeys(testDataStorage.get(value));
				Thread.sleep(1000);
				System.out.println("Drop Down Value Selected");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(
						objectName + " --> Drop Down Type Value from Given Input- PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(
						objectName + " --> Drop Down Type Value from Given Input - FAIL ---> Updated in the report");
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// Added for JTrust-Payments to enter the value in text boxes from the previous
		// given input - The previous object name should be provided in the test data
		// column
		case "SETTEXTFROMGIVENINPUT":
			try {
				System.out.println("CHECK: " + UIOperations.getObject(objectName));
				element = driver.findElement((UIOperations.getObject(objectName)));
				if (value.contains("_")) {
					String finVal = "";
					String[] valueArr = value.split("_");
					for (String val : valueArr) {
						finVal = finVal + testDataStorage.get(val);

					}
					element.sendKeys(finVal);
				} else {
					element.sendKeys(testDataStorage.get(value));
				}
				Thread.sleep(1000);
				System.out.println("Set Text Entered from the previous inputs");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName
						+ " -->  Set Text Entered from the previous inputs- PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName
						+ " --> Set Text not Entered from the previous inputs - FAIL ---> Updated in the report");
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "OPENAPPLICATION":
			// Launch Application and goto Login Page
			try {
				driver.manage().deleteAllCookies();
				driver.get(value);
				driver.manage().window().maximize();
				// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				Thread.sleep(i);
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Open Application - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Open Application - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
			}
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}
			break;
		// Added - for Ithala - LOS and LMS
		case "OPENARXAPPLICATION":
			// Launch Application and goto Login Page
			try {
				driver.manage().deleteAllCookies();
				driver.get(value);
				// driver.manage().window().maximize();
				// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				Thread.sleep(i);
				loggerUI.info(objectName + " --> Open Application - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", alertMessage);
				loggerUI.info(objectName + " --> Open Application - PASS ---> Updated in the report");
				try {
					/*
					 * if(driver.getTitle().contains("Intellect")) { t_Status.add("True"); } else {
					 * tcStatus = "FAIL"; t_Status.add("False"); ExecuteTest.loginCheckFlag = false;
					 * StringWriter strWriter = new StringWriter();
					 * objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row,
					 * "Error",strWriter.toString()); // getScreenShot(objExecuteTest.evidencePath,
					 * fileName_E); }
					 */
					t_Status.add("True");
				} catch (Exception e) {
					System.out.println("Array Exception");
					e.printStackTrace();
				}

			} catch (Exception e) {
				loggerUI.error(objectName + " --> Open Application - FAIL");
				e.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				loggerUI.error(objectName + " --> Open Application - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
			}
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}
			break;
		case "ENABLECHECKBOX":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				if (element.isSelected() == false) {
					element.click();
				}
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Check Box clicked - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.info("Enable Check Box-Failed");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// #YBL
		case "CLICKMORECHECKBOX":
			try {
				Thread.sleep(1000);
				List<WebElement> elements = driver.findElements(UIOperations.getObject(objectName));
				for (WebElement el : elements) {
					if (el.isEnabled()) {
						Thread.sleep(500);
						Actions action = new Actions(driver);
						action.moveToElement(el);
						// action.click();
						// action.wait(1000);
						// action.sendKeys(Keys.TAB);
						action.build().perform();
						JavascriptExecutor executor = (JavascriptExecutor) driver;
						executor.executeScript("arguments[0].click();", el);
					} else {
						System.out.println("element is not enabled");
					}

				}

				loggerUI.info(objectName + " --> Set Text - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", alertMessage);
				loggerUI.info(objectName + " --> Set Text - PASS ---> Updated in the report");
				t_Status.add("True");

			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.info("Click More Check Box Updated as Fail");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "SETTEXT":
			try {

				element = driver.findElement(UIOperations.getObject(objectName));
				System.out.println("Element_Set Text: " + element);
				element.clear();
				element.sendKeys(value);
				t_Status.add("True");
				loggerUI.info(objectName + " -->  for Set Text Performed");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Set Text - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// Created for IDC 19.1 to enter the text using action class
		case "SETTEXTACTIONCLASS":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				System.out.println("Element_Set Text: " + element);
				act.moveToElement(element);
				act.sendKeys(element, value);
				act.build().perform();
				t_Status.add("True");
				loggerUI.info(objectName + " -->  for Set Text Performed");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Set Text - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added for Utkarsh-CBS
		case "SETTEXTDOUBLECLICK":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				String s = element.getText();
				if ((element.getText().equals("")) || (element.getText().equals(" "))) {
					// act.doubleClick(element).sendKeys(value,Keys.ENTER).build().perform();
					act.doubleClick(element);
					act.sendKeys(value);
					act.sendKeys(Keys.ENTER);
					act.build().perform();
					System.out.println("Double Clicked and value entered");
				} else {
					act.doubleClick(element).build().perform();
					driver.switchTo().activeElement().clear();
					act.doubleClick(element).sendKeys(value).build().perform();
				}
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Set Text Double Click - PASS ---> Updated in the report");
				t_Status.add("True");
				if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Set Text Double Click - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "F", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			break;

		// Created by ganeshan to enter the values in the text fields which has more
		// than one fields in DOM
		case "SETTEXTMULTIPLEVALUES":
			try {
				List<WebElement> elements = driver.findElements(UIOperations.getObject(objectName));
				for (WebElement el : elements) {
					Actions actions = new Actions(driver);
					actions.moveToElement(el);
					actions.click();
					actions.sendKeys(value);
					actions.build().perform();
				}
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Set Text Multiple Values - PASS ---> Updated in the report");
				t_Status.add("True");

			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Set Text Multiple Values - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// # NAB - To click on the grid based on the account number and the serial id
		// generated
		case "SEARCHSOURCEKEYANDCLICK":

			if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
			} catch (Exception e) {
				loggerUI.info("Unable to Find Search Result Values in authorization table");
				loggerUI.info("Exception In Finding Search Result Table");
				e.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				System.out.println(objectName + " --> Search Result Table - FAIL ---> Updated in the report");
				loggerUI.error(objectName + " --> Search Result Table - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
				break;
			}

			System.out.println("Test Case Name: " + testCase);
			try {
				System.out.println("Value of SerialID in the Excel: " + value);
				List<WebElement> authTableRows = element.findElements(By.tagName("tr"));
				System.out.println("Rows Count: " + authTableRows.size());
				for (WebElement authTableRow : authTableRows) {
					List<WebElement> authTableValues = authTableRow.findElements(By.tagName("td"));
					System.out.println("Columnss Count: " + authTableValues.size());
					int columnCount = 1;
					for (WebElement authTableValue : authTableValues) {
						System.out.println("1 - AuthTableValue  Before IF Conditon: " + authTableValue.getText());
						if (value != "") {
							String expval = value + "|" + generatedID;
							if (authTableValue.getText().equalsIgnoreCase(expval)) {
								System.out
										.println("2 - AuthTableValue  Inside IF Conditon: " + authTableValue.getText());
								act.moveToElement(authTableValue).doubleClick().build().perform();
								Thread.sleep(2000);
								searchFlag = true;
							} else if (authTableValue.getText().contains(value)) {
								System.out
										.println("2 - AuthTableValue  Inside IF Conditon: " + authTableValue.getText());
								act.moveToElement(authTableValue).doubleClick().build().perform();
								Thread.sleep(2000);
								searchFlag = true; // SearchFlag set as TRUE since the value is found
							}
						} else {
							if ((generatedID == "") || (generatedSerial == "")) {
								objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
										"The number is not generated so please check the respective creation scenario");
								System.out.println(
										"The number is not generated so please check the respective creation scenario");
							} else if (testCase.contains("EntryOfTransaction")) {
								if (authTableValue.getText().contains("/")) {
									String authTableSplitBatchNumber[] = authTableValue.getText().split("/");
									if (authTableSplitBatchNumber[2].equalsIgnoreCase(generatedID)) {
										act.moveToElement(authTableValue).doubleClick().build().perform();
										Thread.sleep(2000);
										searchFlag = true;
									}
								}
							} else if (fileName_E.contains("InwardClearingMastersSetp")) {
								if (authTableValue.getText().contains("ICLGBATCH")) {
									act.moveToElement(authTableValue).doubleClick().build().perform();
									Thread.sleep(2000);
									searchFlag = true;
								}
							} else if (programId.contains("abopauthq")) {
								if (authTableValue.getText().contains("/")) {
									String authTableSplitBatchNumber[] = authTableValue.getText().split("/");
									if (authTableSplitBatchNumber[2].contains(generatedID)) {
										System.out.println("Inside If Statement" + authTableSplitBatchNumber[2]);
										act.moveToElement(authTableValue).doubleClick().build().perform();
										Thread.sleep(2000);
										searchFlag = true;
									}
								}
							} else if (programId.contains("addpoauth")) {
								if (columnCount == 6) {
									System.out.println("Day Serial Table values" + authTableValue.getText());
									System.out.println("Generated Serial Number" + generatedSerial);
									if (authTableValue.getText().contains(generatedSerial)) {

										act.moveToElement(authTableValue).doubleClick().build().perform();
										Thread.sleep(2000);
										searchFlag = true;
									}

								}
							}

							else if (programId.contains("acbauth")) {
								if (columnCount == 6) {
									System.out.println("Entry Serial Table values" + authTableValue.getText());
									System.out.println("Generated Serial Number" + generatedSerial);
									if (authTableValue.getText().contains(generatedSerial)) {
										act.moveToElement(authTableValue).doubleClick().build().perform();
										Thread.sleep(2000);
										searchFlag = true;
									}

								}
							}

							else if (authTableValue.getText().contains(generatedID)) {
								System.out
										.println("3 - AuthTableValue  Inside IF Conditon: " + authTableValue.getText());
								act.moveToElement(authTableValue).doubleClick().build().perform();
								Thread.sleep(2000);
								searchFlag = true; // SearchFlag set as TRUE since the value is found
							}
						}

						columnCount++;
					}

				}
				if (searchFlag) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					t_Status.add("True");
					if (fileName_E.contains("CorporateClientMaster")) {
						corporateClientId = generatedID;
					} else {
						if (fileName_E.contains("AccountOpening")) {
							generatedAccountNumber = generatedID;
						}
					}
				} else {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
							"The Search Value Is Not Found");
					t_Status.add("False");
				}
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			} catch (WebDriverException e) {
				e.printStackTrace();
				System.out.println("WebDriver Exception Occured");
				if (searchFlag) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					t_Status.add("True");
				}

				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			} catch (Exception e) {
				loggerUI.info("Unable to traverse the webtable in the Search Result table");
				e.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				loggerUI.error(objectName + " --> Search Web Table - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
			}

			break;

		//
		case "SEARCHSOURCEKEYANDCLICKUTKARSH":

			if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);

			}
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
			} catch (Exception e) {
				loggerUI.info("Unable to Find Search Result Values in authorization table");
				loggerUI.info("Exception In Finding Search Result Table");
				e.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				System.out.println(objectName + " --> Search Result Table - FAIL ---> Updated in the report");
				loggerUI.error(objectName + " --> Search Result Table - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
				break;
			}

			System.out.println("Test Case Name: " + testCase);
			try {
				System.out.println("Value of SerialID in the Excel: " + value);
				List<WebElement> authTableRows = element.findElements(By.tagName("tr"));
				System.out.println("Rows Count: " + authTableRows.size());
				for (WebElement authTableRow : authTableRows) {
					List<WebElement> authTableValues = authTableRow.findElements(By.tagName("td"));
					System.out.println("Columnss Count: " + authTableValues.size());
					int columnCount = 1;
					for (WebElement authTableValue : authTableValues) {
						System.out.println("1 - AuthTableValue  Before IF Conditon: " + authTableValue.getText());
						if (value != "") {
							String expval = value + "|" + generatedID;
							if (value.contains("AccountNumber")) {
								if (authTableValue.getText().contains(generatedNumberCollection.get("AccountNumber"))) {
									System.out.println(
											"2 - AuthTableValue  Inside IF Conditon: " + authTableValue.getText());
									tableElement = authTableValue;
									t = new Thread(this, "Thread_1");
									t.start();
									// act.moveToElement(authTableValue).doubleClick().build().perform();
									Thread.sleep(2000);
									searchFlag = true;
								}
							} else if (authTableValue.getText().equalsIgnoreCase(expval)) {
								System.out
										.println("2 - AuthTableValue  Inside IF Conditon: " + authTableValue.getText());
								tableElement = authTableValue;
								t = new Thread(this, "Thread_1");
								t.start();
								// act.moveToElement(authTableValue).doubleClick().build().perform();
								Thread.sleep(2000);
								searchFlag = true;
							} else if (authTableValue.getText().equalsIgnoreCase(value)) {
								System.out
										.println("2 - AuthTableValue  Inside IF Conditon: " + authTableValue.getText());
								tableElement = authTableValue;
								t = new Thread(this, "Thread_1");
								t.start();
								// act.moveToElement(authTableValue).doubleClick().build().perform();
								Thread.sleep(2000);
								searchFlag = true; // SearchFlag set as TRUE since the value is found
							} else if ((generatedID != null) && (generatedID != "")) {
								if (authTableValue.getText().contains(generatedID)) {
									System.out.println(
											"2 - AuthTableValue  Inside IF Conditon: " + authTableValue.getText());
									tableElement = authTableValue;
									t = new Thread(this, "Thread_1");
									t.start();
									// act.moveToElement(authTableValue).doubleClick().build().perform();
									Thread.sleep(2000);
									searchFlag = true; // SearchFlag set as TRUE since the value is found
								}
							}
						} else {
							if ((generatedID == "") || (generatedSerial == "")) {
								objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
										"The number is not generated so please check the respective creation scenario");
								System.out.println(
										"The number is not generated so please check the respective creation scenario");
							} else if (testCase.contains("EntryOfTransaction")) {
								if (authTableValue.getText().contains("/")) {
									String authTableSplitBatchNumber[] = authTableValue.getText().split("/");
									if (authTableSplitBatchNumber[2].equalsIgnoreCase(generatedID)) {
										tableElement = authTableValue;
										t = new Thread(this, "Thread_1");
										t.start();
										// act.moveToElement(authTableValue).doubleClick().build().perform();
										Thread.sleep(2000);
										searchFlag = true;
									}
								}
							} else if (fileName_E.contains("InwardClearingMastersSetp")) {
								if (authTableValue.getText().contains("ICLGBATCH")) {
									tableElement = authTableValue;
									t = new Thread(this, "Thread_1");
									t.start();
									// act.moveToElement(authTableValue).doubleClick().build().perform();
									Thread.sleep(2000);
									searchFlag = true;
								}
							} else if (programId.contains("abopauthq")) {
								if (authTableValue.getText().contains("/")) {
									String authTableSplitBatchNumber[] = authTableValue.getText().split("/");
									if (authTableSplitBatchNumber[2].contains(generatedID)
											|| authTableSplitBatchNumber[2]
													.contains(generatedNumberCollection.get("BatchNumber"))) {
										System.out.println("Inside If Statement" + authTableSplitBatchNumber[2]);
										tableElement = authTableValue;
										t = new Thread(this, "Thread_1");
										t.start();
										// act.moveToElement(authTableValue).doubleClick().build().perform();
										Thread.sleep(2000);
										searchFlag = true;
									}
								}
							} else if (programId.contains("aolcdepauth")) {
								if (generatedNumberCollection.get("SerialBatchNumber") != null) {
									if (authTableValue.getText()
											.equalsIgnoreCase(generatedNumberCollection.get("SerialBatchNumber"))
											|| authTableValue.getText()
													.contains(generatedNumberCollection.get("SerialBatchNumber"))) {
										System.out.println("Inside If Statement" + "aolcdepauth");
										tableElement = authTableValue;
										t = new Thread(this, "Thread_1");
										t.start();
										// act.moveToElement(authTableValue).doubleClick().build().perform();
										Thread.sleep(2000);
										searchFlag = true;
									}
								} else {
									System.out.println("CBI - SearchSourceKey - 1");
									System.out.println(
											"Get Serial Number: " + generatedNumberCollection.get("SerialNumber1"));
									if (authTableValue.getText()
											.equalsIgnoreCase(generatedNumberCollection.get("SerialNumber1"))) {
										System.out.println("Inside If Statement" + "aolcdepauth");
										tableElement = authTableValue;
										t = new Thread(this, "Thread_1");
										t.start();
										Thread.sleep(2000);
										searchFlag = true;
										System.out.println("CBI - SearchSourceKey - 2");
									}

								}
							} else if (programId.contains("addpoauth")) {
								if (columnCount == 6) {
									System.out.println("Day Serial Table values" + authTableValue.getText());
									System.out.println("Generated Serial Number" + generatedSerial);
									if (authTableValue.getText().contains(generatedSerial)) {

										tableElement = authTableValue;
										t = new Thread(this, "Thread_1");
										t.start();
										// act.moveToElement(authTableValue).doubleClick().build().perform();
										Thread.sleep(2000);
										searchFlag = true;
									}

								}
							}

							else if (programId.contains("acbauth")) {
								if (columnCount == 6) {
									System.out.println("Entry Serial Table values" + authTableValue.getText());
									System.out.println("Generated Serial Number" + generatedSerial);
									if (authTableValue.getText().contains(generatedSerial)) {
										tableElement = authTableValue;
										t = new Thread(this, "Thread_1");
										t.start();
										// act.moveToElement(authTableValue).doubleClick().build().perform();
										Thread.sleep(2000);
										searchFlag = true;
									}

								}
							}

							else if (authTableValue.getText().equalsIgnoreCase(generatedID)) {

								System.out
										.println("3 - AuthTableValue  Inside IF Conditon: " + authTableValue.getText());
								if (authTableValue.getText().contains("|")) {
									String textSearchSourceKey = authTableValue.getText();
									String splitArr[] = textSearchSourceKey.split("\\|");
									if (splitArr[1].equals(generatedID)) {
										tableElement = authTableValue;
										t = new Thread(this, "Thread_1");
										t.start();
									}
								} else {
									tableElement = authTableValue;
									t = new Thread(this, "Thread_1");
									t.start();
									// act.moveToElement(authTableValue).doubleClick().build().perform();
									Thread.sleep(2000);
								}
								searchFlag = true; // SearchFlag set as TRUE since the value is found
							}
						}

						columnCount++;
					}

				}
				if (searchFlag) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					t_Status.add("True");
					if (fileName_E.contains("CorporateClientMaster")) {
						corporateClientId = generatedID;
					} else {
						if (fileName_E.contains("AccountOpening")) {
							generatedAccountNumber = generatedID;
						}
					}
				} else {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
							"The Search Value Is Not Found");
					t_Status.add("False");
				}
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			} catch (WebDriverException e) {
				e.printStackTrace();
				System.out.println("WebDriver Exception Occured");
				if (searchFlag) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					t_Status.add("True");
				}

				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			} catch (Exception e) {
				loggerUI.info("Unable to traverse the webtable in the Search Result table");
				e.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				loggerUI.error(objectName + " --> Search Web Table - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
			}

			break;

		case "TAKESNAPSHOT":

			if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				t_Status.add("True");
			}
			break;

		case "PRESSENTER":

			try {
				if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
				element = driver.findElement(UIOperations.getObject(objectName));
				element.sendKeys(Keys.ENTER);
				t_Status.add("True");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " -->  for Press Enter Identified");

			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Press Enter - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			break;

		case "PRESSENTERUTKARSH":

			try {
				if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
				element = driver.findElement(UIOperations.getObject(objectName));
				t = new Thread(this, "Thread_2");
				t.start();
				t_Status.add("True");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " -->  for Press Enter Identified");

			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Press Enter - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			break;

		case "CLICKBUTTONUTKARSH":

			try {
				if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
				element = driver.findElement(UIOperations.getObject(objectName));
				t = new Thread(this, "Thread_2");
				t.start();
				t_Status.add("True");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " -->  for Click Button Utkarsh Performed");

			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Click Button Utkarsh - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			break;

		case "PRESSENTEREIGHTTIMES":
			System.out.println("Here:  Press Enter Eight Times");
			try {
				if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
				element = driver.findElement(UIOperations.getObject(objectName));
				element.sendKeys(Keys.ENTER, Keys.ENTER, Keys.ENTER, Keys.ENTER, Keys.ENTER, Keys.ENTER, Keys.ENTER,
						Keys.ENTER);
				t_Status.add("True");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " -->  for Press Enter Eight Times Identified");

			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Press Enter Eight Times - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			break;

		// Get and Verify the text
		case "GETANDVERIFYTEXT":
			try {
				wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.presenceOfElementLocated(UIOperations.getObject(objectName)));
				textvalue = driver.findElement(UIOperations.getObject(objectName)).getText();
				if (textvalue.length() < 1) {
					textvalue = driver.findElement(UIOperations.getObject(objectName)).getAttribute("value");
				}
				gettextvalue = textvalue.replaceAll("^\\s+|\\s+$", "");
				testdatavalue = value.replace("^\\s+|\\s+$", "");
				if (gettextvalue.trim().equals(testdatavalue.trim())) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P",
							"Generated Value is" + " " + textvalue + ",Expected value is" + " " + value);
					loggerUI.info(objectName + " --> Verified Text - PASS ---> Updated in the report");
					t_Status.add("True");
				} else {
					t_Status.add("False");
					loggerUI.error(objectName + " --> Verify Text - FAIL ---> Updated in the report");
					StringWriter strWriter = new StringWriter();
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
							"Expected value is" + ":" + gettextvalue + "," + "TestData Value is" + ":" + testdatavalue);
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Get Text - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "PRESSENTERNINETIMES":
			System.out.println("Here:  Press Enter Eight Times");
			try {
				if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
				element = driver.findElement(UIOperations.getObject(objectName));
				element.sendKeys(Keys.ENTER, Keys.ENTER, Keys.ENTER, Keys.ENTER, Keys.ENTER, Keys.ENTER, Keys.ENTER,
						Keys.ENTER, Keys.ENTER);
				t_Status.add("True");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " -->  for Press Enter Eight Times Identified");

			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Press Enter Eight Times - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			break;

		case "PRESSENTERTENTIMES": // Added for CBA Application

			try {
				if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
				element = driver.findElement(UIOperations.getObject(objectName));
				element.sendKeys(Keys.ENTER, Keys.ENTER, Keys.ENTER, Keys.ENTER, Keys.ENTER, Keys.ENTER, Keys.ENTER,
						Keys.ENTER, Keys.ENTER, Keys.ENTER);
				t_Status.add("True");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " -->  for Press Enter Eight Times Identified");

			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Press Enter Eight Times - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			break;
		// Get the text of a given object and store it in a variable
		case "GETTEXT":
			try {
				wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.presenceOfElementLocated(UIOperations.getObject(objectName)));
				textvalue = driver.findElement(UIOperations.getObject(objectName)).getText();
				if (textvalue.length() < 1) {
					textvalue = driver.findElement(UIOperations.getObject(objectName)).getAttribute("value");
				}
				System.out.println("Get Text Value: " + textvalue);
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Get Text - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Get Text - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Set the Captured text from GETTEXT Keyword and type in the given text
		// box(Added for Jtrust-Payments)
		case "SETCAPTUREDTEXT":
			try {

				element = driver.findElement(UIOperations.getObject(objectName));
				System.out.println(textvalue);
				element.sendKeys(textvalue);
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Set Captured Text - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Set Captured Text - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added for SBS-LMS
		case "GETREFNUMBER":
			try {
				textvalue = driver.findElement(UIOperations.getObject(objectName)).getText();
				String[] LMSAccNum = null;
				LMSAccNum = textvalue.split(value);
				/*
				 * switch (value) { case "Account NO:": LMSAccNum = textvalue.split(value);
				 * break;
				 * 
				 * case "Account No:": LMSAccNum = textvalue.split(value); break;
				 * 
				 * case "Reference No.:": LMSAccNum = textvalue.split(value); break; }
				 */

				Pattern p = Pattern.compile("\\d+");
				Matcher m = p.matcher(LMSAccNum[1]);
				while (m.find()) {
					System.out.println(m.group());
					LMSAccountNumber = m.group();
				}
				System.out.println("Account Number Generated/Reference Number Generated: " + LMSAccountNumber);
				generatedNumberCollection.put(value, LMSAccountNumber);
				if (textvalue.length() < 1) {
					textvalue = driver.findElement(UIOperations.getObject(objectName)).getAttribute("value");
				}
				System.out.println(textvalue);
				loggerUI.info("LMS Account Number Generated" + LMSAccountNumber);
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", LMSAccountNumber);
				loggerUI.info(objectName + " --> Get Ref Number LMS - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Get Ref Number LMS - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added for Jtrust-Payments Input should be given in theis format - Txn Ref.
		// Num_BSLF/Instruction Ref. No_AS
		case "GETREFNUMBERWITHINPUT":
			try {
				String refNumber = driver.findElement(UIOperations.getObject(objectName)).getText();
				String updatedText = refNumber.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "");
				if (value != null && value != "") {
					if (value.contains("_")) {
						String splitValues[] = value.split("_");
						Pattern pattern = Pattern.compile(splitValues[0] + ".+" + splitValues[1] + "\\d+");
						Matcher matcher = pattern.matcher(updatedText);
						while (matcher.find()) {
							System.out.println("Found match at: " + matcher.group());
							String requiredRef = matcher.group();
							Pattern pattern1 = Pattern.compile("\\w+\\d+");
							Matcher matcher1 = pattern1.matcher(requiredRef);
							while (matcher1.find()) {
								System.out.println("Found match at: " + matcher1.group());
								textvalue = matcher1.group();
							}
						}
					} else {
						Pattern pattern = Pattern.compile("\\d+");
						Matcher matcher = pattern.matcher(updatedText);
						while (matcher.find()) {
							System.out.println("Found match at: " + matcher.group());
							textvalue = matcher.group();
						}
					}
				} else {
					Pattern pattern = Pattern.compile("\\d+");
					Matcher matcher = pattern.matcher(updatedText);
					while (matcher.find()) {
						System.out.println("Found match at: " + matcher.group());
						textvalue = matcher.group();
					}
				}

				System.out.println("RefNumberGenerated" + textvalue);
				loggerUI.info("RefNumberGenerated" + textvalue);
				if (objectName.contains("OrderNumber") || objectName.contains("ordernumber")) {
					generatedNumberCollection.put("AccountNumber", textvalue);
				}
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", textvalue);
				loggerUI.info(objectName + " --> Get Ref Number With Input - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Get Ref Number with input- FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added for SBS LMS
		case "SETREFNUMBER":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				if (LMSAccountNumber != null) {
					element.sendKeys(LMSAccountNumber);
					System.out.println("LMS Account Number entered successfully");
				} else {
					element.sendKeys(textvalue);
					System.out.println("Captured ref number entered successfully");
				}
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Set Ref Number - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Set Ref Number - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added for SBS LMS
		case "SEARCHREFNUMFROMGRIDANDCLICK":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				List<WebElement> tableList = element.findElements(By.tagName("table"));
				int tableCount = 1;
				for (WebElement table : tableList) {
					WebElement tbodyElement = table.findElement(By.tagName("tbody"));
					List<WebElement> authTableRows = tbodyElement.findElements(By.tagName("tr"));
					System.out.println("Rows Count: " + authTableRows.size());
					int rCount = 1;
					for (WebElement authTableRow : authTableRows) {
						List<WebElement> authTableValues = authTableRow.findElements(By.tagName("td"));
						System.out.println("Columnss Count: " + authTableValues.size());
						int columnCount = 1;
						for (WebElement authTableValue : authTableValues) {
							System.out.println("1 - AuthTableValue  Before IF Conditon: " + authTableValue.getText());
							if (value != "") {
								String expval = value + "|" + generatedID;
								if (authTableValue.getText().equalsIgnoreCase(expval)) {
									System.out.println(
											"2 - AuthTableValue  Inside IF Conditon: " + authTableValue.getText());
									authTableValue.click();
									Thread.sleep(2000);
									searchFlag = true;
								} else if (authTableValue.getText().contains(value)) {
									System.out.println(
											"2 - AuthTableValue  Inside IF Conditon: " + authTableValue.getText());
									authTableValue.click();
									Thread.sleep(2000);
									searchFlag = true; // SearchFlag set as TRUE since the value is found
								}
							} else {
								if (LMSAccountNumber != "" && LMSAccountNumber != null) {
									if (authTableValue.getText().contains(LMSAccountNumber)) {
										System.out.println(
												"3 - AuthTableValue  Inside IF Conditon: " + authTableValue.getText());
										authTableValue.click();
										Thread.sleep(2000);
										searchFlag = true;
									}
								} else {
									if (authTableValue.getText().contains(textvalue)) {
										System.out.println(
												"3 - AuthTableValue  Inside IF Conditon: " + authTableValue.getText());
										authTableValue.click();
										Thread.sleep(2000);
										searchFlag = true;
									}
								}
							}
							columnCount++;
						}
						rCount++;
					}
					tableCount++;
				}

				if (searchFlag) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					t_Status.add("True");
				}

				System.out.println("LMS Account Number Clicked successfully");
				loggerUI.info(objectName + " --> Search Ref Num From Grid and Click - PASS");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(
						objectName + " --> Search Ref Num From Grid and Click - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added for SBS LMS
		case "SEARCHREFNUMFROMGRIDANDACTIONCLICK":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				List<WebElement> tableList = element.findElements(By.tagName("table"));
				int tableCount = 1;
				for (WebElement table : tableList) {
					WebElement tbodyElement = table.findElement(By.tagName("tbody"));
					List<WebElement> authTableRows = tbodyElement.findElements(By.tagName("tr"));
					System.out.println("Rows Count: " + authTableRows.size());
					int rCount = 1;
					for (WebElement authTableRow : authTableRows) {
						List<WebElement> authTableValues = authTableRow.findElements(By.tagName("td"));
						System.out.println("Columnss Count: " + authTableValues.size());
						int columnCount = 1;
						for (WebElement authTableValue : authTableValues) {
							System.out.println("1 - AuthTableValue  Before IF Conditon: " + authTableValue.getText());
							if (value != "") {
								String expval = value + "|" + generatedID;
								if (authTableValue.getText().equalsIgnoreCase(expval)) {
									System.out.println(
											"2 - AuthTableValue  Inside IF Conditon: " + authTableValue.getText());
									act.click(authTableValue).build().perform();
									// authTableValue.click();
									Thread.sleep(2000);
									searchFlag = true;
								} else if (authTableValue.getText().contains(value)) {
									System.out.println(
											"2 - AuthTableValue  Inside IF Conditon: " + authTableValue.getText());
									act.click(authTableValue).build().perform();
									// authTableValue.click();
									Thread.sleep(2000);
									searchFlag = true; // SearchFlag set as TRUE since the value is found
								}
							} else {
								if (LMSAccountNumber != null && LMSAccountNumber != "") {
									if (authTableValue.getText().contains(LMSAccountNumber)) {
										System.out.println(
												"3 - AuthTableValue  Inside IF Conditon: " + authTableValue.getText());
										act.click(authTableValue).build().perform();
										// authTableValue.click();
										Thread.sleep(2000);
										searchFlag = true;
									}
								} else {
									if (authTableValue.getText().contains(textvalue)) {
										System.out.println(
												"3 - AuthTableValue  Inside IF Conditon: " + authTableValue.getText());
										act.click(authTableValue).build().perform();
										// authTableValue.click();
										Thread.sleep(2000);
										searchFlag = true;
									}
								}
							}
							columnCount++;
						}
						rCount++;
					}
					tableCount++;
				}

				if (searchFlag) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					t_Status.add("True");
				}

				System.out.println("LMS Account Number Clicked successfully");
				loggerUI.info(objectName + " --> Search Ref Num From Grid and Action Click - PASS");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName
						+ " --> Search Ref Num From Grid and Action Click - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// Added for SBS-LMS
		case "SEARCHBUTTONFROMGRIDANDCLICK":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				List<WebElement> tableList = element.findElements(By.tagName("table"));
				int tableCount = 1;
				for (WebElement table : tableList) {
					WebElement tbodyElement = table.findElement(By.tagName("tbody"));
					List<WebElement> authTableRows = tbodyElement.findElements(By.tagName("tr"));
					System.out.println("Rows Count: " + authTableRows.size());
					int rCount = 1;
					for (WebElement authTableRow : authTableRows) {
						List<WebElement> authTableValues = authTableRow.findElements(By.tagName("td"));
						System.out.println("Columnss Count: " + authTableValues.size());
						int columnCount = 1;
						for (WebElement authTableValue : authTableValues) {
							System.out.println("1 - AuthTableValue  Before IF Conditon: " + authTableValue.getText());
							if (value != "") {
								try {
									WebElement btn = authTableValue.findElement(By.tagName("a"));
									if (btn.getText().contains(value)) {
										btn.click();
										searchFlag = true;
										break;

									}
								} catch (Exception e1) {
									System.out.println("Button not found");
								}
							}

							columnCount++;
						}
						rCount++;
					}
					tableCount++;
				}

				if (searchFlag) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					t_Status.add("True");
				}

				System.out.println("LMS Account Number Clicked successfully");
				loggerUI.info(objectName + " --> Search Ref Num From Grid and Click - PASS");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(
						objectName + " --> Search Ref Num From Grid and Click - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// Added for SBS-LMS
		case "SEARCHVALUEFROMGRIDANDRADIOBUTTONCLICK":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				List<WebElement> excessAdjustmentRows = element.findElements(By.tagName("tr"));
				System.out.println("Rows Count: " + excessAdjustmentRows.size());
				int rCount = 1;

				for (WebElement excessAdjustmentRow : excessAdjustmentRows) {
					WebElement radioButton = null;
					List<WebElement> excessAdjustmentValues = excessAdjustmentRow.findElements(By.tagName("td"));
					System.out.println("Columnss Count: " + excessAdjustmentValues.size());
					int columnCount = 1;
					for (WebElement excessAdjustmentValue : excessAdjustmentValues) {
						System.out
								.println("1 - AuthTableValue  Before IF Conditon: " + excessAdjustmentValue.getText());
						if (value != "") {
							if (columnCount == 1) {
								try {
									radioButton = excessAdjustmentValue.findElement(By.tagName("input"));
								} catch (Exception e) {
									e.printStackTrace();
									System.out.println("radioButton Element is not present");
								}
							}
							if (excessAdjustmentValue.getText().contains(value)) {
								System.out.println("2 - ExcessAdjustmentValue  Inside IF Conditon: "
										+ excessAdjustmentValue.getText());
								radioButton.click();
								Thread.sleep(2000);
								searchFlag = true;
							}
						} else {
							System.out.println("There is no input to search the grid");
						}
						columnCount++;
					}
					rCount++;
				}

				if (searchFlag) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					t_Status.add("True");
				}

				System.out.println("Searched the given value and clicked the radio button");
				loggerUI.info(objectName + " --> Searched the given value and clicked the radio button");
			} catch (Exception e) {
				loggerUI.error(objectName + " --> Searched the given value and clicked the radio button");
				e.printStackTrace();
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// Added for SBS-LMS
		case "SEARCHVALUEFROMGRIDANDCLICKONLASTROW":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				List<WebElement> excessAdjustmentRows = element.findElements(By.tagName("tr"));
				System.out.println("Rows Count: " + excessAdjustmentRows.size());
				int rCount = excessAdjustmentRows.size();
				for (int i = rCount; i > 0; i--) {
					if (searchFlag) {
						break;
					}
					WebElement amountLink = null;
					List<WebElement> excessAdjustmentValues = excessAdjustmentRows.get(i - 1)
							.findElements(By.tagName("td"));
					System.out.println("Columnss Count: " + excessAdjustmentValues.size());
					int columnCount = excessAdjustmentValues.size();
					for (WebElement excessAdjustmentValue : excessAdjustmentValues) {
						System.out
								.println("1 - AuthTableValue  Before IF Conditon: " + excessAdjustmentValue.getText());

						try {
							amountLink = excessAdjustmentValue.findElement(By.tagName("a"));
							amountLink.click();
							searchFlag = true;
							break;
						} catch (Exception e) {
							System.out.println("The element is not a link");
						}
					}
				}
				if (searchFlag) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					t_Status.add("True");
				}

				System.out.println("Searched the given value and clicked the Last Row Link");
				loggerUI.info(objectName + " --> Searched the given value and clicked the Last Row Link");
			} catch (Exception e) {
				loggerUI.error(objectName + " --> Searched the given value and clicked the Last Row Link");
				e.printStackTrace();
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "SETTEXTONLASTROWOFGRID":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				List<WebElement> excessAdjustmentRows = element.findElements(By.tagName("tr"));
				System.out.println("Rows Count: " + excessAdjustmentRows.size());
				int rCount = excessAdjustmentRows.size();
				int rowCount = 0;
				List<WebElement> rowElements = driver.findElements(By.xpath("//td[@class='static']"));
				for (WebElement rowElement : rowElements) {
					String roCount = rowElement.getText();
					if (roCount != "") {
						try {
							rowCount = Integer.parseInt(roCount);
						} catch (Exception e) {
							System.out.println("row count is not valid");
						}
					}
				}

				String splitInput[] = value.split("_");

				for (int i = rowCount; i > 0; i--) {
					if (searchFlag) {
						break;
					}
					WebElement installmentAmount = null;
					List<WebElement> excessAdjustmentValues = excessAdjustmentRows.get(i)
							.findElements(By.tagName("td"));
					System.out.println("Columnss Count: " + excessAdjustmentValues.size());
					int columnCount = excessAdjustmentValues.size();
					int cCount = 1;
					for (WebElement excessAdjustmentValue : excessAdjustmentValues) {
						try {
							System.out.println(
									"1 - AuthTableValue  Before IF Conditon: " + excessAdjustmentValue.getText());
							if (cCount == Integer.parseInt(splitInput[1])) {
								installmentAmount = excessAdjustmentValue.findElement(By.tagName("input"));
								installmentAmount.clear();
								installmentAmount.sendKeys(splitInput[0]);
								searchFlag = true;
								break;
							}
						}

						catch (Exception e) {
							System.out.println("The element is not a Input");
						}
						cCount++;
					}
				}
				if (searchFlag) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					t_Status.add("True");
				}

				System.out.println("Set Text on Last row of Grid On the Given Column Number");
				loggerUI.info(objectName + " --> Set Text on Last row of Grid On the Given Column Number");
			} catch (Exception e) {
				loggerUI.error(objectName + " --> Set Text on Last row of Grid On the Given Column Number");
				e.printStackTrace();
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added for SBS-LMS
		case "SEARCHVALUEFROMGRIDANDSETTEXT":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				boolean searFlag = false;
				List<WebElement> excessAdjustmentRows = element.findElements(By.tagName("tr"));
				System.out.println("Rows Count: " + excessAdjustmentRows.size());
				int rCount = 1;
				String[] inputData = value.split("_");
				for (WebElement excessAdjustmentRow : excessAdjustmentRows) {
					WebElement inputBox = null;
					List<WebElement> excessAdjustmentValues = excessAdjustmentRow.findElements(By.tagName("td"));
					System.out.println("Columnss Count: " + excessAdjustmentValues.size());
					int columnCount = 1;
					for (WebElement excessAdjustmentValue : excessAdjustmentValues) {
						System.out
								.println("1 - AuthTableValue  Before IF Conditon: " + excessAdjustmentValue.getText());
						if (value != "") {
							if (columnCount == 8) {
								if (searFlag) {
									inputBox = excessAdjustmentValue.findElement(By.tagName("input"));
									inputBox.sendKeys(inputData[1]);
									searFlag = false;
									break;
								}
							}
							if (excessAdjustmentValue.getText().contains(inputData[0])) {
								System.out.println("2 - ExcessAdjustmentValue  Inside IF Conditon: "
										+ excessAdjustmentValue.getText());
								searFlag = true;
							}
						} else {
							System.out.println("There is no input to search the grid");
						}
						columnCount++;
					}
					rCount++;
				}

				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				t_Status.add("True");

				System.out.println("Searched the given value and clicked the radio button");
				loggerUI.info(objectName + " --> Searched the given value and clicked the radio button");
			} catch (Exception e) {
				loggerUI.error(objectName + " --> Searched the given value and clicked the radio button");
				e.printStackTrace();
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// Compare it with the gettext keyword with no testdata but not exactly it
		// compares the partial value as well
		case "COMPAREGETTEXTNUMBERS":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				// gettextvalue = textvalue.toString();
				gettextvalue = textvalue.toString().replaceAll("[^0-9]", "");
				testdatavalue = element.getText();
				String textval = testdatavalue.toString().replaceAll("[^0-9]", "");
				if (gettextvalue.trim().equals(textval.trim())) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P",
							"Generated Value is" + " " + textvalue + ",Expected value is" + " " + testdatavalue);
					loggerUI.info(objectName + " --> Verified Text - PASS ---> Updated in the report");
					t_Status.add("True");
				} else {
					t_Status.add("False");
					loggerUI.error(objectName + " --> Verify Text - FAIL ---> Updated in the report");
					StringWriter strWriter = new StringWriter();
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
							"Expected value is" + ":" + gettextvalue + "," + "TestData Value is" + ":" + testdatavalue);
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
				}
			} catch (Exception e) {
				System.out.println("Failed in verify text");
				t_Status.add("False");
				loggerUI.error(objectName + " --> Verify Text - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
						value + "-" + "Fields is not displayed in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}

			}

			break;

		case "VERIFYTEXT":
			// Verifytextvalue = textvalue;
			// driver.findElement(UIOperations.getObject(objectName)).getText();
			try {
				gettextvalue = textvalue.replaceAll("^\\s+|\\s+$", "");
				testdatavalue = value.replace("^\\s+|\\s+$", "");
				if (gettextvalue.trim().equals(testdatavalue.trim())) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P",
							"Generated Value is" + " " + textvalue + ",Expected value is" + " " + value);
					loggerUI.info(objectName + " --> Verified Text - PASS ---> Updated in the report");
					t_Status.add("True");
				} else {
					t_Status.add("False");
					loggerUI.error(objectName + " --> Verify Text - FAIL ---> Updated in the report");
					StringWriter strWriter = new StringWriter();
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
							"Expected value is" + ":" + gettextvalue + "," + "TestData Value is" + ":" + testdatavalue);
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
				}
			} catch (Exception e) {
				System.out.println("Failed in verify text");
				t_Status.add("False");
				loggerUI.error(objectName + " --> Verify Text - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
						value + "-" + "Fields is not displayed in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}

			}

			break;
		case "VERIFYDOUBLEVALUE": // To verify the double value irrespective of whether it is truncated or not
									// truncated
			try {
				wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.presenceOfElementLocated(UIOperations.getObject(objectName)));
				textvalue = driver.findElement(UIOperations.getObject(objectName)).getText();
				if (textvalue.length() < 1) {
					textvalue = driver.findElement(UIOperations.getObject(objectName)).getAttribute("value");
				}

				gettextvalue = textvalue.replaceAll("^\\s+|\\s+$", "");
				testdatavalue = value.replace("^\\s+|\\s+$", "");
				// if(gettextvalue.trim().equals(testdatavalue.trim()))
				double d = Double.parseDouble(gettextvalue);
				int v = (int) d;
				System.out.println("Test Data Value:  " + testdatavalue.trim());
				System.out.println("Double: " + d);
				System.out.println("Int: " + v);
				// if(d.)

				// if(testdatavalue.trim().equals(d) || testdatavalue.trim().equals(v))
				if (testdatavalue.trim().equals(d) || testdatavalue.trim().equals(String.valueOf(v))) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P",
							"Generated Value:  " + " " + textvalue + ",  Expected value:  " + " " + value);
					loggerUI.info(objectName + " --> Verify Displayed Text - PASS ---> Updated in the report");
					t_Status.add("True");
				} else {
					t_Status.add("False");
					loggerUI.error(objectName + " --> Verify Displayed Text - FAIL ---> Updated in the report");
					StringWriter strWriter = new StringWriter();
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
							"Expected value:  " + " " + gettextvalue + "," + "TestData Value:  " + " " + testdatavalue);
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Verify Displayed Text - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "VERIFYDISPLAYEDTEXT":
			try {
				wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.presenceOfElementLocated(UIOperations.getObject(objectName)));
				textvalue = driver.findElement(UIOperations.getObject(objectName)).getText();
				if (textvalue.length() < 1) {
					textvalue = driver.findElement(UIOperations.getObject(objectName)).getAttribute("value");
				}
				System.out.println(textvalue);
				gettextvalue = textvalue.replaceAll("^\\s+|\\s+$", "");
				testdatavalue = value.replace("^\\s+|\\s+$", "");
				if (gettextvalue.trim().equals(testdatavalue.trim())) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P",
							"Generated Value:  " + " " + textvalue + ",  Expected value:  " + " " + value);
					loggerUI.info(objectName + " --> Verify Displayed Text - PASS ---> Updated in the report");
					t_Status.add("True");
				} else {
					t_Status.add("False");
					loggerUI.error(objectName + " --> Verify Displayed Text - FAIL ---> Updated in the report");
					StringWriter strWriter = new StringWriter();
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
							"Expected value:  " + " " + gettextvalue + "," + "TestData Value:  " + " " + testdatavalue);
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Verify Displayed Text - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		case "VERIFYERRORMESSAGEDISPLAYED":
			try {
				wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.presenceOfElementLocated(UIOperations.getObject(objectName)));
				String errormessage = driver.findElement(UIOperations.getObject(objectName)).getText();
				if (errormessage.length() >= 1) {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", errormessage);
					loggerUI.info(objectName + " --> Verify Error Message Displayed - FAIL ---> Updated in the report");
					t_Status.add("True");
				} else {
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					loggerUI.info(objectName + " --> Verify Error Message Displayed - PASS ---> Updated in the report");
					t_Status.add("True");
					loginCheckFlag = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Verify Error Message Displayed - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		/*
		 * case "VERIFYPARTIALTEXT" : //Verifytextvalue = textvalue; textvalue =
		 * driver.findElement(UIOperations.getObject(objectName)).getText(); try {
		 * if(value != null) { testdatavalue = value.replace("^\\s+|\\s+$", ""); }
		 * if(textvalue != null) { gettextvalue = textvalue.replaceAll("^\\s+|\\s+$",
		 * ""); }
		 * 
		 * if (gettextvalue != null || testdatavalue != null) {
		 * 
		 * 
		 * if(gettextvalue.contains(testdatavalue) || gettextvalue.contains(value)) {
		 * loggerUI.info(objectName + " --> Verified Partial Text - PASS");
		 * objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row,
		 * "P","Generated Value is" +" " + textvalue+",Expected value is"+ " "+value);
		 * loggerUI.info(objectName +
		 * " --> Verified Partial Text - PASS ---> Updated in the report");
		 * t_Status.add("True"); } else { loggerUI.error(objectName +
		 * " --> Verify Partial Text - FAIL"); tcStatus = "FAIL"; t_Status.add("False");
		 * loggerUI.error(objectName +
		 * " --> Verify Partial Text - FAIL ---> Updated in the report"); StringWriter
		 * strWriter = new StringWriter();
		 * objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row,
		 * "Error","Expected value"+":  " + gettextvalue + ",  " + "TestData Value" +
		 * ":  " + testdatavalue); if(Fail_SnapShot.equalsIgnoreCase("TRUE") &&
		 * !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
		 * getScreenShot(objExecuteTest.evidencePath, fileName_E); } } }
		 * 
		 * } catch (Exception e) { System.out.println("Failed in verify text");
		 * loggerUI.error(objectName + " --> Get Text - FAIL"); tcStatus = "FAIL";
		 * t_Status.add("False"); loggerUI.error(objectName +
		 * " --> Get Text - FAIL ---> Updated in the report"); StringWriter strWriter =
		 * new StringWriter(); objUpdateResult.writeOutputWorkBook(Reportpath, taskCode,
		 * row, "Error",value +" - " + "Partial Text is not displayed in the report");
		 * if(Fail_SnapShot.equalsIgnoreCase("TRUE") &&
		 * !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
		 * getScreenShot(objExecuteTest.evidencePath, fileName_E); }
		 * 
		 * }
		 * 
		 * break;
		 */
		case "SELECTDATE":
			try {

				try {
					wait = new WebDriverWait(driver, 10);
					wait.until(ExpectedConditions.elementToBeClickable(UIOperations.getObject(objectName)));
					WebElement el = driver.findElement(UIOperations.getObject(objectName));
					el.click();
					Thread.sleep(1000);
					wait.until(ExpectedConditions.elementToBeClickable(UIOperations.getObject(objectName)));
					Thread.sleep(1000);
					WebElement ell = driver.findElement(
							By.xpath("//div[(@class='btn-light') and (text()=" + "'" + value + "'" + ")]"));
					ell.click();
				}

				catch (Exception e) {
					WebElement ell = driver
							.findElement(By.xpath("//div[(@class='btn-light bg-primary text-white') and (text()=" + "'"
									+ value + "'" + ")]"));
					ell.click();
				}

				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P",
						"Generated Value is" + " " + textvalue + ",Expected value is" + " " + Verifytextvalue);
				loggerUI.info(objectName + " --> Verified Text - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Get Text - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added to select the date from date picker for FAB Collect
		case "ADHOCSELECTDATE":
			try {

				try {
					wait = new WebDriverWait(driver, 10);
					wait.until(ExpectedConditions.elementToBeClickable(UIOperations.getObject(objectName)));
					WebElement el = driver.findElement(UIOperations.getObject(objectName));
					// el.click();
					act.moveToElement(el).click().build().perform();
					Thread.sleep(2000);
					wait.until(ExpectedConditions.elementToBeClickable(UIOperations.getObject(objectName)));
					Thread.sleep(3000);
					String yearMonth = driver
							.findElement(By.xpath("//div[@class='calendar-hd']/a[@class='calendar-display']"))
							.getText();
					Thread.sleep(2000);
					String dateArr[] = value.split("-");
					String yearMonthInput = dateArr[2] + "/" + dateArr[1];
					// String[] yearMonthArr = yearMonth.split("/");
					while (!(yearMonthInput.equals(yearMonth))) {
						String[] yearMonthArr = yearMonth.split("/");

						driver.findElement(By.xpath("//div[@class='calendar-arrow']/span[@class='next']")).click();
						Thread.sleep(2000);
						yearMonth = driver
								.findElement(By.xpath("//div[@class='calendar-hd']/a[@class='calendar-display']"))
								.getText();

					}
					Thread.sleep(5000);
					String monthminus = Integer.toString((Integer.parseInt(dateArr[1]) - 1));
					WebElement ell = driver.findElement(By.xpath("//li/ol[@class='days']/li[@data-calendar-day='"
							+ dateArr[0] + "-" + monthminus + "-" + dateArr[2] + "']"));
					// ell.click();
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", ell);
					// act.moveToElement(ell).click().build().perform();
				}

				catch (Exception e) {
					e.printStackTrace();
					t_Status.add("False");
					loggerUI.error(objectName + " --> AdhocSelectDate - FAIL ---> Updated in the report");
					StringWriter strWriter = new StringWriter();
					e.printStackTrace(new PrintWriter(strWriter));
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				}

				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> AdhocSelectDate - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> AdhocSelectDate - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// IGTB- to click on the search button where it has a same object repository
		case "CLICKSEARCHBUTTON":
			try {
				Thread.sleep(1000);
				String elementstatus = null;
				wait.until(ExpectedConditions.elementToBeClickable(UIOperations.getObject(objectName)));
				elements = driver.findElements(UIOperations.getObject(objectName));
				for (WebElement el : elements) {
					if (j == elementval) {
						try {
							// wait.until(ExpectedConditions.visibilityOf(el));
							if (el.isEnabled() == true) {
								Actions actions = new Actions(driver);
								actions.moveToElement(el);
								// actions.click(el);
								el.click();
								actions.build().perform();
								elementval++;
								elementstatus = "found";
								break;
							}

						} catch (Exception e) {
						}

					} else {
						j++;
					}
				}

				loggerUI.info(objectName + " --> Search Button - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", " ");
				t_Status.add("True");
			} catch (Exception e) {
				loggerUI.info("Unable to scroll the element");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				t_Status.add("False");
			}
			break;

		case "DOUBLECLICK":
			try {
				wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.elementToBeClickable(UIOperations.getObject(objectName)));
				Thread.sleep(3000);
				element = driver.findElement(UIOperations.getObject(objectName));
				Actions aa = new Actions(driver);
				aa.moveToElement(element);
				Thread.sleep(1000);
				wait.until(ExpectedConditions.elementToBeClickable(UIOperations.getObject(objectName)));
				aa.doubleClick(element);
				aa.build().perform();
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Double Click - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				System.out.println(objectName + " --> Double click Button - FAIL ---> Updated in the report");
				loggerUI.error(objectName + " --> Double Click Button - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// To maximize the window control
		case "MAXIMIZEWINDOW":
			try {
				driver.manage().window().maximize();
				Thread.sleep(3000);
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Maximize Window - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				System.out.println(objectName + " --> Maximize Window - FAIL ---> Updated in the report");
				loggerUI.error(objectName + " --> Maximize Window - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// IGTB- To verify the test data values in the grid
		case "VERIFYGRIDVALUES":
			String gridstatus = null;
			try {
				elements = driver.findElements(UIOperations.getObject(objectName));
				for (WebElement el : elements) {
					if (el.getText().equals(value)) {
						gridstatus = "PASS";
						break;
					}
				}
				if (gridstatus.equals("PASS")) {
					loggerUI.info(objectName + " --> Verifying the values in the grid- PASS");
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", " ");
					t_Status.add("True");
				} else {
					loggerUI.info("Unable to find the element in grid");
					tcStatus = "FAIL";
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", "Unable to find Element");
					t_Status.add("False");
				}

			} catch (Exception e) {
				loggerUI.info("Unable to find the element in grid");
				tcStatus = "FAIL";
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				t_Status.add("False");
			}
			break;

		// IGTB - Verify the expected values in the grid
		case "VERIFYTRANSACTIONSUMMARYGRID":
			try {
				String elementstatus = null;
				List<WebElement> ell = driver
						.findElements(By.xpath("//div[@class='x-grid3-row x-grid3-row-first x-grid3-row-selected']"));
				for (WebElement ele : ell) {
					elements = ele.findElements(UIOperations.getObject(objectName));
					for (WebElement el : elements) {
						if (el.getText().equals(value)) {
							Thread.sleep(500);
							act.moveToElement(el);
							act.click(el);
							act.build().perform();
							break;
						}
					}
				}
				loggerUI.info(objectName + " --> Search Button - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				t_Status.add("True");
			} catch (Exception e) {
				loggerUI.info("Unable to scroll the element");
				tcStatus = "FAIL";
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				t_Status.add("False");
			}
			break;

		case "CLICKGRIDANDRIGHTCLICK":
			try {
				List<WebElement> ell = driver.findElements(UIOperations.getObject(objectName));
				for (WebElement el : ell) {
					if (el.getText().equals(textvalue) || el.getText().equals(value)) {
						WebDriverWait wait = new WebDriverWait(driver, 10);
						wait.until(ExpectedConditions.elementToBeClickable(el));
						Thread.sleep(500);
						act.moveToElement(el);
						act.click(el);
						act.build().perform();
						act.contextClick(el);
						act.build().perform();
						break;
					} else {
						Thread.sleep(1000);
						act.click(el).build().perform();
						act.moveToElement(el).sendKeys(Keys.DOWN).build().perform();
					}
				}
				loggerUI.info(objectName + " --> Search Button - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", " ");
				t_Status.add("True");
			} catch (Exception e) {
				loggerUI.info("Unable to Rightclick on the element");
				tcStatus = "FAIL";
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.getLocalizedMessage());
				t_Status.add("False");
			}
			break;

		// Added to handle the Current Window is closed and switching to new window
		// exist for Ithala - LOS
		case "SWITCHNEWWINDOWEXIST":
			Thread.sleep(5000);
			try {
				if (!windowCount.isEmpty()) {
					Set<String> allWindows = driver.getWindowHandles();
					int runWinCount = allWindows.size();
					allWindows.removeAll(windowCount);
					for (String win : allWindows) {
						driver.switchTo().window(win);
						System.out.println("Switched To: " + driver.getTitle());
					}
				} else {
					Set<String> allWindows = driver.getWindowHandles();
					int windowCount = allWindows.size();
					int x = 1;
					for (String window : allWindows) {
						if (x == windowCount) {
							driver.switchTo().window(window);
						}
						x++;
					}
				}
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Switch New Window Exist - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				loggerUI.error(objectName + " --> Switch New Window Exist - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added to handle the Null window Title in Ithala-LOS
		case "GETCURRENTWINDOWHANDLE":
			try {
				windowCount.add(driver.getWindowHandle());
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Get Current Window Handle - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Get Current Window Handle - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added to delete the window handle while running the scripts in jenkins
		// continuous integration
		case "REMOVEWINDOWHANDLE":
			try {
				windowCount.clear();
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Remove Window Handle - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Remove Window Handle - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added for Collect
		case "REMOVEREADONLYATTRIBUTEANDSETTEXT":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly','readonly')",
						element);
				element.clear();
				element.sendKeys(value);
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(
						objectName + " --> Remove Read Only Attribute and Set Text - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(
						objectName + " --> Remove Read Only Attribute and Set Text - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added to handle the Null window Title in Ithala-LOS
		case "SWITCHWINDOWBYCOUNT":
			try {
				Thread.sleep(5000);
				int count = Integer.parseInt(value);

				driver.switchTo().window(windowCount.get(count - 1));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Switch Window By Count - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Switch Window By Count - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added to handle the Switch window by Title in Ithala-LOS
		case "SWITCHWINDOWBYTITLE":
			// Switching to New Window By Title
			try {
				windowSwitchByTitle(objectName);
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Switch Window By Title - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Switch Window By Title - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			break;

		// Added for Ithala-LOS Alert Handling
		case "ENTERBYROBOT":
			try {

				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				t_Status.add("True");
				loggerUI.info(objectName + " -->  for Enter By Robot Performed");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Click By Robot - FAIL ---> Updated in the report");
				t_Status.add("False");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", alertMessage);
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added for Collect
		case "TABBYROBOT":
			try {
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				t_Status.add("True");
				loggerUI.info(objectName + " -->  for Tab By Robot Performed");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " -->Tab By Robot - FAIL ---> Updated in the report");
				t_Status.add("False");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", alertMessage);
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added for SBS-LMS
		case "PRESSTAB":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				element.sendKeys(Keys.TAB);
				t_Status.add("True");
				loggerUI.info(objectName + " -->  for Press Tab Performed");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Press Tab - FAIL ---> Updated in the report");
				t_Status.add("False");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added for IDC 19.1
		case "PRESSPACEBAR":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				// element.sendKeys(Keys.TAB);
				element.sendKeys(Keys.SPACE);
				t_Status.add("True");
				loggerUI.info(objectName + " -->  for Press spacebar Performed");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Press Tab - FAIL ---> Updated in the report");
				t_Status.add("False");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// Added for CA-DF
		case "ENTERPAC":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				List<WebElement> pacBoxes = element.findElements(By.tagName("div"));
				int iBoxNum = 0;
				for (WebElement pacBox : pacBoxes) {
					if (pacBox.getAttribute("class").equalsIgnoreCase("box")) {
						pacBox.findElement(By.tagName("input")).sendKeys(Integer.toString(iBoxNum));
					}

					iBoxNum++;
				}
				t_Status.add("True");
				loggerUI.info(objectName + " -->  for Enter PAC Performed");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Enter PAC - FAIL ---> Updated in the report");
				t_Status.add("False");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// Added for SBS-LMS Disbursal
		case "ALTF4BYROBOT":
			try {
				Thread.sleep(3000);
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_F4);
				robot.keyRelease(KeyEvent.VK_ALT);
				robot.keyRelease(KeyEvent.VK_F4);
				loggerUI.info(objectName + " -->  for Alt F4 By Robot Performed");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Alt F4 By Robot - FAIL ---> Updated in the report");
				t_Status.add("False");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// Added for SBS-LMS
		case "ALTTABBYROBOT":
			try {
				Thread.sleep(3000);
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_ALT);
				robot.keyRelease(KeyEvent.VK_TAB);
				loggerUI.info(objectName + " -->  for Alt Tab By Robot Performed");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Alt Tab By Robot - FAIL ---> Updated in the report");
				t_Status.add("False");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "TABENTERBYROBOT":
			try {
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				t_Status.add("True");
				loggerUI.info(objectName + " -->  for Tab Enter By Robot Perfoemed");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Tab Enter By Robot - FAIL ---> Updated in the report");
				t_Status.add("False");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// Added for Utkarsh-CBS
		case "GRIDDROPDOWNVALUESELECTION":
			try {
				int dropDownValuecount = Integer.parseInt(value);

				switch (dropDownValuecount) {
				case 1:
					act.sendKeys(Keys.ENTER).build().perform();
					break;
				case 2:
					act.sendKeys(Keys.ARROW_DOWN, Keys.ENTER).build().perform();
					break;
				case 3:
					act.sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER).build().perform();
					break;
				case 4:
					act.sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER).build().perform();
					break;
				case 5:
					act.sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER).build()
							.perform();
					break;
				default:
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "F",
							"The given input is not handled in switch case");
					break;
				}

				t_Status.add("True");
				loggerUI.info(objectName + " -->  Grid Drop Down Value Seleceted successfully");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Grid Drop Down Value Selection - FAIL ---> Updated in the report");
				t_Status.add("False");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// Added for Ithala-LOS Wait Handling
		case "WAITFORSOMETIME":
			try {
				int waitTime = Integer.parseInt(value);
				long waitVal = waitTime * 1000;
				Thread.sleep(waitVal);
				t_Status.add("True");
				loggerUI.info(objectName + " -->  Wait for Some Time using thread is Performed");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Wait for Some Time using thread - FAIL ---> Updated in the report");
				tcStatus = "FAIL";
				t_Status.add("False");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added for Ithala - LOS
		case "GETVALUE":
			try {
				valueAttribute = driver.findElement(UIOperations.getObject(objectName)).getAttribute("value");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Get Value Attribute ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Get Value Attribute - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added for Ithala - LOS
		case "SETVALUE":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				element.sendKeys(valueAttribute);
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Set Value Attribute ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Set Value Attribute - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// Added for IDC
		case "SETGENERATEDNUMBER":
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				element.sendKeys(generatedNumberCollection.get(value));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Set Generated Number ---> Updated in the report");
				t_Status.add("True");
				System.out.println("Generated Number for: " + value + "  is  " + generatedNumberCollection.get(value));
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Set Generated Number - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		// Added for Ithala-LOS
		case "JAVASCRIPTCLICK":
			try {

				element = driver.findElement(UIOperations.getObject(objectName));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				t_Status.add("True");
				loggerUI.info(objectName + " -->  for Java Script Click Performed");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Java Script Click - FAIL ---> Updated in the report");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				t_Status.add("False");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "ACTIONCLICK":
			try {

				element = driver.findElement(UIOperations.getObject(objectName));
				act.moveToElement(element).click().build().perform();
				t_Status.add("True");
				loggerUI.info(objectName + " -->  for Action Click Performed");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Action Click - FAIL ---> Updated in the report");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				t_Status.add("False");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "CLICKGRIDVALUES":
			try {
				// textvalue = "1001030001104";
				int counter11 = 0;
				List<WebElement> ell = driver.findElements(UIOperations.getObject(objectName));
				for (WebElement el : ell) {
					if (textvalue == null) {
						textvalue = "no values";
					}
					if (el.getText().trim().equalsIgnoreCase(textvalue.trim())
							|| el.getText().trim().equalsIgnoreCase(value.trim())) {
						counter11++;
						System.out.println("Values found");
						WebDriverWait wait = new WebDriverWait(driver, 10);
						wait.until(ExpectedConditions.elementToBeClickable(el));
						Thread.sleep(500);
						// act.moveToElement(el);
						// act.click(el);
						Thread.sleep(1000);
						// act.build().perform();
						el.click();
						/*
						 * JavascriptExecutor executor = (JavascriptExecutor)driver;
						 * executor.executeScript("arguments[0].click();", element);
						 */
						objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
						loggerUI.info(objectName + " --> Grid Value clicked - PASS ---> Updated in the report");
						t_Status.add("True");
						break;
					}

					else if (ell.size() == counter11) {
						t_Status.add("False");
						objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
								"Given Test data value is not found in the Grid");
						System.out.println("The values are not found in the Grid");
						break;

					} else {
						counter11++;
						Thread.sleep(1000);
						act.click(el).build().perform();
						act.moveToElement(el).sendKeys(Keys.DOWN).build().perform();
					}
				}
			} catch (Exception e) {
				loggerUI.info("Unable to scroll the element");
				t_Status.add("False");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
						"Given Test data value is not found in the Grid");
				System.out.println("The values are not found in the Grid");
			}
			break;

		// Added for Utkarsh-CBS
		case "CLICKBYROBOT":
			// Perform click on Button
			try {
				try {
					Thread.sleep(5000);
					element = driver.findElement(UIOperations.getObject(objectName));
					element.sendKeys(Keys.ARROW_DOWN);
					robot.keyPress(KeyEvent.VK_ENTER);
					Thread.sleep(5000);
				} catch (MoveTargetOutOfBoundsException e) {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
					element.click();
					// e.printStackTrace();
				} catch (WebDriverException e) {
					e.printStackTrace();
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
					// Thread.sleep(5000);

				} catch (Exception e) {
					loggerUI.info("Unable to Click - Button ");
					e.printStackTrace();
					t_Status.add("False");
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				}
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Click By Robot - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception ex) {
				loggerUI.error(objectName + " --> Click By Robot Failed");
				ex.printStackTrace();
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				ex.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		// Added for Ithala - LOS
		case "SWITCHMULTIPLEFRAMEBYXPATH":
			try {
				String frameSplit[] = null;
				if (frame.contains(":") && frame.contains("//")) {
					frameSplit = frame.split(":");
				} else {
					loggerUI.info("The format of the Muti Frame is incorrect" + frame);
					System.out.println("The format of the Muti Frame is incorrect" + frame);
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
							"The format of the Muti Frame is incorrect");
				}
				for (int i = 0; i < frameSplit.length; i++) {
					wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(frameSplit[i])));
					loggerUI.info("Switched To New Frame: " + frameSplit[i] + " - Successfully");
					System.out.println("Switched To New Frame: " + frameSplit[i] + " - Successfully");
					t_Status.add("True");
					System.out.println("After Frame switch" + driver.getWindowHandles());
				}

				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");

			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(frame + " --> Switch Frame - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
			}

			break;

		case "SWITCHTODEFAULTCONTENT":
			try {
				driver.switchTo().defaultContent();
				loggerUI.info("Switched To Default Content");
				System.out.println("Switched to Default Content Successfully");
				t_Status.add("True");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			} catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(" --> Switch To Default Content - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
			}

			break;
		// to upload a file using windows popup
		case "FILEUPLOAD":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				element.click();
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> File Upload - Find Element - FAIL ---> Updated in the report");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				t_Status.add("False");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			try {
				StringSelection s = new StringSelection(value);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
				System.out.println("Path: " + s);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				Thread.sleep(1000);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				Thread.sleep(2000);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				System.out.println("entered clicked");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> File Upload - PASS ---> Updated in the report");
				System.out.println("Excel updated - File Upload - P and TC_Status Array - True");
				t_Status.add("True");
			} catch (Exception e) {
				e.printStackTrace();
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				tcStatus = "FAIL";
				t_Status.add("False");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			break;

		// Added for IDC 19.1 to enter the file path in IE
		case "IEFILEUPLOAD":
			try {
				StringSelection s = new StringSelection(value);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
				System.out.println("Path: " + s);
				Thread.sleep(1000);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				Thread.sleep(2000);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);

				System.out.println("entered clicked");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> File Upload - PASS ---> Updated in the report");
				System.out.println("Excel updated - File Upload - P and TC_Status Array - True");
				t_Status.add("True");

			} catch (Exception e) {
				e.printStackTrace();
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				tcStatus = "FAIL";
				t_Status.add("False");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			break;

		case "SETRANDOMNUMBER":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				t_Status.add("True");
				loggerUI.info(objectName + " -->  for Set Random Number identified");

			} catch (Exception e) {
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				loggerUI.error(objectName + " --> Set Random Number - FAIL ---> Updated in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			try {
				element.clear();
				System.out.println("Given Digit Value " + value);
				String digits = value;
				String minValue = digits.split("_")[0];
				String maxValue = digits.split("_")[1];
				Random rand = new Random();
				String m = null;
				// int n = rand.nextInt((99999 - 10000) - 1) + 10000;

				int lenOfVal = minValue.length();
				int sp = 0;
				int sp1 = 0;
				if (minValue.length() > 5) {
					switch (lenOfVal) {
					case 6:
						sp = rand.nextInt((99999 - 10000) - 1) + 10000;
						sp1 = rand.nextInt((9 - 1) - 1) + 1;
						m = Integer.toString(sp) + Integer.toString(sp1);
						break;

					case 7:
						sp = rand.nextInt((99999 - 10000) - 1) + 10000;
						sp1 = rand.nextInt((99 - 10) - 1) + 10;
						m = Integer.toString(sp) + Integer.toString(sp1);
						break;

					case 8:
						sp = rand.nextInt((99999 - 10000) - 1) + 10000;
						sp1 = rand.nextInt((999 - 100) - 1) + 100;
						m = Integer.toString(sp) + Integer.toString(sp1);
						break;

					case 9:
						sp = rand.nextInt((99999 - 10000) - 1) + 10000;
						sp1 = rand.nextInt((9999 - 1000) - 1) + 1000;
						m = Integer.toString(sp) + Integer.toString(sp1);
						break;

					case 10:
						sp = rand.nextInt((99999 - 10000) - 1) + 10000;
						sp1 = rand.nextInt((99999 - 10000) - 1) + 10000;
						m = Integer.toString(sp) + Integer.toString(sp1);
						break;

					case 11:
						sp = rand.nextInt((99999 - 10000) - 1) + 10000;
						sp1 = rand.nextInt((999999 - 100000) - 1) + 100000;
						m = Integer.toString(sp) + Integer.toString(sp1);
						break;

					case 12:
						sp = rand.nextInt((99999 - 10000) - 1) + 10000;
						sp1 = rand.nextInt((9999999 - 1000000) - 1) + 1000000;
						m = Integer.toString(sp) + Integer.toString(sp1);
						break;

					case 13:
						sp = rand.nextInt((99999 - 10000) - 1) + 10000;
						sp1 = rand.nextInt((99999999 - 10000000) - 1) + 10000000;
						m = Integer.toString(sp) + Integer.toString(sp1);
						break;

					default:
						System.out.println("The Length of the digit is not handled");
						break;
					}

					System.out.println("Randomly Generated Number: " + m);
					element.sendKeys(m, Keys.ENTER);
					Thread.sleep(5);
					loggerUI.info(objectName + " --> Set Random Number - PASS");
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", alertMessage);
					loggerUI.info(objectName + " --> Set Random Number - PASS ---> Updated in the report");
					t_Status.add("True");
				} else {
					int n = rand.nextInt((Integer.parseInt(maxValue) - Integer.parseInt(minValue) - 1))
							+ Integer.parseInt(minValue);
					System.out.println("Randomly Generated Number: " + n);
					System.out.println("Randomly Generated String: " + Integer.toString(n));
					element.sendKeys(Integer.toString(n), Keys.ENTER);
					Thread.sleep(5);
					loggerUI.info(objectName + " --> Set Random Number - PASS");
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", alertMessage);
					loggerUI.info(objectName + " --> Set Random Number - PASS ---> Updated in the report");
					t_Status.add("True");
					generatedNumberCollection.put("ChequeNumber", Integer.toString(n));
				}
				// int n = rand.nextInt((99999 - 10000) - 1) + 10000;

			} catch (Exception e) {
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				tcStatus = "FAIL";
				t_Status.add("False");
				loggerUI.error(objectName + " --> Set Random Number - PASS");
				e.printStackTrace();
				loggerUI.error(objectName + " --> Set Random Number - FAIL ---> Updated in the report");
				System.out.println("Catch Exception: I = " + row);
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "SETTEXTPRESSENTERTWICE":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				t_Status.add("True");
				loggerUI.info(objectName + " -->  for Set Text Identified");

			} catch (Exception e) {
				e.printStackTrace();
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				loggerUI.error(objectName + " --> Set Text Press Enter Twice - FAIL ---> Updated in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			if (element.getText() != "") {
				element.clear();
			}
			try {
				element.sendKeys(value, Keys.ENTER, Keys.ENTER);
				loggerUI.info(objectName + " --> Set Text Press Enter Twice - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Set Text Press Enter Twice - PASS ---> Updated in the report");
				t_Status.add("True");

			} catch (Exception e) {
				loggerUI.error(objectName + " --> Set Text Press Enter Twice - PASS");
				e.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				loggerUI.error(objectName + " --> Set Text Press Enter Twice - FAIL ---> Updated in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "SETTEXTPRESSENTERTHRICE":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				t_Status.add("True");
				loggerUI.info(objectName + " -->  Set Text Press Enter Thrice Identified");

			} catch (Exception e) {
				e.printStackTrace();
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				loggerUI.error(objectName + " --> Set Text Press Enter Thrice - FAIL ---> Updated in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			if (element.getText() != "") {
				element.clear();
			}
			try {
				element.sendKeys(value, Keys.ENTER, Keys.ENTER, Keys.ENTER);
				loggerUI.info(objectName + " --> Set Text Press Enter Thrice - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Set Text Press Enter Thrice - PASS ---> Updated in the report");
				t_Status.add("True");

			} catch (Exception e) {
				loggerUI.error(objectName + " --> Set Text Press Enter Thrice - PASS");
				e.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				loggerUI.error(objectName + " --> Set Text Press Enter Thrice - FAIL ---> Updated in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "SETTEXTPRESSENTERFOURTIMES":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				t_Status.add("True");
				loggerUI.info(objectName + " -->  for Set Text Press Enter Four Times Identified");

			} catch (Exception e) {
				e.printStackTrace();
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				loggerUI.error(objectName + " --> Set Text Press Enter Four Times - FAIL ---> Updated in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}
			try {
				element.sendKeys(value, Keys.ENTER, Keys.ENTER);
				System.out.println("Three Times Enter Pressed");
				if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
					System.out.println("After Screen Shot Taken");
				}
				driver.switchTo().activeElement().sendKeys(Keys.ENTER, Keys.ENTER);
				System.out.println("Fourth Time Enter Pressed");
				loggerUI.info(objectName + " --> Set Text Press Enter Four Times - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Set Text Press Enter Four Times - PASS ---> Updated in the report");
				t_Status.add("True");

			} catch (Exception e) {
				loggerUI.error(objectName + " --> Set Text Press Enter Four Times - PASS");
				e.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				loggerUI.error(objectName + " --> Set Text Press Enter Four Times - FAIL ---> Updated in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "SETTEXTPRESSENTERFIVETIMES":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				t_Status.add("True");
				loggerUI.info(objectName + " -->  for Set Text Press Enter Five Times Identified");

			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Set Text Press Enter Five Times - FAIL ---> Updated in the report");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			if (element.getText() != "") {
				if (!objectName.equalsIgnoreCase("inwardClearingVerification.paid")) // To avoid clearing the value in a
																						// DROP DOWN
				{
					element.clear();
				}

			}
			try {
				element.sendKeys(value, Keys.ENTER, Keys.ENTER, Keys.ENTER, Keys.ENTER);
				if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
				Thread.sleep(2000);
				driver.switchTo().activeElement().sendKeys(Keys.ENTER);
				loggerUI.info(objectName + " --> Set Text Press Enter Five Times - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Set Text Press Enter Five Times - PASS ---> Updated in the report");
				t_Status.add("True");

			} catch (Exception e) {
				loggerUI.error(objectName + " --> Set Text Press Enter Five Times - PASS");
				e.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				loggerUI.error(objectName + " --> Set Text Press Enter Five Times - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "SELECTCHECKBOX":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
				element.click();
				element.sendKeys(Keys.TAB);
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				t_Status.add("True");
			} catch (WebDriverException e) {
				e.printStackTrace();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				Thread.sleep(500);
				System.out.println("WebDriver Exception Occured");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				t_Status.add("True");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			} catch (Exception e) {
				loggerUI.error("SELECT RADIO BUTTON Failed ");
				e.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				loggerUI.info("SELECT RADIO BUTTON Result Updated as Fail");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "PRESSENTERFOURTIMES":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				t_Status.add("True");
				loggerUI.info(objectName + " -->  for Press Enter Four Times Identified");

			} catch (Exception e) {
				e.printStackTrace();
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				loggerUI.error(objectName + " --> Press Enter Four Times - FAIL ---> Updated in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			try {
				element.sendKeys(Keys.ENTER, Keys.ENTER, Keys.ENTER, Keys.ENTER);
				loggerUI.info(objectName + " --> Press Enter Four Times - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Press Enter Four Times - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				loggerUI.error(objectName + " --> Press Enter Four Times - FAIL");
				e.printStackTrace();
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				loggerUI.error(objectName + " --> Press Enter Four Times - FAIL ---> Updated in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		case "PRESSENTERFIVETIMES":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				t_Status.add("True");
				loggerUI.info(objectName + " -->  for Press Enter Four Times Identified");

			} catch (Exception e) {
				e.printStackTrace();
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
				loggerUI.error(objectName + " --> Press Enter Four Times - FAIL ---> Updated in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			try {
				element.sendKeys(Keys.ENTER, Keys.ENTER, Keys.ENTER, Keys.ENTER, Keys.ENTER);
				loggerUI.info(objectName + " --> Press Enter Four Times - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				loggerUI.info(objectName + " --> Press Enter Four Times - PASS ---> Updated in the report");
				t_Status.add("True");
			} catch (Exception e) {
				loggerUI.error(objectName + " --> Press Enter Four Times - FAIL");
				e.printStackTrace();
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				loggerUI.error(objectName + " --> Press Enter Four Times - FAIL ---> Updated in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "CLICKAUTHORIZEBUTTON":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
			} catch (Exception e) {
				loggerUI.info("Unable to Find Authorize Button ");
				loggerUI.info("Exception In Finding Authorize BUTTON");
				e.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				System.out.println(objectName + " --> Click Authorize Button - FAIL ---> Updated in the report");

				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				loggerUI.error(objectName + " --> Click Authorize Button - FAIL ---> Updated in the report");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
				break;
			}

			try {
				if (objectName.contains("authorize")) {
					element.click();
					Thread.sleep(5000);
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				} else {
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
					Thread.sleep(300);
				}
			} catch (MoveTargetOutOfBoundsException e) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
				element.click();
				e.printStackTrace();
			} catch (WebDriverException e) {
				e.printStackTrace();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			} catch (Exception e) {
				loggerUI.info("Unable to Click - Button ");
				e.printStackTrace();
			}
			if (isAlert() == true) {
				if (alertFlag) {
					alertMessage = getCancelAlert();
				} else {
					alertMessage = getAcceptAlert();
					if (alertMessage.contains("Authorized")) {
						objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					} else {
						objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", alertMessage);
						tcStatus = "FAIL";
						t_Status.add("False");
					}

					if (loginAlertCheck) // If "Invalid User" Alert is thrown, loginCheckFlag is changed to FALSE
					{
						ExecuteTest.loginCheckFlag = false;
						loggerUI.info("Invalid User - 2");
						break;
					}
				}

			} else {
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", errorMessage());
				tcStatus = "FAIL";
				t_Status.add("False");
			}
			break;
		case "SETTEXTRANDOMNUMBER":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				long time = System.currentTimeMillis();
				String sTime = Long.toString(time).substring(6, 13);
				Date currentDate = new Date();
				@SuppressWarnings("deprecation")
				String sDate = Integer.toString(currentDate.getDate());
				randomNumber = sDate + sTime;
				startingChequeLeafNumber = randomNumber;
				System.out.println("Random Number Generated" + randomNumber);
				element.clear();

				if (value == "") {
					element.sendKeys(randomNumber, Keys.ENTER);
				} else {
					element.sendKeys(value, Keys.ENTER);
				}

				loggerUI.info(objectName + " --> Set Text Generated Random Number - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", alertMessage);
				loggerUI.info(objectName + " --> Set Text Generated Random Number - PASS ---> Updated in the report");
				t_Status.add("True");
				if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			catch (Exception e) {
				loggerUI.error(objectName + " --> Set Text Generated Random Number - FAIL");
				e.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				loggerUI.error(objectName + " --> Set Text Generated Random Number - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", alertMessage);
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

		case "SETCHEQUERANDOMNUMBER":

			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				try {
					if (element.getText() != "") {
						element.clear();
					}
					element.sendKeys(randomNumber);
					Thread.sleep(1000);
					element.sendKeys(Keys.ENTER);
					t_Status.add("True");
					System.out.println("Set Cheque Random Value : " + value);
					loggerUI.info(objectName + " -->  for Set Text Identified");
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", alertMessage);
					break;
				} catch (InvalidElementStateException e4) {
					e4.printStackTrace();
					System.out.println("Invalid Element Exception Caught ");
					element.sendKeys(value);
					// element.sendKeys(Integer.parseInt(value));
					js.executeScript("arguments[0].value=" + value + ";", element);
					Thread.sleep(1000);
					element.sendKeys(Keys.ENTER);
					t_Status.add("True");
					System.out.println("Set Text Value : " + value);
					loggerUI.info(objectName + " -->  for Set Text Identified");
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", alertMessage);
					break;
				} catch (Exception e3) {
					e3.printStackTrace();
					loggerUI.error(objectName + " --> Set Text - FAIL ---> Updated in the report");
					t_Status.add("False");
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e3.toString());
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
				}
				// }
			} catch (Exception e1) {
				e1.printStackTrace();
				loggerUI.error(objectName + " --> Set Text - FAIL ---> Updated in the report");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", alertMessage);
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;

		case "DROPDOWNPRESSENTER":
			try {
				System.out.println("CHECK: " + UIOperations.getObject(objectName));
				wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.elementToBeClickable(UIOperations.getObject(objectName)));
				WebElement ddlval = driver.findElement((UIOperations.getObject(objectName)));
				try {
					ddlval.sendKeys(value, Keys.TAB);
					Thread.sleep(1000);
				} catch (Exception e) {
					loggerUI.error(objectName + " --> Drop Down Press Enter - FAIL");
					e.printStackTrace();
					tcStatus = "FAIL";
					t_Status.add("False");
					StringWriter strWriter = new StringWriter();
					e.printStackTrace(new PrintWriter(strWriter));
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				}

				// ddlval.sendKeys(value);
				/*
				 * Actions actions = new Actions(driver); actions.moveToElement(ddlval);
				 * actions.click(); actions.sendKeys(value); //actions.sendKeys(Keys.DOWN);
				 * actions.build().perform(); Thread.sleep(100); actions.sendKeys(Keys.ENTER);
				 * actions.build().perform(); Thread.sleep(200);
				 */
				System.out.println("Drop Down Value Selected");
				loggerUI.info(objectName + " --> Drop Down Value Selected - PASS");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", alertMessage);
				t_Status.add("True");
			} catch (Exception e) {
				loggerUI.error(objectName + " --> Drop Down Press Enter - FAIL");
				e.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			break;
		case "IDCPAGESELECTION": // Added for NOVA
			try {
				element = driver.findElement(UIOperations.getObject(objectName));
				t_Status.add("True");
				if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}

				loggerUI.info(objectName + " -->  IDC Page Selection DropDown Identified");

			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Set Text - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}
			try {

				if (element.getText() != "") {
					element.clear();
					System.out.println("Text Box Value Cleared");
				}
				element.sendKeys(value, Keys.ENTER); // Added for IDC Page Selection Drop Down in Chrome

				System.out.println("Value Selected and Enter Key Pressed");
				Thread.sleep(2000);
				if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}

				while (isAlert() == true) {
					if (alertFlag) {
						alertMessage = getCancelAlert();
						loggerUI.info("Alert - Cancel Clicked ");
					} else {
						alertMessage = getAcceptAlert();
						loggerUI.info("Alert - OK Clicked ");
					}
					act.moveToElement(element).click().sendKeys(Keys.ENTER).build().perform();
					System.out.println("Invalid Alert Handled");
				}
				if (!alertMessage.equalsIgnoreCase("")) {
					if (alertMessage.contains("Invalid") || alertMessage.contains("Not")
							|| alertMessage.contains("invalid") || alertMessage.contains("not")
							|| alertMessage.contains("Rejected") || alertMessage.contains("rejected")) {
						loggerUI.info(objectName + " --> Set Text - FAIL");
						StringWriter strWriter = new StringWriter();
						objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString()); // Alert_Msg:
																														// Changed
																														// to
																														// "P"
																														// from
																														// "F"
																														// and
																														// emptied
																														// the
																														// alert
																														// message
																														// value
						loggerUI.info(objectName + " --> Set Text - FAIL ---> Updated in the report");
						t_Status.add("False");
						if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
							getScreenShot(objExecuteTest.evidencePath, fileName_E);
						}
					} else {
						loggerUI.info(objectName + " --> Set Text - PASS");
						objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", alertMessage);
						loggerUI.info(objectName + " --> Set Text - PASS ---> Updated in the report");
						t_Status.add("True");
					}

				} else {
					loggerUI.info(objectName + " --> Set Text - PASS");
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", alertMessage);
					loggerUI.info(objectName + " --> Set Text - PASS ---> Updated in the report");
					t_Status.add("True");
				}
				System.out.println("Before: Window Title: " + driver.getTitle());
			} catch (Exception e) {
				loggerUI.error(objectName + " --> Set Text - PASS");
				e.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				loggerUI.error(objectName + " --> Set Text - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

			break;

		default:
			uie.perform(testCase, operation, objectName, frame, delay, value, row, tcNameRow, t_Status,
					webDriverLocation, actSheet, taskCode, browser);
			break;
		}
	}

	public static String errorMessage() {
		String errorMsg = null;
		try {
			driver.switchTo().defaultContent();
			if (driver.findElements(By.xpath("//div[contains(text(),'Error in processCustomerUpdate')]")).size() != 0) {
				System.out.println("Error in Authorize is Present1");
				errorMsg = "Error in processCustomerUpdate";

			} else if (driver.findElements(By.xpath("//div[contains(text(),'TBAAUTHQ ROW NOT AVAILABLE')]"))
					.size() != 0) {
				System.out.println("Error in Authorize is Present2");
				errorMsg = "TBAAUTHQ ROW NOT AVAILABLE";

			} else {
				errorMsg = driver.findElement(By.xpath("(//div[@class='dhx_cell_statusbar_text'])[2]")).getText();
			}

		} catch (Exception e) {
			e.printStackTrace();
			loggerUI.info("Error Message Not Present");
		}
		System.out.println("Error Message:  " + errorMsg);
		return errorMsg;
	}

	public void xmlPosting(Properties objProperties, String testCase, String operation, String objectName,
			String objectType, String frame, String delay, String value, int row, int tcNameRow,
			ArrayList<String> t_Status, String textFilePath, String webDriverLocation, XSSFWorkbook xssfWorkbook,
			Sheet actSheet, String taskCode) throws Exception {
		// System.out.println("Check 1");
		ArrayList<String> failedCIFNo = new ArrayList<String>();
		String thisLine = null;
		FileReader fr1 = new FileReader(textFilePath);
		@SuppressWarnings("resource")
		BufferedReader br1 = new BufferedReader(fr1);
		System.out.println("Check 2");
		int successMsg = 0;
		int failureMsg = 0;
		String postingMsg = null;
		int ciFNo = 0;
		// driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
		if (delay != "") {
			i = (long) Double.parseDouble(delay);
			loginAlertCheck = false; // The LoginAlertCheck is changed to FALSE so that the status of the next CLICK
										// BUTTON will be updated properly
			System.out.println("Wait Time: " + i);
		} else {
			System.out.println("Wait Time is Empty " + i);
		}
		wait = new WebDriverWait(driver, i);
		while ((thisLine = br1.readLine()) != null) {
			System.out.println("Check 3");
			ciFNo = ciFNo + 1;
			try {
				wait.until(
						ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[3]/div[1]/ul/li/a/span")));
				driver.findElement(By.xpath("/html/body/div[3]/div[1]/ul/li/a/span")).click(); // Click Search Button in
																								// Menu
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Search Button in Menu - FAIL ---> Updated in the report");
				System.out.println("Click Search Button in Menu Not Found");
				continue;
			}

			System.out.println("CIFNo Count: " + ciFNo);
			System.out.println("CIFNo: " + thisLine);
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchValue")));
				driver.findElement(By.id("searchValue")).sendKeys(thisLine); // Enter CIF Number
			} catch (Exception e) {
				e.printStackTrace();
				// tcStatus = "FAIL";
				// t_Status.add("False");
				loggerUI.error(objectName + " --> Search Text Box - FAIL ---> Updated in the report");
				// objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "F","");
				System.out.println("Search Text Box Not Found");
				continue;
			}

			try {
				System.out.println("Check 3_1");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id("autofocus")));
				System.out.println("Check 3_2");
				driver.findElement(By.id("autofocus")).click(); // Click Search Button
				System.out.println("Check 3_2_1");
			} catch (Exception e) {
				e.printStackTrace();
				// tcStatus = "FAIL";
				// t_Status.add("False");
				loggerUI.error(objectName + " --> Search Button - FAIL ---> Updated in the report");
				// objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "F","");
				System.out.println("Search Button Not Found");
				continue;
			}

			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("/html/body/div[2]/div[3]/div/form/div/table[2]/tbody/tr/td")));
				driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/form/div/table[2]/tbody/tr/td")).click(); // Click
																													// Search
																													// Result
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Search Result - FAIL ---> Updated in the report");
				System.out.println("Search Result Not Found");
				continue;
			}

			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Utilities")));
				driver.findElement(By.linkText("Utilities")).click(); // Click Utilities
				System.out.println("Check 3_3");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("XML Interface")));
				driver.findElement(By.linkText("XML Interface")).click(); // Click XMLInterface
				System.out.println("Check 3_4");
				if (driver.findElements(By.id("popup_ok")).size() != 0) {
					System.out.println(
							"Invalid CIF Number - BI, Portrait, Address to be updated before XML Posting: " + thisLine);
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
						failureMsg = failureMsg + 1;
						failedCIFNo.add(thisLine);
						System.out.println("Invalid CIFNo: " + thisLine);
					}
					driver.findElement(By.id("popup_ok")).click();
					continue;
				}
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id("start")));
				driver.findElement(By.id("start")).click(); // Click Post Button
				System.out.println("Check 3_5");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Utilities or XML or Post - FAIL ---> Updated in the report");
				System.out.println("Utilities or XML or Post Not Found");
				continue;
			}
			/*
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(
			 * "Utilities"))); driver.findElement(By.linkText("Utilities")).click(); //
			 * Click Utilities // Thread.sleep(500);
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.
			 * linkText("XML Interface")));
			 * driver.findElement(By.linkText("XML Interface")).click(); // Click
			 * XMLInterface // Thread.sleep(1000);
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("start")));
			 * driver.findElement(By.id("start")).click(); // Click Post Button
			 *//****************************************************
				 * Get Posting Message - Starts
				 *********************************************************/
			/*
			 * try {
			 */
			System.out.println("Check 4");
			try {
				System.out.println("Check 4_1");
				wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("/html/body/div[2]/form/table[4]/thead/tr/td/div/div[2]/p"))); // Added_WebDriverWait
				// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='main_content']/p")));
				// // Added_WebDriverWait
				System.out.println("Check 4_2");
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Posting Message - FAIL ---> Updated in the report");
				System.out.println("Posting Message Not Found");
				t_Status.add("False");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", "");
				continue;
			}

			try {
				postingMsg = driver.findElement(By.xpath("/html/body/div[2]/form/table[4]/thead/tr/td/div/div[2]/p"))
						.getText();
			} catch (Exception e) {
				e.printStackTrace();
				loggerUI.error(objectName + " --> Unable To Get Posting Message - FAIL ---> Updated in the report");
				System.out.println("Unable To Get Posting Message");
				t_Status.add("False");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", "");
				continue;
			}

			System.out.println("Check 5");
			try {
				System.out.println("Check 6");
				if (postingMsg.contains("successfully")) {
					loggerUI.info(objectName + " --> Get Text - PASS");
					// objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row,
					// "P",alertMessage);
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
					loggerUI.info(objectName + " --> Get Text - PASS ---> Updated in the report");
					t_Status.add("True");
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
					successMsg = successMsg + 1;
				} else {
					loggerUI.error(objectName + " --> Get Text - FAIL");
					tcStatus = "FAIL";
					t_Status.add("False");
					loggerUI.error(objectName + " --> Get Text - FAIL ---> Updated in the report");
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", "");
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
					failureMsg = failureMsg + 1;
					failedCIFNo.add(thisLine);
					System.out.println("Failed CIFNo: " + thisLine);
				}
			} catch (Exception e) {
				loggerUI.error(objectName + " --> Get Text - FAIL");
				e.printStackTrace();
				tcStatus = "FAIL";
				t_Status.add("False");
				loggerUI.error(objectName + " --> POSTING MESSAGE - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
				failureMsg = failureMsg + 1;
				failedCIFNo.add(thisLine);
				System.out.println("Failed CIFNo: " + thisLine);
				continue;
			}

			/*
			 * } catch(Exception e) { loggerUI.error(objectName + " --> XMLPosting - FAIL");
			 * e.printStackTrace(); tcStatus = "FAIL"; t_Status.add("False");
			 * loggerUI.error(objectName +
			 * " --> XMLPosting - FAIL ---> Updated in the report"); StringWriter strWriter
			 * = new StringWriter(); e.printStackTrace(new PrintWriter(strWriter));
			 * objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row,
			 * "F",strWriter.toString()); continue; }
			 */
		}
		System.out.println("Successfully Posted Count: " + successMsg);
		System.out.println("Fail Posted Count: " + failureMsg);
		System.out.println("Failed CIF Numbers: ");
		for (int k = 0; k < failedCIFNo.size(); k++) {
			System.out.println(failedCIFNo.get(k));
		}
		/****************************************************
		 * Get Posting Message - Starts
		 *********************************************************/

	}

	/**
	 * Find element BY using object type and value
	 * 
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @return
	 * @throws Exception
	 */
	public static By getObject(String objectName) throws Exception {
		// Find by ID

		/********
		 * New Change - Reading the object repository from excelfile
		 *****************************************/
		String filePathOR = LoadPropertySingleton.configResourceBundle.getProperty("ObjectRepositoryFilePath");
		String fileNameOR = LoadPropertySingleton.configResourceBundle.getProperty("ObjectRepositoryFileName");
		String sheetNameOR = LoadPropertySingleton.configResourceBundle.getProperty("ObjectRepositorySheetName");
		ReadExcel objRepoExcel = new ReadExcel();
		Sheet sheetOR = objRepoExcel.readSheetContents(filePathOR, fileNameOR, sheetNameOR);
		@SuppressWarnings("unused")
		int rowCountOR = sheetOR.getLastRowNum() - sheetOR.getFirstRowNum();
		int totRowCount = sheetOR.getLastRowNum();
		By byValueOR = null;
		for (int i = 1; i <= totRowCount; i++) {
			Row rowOR = sheetOR.getRow(i);
			try {
				String objectLabelOR = rowOR.getCell(0).getStringCellValue();
				if (objectName.equalsIgnoreCase(objectLabelOR)) {
					String locatorType = rowOR.getCell(2).getStringCellValue();
					String locatorValue = rowOR.getCell(3).getStringCellValue();
					if (locatorType.equalsIgnoreCase("ID")) {
						byValueOR = By.id(locatorValue);
						break;
					}
					// Find by name
					else if (locatorType.equalsIgnoreCase("NAME")) {
						loggerUI.info("Object Name: " + objectName);
						try {
							byValueOR = By.name(locatorValue);
						} catch (Exception e) {
							e.printStackTrace();
							switchAvailableWindow();
							byValueOR = By.name(locatorValue);
						}

						break;
					}
					// Find by class
					else if (locatorType.equalsIgnoreCase("CLASSNAME")) {
						byValueOR = By.className(locatorValue);
						break;

					}
					// Find by link
					else if (locatorType.equalsIgnoreCase("LINKTEXT")) {
						System.out.println("Link Text: " + objectName);
						byValueOR = By.linkText(locatorValue);
						break;
					}
					// Find by partial link text
					else if (locatorType.equalsIgnoreCase("PARTIALLINKTEXT")) {
						byValueOR = By.partialLinkText(locatorValue);
						break;
					}
					// Find by tag name
					else if (locatorType.equalsIgnoreCase("TAGNAME")) {
						byValueOR = By.tagName(locatorValue);
						break;
					}
					// Find by Xpath
					else if (locatorType.equalsIgnoreCase("XPATH")) {

						byValueOR = By.xpath(locatorValue);
						break;
					}
					// Find by css
					else if (locatorType.equalsIgnoreCase("CSS")) {
						byValueOR = By.cssSelector(locatorValue);
						break;
					} else {
						loggerUI.info("Wrong Object Type: " + locatorType);
						loggerUI.error("Invalid Object Type");
						throw new Exception("Wrong object type");
					}
				} else {
					loggerUI.info("The object Label given in test case sheet and OR sheet is miasmatched" + "-"
							+ objectName + "-" + objectLabelOR);
				}
			} catch (Exception e) {

			}

		}

		return byValueOR;

	}

	// To get the Object value from the Object Repository
	public static String getObjectValue(String objectName) throws Exception {
		// Find by ID

		/********
		 * New Change - Reading the object repository from excelfile
		 *****************************************/
		String locatorValue = null;
		try {
			String filePathOR = LoadPropertySingleton.configResourceBundle.getProperty("ObjectRepositoryFilePath");
			String fileNameOR = LoadPropertySingleton.configResourceBundle.getProperty("ObjectRepositoryFileName");
			String sheetNameOR = LoadPropertySingleton.configResourceBundle.getProperty("ObjectRepositorySheetName");
			ReadExcel objRepoExcel = new ReadExcel();

			Sheet sheetOR = objRepoExcel.readSheetContents(filePathOR, fileNameOR, sheetNameOR);
			@SuppressWarnings("unused")
			int rowCountOR = sheetOR.getLastRowNum() - sheetOR.getFirstRowNum();
			int totRowCount = sheetOR.getLastRowNum();
			By byValueOR = null;
			for (int i = 1; i <= totRowCount; i++) {
				Row rowOR = sheetOR.getRow(i);
				try {
					String objectLabelOR = rowOR.getCell(0).getStringCellValue();
					if (objectName.equalsIgnoreCase(objectLabelOR)) {
						String locatorType = rowOR.getCell(2).getStringCellValue();
						locatorValue = rowOR.getCell(3).getStringCellValue();
						break;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return locatorValue;

	}

	/*
	 * private static String getObject1(Properties p, String objectName, String
	 * objectType) throws Exception { // Find by ID
	 * if(objectType.equalsIgnoreCase("ID")) {
	 * if(objectName.equals("welcomePage.loginCheck")) Thread.sleep(500); return
	 * p.getProperty(objectName); } return p.getProperty(objectName); }
	 */

	public static String getDateObject(Properties p, String objectName, String objectType) {
		return p.getProperty(objectName);
	}

	// Switching Window
	public static void switchToNewWindow(String parentWindow) throws Exception {
		try {
			Set<String> handles = driver.getWindowHandles();
			loggerUI.info("Windows Count: " + handles.size());
			for (String windowHandle : handles) {
				loggerUI.info("Window Handles: " + driver.getWindowHandle());
				if ((!windowHandle.equals(parentWindow))) {
					try {
						driver.switchTo().window(windowHandle);

						if (driver.getTitle().contains("Certificate Error")) {
							loggerUI.info("Certificate error recieved, by passing certificate error");
							Thread.sleep(1000);
							driver.navigate().to("javascript:document.getElementById('overridelink').click()");
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (NoSuchWindowException e) {
			loggerUI.error("Switch To New Window - Failed");
		}
	}

	// Switch to the Available Window
	public static void switchAvailableWindow() throws Exception {
		try {
			Set<String> handles = driver.getWindowHandles();
			System.out.println("Windows Count: " + handles.size());
			for (String windowHandle : handles) {
				System.out.println("Check One");
				System.out.println("Before Switch: " + driver.getWindowHandle());
				driver.switchTo().window(windowHandle);
				System.out.println("After Switch: " + driver.getWindowHandle());
				try {
					if (driver.getTitle().contains("Certificate Error")) {

						Thread.sleep(1000);
						driver.navigate().to("javascript:document.getElementById('overridelink').click()");
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			/*
			 * System.out.println("Waiting for Click"); Thread.sleep(5000); Robot robot =
			 * new Robot(); robot.mousePress(InputEvent.BUTTON1_MASK);
			 * robot.mouseRelease(InputEvent.BUTTON1_MASK); robot.mouseWheel(-100); Actions
			 * act = new Actions(driver); act.moveByOffset(200, 400).build().perform();
			 */

		} catch (NoSuchWindowException e) {
			loggerUI.info("Switching to window failed");
			loggerUI.error("Switch Available Window - Failed");
		}
	}

	// Window Switch using window title
	public boolean windowSwitchByTitle(String objectName) throws Exception {
		boolean switched = false;

		do {
			Thread.sleep(10000);
			Set<String> handles = driver.getWindowHandles();

			for (String windowHandle : handles) {
				driver.switchTo().window(windowHandle);
				loggerUI.info("Switched to " + driver.getTitle());
				loggerUI.info("Object Name: " + objectName);
				if (driver.getTitle().contains(objectName)) {
					loggerUI.info("Finally Switched to " + driver.getTitle());
					System.out.println("Finally Switched to " + driver.getTitle());
					switched = true;
					break;
				}

			}
			return switched;
		} while (!switched);
	}

	// Switching Frame

	/*
	 * private boolean waitForFrame(String objectName, String objectType) throws
	 * Exception { String frame = objectName; boolean switched = false; boolean
	 * multiframes = frame.contains("_"); if(frame.equalsIgnoreCase("left_fraMenu"))
	 * { Thread.sleep(1000); } while(isAlert()==true) { if(alertFlag) {
	 * getCancelAlert(); loggerUI.info("Alert - Cancel Clicked "); } else {
	 * getAcceptAlert(); loggerUI.info("Alert - OK Clicked "); } } if (!multiframes)
	 * { switched = false; try { long startTime = System.currentTimeMillis();
	 * driver.switchTo().defaultContent(); WebDriverWait wait = new
	 * WebDriverWait(driver, 3);
	 * wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
	 * switched = true; loggerUI.info( "Switched to " + frame + " Successfully  in "
	 * + (System.currentTimeMillis() - startTime));
	 * loggerUI.info("Switched To New Frame: " + frame + " - Successfully"); } catch
	 * (Exception e) { loggerUI.info("frame does not exist: " + frame); //
	 * loggerUI.info("Current Window Title: " + driver.getTitle() );
	 * e.printStackTrace(); loggerUI.error("Switch To New Frame: " + frame +
	 * " - Failed"); return false; } } else if (multiframes) { try { long startTime
	 * = System.currentTimeMillis(); int l = frame.split("_").length;
	 * driver.switchTo().defaultContent(); for (int f = 0; f < l; f++) {
	 * WebDriverWait wait = new WebDriverWait(driver, 2);
	 * wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame.split("_"
	 * )[f])); } System.out .println("Switched to " + frame + " Successfully  in " +
	 * (System.currentTimeMillis() - startTime));
	 * 
	 * switched = true; } catch (Exception e) {
	 * loggerUI.info("Mutli frame does not exist: " + frame);
	 * loggerUI.info("Current Window Title: " + driver.getTitle() );
	 * e.printStackTrace(); loggerUI.error("Switch To New Frame: " + frame +
	 * " - Failed"); return false; } } loggerUI.info("Switched To Multi Frame: " +
	 * frame + " - Successfully"); return switched; }
	 */

	private boolean waitForFrame(String objectName, String browser) throws Exception {
		String frame = objectName;
		boolean switched = false;
		boolean multiframes = frame.contains("_");
		boolean xpathFrame = frame.contains("//");
		System.out.println("Frame: " + frame);
		System.out.println("xpathFrame: " + xpathFrame);
		if (xpathFrame) {
			try {
				waitForFrameXpath(objectName, browser);
			} catch (Exception e) {
				loggerUI.info("frame does not exist: " + frame);
				e.printStackTrace();
				loggerUI.error("Switch To New Frame: " + frame + " - Failed");
				System.out.println("Xpath Frame doesnot exist-2");
				return false;
			}
		} else if (!multiframes) {
			switched = false;
			try {
				long startTime = System.currentTimeMillis();
				driver.switchTo().defaultContent();
				WebDriverWait wait = new WebDriverWait(driver, 40);
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
				switched = true;
				loggerUI.info("Switched to " + frame + " Successfully  in " + (System.currentTimeMillis() - startTime));
				loggerUI.info("Switched To New Frame: " + frame + " - Successfully");
			} catch (Exception e) {
				loggerUI.info("frame does not exist: " + frame);
				// loggerUI.info("Current Window Title: " + driver.getTitle() );
				e.printStackTrace();
				loggerUI.error("Switch To New Frame: " + frame + " - Failed");
				return false;
			}
		} else if (multiframes) {
			try {
				long startTime = System.currentTimeMillis();
				int l = frame.split("_").length;
				driver.switchTo().defaultContent();
				for (int f = 0; f < l; f++) {
					WebDriverWait wait = new WebDriverWait(driver, 60);
					wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame.split("_")[f]));
				}
				System.out.println(
						"Switched to " + frame + " Successfully  in " + (System.currentTimeMillis() - startTime));

				switched = true;
			} catch (Exception e) {
				loggerUI.info("Mutli frame does not exist: " + frame);
				loggerUI.info("Current Window Title: " + driver.getTitle());
				e.printStackTrace();
				loggerUI.error("Switch To New Frame: " + frame + " - Failed");
				return false;
			}
		}
		loggerUI.info("Switched To Multi Frame: " + frame + " - Successfully");
		return switched;
	}

	private boolean waitForFrameXpath(String objectName, String browser) throws Exception {
		String frame = objectName;
		boolean switched = false;
		@SuppressWarnings("unused")
		boolean multiframes = frame.contains("_");
		switched = false;
		try {
			try {
				System.out.println("Wait: Window Title: " + driver.getTitle());
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Getting error while taking the title");
			}

			driver.switchTo().defaultContent();
			/*
			 * if(browser.equalsIgnoreCase("ie")) { String mcorpclientURL =
			 * driver.findElement(By.xpath(frame)).getAttribute("src");
			 * driver.get(mcorpclientURL); } else {
			 */

			if (!(frame.contains("mainFrame"))) {
				try {
					String srcUrl[] = driver.findElement(By.xpath(frame)).getAttribute("src").split("/");
					System.out.println("Src url splited with / " + srcUrl[5] + "," + srcUrl[4] + "," + srcUrl[3] + ","
							+ srcUrl[2] + "," + srcUrl[1] + "," + srcUrl[0]);
					String pgmId = srcUrl[5];
					String srcUrlSplit[] = pgmId.split("\\.");
					programId = srcUrlSplit[0];
					System.out.println("Actual Program Id" + programId + "," + srcUrlSplit[1]);
				}

				catch (Exception e) {
					System.out.println("Array Index Out of Bound Exception while taking the program id");
					loggerUI.info("Array Index Out of Bound Exception while taking the program id");
				}
			}
			// WebDriverWait wait = new WebDriverWait(driver, 3);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(frame)));
			switched = true;
			loggerUI.info("Switched To New Frame: " + frame + " - Successfully");
			System.out.println("Switched To New Frame: " + frame + " - Successfully");
			System.out.println("After Frame switch" + driver.getWindowHandles());
			// }
			/*
			 * WebDriverWait wait = new WebDriverWait(driver, 3);
			 * wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(frame)
			 * )); switched = true; loggerUI.info("Switched To New Frame: " + frame +
			 * " - Successfully"); System.out.println("Switched To New Frame: " + frame +
			 * " - Successfully");
			 */
		} catch (Exception e) {
			loggerUI.info("frame does not exist: " + frame);
			// loggerUI.info("Current Window Title: " + driver.getTitle() );
			e.printStackTrace();
			loggerUI.error("Switch To New Frame: " + frame + " - Failed");
			System.out.println("Xpath Frame doesnot exist-1");
			return false;
		}

		return switched;
	}

	// Screen Shot
	public void getScreenShot(String filePath_E, String fileName_E)
			throws HeadlessException, AWTException, IOException, InterruptedException {

		String url = filePath_E + "\\" + fileName_E + "_" + imagedir + "_" + imagecounter + ".jpg";
		// String url = filePath_E+"\\"+fileName_E+"_"+imagedir+"_"+imagecounter+".png";
		System.out.println("ScreenShot Taken : " + url);
		BufferedImage image = new Robot()
				.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		File screenShot = new File(filePath_E + "\\" + fileName_E + "\\" + fileName_E + "_" + imagecounter + ".jpg");		
		screenshotfilepath = filePath_E + "\\" + fileName_E + "\\" + fileName_E + "_" + imagecounter + ".jpg";
		// File screenShot = new File(filePath_E +"\\" + fileName_E + "\\" + fileName_E
		// + "_" + imagecounter + ".png");
		ImageIO.write(image, "jpg", screenShot);
		// ImageIO.write(image, "png", screenShot);
		/*
		 * File srcFile = ((TakesScreenshot) (driver)).getScreenshotAs(OutputType.FILE);
		 * File dstFile1 = new
		 * File("C:\\Baskar\\Automation\\NOVA\\Evidences\\img4.jpg"); File dstFile = new
		 * File(url); FileUtils.copyFile(srcFile, dstFile);
		 */
		imagecounter++;
		/*
		 * Document document = new Document(); try { PdfWriter writer =
		 * PdfWriter.getInstance(document, new FileOutputStream(fileName_E));
		 * document.open(); document.add(new Paragraph(fileName_E));
		 * 
		 * //Add Image Image image1 = Image.getInstance(fileName_E); //Fixed Positioning
		 * image1.setAbsolutePosition(100f, 550f); //Scale to new height and new width
		 * of image image1.scaleAbsolute(200, 200); //Add to document
		 * document.add(image1);
		 * 
		 * // String imageUrl = "http://www.eclipse.org/xtend/images/java8_logo.png";
		 * Image image2 = Image.getInstance(new URL(filePath_E)); document.add(image2);
		 * 
		 * document.close(); writer.close(); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
	}

	// File name or Folder Name Creation
	public void tcNameFolder(String folderName) {
		fileName_E = folderName;
		loggerUI.info("fileName_R1: " + fileName_E);
		imagecounter = 1;
		new File(objExecuteTest.evidencePath + "\\" + fileName_E).mkdirs();
	}

	// Select value from DDL
	public static void selectFromDDL(Properties p, String objectName, String objectType, String inputdata) {
		if (!(inputdata == "")) {
			try {
				WebElement element = driver.findElement(getObject(objectName));
				Select ddl = new Select(element);
				ddl.selectByVisibleText(inputdata);
				loggerUI.info("DDL value Selected: " + inputdata + " - Successfully");
			} catch (Exception e) {
				loggerUI.error("DDL value Selected: " + inputdata + " - Failed");
			}
		} else {
			System.out.println("Input Value found as NULL");
			loggerUI.error("The input value for the DDL is found as NULL");
		}
	}

	// Select Radio Button by Name or Xpath
	public static void selectRadio(String proptype, String propvalue, String value) throws Exception {
		if (!proptype.equalsIgnoreCase("name"))
			throw new Exception("RADIO BUTTON property should be NAME");
		if (value != null && value.equalsIgnoreCase("EMPTY")) {
			return;
		}
		java.util.List<WebElement> allradioElements = driver.findElements(By.name(propvalue));
		try {
			for (WebElement element : allradioElements) {
				if (value == null || value.isEmpty()) {
					element.click();
					return;
				}
				if (element.getAttribute("value").equalsIgnoreCase(value)) {
					element.click();
					return;
				}
				// for CorpCif
				else if (value.contains("/")) {
					String[] arr = value.split("/");
					if (element.getAttribute("value").contains(arr[0])) {
						String s = value.split("/")[1];
						int index = Integer.parseInt(s);
						element = allradioElements.get(index);
					}
					element.click();
					return;
				}
			}
			throw new Exception("RADIO BUTTON - Value not found");
		} catch (org.openqa.selenium.NoSuchElementException e) {
		} catch (ElementNotVisibleException e1) {
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	// For Date Setting
	public static void dateSetter(WebElement element, String date) throws Exception {
		try {
			loggerUI.info("Date Picker : " + element + " : " + date);
			loggerUI.info("Title before clicking date picker : " + driver.getTitle());

			if (date == null || date.isEmpty())
				return;

			Set<String> oldWindows = driver.getWindowHandles();
			element.click();

			waitForNoOfWindows(oldWindows.size() + 1);
			Thread.sleep(1000); // Changed from 3000 to 9000 for CIB Admin
			switchToNewWindow(oldWindows);
			Thread.sleep(1000); // Added for CIB Admin
			try {
				driver.navigate().to("javascript:document.getElementById('overridelink').click()");
			} catch (Exception exp) {
				exp.printStackTrace();
			}
			try {
				((JavascriptExecutor) driver).executeScript(
						"javascript:set_datetime(" + String.valueOf(formatter.parse(date).getTime()) + ",true)");
			} catch (Exception e) {
				e.printStackTrace();
			}

			loggerUI.info("DATE SELECTED: " + date);
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			Alertcaught();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// For Alert Handling
	public static String Alertcaught() {
		String Result = "";
		try {
			Alert alert = driver.switchTo().alert();
			Result = alert.getText();
			alert.accept();
		} catch (org.openqa.selenium.NoAlertPresentException ex) {
			ex.printStackTrace();
		} catch (org.openqa.selenium.NoSuchWindowException exp) {
			exp.printStackTrace();
		}
		return Result;

	}

	// Wait for the respective no of windows to be opened: For Calendar
	public static void waitForNoOfWindows(int noOfWindows) throws Exception {
		boolean windowOpen = false;
		for (int i = 0; i < 50; i++) {
			Set<String> handles = driver.getWindowHandles();
			if (handles.size() == noOfWindows) {
				windowOpen = true;
				break;
			} else {
				Thread.sleep(1000);
			}
		}
		if (windowOpen == false)
			throw new Exception("Required number of window not open");
	}

	// Switch to new window from the list of old windows mainly for Calendar

	public static void switchToNewWindow(Set<String> oldWindows) throws Exception {
		Thread.sleep(1000); // Added for CorpCIF on June-14
		try {
			Set<String> handles = driver.getWindowHandles();
			for (String windowHandle : handles) {
				if (!oldWindows.contains(windowHandle)) {
					driver.switchTo().window(windowHandle);
					loggerUI.info("Swithced to New Window");
					try {
						if (driver.getTitle().contains("Certificate Error")) {
							loggerUI.info("Certificate error recieved, by passing certificate error");
							Thread.sleep(1000);
							driver.navigate().to("javascript:document.getElementById('overridelink').click()");
						}
					} catch (Exception e) {
					}
					break;
				}
			}
		} catch (NoSuchWindowException e) {
			loggerUI.info("Switching to window failed");
			e.printStackTrace();
		}
	}

	public static boolean isAlert() {
		System.out.println("Alert Counter: " + alertCounter);
		try {
			Thread.sleep(500);
			Alert alert = driver.switchTo().alert();
			System.out.println("After Alert");
			if ((alert.getText()).contains("Print")) {
				alertFlag = true;
			}
			if (alertCounter == 5) {
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				System.out.println("Enter Key Pressed for Continous Alert");
			}
			alertCounter++;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getAcceptAlert() throws HeadlessException, AWTException, IOException, InterruptedException {

		Alert alert = driver.switchTo().alert();
		String alertMsg = alert.getText();
		loggerUI.info("Alert Message: " + alertMsg);
		if (alert.getText().equalsIgnoreCase("Invalid user") || alert.getText().equalsIgnoreCase("Invalid login")) {
			loginAlertCheck = true;
			loggerUI.info("Invalid User Alert Occured");

		}
		/*
		 * if((Fail_SnapShot.equalsIgnoreCase("TRUE") &&
		 * !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) ||
		 * (Fail_SnapShot.equalsIgnoreCase("TRUE") &&
		 * (Pass_SnapShot.equalsIgnoreCase("TRUE")))) {
		 * getScreenShot(objExecuteTest.evidencePath, fileName_E); }
		 */
		if (alert.getText().contains("Invalid") || alert.getText().contains("invalid")
				|| alert.getText().contains("Not") || alert.getText().contains("not")) {
			if ((Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE")))
					|| (Fail_SnapShot.equalsIgnoreCase("TRUE") && (Pass_SnapShot.equalsIgnoreCase("TRUE")))) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);

			}
		} else {
			if ((Pass_SnapShot.equalsIgnoreCase("TRUE")) && !(Fail_SnapShot.equalsIgnoreCase("TRUE"))
					|| (Pass_SnapShot.equalsIgnoreCase("TRUE") && (Fail_SnapShot.equalsIgnoreCase("TRUE")))) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);

			}
		}
		alert.accept();
		loggerUI.info("Alert - OK:  Clicked");
		Thread.sleep(100);
		alertMessage = alertMsg;
		System.out.println("Alert: " + alertMsg);
		return alertMsg;
	}

	public String getCancelAlert() throws HeadlessException, AWTException, IOException, InterruptedException {
		if (Pass_SnapShot.equalsIgnoreCase("TRUE")) {
			getScreenShot(objExecuteTest.evidencePath, fileName_E);
		}
		Alert alert = driver.switchTo().alert();
		String alertMsg = alert.getText();
		alert.dismiss();
		loggerUI.info("Alert - CANCEL:  Clicked");
		Thread.sleep(100);
		return alertMsg;
	}

	// Added by ganeshan to open the YBL master sheet to read the programs
	public void ReadExcelvlaue(String xlFilePath) throws IOException {
		try {
			fis = new FileInputStream(xlFilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			workbook = new HSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Getting values of the tabs from the Master Excel sheet
	public void addtabvalues() throws IOException {

		/*
		 * FileInputStream file = new FileInputStream(new File(ExcelReader.xlFilePath));
		 * 
		 * @SuppressWarnings("resource") HSSFWorkbook workbook = new HSSFWorkbook(file);
		 * HSSFSheet srcsheet = workbook.getSheet("Data Fields");
		 */
		row = sheet.getRow(1);
		counterval1 = 0;
		size = row.getLastCellNum() - 22;
		tabvalues = new String[size];
		for (int b = 22; b < row.getLastCellNum(); b++) {
			try {
				String cellvalues = row.getCell(b).getStringCellValue();
				tabvalues[counterval1] = cellvalues;
				System.out.println(tabvalues[counterval1]);
				counterval1++;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		// Comparison of the expected values and the actual values
		for (int tabval = 0; tabval < tabvalues.length; tabval++) {
			if (tabvalues[tabval].equals(cellval)) {
				int endval = tabval + 1;
				cellval = "Tab" + endval;
				break;
			}
		}
	}

	public void fieldinput(String field) throws IOException {
		System.out.println("Field name is:" + field);
		switch (field) {
		case "Tab1":
			driver.findElement(By.xpath("//div[(text()='Score Card I')]")).click();
			break;
		case "Tab2":
			driver.findElement(By.xpath("//div[(text()='Score Card II')]")).click();
			break;
		case "Tab3":
			driver.findElement(By.xpath("//div[(text()='Smart OD')]")).click();
			break;
		case "Tab4":
			driver.findElement(By.xpath("//div[(text()='Unsecured Smart OD')]")).click();
			break;
		case "Tab5":
			driver.findElement(By.xpath("//div[(text()='Quick OD')]")).click();
			break;
		case "Tab6":
			driver.findElement(By.xpath("//div[(text()='Loan Against Property')]")).click();
			break;
		case "Tab7":
			driver.findElement(By.xpath("//div[(text()='LACR')]")).click();
			break;
		case "Tab8":
			driver.findElement(By.xpath("//div[(text()='YES SWIFT')]")).click();
			break;
		case "Tab9":
			driver.findElement(By.xpath("//div[(text()='Normal 3I ')]")).click();
			break;
		case "Tab10":
			driver.findElement(By.xpath("//div[(text()='CGTMSE')]")).click();
			break;
		}
	}

}
