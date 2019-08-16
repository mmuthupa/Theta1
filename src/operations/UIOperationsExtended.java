package operations;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UIOperationsExtended extends UIOperations 
{

	public UIOperationsExtended(WebDriver driver) 
	{
		super(driver);
	}

	public void perform(String testCase, String operation, String objectName, String frame, String delay, String value,
			int row, int tcNameRow, ArrayList<String> t_Status, String webDriverLocation, Sheet actSheet,
			String taskCode, String browser) throws Exception 
	{

		switch (operation.toUpperCase()) 
		{
			// Added to press up arrow for collect
			case "PAGEUP":
	
				pageUp(objectName, value, taskCode, row);
				break;
	
			// Added to Scroll Up for Collect
			case "SCROLLUP":
				scrollUp(objectName, value, taskCode, row);
				break;
	
			// This will identify the radio button based on value attribute - Added for YBL
			// CLO
			case "SELECTRADIOBUTTONWITHVALUE":
	
				selectRadioButtonWithValue(objectName, value, taskCode, row);
				break;
	
			// Added for CA-IDC To enter the alias name each time dynamic value
			case "SETDYNAMICNAME":
	
				setDynamicName(objectName, value, taskCode, row);
				break;
	
			// It will identify all the elements in a page and scroll to all the elements -
			// Added for Collect
			case "SCROLLDOWNTOLAST":
				scrollDownToLast(objectName, value, taskCode, row);
				break;
			case "CHECKGRID1VALUEIRTM":
				checkGrid1ValueiRTM(objectName, value, taskCode, row);
				break;
			case "CHECKGRID2VALUEIRTM":
				checkGrid2ValueiRTM(objectName, value, taskCode, row);
				break;
			case "VERIFYPARTIALTEXT":
				verifyPartialText(objectName, value, taskCode, row);
				break;
			// Added for Collect - To search the given input in the grid and clicking on
			// Approve/Reject/Refer based on the input and enter the remarks
			// Sample Inputs: Approval_Reject_Refer_Remarks_30225552_Refer_test
			// Header Xpath:
			// //div[@id='printableAreaCIF']//div[@class='table-header']/div[@class='header-flex']
			// Body Table Xpath//div[@id='printableAreaCIF']//div[@class='activetable1']/div
			case "SEARCHACCOUNTFROMGRIDANDENTERINPUTS":
				searchAccountFromGridAndEnterInputs(objectName, value, taskCode, row);
				break;
			// Added for collect - To search the given input from the grid and clicking the
			// check box in the first column and entering tenor in fifth column,Entering PP
			// Date and Amount in 6th and 7th column
			case "SEARCHCUSTOMERNOFROMGRIDANDENTERINPUTS":
				searchCustomerNoFromGridAndEnterInputs(objectName, value, taskCode, row);
				break;
			// Added for YBL Legal Initiation Grid handling
			case "CLICKENABLEDBUTTONFROMGRIDANDENTERTHENEXTROW":
				clickEnabledButtonFromGridAndEnterTheNextRow(objectName, value, taskCode, row);
				break;
			//Added for YBL-LS to click the element based on the text input providing in the test data
			//sample xpath: //div[text()='RAUSER1 ']/button[@class='dot']/span
			//sample xpath: //div[text()='~']/button[@class='dot']/span
			case "CLICKBUTTONWITHTEXTINPUT":
				clickButtonWithTextInput(objectName, value, taskCode, row);
				break;
			//Added for YBL-LS to verify the element displayed(Green and Red icons) with the inputs
			//sample xpath: //div[@class='querycontainer']//p[text()='ding dong']/../table[@class='devitable']//td/i[@class='far fa-square redf']
			//sample xpath: //div[@class='querycontainer']//p[text()='dong ding']/../table[@class='devitable']//td/i[@class='fas fa-check-square greenfont']
			//sample xpath: //div[@class='querycontainer']//p[text()='~']/../table[@class='devitable']//td/i[@class='far fa-square redf']
			case "VERIFYELEMENTDISPLAYEDWITHINPUT":
				verifyElementDisplayedWithInput(objectName, value, taskCode, row);
				break;
			//Read data from excel files and put it on screen	
			case "READDATAFROMEXCELANDSETTEXT":
				readDataFromExcelAndSetText(objectName, value, taskCode, row);
				break;
			//Added for YBL-LS
			//To type the text on the text box which is present in the grid
			//Remarks should be filled based on the type-Borrower in xpath we have to use ~ symbol it will come from the input
			case "SETTEXTWITHTEXTINPUT":
				setTextWithTextInput(objectName, value, taskCode, row);
				break;
			//Added for Collect Admin Date Picker
			case "SELECTDATEFROMPICKER":
				selectDateFromPicker(objectName, value, taskCode, row);
				break;
			//Added to select all values in the drop down for collect
			case "SELECTALLDROPDOWNVALUES":
				selectAllDropDownValues(objectName, value, taskCode, row);
				break;
			//Added for YBL-LS to select the send to RA and Retrigger checkboxes based on the property type
			case "CLICKCHECKBOXESFROMGRIDWITHINPUT":
				clickCheckBoxesFromGridWithInput(objectName, value, taskCode, row);
				break;
			default:
				break;
		}
	}

	private void verifyPartialText(String objectName, String value, String taskCode, int row) throws Exception 
	{
		textvalue = driver.findElement(UIOperations.getObject(objectName)).getText();
		try 
		{
			if (value != null) 
			{
				testdatavalue = value.replace("^\\s+|\\s+$", "");
			}
			if (textvalue != null) 
			{
				gettextvalue = textvalue.replaceAll("^\\s+|\\s+$", "");
			}

			if (gettextvalue != null || testdatavalue != null) 
			{

				if (gettextvalue.contains(testdatavalue) || gettextvalue.contains(value)) 
				{
					loggerUI.info(objectName + " --> Verified Partial Text - PASS");
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P",
							"Generated Value is" + " " + textvalue + ",Expected value is" + " " + value);
					loggerUI.info(objectName + " --> Verified Partial Text - PASS ---> Updated in the report");
					t_Status.add("True");
				} 
				else 
				{
					loggerUI.error(objectName + " --> Verify Partial Text - FAIL");
					tcStatus = "FAIL";
					t_Status.add("False");
					loggerUI.error(objectName + " --> Verify Partial Text - FAIL ---> Updated in the report");
					StringWriter strWriter = new StringWriter();
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
							"Expected value" + ":  " + gettextvalue + ",  " + "TestData Value" + ":  " + testdatavalue);
					if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) 
					{
						getScreenShot(objExecuteTest.evidencePath, fileName_E);
					}
				}
			}

		} 
		catch (Exception e) 
		{
			System.out.println("Failed in verify text");
			loggerUI.error(objectName + " --> Get Text - FAIL");
			tcStatus = "FAIL";
			t_Status.add("False");
			loggerUI.error(objectName + " --> Get Text - FAIL ---> Updated in the report");
			StringWriter strWriter = new StringWriter();
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
					value + " - " + "Partial Text is not displayed in the report");
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) 
			{
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}

		}

	}

	private void checkGrid2ValueiRTM(String objectName, String value, String taskCode, int row) throws Exception 
	{

		try 
		{
			String[] valSpt = value.split("_");
			System.out.println("valSplt 0: " + valSpt[0]);
			System.out.println("valSplt 1: " + valSpt[1]);
			int headerColumnPosition = 0;
			List<WebElement> headerRows = driver.findElements(UIOperations.getObject(objectName));
			int headerColumnCount = 1;
			for (WebElement headerRow : headerRows) 
			{
				// List<WebElement> headerColumns =
				// headerRow.findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.tagName("span"));
				WebElement headerColumn = null;
				try 
				{
					headerColumn = headerRow.findElement(By.tagName("div")).findElement(By.tagName("div"))
							.findElement(By.tagName("span"));
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				System.out.println("Column Header2: " + headerColumn.getText());
				if (headerColumn.getText().equals("Pending Qty")) 
				{
					System.out.println("Here");
				}
				if (headerColumn.getText().equalsIgnoreCase(valSpt[0])) 
				{
					headerColumnPosition = headerColumnCount;
					break;
				}

				headerColumnCount++;

			}
			// WebElement gridElement =
			// driver.findElement(By.xpath("//*[@id='row0gridOrderBookHistory']/div[" +
			// headerColumnPosition +"]/div"));
			WebElement gridElement = driver
					.findElement(By.xpath("//*[@id='row1gridOrderBookHistory']/div[" + headerColumnPosition + "]/div"));
			System.out.println("Grid Element Text: " + gridElement.getText());
			if (gridElement.getText().equals(valSpt[1])) 
			{
				System.out.println("Grid2 Value Matched");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				t_Status.add("True");
			} 
			else 
			{
				System.out.println("Grid2 Value Not Matched");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", "Value Doesnot Match");
				t_Status.add("False");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) 
				{
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

		} 
		catch (Exception e) 
		{
			loggerUI.info("Getting error while Click Enabled Button From Grid and Enter Next Row ");
			e.printStackTrace();
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
			t_Status.add("False");
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) 
			{
				try 
				{
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				} 
				catch (HeadlessException | AWTException | InterruptedException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
	}

	private void checkGrid1ValueiRTM(String objectName, String value, String taskCode, int row) throws Exception 
	{
		try 
		{
			String[] valSpt = value.split("_");
			System.out.println("valSplt 0: " + valSpt[0]);
			System.out.println("valSplt 1: " + valSpt[1]);
			int headerColumnPosition = 0;
			List<WebElement> headerRows = driver.findElements(UIOperations.getObject(objectName));
			int headerColumnCount = 1;
			for (WebElement headerRow : headerRows) 
			{
				// List<WebElement> headerColumns =
				// headerRow.findElement(By.tagName("div")).findElement(By.tagName("div")).findElements(By.tagName("span"));
				WebElement headerColumn = null;
				try 
				{
					headerColumn = headerRow.findElement(By.tagName("div")).findElement(By.tagName("div"))
							.findElement(By.tagName("span"));
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				System.out.println("Column1 Header: " + headerColumn.getText());
				if (headerColumn.getText().equals("Total Order Qty")) 
				{
					System.out.println("Here");
				}
				if (headerColumn.getText().equalsIgnoreCase(valSpt[0])) 
				{
					headerColumnPosition = headerColumnCount;
					break;
				}

				headerColumnCount++;

			}
			WebElement gridElement = driver.findElement(
					By.xpath("//*[@id='row0gridQueryBlotterDetail']/div[" + headerColumnPosition + "]/div"));
			System.out.println("Grid Element Text: " + gridElement.getText());
			if (gridElement.getText().equals(valSpt[1])) 
			{
				System.out.println("Grid1 Value Matched");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
				t_Status.add("True");
			} 
			else 
			{
				System.out.println("Grid2 Value Not Matched");
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", "Value Doesnot Match");
				t_Status.add("False");
				if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) 
				{
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				}
			}

		} 
		catch (Exception e) 
		{
			loggerUI.info("Getting error while Click Enabled Button From Grid and Enter Next Row ");
			e.printStackTrace();
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
			t_Status.add("False");
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) 
			{
				try 
				{
					getScreenShot(objExecuteTest.evidencePath, fileName_E);
				} 
				catch (HeadlessException | AWTException | InterruptedException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}

	}

	public void pageUp(String objectName, String value, String taskCode, int row) throws Exception 
	{
		try 
		{
			element = driver.findElement(UIOperations.getObject(objectName));
			int pageUpCount = Integer.parseInt(value);

			switch (pageUpCount) 
			{
				case 1:
					act.sendKeys(element, Keys.ARROW_UP).build().perform();
					break;
				case 2:
					act.sendKeys(element, Keys.ARROW_UP, Keys.ARROW_UP).build().perform();
					break;
				case 3:
					act.sendKeys(element, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP).build().perform();
					break;
				case 4:
					act.sendKeys(element, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP).build().perform();
					break;
				case 5:
					act.sendKeys(element, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP).build()
							.perform();
					break;
				case 6:
					act.sendKeys(element, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP,
							Keys.ARROW_UP).build().perform();
					break;
				case 7:
					act.sendKeys(element, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP,
							Keys.ARROW_UP, Keys.ARROW_UP).build().perform();
					break;
				case 8:
					act.sendKeys(element, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP,
							Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP).build().perform();
					break;
				case 9:
					act.sendKeys(element, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP,
							Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP).build().perform();
					break;
				case 10:
					act.sendKeys(element, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP,
							Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP).build().perform();
					break;
				case 11:
					act.sendKeys(element, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP,
							Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP)
							.build().perform();
					break;
				case 12:
					act.sendKeys(element, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP,
							Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP,
							Keys.ARROW_UP).build().perform();
					break;
				case 13:
					act.sendKeys(element, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP,
							Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP,
							Keys.ARROW_UP, Keys.ARROW_UP).build().perform();
					break;
				case 14:
					act.sendKeys(element, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP,
							Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP,
							Keys.ARROW_UP, Keys.ARROW_UP, Keys.ARROW_UP).build().perform();
					break;
				default:
					objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error",
							"The given input is not handled in switch case");
					break;
			}
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			loggerUI.info(objectName + " --> Page Up - PASS ---> Updated in the report");
			t_Status.add("True");
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
			t_Status.add("False");
			System.out.println(objectName + " --> Page Up - FAIL ---> Updated in the report");
			loggerUI.error(objectName + " --> Page Up - FAIL ---> Updated in the report");
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e1.toString());
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) 
			{
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}
		}
	}

	public void scrollUp(String objectName, String value, String taskCode, int row) throws Exception 
	{
		try 
		{
			element = driver.findElement(UIOperations.getObject(objectName));
			int y = element.getLocation().getY();
			int x = element.getLocation().getX();
			((JavascriptExecutor) driver).executeScript("window.scrollBy(" + x + ",-" + y + ")");
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			loggerUI.info(objectName + " --> Scroll Up - PASS ---> Updated in the report");
			t_Status.add("True");
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
			t_Status.add("False");
			System.out.println(objectName + " --> Scroll Up - FAIL ---> Updated in the report");
			loggerUI.error(objectName + " --> Scroll Up - FAIL ---> Updated in the report");
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e1.toString());
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) 
			{
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}
		}
	}

	public void selectRadioButtonWithValue(String objectName, String value, String taskCode, int row) throws Exception 
	{
		try 
		{
			List<WebElement> elements = driver.findElements(UIOperations.getObject(objectName));
			for (WebElement element : elements) 
			{
				if (element.getAttribute("value").equalsIgnoreCase(value)) 
				{
					element.click();
				} 
				else 
				{
					System.out.println(
							"The value given is not matched with the radio button value which is present in the web page");
				}
			}
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			loggerUI.info(objectName + " --> Select Radio Button With Value - PASS ---> Updated in the report");
			t_Status.add("True");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			t_Status.add("False");
			loggerUI.error(objectName + " --> Select Radio Button With Value - FAIL ---> Updated in the report");
			StringWriter strWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(strWriter));
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) 
			{
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}
		}
	}

	public void setDynamicName(String objectName, String value, String taskCode, int row) throws Exception 
	{
		try 
		{
			element = driver.findElement(UIOperations.getObject(objectName));
			String dynamicName = value + generatedNumberCollection.get("SerialNumber");
			element.sendKeys(dynamicName, Keys.ENTER);
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			loggerUI.info(objectName + " --> Set Dynamic Name - PASS ---> Updated in the report");
			t_Status.add("True");
			System.out.println("Generated Dynamic Name: " + value + "  is  " + dynamicName);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			t_Status.add("False");
			loggerUI.error(objectName + " --> Generated Dynamic Name - FAIL ---> Updated in the report");
			StringWriter strWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(strWriter));
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) 
			{
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}
		}
	}

	public void scrollDownToLast(String objectName, String value, String taskCode, int row) throws Exception 
	{
		try 
		{
			List<WebElement> elements = driver.findElements(UIOperations.getObject(objectName));
			for (WebElement ele : elements) 
			{
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
			}
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			loggerUI.info(objectName + " --> Scroll Down to Last- PASS ---> Updated in the report");
			t_Status.add("True");
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
			t_Status.add("False");
			System.out.println(objectName + " --> Scroll Down to Last - FAIL ---> Updated in the report");
			loggerUI.error(objectName + " --> Scroll Down to Last - FAIL ---> Updated in the report");
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e1.toString());
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) 
			{
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}
		}
	}

	public void searchAccountFromGridAndEnterInputs(String objectName, String value, String taskCode, int row)
			throws Exception 
	{
		try 
		{

			String[] valSpt = value.split("_");
			String[] objectValue = UIOperations.getObjectValue(objectName).split("~");
			Map<String, Integer> counterMap = new HashMap<String, Integer>();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(objectValue[0])));
			List<WebElement> headerColumns = driver.findElement(By.xpath(objectValue[0]))
					.findElements(By.tagName("div"));
			int headerColumnCount = 1;
			for (WebElement headerColumn : headerColumns) 
			{
				for (int i = 0; i < valSpt.length; i++) 
				{
					if (headerColumn.getText().equalsIgnoreCase(valSpt[i])) 
					{
						counterMap.put(valSpt[i], headerColumnCount);
					}
				}
				headerColumnCount++;
			}

			List<WebElement> bodyRows = driver.findElements(By.xpath(objectValue[1]));

			int bodyRowCount = 1;
			boolean searchFlag = false;
			boolean completeFlag = false;
			for (WebElement bodyRow : bodyRows) {
				List<WebElement> bodyColumns = bodyRow.findElements(By.tagName("div"));
				int bodyColumnCount = 1;
				for (WebElement bodyColumn : bodyColumns) 
				{
					if (bodyColumn.getText().equals(valSpt[5])) 
					{
						searchFlag = true;
					}
					if (searchFlag) 
					{
						if (bodyColumnCount == counterMap.get(valSpt[0])) 
						{
							try 
							{
								WebElement approvalCheckBox = bodyColumn.findElement(By.tagName("input"));
								if (valSpt[6].equalsIgnoreCase("Approval")) 
								{
									approvalCheckBox.click();
								}
							} 
							catch (Exception e) 
							{
								e.printStackTrace();
							}

						} 
						else if (bodyColumnCount == counterMap.get(valSpt[1])) 
						{
							try 
							{
								WebElement rejectCheckBox = bodyColumn.findElement(By.tagName("input"));
								if (valSpt[6].equalsIgnoreCase("Reject")) 
								{
									rejectCheckBox.click();
								}
							} 
							catch (Exception e) 
							{
								e.printStackTrace();
							}

						} 
						else if (bodyColumnCount == counterMap.get(valSpt[2])) 
						{
							try 
							{
								WebElement referCheckBox = bodyColumn.findElement(By.tagName("input"));
								if (valSpt[6].equalsIgnoreCase("Refer")) 
								{
									referCheckBox.click();
								}
							} 
							catch (Exception e) 
							{
								e.printStackTrace();
							}

						} 
						else if (bodyColumnCount == counterMap.get(valSpt[3])) 
						{
							try 
							{
								WebElement referToInput = bodyColumn.findElement(By.tagName("select"));
								referToInput.sendKeys(valSpt[7]);
							} 
							catch (Exception e) 
							{
								e.printStackTrace();
							}

						} 
						else if (bodyColumnCount == counterMap.get(valSpt[4])) 
						{
							try 
							{
								WebElement remarksInput = bodyColumn.findElement(By.tagName("textarea"));
								remarksInput.sendKeys(valSpt[8]);
								completeFlag = true;
								break;
							} 
							catch (Exception e) 
							{
								e.printStackTrace();
							}

						}
					}

					bodyColumnCount++;
				}
				if (completeFlag) 
				{
					break;
				}

				bodyRowCount++;

			}

			loggerUI.info(objectName + " --> Search Account From Grid And Enter Inputs - PASS - Updated in the Report");
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			t_Status.add("True");
		} 
		catch (Exception e) 
		{
			loggerUI.info(" --> Search Account From Grid And Enter Inputs - FAIL - Updated in the Report");
			e.printStackTrace();
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
			t_Status.add("False");
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) 
			{
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}

		}

	}

	public void searchCustomerNoFromGridAndEnterInputs(String objectName, String value, String taskCode, int row)
			throws Exception 
	{
		try 
		{
			String[] valSplit = value.split("_");
			List<WebElement> rows = driver.findElements(UIOperations.getObject(objectName));
			int totalRows = rows.size();
			String objectVal = UIOperations.getObjectValue(objectName);
			for (int i = 1; i <= totalRows; i++) 
			{

				String customerNoElement = objectVal + "[" + i + "]//td[contains(text(),'" + valSplit[0] + "')]";
				String checkBoxElement = objectVal + "[" + i + "]//td[1]/label/input";
				String tenorElement = objectVal + "[" + i + "]//td[5]/input";
				String PPDateElement = objectVal + "[" + i + "]//td[6]/input";
				String PPAmount = objectVal + "[" + i + "]//td[7]/input";
				WebElement customerNo = null;
				try 
				{
					customerNo = driver.findElement(By.xpath(customerNoElement));
				} 
				catch (Exception ex) 
				{
					System.out.println("Table element is not found in Multiple Installment Plan");
				}
				if (customerNo != null) 
				{
					if (customerNo.getText().equals(valSplit[0])) 
					{
						Thread.sleep(2000);
						driver.findElement(By.xpath(checkBoxElement)).click();
						Thread.sleep(2000);
						driver.findElement(By.xpath(tenorElement)).sendKeys(valSplit[1], Keys.TAB);
						Thread.sleep(2000);
						WebElement PPDate = driver.findElement(By.xpath(PPDateElement));
						Thread.sleep(2000);
						adhocSelectDate(PPDate, valSplit[2], taskCode, row);
						Thread.sleep(2000);
						driver.findElement(By.xpath(PPAmount)).sendKeys(valSplit[3]);

						int tenorCount = Integer.parseInt(valSplit[1]);

						int x = 4;
						for (int j = 2; j <= tenorCount; j++) 
						{
							driver.findElement(By.xpath(objectVal + "[" + i + "]" + "//tr[" + j + "]/td[7]/input"))
									.sendKeys(valSplit[x]);
							x++;
						}

					}
				}
			}

			loggerUI.info(
					objectName + " --> Search CustomerNo From Grid And Enter Inputs - PASS - Updated in the Report");
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			t_Status.add("True");

		} 
		catch (Exception e) 
		{
			loggerUI.info(" --> Search CustomerNo From Grid And Enter Inputs - FAIL - Updated in the Report");
			e.printStackTrace();
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
			t_Status.add("False");
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) 
			{
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}
		}

	}

	public void adhocSelectDate(WebElement ele, String value, String taskCode, int row) throws Exception 
	{

		try 
		{

			act.moveToElement(ele).click().build().perform();
			Thread.sleep(2000);
			String yearMonth = driver.findElement(By.xpath("//div[@class='calendar-hd']/a[@class='calendar-display']"))
					.getText();
			Thread.sleep(2000);
			String dateArr[] = value.split("-");
			String yearMonthInput = dateArr[2] + "/" + dateArr[1];
			// String[] yearMonthArr = yearMonth.split("/");
			while (!(yearMonthInput.equals(yearMonth))) 
			{
				String[] yearMonthArr = yearMonth.split("/");

				driver.findElement(By.xpath("//div[@class='calendar-arrow']/span[@class='next']")).click();
				Thread.sleep(2000);
				yearMonth = driver.findElement(By.xpath("//div[@class='calendar-hd']/a[@class='calendar-display']"))
						.getText();

			}
			Thread.sleep(5000);
			String monthminus = Integer.toString((Integer.parseInt(dateArr[1]) - 1));
			WebElement ell = driver.findElement(By.xpath("//li/ol[@class='days']/li[@data-calendar-day='" + dateArr[0]
					+ "-" + monthminus + "-" + dateArr[2] + "']"));
			// ell.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ell);
			// act.moveToElement(ell).click().build().perform();
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			t_Status.add("False");
			loggerUI.error(" --> AdhocSelectDate - FAIL ---> Updated in the report");
			StringWriter strWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(strWriter));
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
		}

	}

	public void clickEnabledButtonFromGridAndEnterTheNextRow(String objectName, String value, String taskCode, int row)
			throws Exception 
	{
		try 
		{
			String[] valSpt = value.split("_");
			Map<String, Integer> counterMap = new HashMap<String, Integer>();
			List<WebElement> headerRows = driver.findElement(UIOperations.getObject(objectName))
					.findElement(By.tagName("thead")).findElements(By.tagName("tr"));
			int headerColumnCount = 1;
			for (WebElement headerRow : headerRows) 
			{
				List<WebElement> headerColumns = headerRow.findElements(By.tagName("td"));

				for (WebElement headerColumn : headerColumns) 
				{
					for (int i = 0; i < valSpt.length; i++) 
					{
						if (headerColumn.getText().equalsIgnoreCase(valSpt[i])) 
						{
							counterMap.put(valSpt[i], headerColumnCount);
						}
					}

					headerColumnCount++;
				}
			}

			List<WebElement> bodyRows = driver.findElement(UIOperations.getObject(objectName))
					.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

			int bodyRowCount = 1;
			boolean searchFlag = false;
			boolean plusIconIdentification = false;
			for (WebElement bodyRow : bodyRows) 
			{
				List<WebElement> bodyColumns = bodyRow.findElements(By.tagName("td"));
				int bodyColumnCount = 1;
				for (WebElement bodyColumn : bodyColumns) 
				{
					if (bodyColumnCount == counterMap.get(valSpt[0])) 
					{
						String propertyType = null;
						try 
						{
							propertyType = bodyColumn.findElement(By.tagName("div")).getText();
						} 
						catch (Exception e) 
						{
							System.out.println("Exception while getting the Property Type Text");
						}
						if (propertyType != null && propertyType != "") 
						{
							if (value.contains("Waive Off")) 
							{
								if (propertyType.equals(valSpt[3])) 
								{
									searchFlag = true;
								}
							} 
							else if (propertyType.equals(valSpt[6])) 
							{
								searchFlag = true;
							}
						}
					}

					if (searchFlag) 
					{
						if (!(value.contains("Waive Off"))) 
						{
							if (bodyColumnCount == counterMap.get(valSpt[4])) 
							{

								if (!(valSpt[7].equalsIgnoreCase("nochange"))) 
								{
									WebElement city1 = driver
											.findElement(By.xpath("//div[@id='facilitytable2']/table/tbody/tr["
													+ bodyRowCount + "]/td[" + counterMap.get(valSpt[1])
													+ "]/ng-select//div[@class='ng-input']/input"));
									city1.click();
									Thread.sleep(2000);
									city1.sendKeys(valSpt[7]);
									Thread.sleep(2000);
									city1.sendKeys(Keys.ENTER);
									Thread.sleep(2000);
									city1.sendKeys(Keys.TAB);

								}

								Thread.sleep(2000);
								if (!(valSpt[8].equalsIgnoreCase("nochange"))) 
								{
									WebElement agency1 = driver.findElement(By.xpath(
											"//div[@id='facilitytable2']/table/tbody/tr[" + bodyRowCount + "]/td["
													+ counterMap.get(valSpt[2]) + "]/select[@name='Agency']"));
									Select s = new Select(agency1);
									s.selectByVisibleText(valSpt[8]);

								}
								Thread.sleep(2000);
								if (!(valSpt[9].equalsIgnoreCase("nochange"))) 
								{
									WebElement remarks = driver.findElement(
											By.xpath("//div[@id='facilitytable2']/table/tbody/tr[" + bodyRowCount
													+ "]/td[" + counterMap.get(valSpt[3]) + "]/textarea"));
									remarks.sendKeys(valSpt[9]);

								}
								Thread.sleep(2000);
								if (valSpt[10].equalsIgnoreCase("InitiateButton-Yes")) 
								{
									WebElement initiate1 = driver
											.findElement(By.xpath("//div[@id='facilitytable2']/table/tbody/tr["
													+ bodyRowCount + "]/td[" + counterMap.get(valSpt[5]) + "]/button"));
									((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
											initiate1);
									initiate1.click();
									Thread.sleep(5000);
								}

								boolean nextRowInput = false;
								try 
								{
									String nextRowInputCity = valSpt[11];
									nextRowInput = true;
								} 
								catch (ArrayIndexOutOfBoundsException e) 
								{
									nextRowInput = false;
									plusIconIdentification = true;
								}

								if (nextRowInput) 
								{
									WebElement plusButton = bodyColumn.findElement(By.tagName("div"))
											.findElement(By.tagName("button")).findElement(By.tagName("span"));
									if (plusButton.getAttribute("class").equals("glyphicon glyphicon-plus-sign")) {
										((JavascriptExecutor) driver)
												.executeScript("arguments[0].scrollIntoView(true);", plusButton);
										plusButton.click();
										Thread.sleep(3000);
										int secondRow = bodyRowCount + 1;
										WebElement city = driver
												.findElement(By.xpath("//div[@id='facilitytable2']/table/tbody/tr["
														+ secondRow + "]/td[" + counterMap.get(valSpt[1])
														+ "]/ng-select//div[@class='ng-input']/input"));
										city.click();
										Thread.sleep(2000);
										city.sendKeys(valSpt[11]);
										Thread.sleep(2000);
										city.sendKeys(Keys.ENTER);
										Thread.sleep(2000);
										city.sendKeys(Keys.TAB);
										// city.sendKeys(valSpt[5]);
										Thread.sleep(3000);
										WebElement agency = driver.findElement(By.xpath(
												"//div[@id='facilitytable2']/table/tbody/tr[" + secondRow + "]/td["
														+ counterMap.get(valSpt[2]) + "]/select[@name='Agency']"));
										Select s1 = new Select(agency);
										s1.selectByVisibleText(valSpt[12]);
										Thread.sleep(3000);
										WebElement remarks = driver.findElement(
												By.xpath("//div[@id='facilitytable2']/table/tbody/tr[" + secondRow
														+ "]/td[" + counterMap.get(valSpt[3]) + "]/textarea"));
										remarks.sendKeys(valSpt[13]);
										Thread.sleep(3000);
										if (valSpt[14].equalsIgnoreCase("InitiateButton-Yes")) {
											WebElement initiate = driver.findElement(
													By.xpath("//div[@id='facilitytable2']/table/tbody/tr[" + secondRow
															+ "]/td[" + counterMap.get(valSpt[5]) + "]/button"));
											((JavascriptExecutor) driver)
													.executeScript("arguments[0].scrollIntoView(true);", initiate);
											initiate.click();
											Thread.sleep(3000);
										}
										plusIconIdentification = true;
									}
									break;
								}
							}
						} 
						else 
						{
							if (bodyColumnCount == counterMap.get(valSpt[1])) 
							{
								if (valSpt[4].equalsIgnoreCase("Waive Off-Yes")) 
								{
									WebElement waiveOff = driver
											.findElement(By.xpath("//div[@id='facilitytable2']/table/tbody/tr["
													+ bodyRowCount + "]/td[" + counterMap.get(valSpt[1]) + "]/input"));
									waiveOff.click();
									Thread.sleep(3000);
								}

								if (valSpt[5].equalsIgnoreCase("Initiate-Yes")) 
								{
									WebElement initiate = driver
											.findElement(By.xpath("//div[@id='facilitytable2']/table/tbody/tr["
													+ bodyRowCount + "]/td[" + counterMap.get(valSpt[2]) + "]/button"));
									initiate.click();
									Thread.sleep(3000);
								}
								plusIconIdentification = true;
							}
						}

					}
					bodyColumnCount++;

				}
				if (plusIconIdentification) 
				{
					break;
				}
				bodyRowCount++;

			}
			loggerUI.info(objectName + " --> Click Enabled Button From Grid and Enter Next Row - PASS");
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			t_Status.add("True");
		} 
		catch (Exception e) 
		{
			loggerUI.info("Getting error while Click Enabled Button From Grid and Enter Next Row ");
			e.printStackTrace();
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
			t_Status.add("False");
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) 
			{
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}

		}
	}
	
	public void clickButtonWithTextInput(String objectName, String value, String taskCode, int row) throws Exception {
		try {
			String objectValue = UIOperations.getObjectValue(objectName);
			String[] objctVal = objectValue.split("~");
			String xpathStr = objctVal[0] + value + objctVal[1];
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStr)));
			element = driver.findElement(By.xpath(xpathStr));
			element.click();
			loggerUI.info(objectName + " --> Click Button With Text Input - PASS");
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			t_Status.add("True");
		} catch (Exception e) {
			loggerUI.info("--> Click Button With Text Input - FAIL");
			e.printStackTrace();
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
			t_Status.add("False");
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}

		}

	}
	
	public void verifyElementDisplayedWithInput(String objectName, String value, String taskCode, int row) throws Exception {
		try {
			String objectValue = UIOperations.getObjectValue(objectName);
			String[] objctVal = objectValue.split("~");
			String xpathStr = objctVal[0] + value + objctVal[1];
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStr)));
			element = driver.findElement(By.xpath(xpathStr));
			element.isDisplayed();
			loggerUI.info(objectName + " --> Verify Element Displayed With Input - PASS");
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			t_Status.add("True");
		} catch (Exception e) {
			loggerUI.info("--> Verify Element Displayed With Input - FAIL");
			e.printStackTrace();
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
			t_Status.add("False");
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}

		}

	}
	
	public void readDataFromExcelAndSetText(String objectName, String value, String taskCode, int row) throws Exception {
		try {
			element = driver.findElement(UIOperations.getObject(objectName));
			String[] valArr = value.split("_");
			Sheet dataSheet = objReadExcel.readSheetContents(valArr[0], valArr[1] , valArr[2]);
			Row dataRow = dataSheet.getRow(1);
			Cell dataCell = dataRow.getCell(0);
			String inputFromExcel = dataCell.getStringCellValue();
			element.sendKeys(inputFromExcel,Keys.ENTER);
			loggerUI.info(objectName + " --> Read Data From Excel And Set Text - PASS");
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			t_Status.add("True");
		} catch (Exception e) {
			loggerUI.info("--> Read Data From Excel And Set Text - FAIL");
			e.printStackTrace();
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
			t_Status.add("False");
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}

		}

	}
	
	public void setTextWithTextInput(String objectName, String value, String taskCode, int row) throws Exception {
		try {
			String objectValue = UIOperations.getObjectValue(objectName);
			String[] objctVal = objectValue.split("~");
			String[] valArr = value.split("_");
			String xpathStr = objctVal[0] + valArr[0] + objctVal[1];
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStr)));
			element = driver.findElement(By.xpath(xpathStr));
			element.sendKeys(valArr[1]);
			loggerUI.info(objectName + " --> Set Text With Text Input - PASS");
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			t_Status.add("True");
		} catch (Exception e) {
			loggerUI.info("--> Set Text With Text Input - FAIL");
			e.printStackTrace();
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
			t_Status.add("False");
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}

		}

	}
	
  public void selectDateFromPicker(String objectName, String value, String taskCode, int row) throws Exception{
	  try {

			try {
				Thread.sleep(3000);
				String yearMonth = driver
						.findElement(By.xpath("//font[@face='Verdana']/b"))
						.getText();
				String[] yearMonthArr = yearMonth.split(" ");
				String mnth =  yearMonthArr[0].substring(0, 3).toUpperCase();
				String fnlYearMonth = mnth+" "+yearMonthArr[1];
				Thread.sleep(2000);
				String dateArr[] = value.split("-");
				if(dateArr[2].length()==2)
				{
					String yearTwoDigit = yearMonthArr[1].substring(2);
					fnlYearMonth=mnth+" "+yearTwoDigit;
				}
				String yearMonthInput = dateArr[1] + " " + dateArr[2];
				// String[] yearMonthArr = yearMonth.split("/");
				while (!(yearMonthInput.equals(fnlYearMonth))) {
					driver.findElement(By.xpath("//a[contains(text(),'>')]")).click();
					Thread.sleep(2000);
					yearMonth = driver
							.findElement(By.xpath("//font[@face='Verdana']/b"))
							.getText();
					yearMonthArr = yearMonth.split(" ");
					mnth =  yearMonthArr[0].substring(0, 3).toUpperCase();
					fnlYearMonth = mnth+" "+yearMonthArr[1];
					if(dateArr[2].length()==2)
					{
						String yearTwoDigit = yearMonthArr[1].substring(2);
						fnlYearMonth=mnth+" "+yearTwoDigit;
					}
				}
				Thread.sleep(5000);
				WebElement ell = driver.findElement(By.xpath("//a[contains(@onclick,'"+value+"')]"));

				ell.click();
				//((JavascriptExecutor) driver).executeScript("arguments[0].click();", ell);
				// act.moveToElement(ell).click().build().perform();
			}

			catch (Exception e) {
				e.printStackTrace();
				t_Status.add("False");
				loggerUI.error(objectName + " --> Select Date from Picker - FAIL ---> Updated in the report");
				StringWriter strWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(strWriter));
				objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
			}

			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			loggerUI.info(objectName + " --> Select Date from Picker - PASS ---> Updated in the report");
			t_Status.add("True");
		} catch (Exception e) {
			e.printStackTrace();
			t_Status.add("False");
			loggerUI.error(objectName + " --> Select Date from Picker - FAIL ---> Updated in the report");
			StringWriter strWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(strWriter));
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", strWriter.toString());
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}
		}
  }
  
  public void selectAllDropDownValues(String objectName, String value, String taskCode, int row) throws Exception {
		try {
			element = driver.findElement(UIOperations.getObject(objectName));
			Select sel = new Select(element);
			List<WebElement> options = sel.getOptions();
			for(WebElement option:options)
			{
				option.click();
			}
			loggerUI.info(objectName + " --> Selected All the values from the drop down field - PASS");
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			t_Status.add("True");
		} catch (Exception e) {
			loggerUI.info("--> Select All Drop Down Values - FAIL");
			e.printStackTrace();
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
			t_Status.add("False");
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) {
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}

		}

	}
  
  public void clickCheckBoxesFromGridWithInput(String objectName, String value, String taskCode, int row)
			throws Exception 
	{
		try 
		{
			String[] valSpt = value.split("_");
			Map<String, Integer> counterMap = new HashMap<String, Integer>();
			List<WebElement> headerRows = driver.findElement(UIOperations.getObject(objectName))
					.findElement(By.tagName("thead")).findElements(By.tagName("tr"));
			int headerColumnCount = 1;
			for (WebElement headerRow : headerRows) 
			{
				List<WebElement> headerColumns = headerRow.findElements(By.tagName("td"));

				for (WebElement headerColumn : headerColumns) 
				{
					for (int i = 0; i < valSpt.length; i++) 
					{
						if (headerColumn.getText().equalsIgnoreCase(valSpt[i])) 
						{
							counterMap.put(valSpt[i], headerColumnCount);
						}
					}

					headerColumnCount++;
				}
			}

			List<WebElement> bodyRows = driver.findElement(UIOperations.getObject(objectName))
					.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

			int bodyRowCount = 1;
			boolean searchFlag = false;
			boolean plusIconIdentification = false;
			for (WebElement bodyRow : bodyRows) 
			{
				List<WebElement> bodyColumns = bodyRow.findElements(By.tagName("td"));
				int bodyColumnCount = 1;
				for (WebElement bodyColumn : bodyColumns) 
				{
					if (bodyColumnCount == counterMap.get(valSpt[0])) 
					{
						String propertyType = null;
						try 
						{
							propertyType = bodyColumn.getText();
						} 
						catch (Exception e) 
						{
							System.out.println("Exception while getting the Property Type Text");
						}
						if (propertyType != null && propertyType != "") 
						{
							if (propertyType.equals(valSpt[3])) 
							{
								searchFlag = true;
							}
						}
					}

					if (searchFlag) 
					{
							if (bodyColumnCount == counterMap.get(valSpt[1])) 
							{
								if (valSpt[4].equalsIgnoreCase("Send To RA-Yes")) 
								{
									WebElement sendToRA = driver
											.findElement(By.xpath("//div[@id='facilitytable2']/table/tbody/tr["
													+ bodyRowCount + "]/td[" + counterMap.get(valSpt[1]) + "]/input"));
									sendToRA.click();
									Thread.sleep(3000);
								}

								if (valSpt[5].equalsIgnoreCase("Recommend Re-trigger-Yes")) 
								{
									WebElement recommendRetrigger = driver
											.findElement(By.xpath("//div[@id='facilitytable2']/table/tbody/tr["
													+ bodyRowCount + "]/td[" + counterMap.get(valSpt[2]) + "]/button"));
									recommendRetrigger.click();
									Thread.sleep(3000);
								}
								plusIconIdentification = true;
							}

					}
					bodyColumnCount++;

				}
				if (plusIconIdentification) 
				{
					break;
				}
				bodyRowCount++;

			}
			loggerUI.info(objectName + " --> Click Check Boxes From Grid With Input - PASS");
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "P", "");
			t_Status.add("True");
		} 
		catch (Exception e) 
		{
			loggerUI.info("Click Check Boxes From Grid With Input - Fail");
			e.printStackTrace();
			objUpdateResult.writeOutputWorkBook(Reportpath, taskCode, row, "Error", e.toString());
			t_Status.add("False");
			if (Fail_SnapShot.equalsIgnoreCase("TRUE") && !(Pass_SnapShot.equalsIgnoreCase("TRUE"))) 
			{
				getScreenShot(objExecuteTest.evidencePath, fileName_E);
			}

		}
	}

}
